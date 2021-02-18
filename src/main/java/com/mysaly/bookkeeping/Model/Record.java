package com.mysaly.bookkeeping.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "records")
public class Record {
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Date date;

    @Getter @Setter
    private BigDecimal value;

    @Getter @Setter
    private String action;
}
