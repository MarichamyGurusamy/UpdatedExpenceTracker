package com.example.expencetrackerapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.Expense;

import java.util.List;


@Dao
public interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudget(Budget budget);

    @Query("SELECT * FROM budget_table")
    LiveData<List<Budget>> getAllBudget();



}
