package com.citi.ap.citiapp.model;

/**
 * Created by emi91_000 on 06/03/2015.
 */
public class Account {
    private String id;
    private String prodDesc;
    private String accountNumber;

    public Account(String id, String prodDesc, String accountNumber) {
        this.id = id;
        this.prodDesc = prodDesc;
        this.accountNumber = accountNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
