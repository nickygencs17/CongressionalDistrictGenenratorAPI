package com.sbu.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

@Configuration
public class PropertiesLoader {

    /**
     * Logger used to log all output throughout entire project.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    public static final String PROPERTIES_FILE_NAME = "src/main/resources/constants.properties";

    private Properties config;


    public enum PropertyNames {
        USER_NOT_FOUND,
        USER_EXISTS,
        PREPROCESS_FAIL,
        CONGRESS,
        STATE,
        UPPER,
        LOWER,
        POST,
        COMMENTS,
        POST_NOT_FOUND,
        STATE_ID_LENGTH_GREATER_THAN_TWO,
        NOT_EAGLE_STATE,
        INDIANA,
        WEST_VIRGINA,
        ARKANSAS,
        CONGRESS_ELECTION_INFO,
        PRESIDENT_ELECTION_INFO,
        CURRENT_OFFICIALS,
        NUMBER_OF_CONGRESS_DISTRICTS,
        SUCCESS,
        ARRAY_START,
        ARRAY_END,
        USERNAME,
        USER_PASSWORD,
        FIRST_NAME,
        LAST_NAME,
        CITY,
        STATE_ID,
        ADDRESS,
        ZIP,
        POP_COEF,
        FAIR_COEF,
        WORKING_DIRECTORY,
        VD,
        CD,
        USER_DIR,
        RESOURCES,
        VD_RESOURCES,
        VD_SUFFIX,
        GEOJSON,
        COMP_COEF,
        LOGIN_SUCCESS,
        MESSAGE,
        LOGIN_SUCESS_MESSAGE,
        LOG_USER,
        LOG_STATE,
        LOG_INCLUDED,
        ROLES,
        ROLE_ADMIN_STRING,
        ROLE_USER_STRING,
        AR_LAT,
        AR_LNG,
        IN_LAT,
        IN_LNG,
        WV_LAT,
        WV_LNG,
        MAX_UNCHANGED_CHECKS,
        AREA_SQUAREMILES,
        MAX_MOVES_PER_UPDATE,
        ZERO,
        THOUSAND_HUNDRED
    }

    /**
     * This method will call our load method as well as catch any IOexceptions.
     */
    public PropertiesLoader() {
        logger.debug("Attempting to Load properties");
        try {
            load();
        } catch (IOException e) {
            logger.error("Failed to load properties: " + e.getLocalizedMessage());

        }
        logger.debug("Loaded Properties");
    }

    /**
     * This method will load in out node_config.properties file.
     * @throws IOException
     */
    public void load() throws IOException {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        config = new Properties();
        FileInputStream fileInputStream;
        logger.debug("Opening File: "+PROPERTIES_FILE_NAME);
        fileInputStream = new FileInputStream(PROPERTIES_FILE_NAME);
        config.load(fileInputStream);
        fileInputStream.close();
        logger.debug("Closing File: "+PROPERTIES_FILE_NAME);
    }

    /**
     * This method returns the value of the given property name.
     * @param propertyName
     * @return nodeconfigvalue
     */
    public final String get(String propertyName) {
        return config.getProperty(propertyName);
    }

}

