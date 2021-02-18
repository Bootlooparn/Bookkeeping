package com.mysaly.bookkeeping.Controllers;

import com.mysaly.bookkeeping.Model.Loan;
import com.mysaly.bookkeeping.Services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/loans/{loanname}/{lender}/{borrower}/{principal}")
    public void loan(@PathVariable String loanname, @PathVariable String lender, @PathVariable String borrower, @PathVariable BigDecimal principal) {
        loanService.createLoan(loanname, lender, borrower, principal);
    }

    @PostMapping("/loans/{loanname}/repay/{value}")
    public void repay(@PathVariable String loanname, @PathVariable BigDecimal value) throws InterruptedException {
        loanService.repay(loanname, value);
    }

    @GetMapping("/loans")
    public ArrayList<Loan> getLoans() throws ExecutionException, InterruptedException {
        CompletableFuture<ArrayList<Loan>> loans = loanService.getLoans();

        return loans.get();
    }
}
