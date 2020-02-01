package com.example.crimereporter;

public class User {

    private String name,username, address, email, type;
    private int mobile;

    public User(String name, String username, String address, String email, int mobile, String type) {
        this.name = name;
        this.username = username;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public int getMobile() {
        return mobile;
    }

    public String getType() {
        return type;
    }
}