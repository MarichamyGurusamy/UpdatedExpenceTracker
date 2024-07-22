package com.example.expencetrackerapp.database;

public class Expense {
    private int id;
    private String recipient;
    private String message;
    private double amount;
    private String date;
    private String bankName;
    private String category;

    public Expense(String recipient, String message, double amount, String date, String bankName, String category) {
        this.recipient = recipient;
        this.message = message;
        this.amount = amount;
        this.date = date;
        this.bankName = bankName;
        this.category = category;
    }

    public Expense(int id, String recipient, String message, double amount, String date, String bankName, String category) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.amount = amount;
        this.date = date;
        this.bankName = bankName;
        this.category = category;
    }

    // Getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}