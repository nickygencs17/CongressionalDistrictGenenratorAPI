package com.sbu.controller;

import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @RequestMapping(method = RequestMethod.GET)
    Response getAllPosts(){

        return build200("Test");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    Response getPostByID(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.POST)
    Response postComment(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(method = RequestMethod.POST)
    Response postNewPost(){

        return build200("Test");
    }
}
