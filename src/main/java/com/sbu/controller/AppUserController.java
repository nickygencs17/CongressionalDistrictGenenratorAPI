package com.sbu.controller;


import com.sbu.data.entitys.AppUser;
import com.sbu.exceptions.BadRequestException;
import com.sbu.services.AppUserService;
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

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build201;
import static com.sbu.utils.ResponseUtil.build409;


@RestController
@CrossOrigin
@RequestMapping("/user")
public class AppUserController {


    private static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");

    @Autowired
    AppUserService appUserService;
    @Autowired
    private final InMemoryUserDetailsManager userManager;

    @Autowired
    public AppUserController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userManager = inMemoryUserDetailsManager;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    public Response postUser(@RequestBody AppUser user) throws BadRequestException {

        if (userManager.userExists(user.getUsername())) {
            return build409();
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(ROLE_USER);
        userManager.createUser(new User(user.getUsername(), user.getUser_password(), roles));

        return build201(user.getUsername());
    }


    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    Response getUserByUsername(@PathVariable(value="username") String username){

        return build200(appUserService.getUserByUsername(username));
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    Response getAllUsers(){
        return build200(appUserService.getAllUsers());
    }


    @RequestMapping(value = "/edit",method = RequestMethod.PUT)
    Response putEditUser(@RequestBody AppUser user){

        return build200("Test");
    }

    @RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
    Response deleteUser(@PathVariable(value="username") String username){

        return build200("Test");
    }

    @RequestMapping(value = "/vd/{username}",method = RequestMethod.GET)
    Response getVotingDistrictByUsername(@PathVariable(value="username") String username){

        return build200("Test");
    }

}
