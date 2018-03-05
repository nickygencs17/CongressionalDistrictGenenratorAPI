package com.sbu.utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.io.IOException;

/**
 * Created by Nicholas Genco on 3/1/17.
 * utils will go here
 */
public class Utils {




    public static void checkMovieExists(String movieID) {


    }

    public static JsonPatch checkPatchValid(JsonNode node) throws JsonPatchException {
        if (node.size() == 0){
            throw new IllegalArgumentException("Patch cannot be empty");
        }
        JsonPatch patch = null;
        try{
            patch = JsonPatch.fromJson(node);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid JSON patch");
        }
        return patch;
    }



}
