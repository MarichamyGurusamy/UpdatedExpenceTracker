package com.example.expencetrackerapp.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.BudgetCategoryDao;
import com.example.expencetrackerapp.dao.BudgetDao;
import com.example.expencetrackerapp.database.BudgetCategoryDatabase;
import com.example.expencetrackerapp.database.BudgetsDatabase;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.BudgetCategory;
import com.example.expencetrackerapp.repository.BudgetCategoryRepository;
import com.example.expencetrackerapp.repository.BudgetDetailsRepository;

import java.util.List;

public class BudgetCategoryViewModel extends AndroidViewModel {
    private final BudgetCategoryRepository repository;

    public BudgetCategoryViewModel(@NonNull Application application) {
        super(application);
        BudgetCategoryDao budgetCategoryDao = BudgetCategoryDatabase.getDatabase(application).budgetCategoryDao();
        repository = new BudgetCategoryRepository(budgetCategoryDao);
    }

    public LiveData<List<BudgetCategory>> getAllNotes() {
        return repository.getAllNotes();
    }



    public void insert(BudgetCategory budgetCategory) {
        repository.insert(budgetCategory);
    }

//    public void update(Expense expense) {
//        repository.update(expense);
//    }
//
//    public void deleteItme(int id) {
//        repository.deleteItme(id);
//    }
//
//


}