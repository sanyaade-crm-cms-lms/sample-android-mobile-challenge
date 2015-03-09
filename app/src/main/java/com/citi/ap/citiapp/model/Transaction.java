package com.citi.ap.citiapp.model;

import java.util.Date;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class Transaction {
    private String id;
    private Date pendingDate;
    private String transactionType;
    private String description;
    private String activity;

    public Transaction(String id, Date pendingDate, String transactionType, String description, String activity) {
        this.id = id;
        this.pendingDate = pendingDate;
        this.transactionType = transactionType;
        this.description = description;
        this.activity = activity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPendingDate() {
        return pendingDate;
    }

    public void setPendingDate(Date pendingDate) {
        this.pendingDate = pendingDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }
}
