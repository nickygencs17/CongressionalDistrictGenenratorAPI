package com.sbu.main;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class Constants {
    public static final GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    public static final GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    public static final String ROLE_ADMIN_STRING = "[ROLE_ADMIN]";
    public static final String ROLE_USER_STRING = "[ROLE_USER]";
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
    ARKANSAS = "AR",
    CONGRESS_ELECTION_INFO = "CONGRESS_ELECTION_INFO",
    PRESIDENT_ELECTION_INFO = "PRESIDENT_ELECTION_INFO",
    CURRENT_OFFICIALS = "CURRENT_OFFICIALS",
    NUMBER_OF_CONGRESS_DISTRICTS = "NUMBER_OF_CONGRESS_DISTRICTS";
}
