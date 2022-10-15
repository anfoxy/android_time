package com.example.timelab.ui.model;
import java.util.Date;
public class AlarmModel {
    private String date;
    private Long time;

    public AlarmModel(String date, Long time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
