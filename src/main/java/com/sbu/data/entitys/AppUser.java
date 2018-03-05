package com.sbu.data.entitys;


public class AppUser {

    String username;
    String password;

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public AppUser() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
