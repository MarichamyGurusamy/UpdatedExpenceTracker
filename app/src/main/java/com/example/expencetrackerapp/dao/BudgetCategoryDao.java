package com.example.expencetrackerapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.BudgetCategory;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface BudgetCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBudgetCategory(BudgetCategory budgetCategory);

    @Query("SELECT * FROM budgetcategory_table")
    LiveData<List<BudgetCategory>> getAllBudget();

    @Query("DELETE FROM budgetcategory_table WHERE id = :id")
    void deleteById(int id);

    @Update
    void updateBudgetCategory(BudgetCategory budgetCategory);

}
