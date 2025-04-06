package com.example.expencetrackerapp.repository;



import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.BudgetCategoryDao;
import com.example.expencetrackerapp.dao.BudgetTotalDao;
import com.example.expencetrackerapp.models.BudgetCategory;
import com.example.expencetrackerapp.models.BudgetTotal;

import java.util.List;

public class BudgetTotalRepository {
    private final BudgetTotalDao budgetTotalDao;

    public BudgetTotalRepository(BudgetTotalDao budgetTotalDao) {
        this.budgetTotalDao = budgetTotalDao;
    }

    public LiveData<List<BudgetTotal>> getAllNotes() {
        return budgetTotalDao.getAllBudget();
    }



//    public void insert(BudgetTotal budgetTotal) {
//        budgetTotalDao.insert(budgetTotal);
//    }

//    public void update(Budget budget) {
//        budgetDao.updateBudget(budget);
//    }
//
//    public void deleteItme(int id) {
//        budgetDao.deleteBudget(id);
//    }


}
