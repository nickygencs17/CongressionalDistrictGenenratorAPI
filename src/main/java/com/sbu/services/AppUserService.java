package com.sbu.services;

import com.sbu.data.AppUserRepository;
import com.sbu.data.entitys.AppUser;
import com.sbu.data.entitys.UsState;
import com.sbu.main.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    HashMap<String, UsState> userStateMap = new HashMap<>();

    public Iterable<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public Object getUserByUsername(String username) {
        return appUserRepository.findOne(username);
    }

    public AppUser createAppUser(AppUser user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(Constants.ROLE_USER);
        inMemoryUserDetailsManager.createUser(new User(user.getUsername(), user.getUser_password(), roles));
        return appUserRepository.save(user);
    }

    public AppUser editUser(AppUser user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        AppUser old_app_user = (AppUser) getUserByUsername(user.getUsername());
        removeUser(old_app_user, inMemoryUserDetailsManager);
        return createAppUser(user, inMemoryUserDetailsManager);
    }

    public void removeUser(AppUser user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        inMemoryUserDetailsManager.deleteUser(user.getUsername());
        appUserRepository.delete(user.getUsername());
    }

    public void addStateforUser(String username, UsState selectedstate) {
        this.userStateMap.put(username, selectedstate);
    }

    public UsState getStateforUser(String username, String stateName) {
        if (!userStateMap.containsKey(username)) return null;
        UsState selectedState = userStateMap.get(username);
        if (selectedState.getState_id().equals(stateName)) {
            return selectedState;
        }
        return null;
    }

    public void removeStateforUser(String username) {
        if (userStateMap.containsKey(username)) {
            userStateMap.remove(username);
        }
    }
}
