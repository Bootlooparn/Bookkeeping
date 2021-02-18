package com.mysaly.bookkeeping.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="loans")
public class Loan {
    @Id
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String lender;

    @Getter @Setter
    private String borrower;

    @Getter @Setter
    private BigDecimal principal;

    @Getter @Setter
    private BigDecimal remainder;

    @Getter @Setter
    private int repayments;

    @Getter @Setter
    @Column(columnDefinition = "boolean default true")
    private Boolean open;
}
