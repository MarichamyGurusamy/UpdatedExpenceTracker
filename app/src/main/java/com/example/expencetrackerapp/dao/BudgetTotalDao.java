package com.example.expencetrackerapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.expencetrackerapp.models.BudgetCategory;
import com.example.expencetrackerapp.models.BudgetTotal;

import java.util.List;

@Dao
public interface BudgetTotalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BudgetTotal budgetTotal);

    @Query("SELECT * FROM budget_totals")
    LiveData<List<BudgetTotal>> getAllBudget();

}
