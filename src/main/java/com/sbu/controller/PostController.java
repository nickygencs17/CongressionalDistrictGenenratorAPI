package com.sbu.controller;

import com.sbu.data.entitys.BlogPost;
import com.sbu.data.entitys.Comment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    Response getAllPosts(){

        return build200("Test");
    }

    @RequestMapping(value = "/id/{id}",method = RequestMethod.GET)
    Response getPostByID(@PathVariable(value="id") String id){

        return build200("Test");
    }

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    Response postComment(@RequestBody @Valid Comment comment){


        return build200("Test");
    }

    @RequestMapping(method = RequestMethod.POST)
    Response postNewPost(@RequestBody @Valid BlogPost post){

        return build200("Test");
    }
}
