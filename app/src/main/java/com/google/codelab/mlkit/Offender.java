package com.google.codelab.mlkit;

public class Offender {
    private int of_id;
    private String of_fname;
    private String of_lname;
    private String of_carnum;
    private String of_state;
    private String of_numplate;  //this is the whole value
    private String of_mail;

    public Offender(int of_id, String of_fname, String of_lname, String of_carnum, String of_state, String of_numplate, String of_mail) {
        this.of_id = of_id;
        this.of_fname = of_fname;
        this.of_lname = of_lname;
        this.of_carnum = of_carnum;
        this.of_state = of_state;
        this.of_numplate = of_numplate;
        this.of_mail = of_mail;

    }

    public Offender() {
    }

    public Offender(String of_fname, String of_lname, String of_carnum, String of_state, String of_numplate, String of_mail) {
        this.of_fname = of_fname;
        this.of_lname = of_lname;
        this.of_carnum = of_carnum;
        this.of_state = of_state;
        this.of_numplate = of_numplate;
        this.of_mail = of_mail;
    }

    public int getOf_id() {
        return of_id;
    }

    public void setOf_id(int of_id) {
        this.of_id = of_id;
    }

    public String getOf_fname() {
        return of_fname;
    }

    public void setOf_fname(String of_fname) {
        this.of_fname = of_fname;
    }

    public String getOf_lname() {
        return of_lname;
    }

    public void setOf_lname(String of_lname) {
        this.of_lname = of_lname;
    }

    public String getOf_carnum() {
        return of_carnum;
    }

    public void setOf_carnum(String of_carnum) {
        this.of_carnum = of_carnum;
    }

    public String getOf_state() {
        return of_state;
    }

    public void setOf_state(String of_state) {
        this.of_state = of_state;
    }

    public String getOf_numplate() {
        return of_numplate;
    }

    public void setOf_numplate(String of_numplate) {
        this.of_numplate = of_numplate;
    }

    public String getOf_mail() {
        return of_mail;
    }

    public void setOf_mail(String of_mail) {
        this.of_mail = of_mail;
    }

}
