package com.sbu.services;
import com.sbu.data.BlogPostRepository;
import com.sbu.data.CommentRepository;
import com.sbu.data.entitys.BlogPost;
import com.sbu.data.entitys.Comment;
import com.sbu.main.Constants;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.sbu.utils.ResponseUtil.build404;

@Component
public class PostService {

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    CommentRepository commentRepository;

    public Iterable<Comment> getAllCommentsForAPost(String post_id) {
        return commentRepository.findByPost_Id(post_id);
    }

    public Object addComment(Comment comment) {
        Object blog_post = blogPostRepository.findOne(Integer.parseInt(comment.getPost_id()));
        if(blog_post==null){
            return build404(Constants.POST_NOT_FOUND);
        }
        return commentRepository.save(comment);
    }

    public Object getPostById(String id) {
        Object blog_post = blogPostRepository.findOne(Integer.parseInt(id));
        if(blog_post==null){
            return build404(Constants.POST_NOT_FOUND);
        }
        Iterable<Comment> comments = getAllCommentsForAPost(id);
        JSONObject return_node = new JSONObject();
        return_node.put(Constants.POST,blog_post);
        return_node.put(Constants.COMMENTS,comments);
        return return_node;
    }

    public Object addPost(BlogPost post) {
        return blogPostRepository.save(post);
    }

    public Object getAllPosts() {
        return blogPostRepository.findAll();

    }
}
