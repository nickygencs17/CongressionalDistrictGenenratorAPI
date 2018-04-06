package com.sbu.data.entitys;

import java.sql.Time;

public class BlogPost {

    String image_url;
    String post_text;
    String comment_ids;
    String post_ids;
    Time time_date;


    public BlogPost(String image_url, String post_text, String comment_ids, String post_ids, Time time_date) {
        this.image_url = image_url;
        this.post_text = post_text;
        this.comment_ids = comment_ids;
        this.post_ids = post_ids;
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

    public String getPost_ids() {
        return post_ids;
    }

    public void setPost_ids(String post_ids) {
        this.post_ids = post_ids;
    }

    public Time getTime_date() {
        return time_date;
    }

    public void setTime_date(Time time_date) {
        this.time_date = time_date;
    }



}
