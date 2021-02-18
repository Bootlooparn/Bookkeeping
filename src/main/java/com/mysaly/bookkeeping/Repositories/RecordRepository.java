package com.mysaly.bookkeeping.Repositories;

import com.mysaly.bookkeeping.Model.Record;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface RecordRepository extends CrudRepository<Record, Integer> {
    List<Record> findByDate(Date date);

    List<Record> findByName(String name);

    List<Record> findByDateAndName(Date date, String name);

    @Query(value = "SELECT DISTINCT NAME FROM records", nativeQuery = true)
    List<String> getDistinctNames();

    boolean existsByDateAndName(Date date, String name);
}
