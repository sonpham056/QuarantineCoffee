package com.microwaveteam.quarantinecoffee.models;

public class User {

    private String userName;
    private String password;
    private String fullName;
    private String role;
    private String isMale;


    public User(){
        //default
    }

    public String getIsMale() {
        return isMale;
    }

    public void setIsMale(String isMale) {
        this.isMale = isMale;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String userName, String password, String fullName, String role, String isMale) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.isMale = isMale;
    }

    public User(String userName, String fullName, String role, String ismale) {
        this.userName = userName;
        this.fullName = fullName;
        this.role = role;
        isMale = ismale;
    }




}
