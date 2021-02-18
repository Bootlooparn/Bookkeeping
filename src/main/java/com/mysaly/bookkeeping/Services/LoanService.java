package com.mysaly.bookkeeping.Services;

import com.mysaly.bookkeeping.Enum.Payment;
import com.mysaly.bookkeeping.Model.Loan;
import com.mysaly.bookkeeping.Repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private BalanceService balanceService;
    Logger log = Logger.getLogger(LoanService.class.getName());

    @Async("asyncExecutor")
    public void createLoan(String loanname, String lender, String borrower, BigDecimal principal) {
        if (!loanRepository.existsById(loanname)) {
            log.info("Creating loan");

            CompletableFuture.runAsync(() -> {
                balanceService.adjustBalance(lender, principal, Payment.SUBSTRACT);
                balanceService.adjustBalance(borrower, principal, Payment.ADD);
            });

            Loan loan = new Loan();
            loan.setName(loanname);
            loan.setLender(lender);
            loan.setBorrower(borrower);
            loan.setPrincipal(principal);
            loan.setRemainder(principal);
            loan.setOpen(true);
            loanRepository.save(loan);
        } else {
            log.info("The loan already exists please create a new loan under a new loan name");
        }
    }

    @Async("asyncExecutor")
    public void repay(String loanname, BigDecimal value) {
        if(loanRepository.existsById(loanname)) {
            Loan loan = loanRepository.findById(loanname).get();
            if (loan.getRemainder().doubleValue() - value.doubleValue() > 0) {
                log.info(Thread.currentThread().getName() +  " Adjusting loan");
                loan.setRepayments(loan.getRepayments() + 1);
                loan.setRemainder(loan.getRemainder().subtract(value));
                loanRepository.save(loan);

                log.info("Adjusting balance");


                balanceService.adjustBalance(loan.getLender(), value, Payment.ADD);
                balanceService.adjustBalance(loan.getBorrower(), value, Payment.SUBSTRACT);

            } else {
                log.info("Pay value exceeds the remainder");
            }
        } else {
            log.info("No such loan exists");
        }
    }

    @Async("asyncExecutor")
    public CompletableFuture<ArrayList<Loan>> getLoans() {
        Iterable<Loan> iterableLoans = loanRepository.findAll();
        ArrayList<Loan> loans = new ArrayList<>();
        iterableLoans.forEach(loans::add);

        return CompletableFuture.completedFuture(loans);
    }
}
