package com.microwaveteam.quarantinecoffee.models;

public class User {

    private String UserName;
    private String Password;
    private String FullName;
    private String DateOfBird;
    private String role;
    private String isMale;


    public User(){
        //default
    }


    public String getDateOfBird() {
        return DateOfBird;
    }

    public void setDateOfBird(String dateOfBird) {
        DateOfBird = dateOfBird;
    }

    public String getIsMale() {
        return isMale;
    }

    public void setIsMale(String isMale) {
        this.isMale = isMale;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(String userName, String password, String fullName, String DOB, String _role, String ismale) {
        UserName = userName;
        Password = password;
        FullName = fullName;
        DateOfBird = DOB;
        role = _role;
        isMale = ismale;
    }

    public String getUserName() {
        return UserName;
    }


}
