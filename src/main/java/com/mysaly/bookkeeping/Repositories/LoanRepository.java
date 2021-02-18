package com.mysaly.bookkeeping.Repositories;

import com.mysaly.bookkeeping.Model.Loan;
import org.springframework.data.repository.CrudRepository;

public interface LoanRepository extends CrudRepository<Loan, String> {
}
