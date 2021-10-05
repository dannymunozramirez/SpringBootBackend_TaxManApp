package com.iki.taxmanagement.react.spring.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Transaction {

    public String document_id;
    // @Column(name="type")
    public String type;
    // @Column(name="title")
    public String title;
    // @Column(name="date")
    public String date;
    // @Column(name="description")
    public String description;
    // @Column(name="amount")
    public long amount;

}
