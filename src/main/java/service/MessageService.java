package service;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.hash.Hashing;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/messages")
public class MessageService{
    BiMap<String, String> biMap = HashBiMap.create();

    public static String DIGEST_CONSTANT = "digest";
    public static String MESSAGE_CONSTANT = "message";
    public static String ERROR_MESSAGE = "err_msg";
    public static String MESSAGE_NOT_FOUND ="Message not found";
    public static String MESSAGE_EXAMPLE = "Please provide message. Example: { \"message\" : \"foo\"}";


    @RequestMapping(method = RequestMethod.POST)
    public Response createHashService(@RequestBody JsonNode messageJson) {
        JSONObject jsonObject = new JSONObject();
        String message;
        if(messageJson.has(MESSAGE_CONSTANT)) {
            message = messageJson.get(MESSAGE_CONSTANT).asText();
        }
        else{
            jsonObject.put(ERROR_MESSAGE,MESSAGE_EXAMPLE);
            return Response.status(Response.Status.BAD_REQUEST).entity(jsonObject).build();
        }
        String sha256hex = Hashing.sha256()
                .hashString(message, StandardCharsets.UTF_8)
                .toString();
        jsonObject.put(DIGEST_CONSTANT,sha256hex);
        biMap.put(message,sha256hex);
        return Response.status(Response.Status.OK).entity(jsonObject).build();
    }

    @RequestMapping(value = "/{hash}", method = RequestMethod.GET)
    public Response getMessageService(@PathVariable String hash) {
        JSONObject jsonObject = new JSONObject();
        if(biMap.containsValue(hash)){
            String key = biMap.inverse().get(hash).toString();
            jsonObject.put(MESSAGE_CONSTANT,key);
            return Response.status(Response.Status.OK).entity(jsonObject).build();
        }
        jsonObject.put(ERROR_MESSAGE,MESSAGE_NOT_FOUND);
        return Response.status(Response.Status.NOT_FOUND).entity(jsonObject).build();

    }

}
