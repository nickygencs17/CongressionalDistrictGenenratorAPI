package com.sbu.data.entitys;


public class AppUser {

    String username;
    String user_password;
    String first_name;
    String last_name;
    String city;
    String state_id;
    String address;
    int zip;

    public AppUser() {
    }

    public AppUser(String username, String user_password, String first_name, String last_name, String city, String state_id, String address, int zip) {
        this.username = username;
        this.user_password = user_password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.city = city;
        this.state_id = state_id;
        this.address = address;
        this.zip = zip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }



}
