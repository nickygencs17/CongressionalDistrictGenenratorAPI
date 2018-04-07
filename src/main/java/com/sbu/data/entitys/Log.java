package com.sbu.data.entitys;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Entity
@Table(name = "log")
public class Log {


    @NotNull
    Time time_date;

    @Id
    int log_id;

    @NotNull
    String log_details;

    public Log(Time time_date, int log_id, String log_details) {
        this.time_date = time_date;
        this.log_id = log_id;
        this.log_details = log_details;
    }

    public Log() {

    }

    public Time getTime_date() {
        return time_date;
    }

    public void setTime_date(Time time_date) {
        this.time_date = time_date;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getLog_details() {
        return log_details;
    }

    public void setLog_details(String log_details) {
        this.log_details = log_details;
    }


}
