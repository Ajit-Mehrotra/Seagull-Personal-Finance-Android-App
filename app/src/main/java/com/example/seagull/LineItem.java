package com.example.seagull;

import java.util.Date;

public class LineItem {
    //ATTRIBUTES
    private double amount;
    private String title;
    private Date date;
    //CONSTRUCTOR
    public LineItem(double amount, String title, Date date) {
        this.amount = amount;
        this.title = title;
        this.date = date;
    }

    //ACCESSORS
    public String getTitle() {
        return title;
    }
    public double getAmount() {
        return amount;
    }
    public Date getDate() {
        return date;
    }

    //MUTATORS
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}

