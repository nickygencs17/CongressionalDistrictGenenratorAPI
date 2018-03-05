package com.sbu.services;


import com.sbu.data.entitys.AppUser;
import com.sbu.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static com.sbu.utils.ResponseUtil.build201;
import static com.sbu.utils.ResponseUtil.build409;


@RestController
@CrossOrigin
@RequestMapping("/storage")
public class UserService {


    private static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");

    @Autowired
    private final InMemoryUserDetailsManager userManager;

    @Autowired
    public UserService(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userManager = inMemoryUserDetailsManager;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Response addCustomer(@RequestBody AppUser user) throws BadRequestException {

        if(userManager.userExists(user.getUsername())){
            return build409();
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(ROLE_USER);
        userManager.createUser(new User(user.getUsername(),user.getPassword(), roles));

        return build201(user.getUsername());
    }
}
