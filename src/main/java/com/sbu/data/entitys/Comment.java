package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;


@Entity
@Table(name = "blog_post")
public class Comment {

    @NotNull
    String comment_text;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    String comment_id;

    @NotNull
    Time comment_time_date;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    AppUser author;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    BlogPost post_id;

    public Comment(String comment_text, Time comment_time_date, AppUser author, BlogPost post_id) {
        this.comment_text = comment_text;
        this.comment_time_date = comment_time_date;
        this.author = author;
        this.post_id = post_id;
    }

    public Comment() {
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public BlogPost getPost_id() {
        return post_id;
    }

    public void setPost_id(BlogPost post_id) {
        this.post_id = post_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public Time getComment_time_date() {
        return comment_time_date;
    }

    public void setComment_time_date(Time comment_time_date) {
        this.comment_time_date = comment_time_date;
    }








}
