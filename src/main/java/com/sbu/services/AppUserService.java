package com.sbu.services;

import com.sbu.data.AppUserRepository;
import com.sbu.data.entitys.AppUser;
import com.sbu.main.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    public Iterable<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public Object getUserByUsername(String username) {
        return appUserRepository.findOne(username);
    }

    public AppUser createAppUser(AppUser user, InMemoryUserDetailsManager inMemoryUserDetailsManager, String role) {
        List<GrantedAuthority> roles = new ArrayList<>();
        if(role.equals("ROLE_ADMIN")) {
            roles.add(Constants.ROLE_ADMIN);
            user.setRole("ROLE_ADMIN");
        }
        else {
            roles.add(Constants.ROLE_USER);
            user.setRole("ROLE_USER");
        }
        inMemoryUserDetailsManager.createUser(new User(user.getUsername(), user.getUser_password(), roles));
        return appUserRepository.save(user);
    }

    public AppUser editUser(AppUser user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        AppUser old_app_user = (AppUser) getUserByUsername(user.getUsername());
        removeUser(old_app_user, inMemoryUserDetailsManager);
        return createAppUser(user, inMemoryUserDetailsManager, old_app_user.getRole());
    }

    public void removeUser(AppUser user, InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        inMemoryUserDetailsManager.deleteUser(user.getUsername());
        appUserRepository.delete(user.getUsername());
    }
}
