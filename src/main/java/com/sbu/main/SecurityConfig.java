package com.sbu.main;

import com.sbu.data.entitys.AppUser;
import com.sbu.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Properties;

@CrossOrigin
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AppUserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                //Protects all requests from unauthorized users
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/algo").permitAll()
                .antMatchers("/state/**").permitAll()
                .antMatchers("/post/all").permitAll()
                .antMatchers("/post/id/{id}").permitAll()
                .antMatchers("/user").permitAll()
                .anyRequest().authenticated()
                //Allows HTTP Basic auth
                .and().httpBasic()
                .and().headers()
                .and().logout().disable()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        Iterable<AppUser> appUsers = userService.getAllUsers();
        for (AppUser appUser : appUsers) {
            users.put(appUser.getUsername(), appUser.getUser_password() + ',' + appUser.getRole() + ",enabled");
        }
        users.put("rama", "pass,ROLE_ADMIN,enabled");
        users.put("ethan", "pass,ROLE_ADMIN,enabled");
        users.put("rahul", "pass,ROLE_ADMIN,enabled");
        users.put("nick", "pass,ROLE_ADMIN,enabled");
        users.put("g", "pass,ROLE_USER,enabled");
        return new InMemoryUserDetailsManager(users);
    }
}
