package com.example.expencetrackerapp.repository;



import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.BudgetDao;
import com.example.expencetrackerapp.dao.ExpenseDao;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.Expense;

import java.util.List;

public class BudgetDetailsRepository {
    private final BudgetDao budgetDao;

    public BudgetDetailsRepository(BudgetDao budgetDao) {
        this.budgetDao = budgetDao;
    }

    public LiveData<List<Budget>> getAllNotes() {
        return budgetDao.getAllBudget();
    }



    public void insert(Budget budget) {
        budgetDao.insertBudget(budget);
    }

//    public void update(Budget budget) {
//        budgetDao.updateBudget(budget);
//    }
//
//    public void deleteItme(int id) {
//        budgetDao.deleteBudget(id);
//    }


}
