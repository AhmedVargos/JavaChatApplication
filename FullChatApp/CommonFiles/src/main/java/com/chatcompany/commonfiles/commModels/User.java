package com.chatcompany.commonfiles.commModels;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String username;
    private String email;
    private String fname;
    private String lname;
    private String password;
    private int gender;
    private String country;
    private int connStatus;
    private int appearanceStatus;

    public User() {
    }

    public User(int id, String username, String email, String fname, String lname, String password, int gender, String country, int connStatus, int appearanceStatus) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.connStatus = connStatus;
        this.appearanceStatus = appearanceStatus;
    }

    public User(String username, String email, String fname, String lname, String password, int gender, String country, int connStatus, int appearanceStatus) {
        this.username = username;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.gender = gender;
        this.country = country;
        this.connStatus = connStatus;
        this.appearanceStatus = appearanceStatus;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getConnStatus() {
        return connStatus;
    }

    public void setConnStatus(int connStatus) {
        this.connStatus = connStatus;
    }

    public int getAppearanceStatus() {
        return appearanceStatus;
    }

    public void setAppearanceStatus(int AppearanceStatus) {
        this.appearanceStatus = AppearanceStatus;
    }

}
