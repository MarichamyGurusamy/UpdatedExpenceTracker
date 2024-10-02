package com.example.expencetrackerapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget_totals")
public class BudgetTotal {
    @PrimaryKey
    private int id;
    private String name;
    private double totalAmount;

    // Constructor
    public BudgetTotal(int id,double totalAmount,String name) {
        this.id=id;
        this.totalAmount = totalAmount;
        this.name = name;

    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
