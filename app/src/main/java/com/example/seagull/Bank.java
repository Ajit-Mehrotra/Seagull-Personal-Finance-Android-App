package com.example.seagull;

public class Bank {
    //ATTRIBUTES
    private String ID;
    private String name;
    private String phone;
    private String address;
    private String website;

    //CONSTRUCTOR
    Bank(String ID){
        this.ID = ID;
    }

    //ACCESSORS
    public String getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getAddress() {
        return address;
    }
    public String getWebsite() {
        return website;
    }

    //MUTATORS
    public void setID(String ID) {this.ID = ID;}
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setAddress(String address) {this.address = address;}
    public void setWebsite(String website) {this.website = website;}

}
