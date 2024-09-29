package com.example.expencetrackerapp.repository;



import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.BudgetCategoryDao;
import com.example.expencetrackerapp.dao.BudgetDao;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.BudgetCategory;

import java.util.List;

public class BudgetCategoryRepository {
    private final BudgetCategoryDao budgetCategoryDao;

    public BudgetCategoryRepository(BudgetCategoryDao budgetCategoryDao) {
        this.budgetCategoryDao = budgetCategoryDao;
    }

    public LiveData<List<BudgetCategory>> getAllNotes() {
        return budgetCategoryDao.getAllBudget();
    }



    public void insert(BudgetCategory budgetCategory) {
        budgetCategoryDao.insertBudgetCategory(budgetCategory);
    }

//    public void update(Budget budget) {
//        budgetDao.updateBudget(budget);
//    }
//
//    public void deleteItme(int id) {
//        budgetDao.deleteBudget(id);
//    }


}
