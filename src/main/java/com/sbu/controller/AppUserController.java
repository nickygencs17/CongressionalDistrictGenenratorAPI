package com.sbu.controller;
import com.sbu.data.entitys.AppUser;
import com.sbu.exceptions.BadRequestException;
import com.sbu.main.Constants;
import com.sbu.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.*;
import static com.sbu.utils.Utils.checkIfUserIsAdmin;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class AppUserController {

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
        if(user.getState_id().length()>2){
            return build400(Constants.STATE_ID_LENGTH_GREATER_THAN_TWO);
        }
        if (checkIfUserExists(user)) {
            return build409();
        }
        appUserService.createAppUser(user,userManager);
        return build201(user.getUsername());
    }

    @RequestMapping(value = "/{username}",method = RequestMethod.GET)
    Response getUserByUsername(@PathVariable(value="username") String username) {
        if (handleAdminCall()) {
            return build401();
        }
        return build200(appUserService.getUserByUsername(username));
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    Response getAllUsers() {
        if (handleAdminCall()) {
            return build401();
        }
        return build200(appUserService.getAllUsers());
    }

    @RequestMapping(value = "/edit",method = RequestMethod.PUT)
    Response putEditUser(@RequestBody AppUser user){
        if (handleAdminCall()) {
            return build401();
        }
        if(user.getState_id().length()>2){
            return build400(Constants.STATE_ID_LENGTH_GREATER_THAN_TWO);
        }
        if (!checkIfUserExists(user)) {
            return build404(Constants.USER_NOT_FOUND);
        }
        return build200(appUserService.editUser(user,userManager));
    }

    @RequestMapping(value = "/{username}",method = RequestMethod.DELETE)
    Response deleteUser(@PathVariable(value="username") String username){
        if (handleAdminCall()) return build401();
        if(!userManager.userExists(username)){
            return build404(Constants.USER_NOT_FOUND);
        }
        AppUser appUser = (AppUser) appUserService.getUserByUsername(username);
        appUserService.removeUser(appUser,userManager);
        return build204();
    }

    @RequestMapping(value = "/vd/{username}",method = RequestMethod.GET)
    Response getVotingDistrictByUsername(@PathVariable(value="username") String username){
        //TODO: HOW DO WE EVEN DO THIS?
        return build200("Test");
    }

    private boolean handleAdminCall() {
        String requesting_username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!checkIfUserIsAdmin(requesting_username,userManager)){
            return true;
        }
        return false;
    }

    private boolean checkIfUserExists(AppUser user) {
        if (userManager.userExists(user.getUsername())) {
            return true;
        }
        return false;
    }

}
