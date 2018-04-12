package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "blog_post")
public class BlogPost {


    @NotNull
    String title;

    //TODO: add title
    @NotNull
    String image_url;

    @NotNull
    String post_text;


    @NotNull
    String author;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int post_id;

    @NotNull
    String time_date;\


    public BlogPost(String title, String image_url, String post_text, String author, String time_date) {
        this.title = title;
        this.image_url = image_url;
        this.post_text = post_text;
        this.author = author;
        this.time_date = time_date;
    }

    public BlogPost() {
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
