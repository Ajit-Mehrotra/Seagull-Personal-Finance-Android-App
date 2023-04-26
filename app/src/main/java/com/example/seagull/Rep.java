package com.example.seagull;

public class Rep {
    private int id;
    private String name;
    private int bankId;
    private String email;
    private int phone;

    // Constructor
    public Rep(int id, String name, int bankId,String email,int phone) {
        this.id = id;
        this.name = name;
        this.bankId = bankId;
        this.email = email;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBankId() {
        return bankId;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}