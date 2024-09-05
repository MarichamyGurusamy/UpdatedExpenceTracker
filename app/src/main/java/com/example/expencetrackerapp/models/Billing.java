package com.example.expencetrackerapp.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "billing")
public class Billing {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String amount;
    private String date;
    private boolean isMarkAsRead;


    public Billing(int id ,String title, String amount, String date,boolean isMarkAsRead) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.date = date;
        this.isMarkAsRead = isMarkAsRead;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isMarkAsRead() {
        return isMarkAsRead;
    }

    public void setMarkAsRead(boolean markAsRead) {
        isMarkAsRead = markAsRead;
    }


}
