package com.vvpcollege.vvp.placementappfinal;

public class Modal {

    String name, location, domain, domain1, location1, date, enroll, sname;
    String lflag, yesCompany;

    public String getName() {
        return name;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYesCompany() {
        return yesCompany;
    }

    public void setYesCompany(String yesCompany) {
        this.yesCompany = yesCompany;
    }

    public Modal() {

    }

    public String getLflag() {
        return lflag;
    }

    public void setLflag(String lflag) {
        this.lflag = lflag;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getDomain1() {

        return domain1;

    }

    public void setDomain1(String domain1) {
        this.domain1 = domain1;
    }

    public Modal(String name, String location, String domain, String domain1, String location1, String date, String lflag, String yesCompany, String enroll, String sname) {
        this.name = name;
        this.location = location;
        this.domain = domain;
        this.domain1 = domain1;
        this.location1 = location1;
        this.date = date;
        this.lflag = lflag;
        this.yesCompany = yesCompany;
        this.enroll = enroll;
        this.sname = sname;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


}
