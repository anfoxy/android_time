package com.example.timelab.ui.model;

public class Date {
    public int hour;
    public int min;
    public int sec;
    public Date() {
        hour = min = sec = 0;
    }
    public Date(Date c) {
        hour = c.hour;
        min = c.min;
        sec = c.sec;
    }
    public Date(int _hour, int _min, int _sec) {
        this.setTime(_hour, _min, _sec);
    }
    public void setTime(int _hour, int _min, int _sec) {
        hour = _hour % 24;
        min = _min % 60;
        sec = _sec % 60;
    }
    public void addSecond() {
        sec += 1;
        min = min + sec / 60;
        hour = hour + min / 60;
        sec = sec % 60;
        min = min % 60;
        hour = hour % 24;
    }
    public void rmSecond() {
        sec -= 1;
        if (sec < 0) {
            sec = 59;
            min -= 1;
        }
        if (min < 0) {
            min = 59;
            hour -= 1;
        }
        if (hour < 0) {
            hour = 23;
        }
    }

    @Override
    public String toString() {
        return "Hour: " + hour + "| Min: " + min + "| Sec: " + sec;
    }
}