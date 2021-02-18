package com.mysaly.bookkeeping.Repositories;

import com.mysaly.bookkeeping.Model.Balance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, String> {
}
