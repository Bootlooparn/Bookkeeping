package com.mysaly.bookkeeping.Model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

public class BalanceChange {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private Date startDate;

    @Getter @Setter
    private Date endDate;

    @Getter @Setter
    private BigDecimal diff;
}
