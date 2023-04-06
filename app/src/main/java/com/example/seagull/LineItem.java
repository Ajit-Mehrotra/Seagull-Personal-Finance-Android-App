package com.example.seagull;

import java.util.Date;

public class LineItem {
    private double amount;
    private String title;
    private Date date;
    private String category;


    public LineItem(double amount, String title, Date date, String category) {
        this.amount = amount;
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
