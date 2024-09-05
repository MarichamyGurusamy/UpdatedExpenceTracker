package com.example.expencetrackerapp.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expencetrackerapp.models.Expense;

import java.util.List;

@Dao
public interface ExpenseDao {
    @Insert
    void insertExpense(Expense expense);

    @Update
    void updateExpense(Expense expense);

    @Query("DELETE FROM expenses WHERE id = :id")
    void deleteExpense(int id);

    @Query("SELECT * FROM expenses")
    LiveData<List<Expense>> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE id = :id")
    Expense getExpenseById(int id);

    @Query("SELECT * FROM expenses WHERE strftime('%m', date) = :month")
    LiveData<List<Expense>> getExpensesByMonth(String month);

    @Query("SELECT * FROM expenses WHERE category = :postions")
    LiveData<List<Expense>> getSpecifityExpenses(String postions);


}