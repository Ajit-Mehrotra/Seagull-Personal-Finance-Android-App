package com.example.seagull;

public class Bank {
    private String ID;
private String name;
private String phone;
private String address;
private String website;



    Bank(String ID){
        this.ID = ID;
    }


    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getID() {
        return ID;
    }
}
