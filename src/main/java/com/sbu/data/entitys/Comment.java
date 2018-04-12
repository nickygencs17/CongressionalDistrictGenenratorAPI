package com.sbu.data.entitys;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comment")
public class Comment {

    @NotNull
    String comment_text;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int comment_id;

    @NotNull
    String comment_time_date;

    @NotNull
    String author;

    @NotNull
    String post_id;

    public Comment(String comment_text, String comment_time_date, String author, String post_id) {
        this.comment_text = comment_text;
        this.comment_time_date = comment_time_date;
        this.author = author;
        this.post_id = post_id;
    }

    public Comment() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_time_date() {
        return comment_time_date;
    }

    public void setComment_time_date(String comment_time_date) {
        this.comment_time_date = comment_time_date;
    }








}
