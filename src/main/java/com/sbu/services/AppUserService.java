package com.sbu.services;


import com.sbu.data.AppUserRepository;
import com.sbu.data.entitys.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
