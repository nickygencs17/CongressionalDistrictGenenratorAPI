package com.sbu.main;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by Nicholas Genco on 3/1/17.
 */
public class Constants {


    public static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    public static final String ROLE_ADMIN = "[ROLE_ADMIN]";

    public static final String STATE_CONSTANT = "state",
    USER_NOT_FOUND = "user not found",
    USER_EXISTS = "user exists",
    CONGRESS = "CONGRESS",
    STATE = "STATE",
    UPPER = "UPPER",
    LOWER = "LOWER",
    POST = "POST",
    COMMENTS = "COMMENTS",
    POST_NOT_FOUND= "POST_NOT_FOUND";

}
