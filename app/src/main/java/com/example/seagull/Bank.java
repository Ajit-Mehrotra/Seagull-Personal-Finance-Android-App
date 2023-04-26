package com.example.seagull;

public class Bank {
    private int id;
    private String name;
    private String website;

    // Constructor
    public Bank(int id, String n, String w) {
        this.id = id;
        this.name = n;
        this.website = w;
    }
    public Bank(String n, String w) {
        super();
        this.name = n;
        this.website = w;
    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}