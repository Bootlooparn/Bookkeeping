package com.mysaly.bookkeeping.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LoanId implements Serializable {
    @Getter @Setter
    private String lender;

    @Getter @Setter
    private String borrower;

    @Getter @Setter
    private String name;
}
