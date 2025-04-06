package com.example.expencetrackerapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "budgetcategory_table")
public class BudgetCategory implements Parcelable {
    @PrimaryKey
    private int id;
    private String categoryName;
    private double budgetAmount;
    private double spentAmount;

    // Constructor
    public BudgetCategory(int id, String categoryName, double budgetAmount, double spentAmount) {
        this.id = id;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
    }

    // Getter methods
    public String getCategoryName() {
        return categoryName;
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public int getId() {
        return id;
    }

    // Optionally, you can also add setter methods if needed
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public void setSpentAmount(double spentAmount) {
        this.spentAmount = spentAmount;
    }


    @Ignore
    public BudgetCategory(String categoryName, double budgetAmount) {
        this(0, categoryName, budgetAmount, 0); // Use default ID (0) and spentAmount (0)
    }




    public BudgetCategory(Parcel in) {
        id = in.readInt();
        budgetAmount = in.readDouble();
        categoryName = in.readString();
        spentAmount = in.readDouble();

    }

    public static final Creator<BudgetCategory> CREATOR = new Creator<BudgetCategory>() {
        @Override
        public BudgetCategory createFromParcel(Parcel in) {
            return new BudgetCategory(in);
        }

        @Override
        public BudgetCategory[] newArray(int size) {
            return new BudgetCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(budgetAmount);
        dest.writeString(categoryName);
        dest.writeDouble(spentAmount);
    }



}