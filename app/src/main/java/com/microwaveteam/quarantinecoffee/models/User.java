package com.microwaveteam.quarantinecoffee.models;

public class User {
    public String UserName;
    public String Password;
    public String FullName;
    public String DOB;
    public String role;
    Boolean Gender;

    public User(){
        //default
    }
    public User(String userName, String password, String fullName, String DOB, String role) {
        UserName = userName;
        Password = password;
        FullName = fullName;
        this.DOB = DOB;
        this.role = role;
    }
}
