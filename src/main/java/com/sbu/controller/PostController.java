package com.sbu.controller;

import com.sbu.data.entitys.BlogPost;
import com.sbu.data.entitys.Comment;
import com.sbu.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;

import static com.sbu.utils.ResponseUtil.build200;
import static com.sbu.utils.ResponseUtil.build201;
import static com.sbu.utils.Utils.checkIfUserIsAdmin;

@RestController
@CrossOrigin
@RequestMapping("/post")
public class PostController {

    @Autowired
    private final InMemoryUserDetailsManager userManager;

    @Autowired
    PostService postService;

    @Autowired
    public PostController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.userManager = inMemoryUserDetailsManager;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    Response getAllPosts() {
        return build200(postService.getAllPosts());
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    Response getPostByID(@PathVariable(value = "id") String id) {
        return build200(postService.getPostById(id));
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    Response postComment(@RequestBody @Valid Comment comment) {
        return build201(postService.addComment(comment));
    }

    @RequestMapping(method = RequestMethod.POST)
    Response postNewPost(@RequestBody @Valid BlogPost post) {
        return build201(postService.addPost(post));
    }

    private boolean handleAdminCall() {
        String requesting_username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!checkIfUserIsAdmin(requesting_username, userManager)) {
            return true;
        }
        return false;
    }
}
