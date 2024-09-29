package com.example.expencetrackerapp.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.BudgetDao;
import com.example.expencetrackerapp.dao.ExpenseDao;
import com.example.expencetrackerapp.database.BudgetsDatabase;
import com.example.expencetrackerapp.database.ExpenseDatabase;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.repository.AllDetailsRepository;
import com.example.expencetrackerapp.repository.BudgetDetailsRepository;

import java.util.List;

public class BudgetViewModel extends AndroidViewModel {
    private final BudgetDetailsRepository repository;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        BudgetDao budgetDao = BudgetsDatabase.getDatabase(application).budgetDao();
        repository = new BudgetDetailsRepository(budgetDao);
    }

    public LiveData<List<Budget>> getAllNotes1() {
        return repository.getAllNotes();
    }



    public void insert(Budget budget) {
        repository.insert(budget);
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