package com.sbu.services;

import com.sbu.data.entitys.AppUser;
import com.sbu.exceptions.UnauthorizedException;
import com.sbu.main.Constants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build404;

/**
 * Created by ngenco .
 */

@RestController
@CrossOrigin
public class LoginService {

    private static final Logger logger = Logger.getLogger(LoginService.class.getName());
    private final InMemoryUserDetailsManager userManager;


    @Autowired
    public LoginService(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userManager = inMemoryUserDetailsManager;
    }

    @RequestMapping(value= "/login", method = RequestMethod.POST)
    public Response login (@RequestBody @Valid AppUser user) throws UnauthorizedException {
        String current_password;

        if(userManager.userExists(user.getUsername())){
            current_password = userManager.loadUserByUsername(user.getUsername()).getPassword();
        }
        else{
            return build404(Constants.USER_NOT_FOUND);

        }
        if(!current_password.equals(user.getPassword())){
            throw new UnauthorizedException();

        }

        JSONObject response = new JSONObject();
        response.put("success", true);
        response.put("message", "Login Successful");
        response.put("roles",userManager.loadUserByUsername(user.getUsername()).getAuthorities());

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
