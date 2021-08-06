package com.google.codelab.mlkit;

public class User {
    private int id;
    private int offID;
    private String Fname;
    private String Lname;
    private String emailId;
    private String pwd;
    private String secQue;
    private int secQueID;

    public User(int id, int offID, String fname, String lname, String emailId, String pwd, String secQue, int secQueID) {
        this.id = id;
        this.offID = offID;
        Fname = fname;
        Lname = lname;
        this.emailId = emailId;
        this.pwd = pwd;
        this.secQue = secQue;
        this.secQueID = secQueID;
    }

    public User(int offID, String fname, String lname, String emailId, String pwd, String secQue, int secQueID) {
        this.offID = offID;
        Fname = fname;
        Lname = lname;
        this.emailId = emailId;
        this.pwd = pwd;
        this.secQue = secQue;
        this.secQueID = secQueID;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffID() {
        return offID;
    }

    public void setOffID(int offID) {
        this.offID = offID;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSecQue() {
        return secQue;
    }

    public void setSecQue(String secQue) {
        this.secQue = secQue;
    }

    public int getSecQueID() {
        return secQueID;
    }

    public void setSecQueID(int secQueID) {
        this.secQueID = secQueID;
    }
}
