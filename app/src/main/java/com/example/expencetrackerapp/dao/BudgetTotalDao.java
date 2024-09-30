package com.example.expencetrackerapp.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.expencetrackerapp.models.BudgetTotal;

@Dao
public interface BudgetTotalDao {
    @Insert
    void insert(BudgetTotal budgetTotal);
}
