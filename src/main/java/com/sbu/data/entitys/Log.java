package com.sbu.data.entitys;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "log")
public class Log {


    @NotNull
    String time_date;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int log_id;

    @NotNull
    String log_details;


    public Log(String time_date, String log_details) {
        this.time_date = time_date;
        this.log_details = log_details;
    }

    public Log() {

    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }



    public String getLog_details() {
        return log_details;
    }

    public void setLog_details(String log_details) {
        this.log_details = log_details;
    }


}
