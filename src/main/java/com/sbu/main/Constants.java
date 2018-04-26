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
    PREPROCESS_FAIL = "PREPROCESS_FAIL",
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
    NUMBER_OF_CONGRESS_DISTRICTS = "NUMBER_OF_CONGRESS_DISTRICTS",
    SUCCESS ="SUCCESS",
    ARRAY_START =  "[",
    ARRAY_END = "]",
    USERNAME = "username",
    USER_PASSWORD= "user_password",
    FIRST_NAME = "first_name",
    LAST_NAME = "last_name",
    CITY = "city",
    STATE_ID = "state_id",
    ADDRESS = "address",
    ZIP = "zip",
    POP_COEF ="population_coefficient",
    FAIR_COEF ="fairness_coefficient",
    COMP_COEF ="compactness_coefficient";

    public static final int MAX_UNCHANGED_CHECKS = 100000;
    public static final int MAX_MOVES_PER_UPDATE = 1000;
}
