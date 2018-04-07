package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Entity
@Table(name = "blog_post")
public class BlogPost {


    @NotNull
    String image_url;

    @NotNull
    String post_text;


    String comment_ids;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    AppUser author;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int post_id;

    @NotNull
    Time time_date;


    public BlogPost(String image_url, String post_text, String comment_ids, AppUser author, Time time_date) {
        this.image_url = image_url;
        this.post_text = post_text;
        this.comment_ids = comment_ids;
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

    public String getComment_ids() {
        return comment_ids;
    }

    public void setComment_ids(String comment_ids) {
        this.comment_ids = comment_ids;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public Time getTime_date() {
        return time_date;
    }

    public void setTime_date(Time time_date) {
        this.time_date = time_date;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }
}
