package com.sbu.utils;


import com.sbu.main.Constants;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by Nicholas Genco on 3/1/17.
 * utils will go here
 */
public class Utils {


    public static boolean checkIfUserIsAdmin(String requesting_username, InMemoryUserDetailsManager inMemoryUserDetailsManager) {

        if(inMemoryUserDetailsManager.loadUserByUsername(requesting_username).getAuthorities() == Constants.ROLE_ADMIN){
            return true;
        }
        return false;
    }




}
