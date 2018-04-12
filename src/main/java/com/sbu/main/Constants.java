package com.sbu.main;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by Nicholas Genco on 3/1/17.
 */
public class Constants {


    public static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    public static final GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    public static final String ROLE_ADMIN_STRING = "[ROLE_ADMIN]";

    public static final String
    USER_NOT_FOUND = "USER_NOT_FOUND",
    USER_EXISTS = "USER_EXISTS",
    CONGRESS = "CONGRESS",
    STATE = "STATE",
    UPPER = "UPPER",
    LOWER = "LOWER",
    POST = "POST",
    COMMENTS = "COMMENTS",
    POST_NOT_FOUND= "POST_NOT_FOUND",
    STATE_ID_LENGTH_GREATER_THAN_TWO = "STATE_ID_LENGTH_GREATER_THAN_TWO ",
    NOT_EAGLE_STATE = "NOT_EAGLE_STATE",
    INDIANA = "IN",
    WEST_VIRGINA = "WV",
    ARKSANSAS = "AR";
}
