package com.example.seagull;

public class BankRep {
    private int id;
    private String bankName;
    private String website;
    private String repName;
    private String email;
    private int phone;

    public BankRep(String bn, String w,String rn,String e,int p) {
        super();
        this.bankName = bn;
        this.website = w;
        this.repName = rn;
        this.email=e;
        this.phone=p;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankName() {
        return bankName;
    }

    public String getWebsite() {
        return website;
    }
    public String getRepName() {
        return repName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

}
