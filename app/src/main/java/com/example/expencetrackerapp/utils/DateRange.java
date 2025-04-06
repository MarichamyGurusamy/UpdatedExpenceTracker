package com.example.expencetrackerapp.utils;

import java.util.Date;

public class DateRange {
    private Date firstDate;
    private Date lastDate;

    public DateRange(Date firstDate, Date lastDate) {
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public Date getLastDate() {
        return lastDate;
    }

    @Override
    public String toString() {
        return "First Date: " + firstDate + ", Last Date: " + lastDate;
    }
}