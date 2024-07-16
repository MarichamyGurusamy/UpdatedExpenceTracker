package com.example.expencetrackerapp.data.model;


public class Transaction {
    private String smsMessage;
    private double amount;
    // Add other necessary fields and methods

    public Transaction(String smsMessage, double amount) {
        this.smsMessage = smsMessage;
        this.amount = amount;
    }

    public String getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(String smsMessage) {
        this.smsMessage = smsMessage;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountFormatted() {
        // Implement formatting logic if needed
        return String.format("%.2f", amount); // Example formatting to two decimal places
    }
}

