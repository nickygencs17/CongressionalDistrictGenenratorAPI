package com.sbu.services;


import com.sbu.data.BlogPostRepository;
import com.sbu.data.CommentRepository;
import com.sbu.data.entitys.BlogPost;
import com.sbu.data.entitys.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostService {

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    CommentRepository commentRepository;

    public Object getAllCommentsForAPost() {
        //TODO: may need this....
        return "TEST";
    }

    public Object addComment(Comment comment) {
        BlogPost blogPost = blogPostRepository.findOne(Integer.getInteger(comment.getPost_id()));
        //TODO: add comment id to post array of comments
        return commentRepository.save(comment);
    }

    public Object getPostById(String id) {
        return blogPostRepository.findOne(Integer.getInteger(id));
    }

    public Object addPost(BlogPost post) {
        return blogPostRepository.save(post);
    }

    public Object getAllPosts() {
        return blogPostRepository.findAll();

    }
}
