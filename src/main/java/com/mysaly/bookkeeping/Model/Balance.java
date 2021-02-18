package com.mysaly.bookkeeping.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name="balances")
public class Balance {
    @Id
    @Getter @Setter
    private String name;

    @Getter @Setter
    private BigDecimal value;
}
