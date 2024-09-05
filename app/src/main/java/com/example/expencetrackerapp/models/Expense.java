package com.example.expencetrackerapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class Expense implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String recipient;
    private double amount;
    private String date;
    private String category;
    private String bankName;

    // Constructors
    public Expense(int id ,String recipient, double amount, String date, String category, String bankName) {
        this.id = id;
        this.recipient = recipient;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.bankName = bankName;
    }



    // Getters and Setters
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }



    protected Expense(Parcel in) {
        id = in.readInt();
        recipient = in.readString();
        amount = in.readDouble();
        date = in.readString();
        category = in.readString();
        bankName = in.readString();
    }

    public static final Creator<Expense> CREATOR = new Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }

        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(recipient);
        dest.writeDouble(amount);
        dest.writeString(date);
        dest.writeString(category);
        dest.writeString(bankName);
    }

}