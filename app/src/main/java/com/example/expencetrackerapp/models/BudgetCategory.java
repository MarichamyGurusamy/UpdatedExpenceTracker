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
    private int budgetAmount;
    private int spentAmount;

    // Constructor
    public BudgetCategory(int id, String categoryName, int budgetAmount, int spentAmount) {
        this.id = id;
        this.categoryName = categoryName;
        this.budgetAmount = budgetAmount;
        this.spentAmount = spentAmount;
    }

    // Getter methods
    public String getCategoryName() {
        return categoryName;
    }

    public int getBudgetAmount() {
        return budgetAmount;
    }

    public int getSpentAmount() {
        return spentAmount;
    }

    public int getId() {
        return id;
    }

    // Optionally, you can also add setter methods if needed
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBudgetAmount(int budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public void setSpentAmount(int spentAmount) {
        this.spentAmount = spentAmount;
    }


    @Ignore
    public BudgetCategory(String categoryName, int budgetAmount) {
        this(0, categoryName, budgetAmount, 0); // Use default ID (0) and spentAmount (0)
    }




    public BudgetCategory(Parcel in) {
        id = in.readInt();
        budgetAmount = in.readInt();
        categoryName = in.readString();
        spentAmount = in.readInt();

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
        dest.writeInt(budgetAmount);
        dest.writeString(categoryName);
        dest.writeInt(spentAmount);
    }



}