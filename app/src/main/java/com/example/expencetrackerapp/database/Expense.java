package com.example.expencetrackerapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String sender;
    private String messageBody;
    private double amount;

    public Expense(String sender, String messageBody, double amount) {
        this.sender = sender;
        this.messageBody = messageBody;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public double getAmount() {
        return amount;
    }
}