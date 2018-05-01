package com.sbu.controller;

import com.sbu.exceptions.UnauthorizedException;
import com.sbu.main.Constants;
import com.sbu.services.AppUserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@SuppressWarnings("Duplicates")
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private final InMemoryUserDetailsManager userManager;
    @Autowired
    AppUserService appUserService;

    @Autowired
    public LoginController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userManager = inMemoryUserDetailsManager;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Response login() throws UnauthorizedException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //Auth handled in SecurityConfig we can just return 200 if we got this far
        Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> roleNames = new ArrayList<>();
        roles.forEach((GrantedAuthority ga) -> roleNames.add(ga.getAuthority()));
        System.out.println("Attempting to login user: " + username + " with roles: " + roleNames);
        JSONObject response = new JSONObject();
        response.put(Constants.LOGIN_SUCCESS, true);
        response.put(Constants.MESSAGE, Constants.LOGIN_SUCESS_MESSAGE);
        response.put(Constants.ROLES, roleNames);
        return build200(response);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        appUserService.removeStateforUser(username);
        return build200(username);
    }
}
