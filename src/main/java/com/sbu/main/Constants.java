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
            POST_NOT_FOUND = "POST_NOT_FOUND",
            STATE_ID_LENGTH_GREATER_THAN_TWO = "STATE_ID_LENGTH_GREATER_THAN_TWO ",
            NOT_EAGLE_STATE = "NOT_EAGLE_STATE",
            INDIANA = "IN",
            WEST_VIRGINA = "WV",
            ARKANSAS = "AR",
            CONGRESS_ELECTION_INFO = "CONGRESS_ELECTION_INFO",
            PRESIDENT_ELECTION_INFO = "PRESIDENT_ELECTION_INFO",
            CURRENT_OFFICIALS = "CURRENT_OFFICIALS",
            NUMBER_OF_CONGRESS_DISTRICTS = "NUMBER_OF_CONGRESS_DISTRICTS",
            SUCCESS = "SUCCESS",
            ARRAY_START = "[",
            ARRAY_END = "]",
            USERNAME = "username",
            USER_PASSWORD = "user_password",
            FIRST_NAME = "first_name",
            LAST_NAME = "last_name",
            CITY = "city",
            STATE_ID = "state_id",
            ADDRESS = "address",
            ZIP = "zip",
            POP_COEF = "population_coefficient",
            FAIR_COEF = "fairness_coefficient",
            WORKING_DIRECTORY = "Working Directory = ",
            VD = "vd",
            CD = "cd",
            USER_DIR = "user.dir",
            RESOURCES = "/src/main/resources/",
            VD_RESOURCES = "/src/main/resources/individual_vtds",
            VD_SUFFIX = "_vtd",
            GEOJSON = ".geojson",
            COMP_COEF = "compactness_coefficient",
            LOGIN_SUCCESS = "success",
            MESSAGE = "message",
            LOGIN_SUCESS_MESSAGE = "Login Successful",
            ROLES = "roles";


    public static final double AR_LAT = 68.935125;
    public static final double AR_LNG = 56.723705;
    public static final double IN_LAT = 68.993567;
    public static final double IN_LNG = 53.061157;
    public static final double WV_LAT = 68.969859;
    public static final double WV_LNG = 54.576432;
    public static final int MAX_UNCHANGED_CHECKS = 700;
    public static final int AREA_SQUAREMILES = 2589988;
    public static final int MAX_MOVES_PER_UPDATE = 300;
    public static final int ZERO = 0;
    public static final int THOUSAND_HUNDRED = 1000000;

}
