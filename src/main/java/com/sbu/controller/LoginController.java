package com.sbu.controller;

import com.sbu.data.entitys.AppUser;
import com.sbu.exceptions.UnauthorizedException;
import com.sbu.main.Constants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build404;

/**
 * Created by ngenco .
 */

@RestController
@CrossOrigin
public class LoginController {

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private final InMemoryUserDetailsManager userManager;


    @Autowired
    public LoginController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userManager = inMemoryUserDetailsManager;
    }

    @RequestMapping(value= "/login", method = RequestMethod.GET)
    public Response login () throws UnauthorizedException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //Auth handled in SecurityConfig we can just return 200 if we got this far
        Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<String> roleNames = new ArrayList<>();
        roles.forEach((GrantedAuthority ga) -> roleNames.add(ga.getAuthority()));
        System.out.println("Attempting to login user: " + username + " with roles: " + roleNames);

        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("message", "Login Successful");
        response.put("roles", roleNames);

        return build200(response);

    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response logout(@RequestBody @Valid AppUser user){
        if(!userManager.userExists(user.getUsername())){
            return build404(Constants.USER_NOT_FOUND);
        }
        return Response.ok().build();
    }
}
