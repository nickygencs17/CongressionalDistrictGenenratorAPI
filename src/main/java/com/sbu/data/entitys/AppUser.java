package com.sbu.data.entitys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @NotNull
    String username;

    @NotNull
    String user_password;

    @NotNull
    String first_name;

    @NotNull
    String last_name;

    @NotNull
    String city;

    @NotNull
    String state_id;

    @NotNull
    String address;

    @NotNull
    int zip;

    @NotNull
    String role;

    @NotNull
    float population_coefficient;

    @NotNull
    float fairness_coefficient;

    @NotNull
    float compactness_coefficient;


    public AppUser(String username, String user_password, String first_name, String last_name,
                   String city, String state_id, String address, int zip, String role,
                   float population_coefficient, float fairness_coefficient, float compactness_coefficient) {
        this.username = username;
        this.user_password = user_password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.city = city;
        this.state_id = state_id;
        this.address = address;
        this.zip = zip;
        this.role = role;
        this.population_coefficient = population_coefficient;
        this.fairness_coefficient = fairness_coefficient;
        this.compactness_coefficient = compactness_coefficient;
    }

    public AppUser() {
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public float getPopulation_coefficient() {
        return population_coefficient;
    }

    public void setPopulation_coefficient(float population_coefficient) {
        this.population_coefficient = population_coefficient;
    }

    public float getFairness_coefficient() {
        return fairness_coefficient;
    }

    public void setFairness_coefficient(float fairness_coefficient) {
        this.fairness_coefficient = fairness_coefficient;
    }

    public float getCompactness_coefficient() {
        return compactness_coefficient;
    }

    public void setCompactness_coefficient(float compactness_coefficient) {
        this.compactness_coefficient = compactness_coefficient;
    }
}
