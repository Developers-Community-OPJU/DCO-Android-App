package com.android.dcoapp.model;

public class SignupRequest {
    private String firstName;
    private String lastName;
    private String branch;
    private String username;
    private String password;
    private String email;

    public SignupRequest(String firstName, String lastName, String branch, String username, String password, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.branch = branch;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBranch() {
        return branch;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
