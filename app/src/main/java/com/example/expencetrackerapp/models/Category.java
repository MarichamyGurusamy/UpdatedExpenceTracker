package com.example.expencetrackerapp.models;

public class Category {
    private String name;
    private double amount;

    public Category(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
}
