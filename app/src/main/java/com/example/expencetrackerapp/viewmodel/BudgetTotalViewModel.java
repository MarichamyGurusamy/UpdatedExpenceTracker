package com.example.expencetrackerapp.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.BudgetCategoryDao;
import com.example.expencetrackerapp.dao.BudgetTotalDao;
import com.example.expencetrackerapp.database.BudgetCategoryDatabase;
import com.example.expencetrackerapp.database.BudgetsTotalDatabase;
import com.example.expencetrackerapp.models.BudgetCategory;
import com.example.expencetrackerapp.models.BudgetTotal;
import com.example.expencetrackerapp.repository.BudgetCategoryRepository;
import com.example.expencetrackerapp.repository.BudgetTotalRepository;

import java.util.List;

public class BudgetTotalViewModel extends AndroidViewModel {
    private final BudgetTotalRepository repository;

    public BudgetTotalViewModel(@NonNull Application application) {
        super(application);
        BudgetTotalDao budgetTotalDao = BudgetsTotalDatabase.getDatabase(application).budgetTotalDao();
        repository = new BudgetTotalRepository(budgetTotalDao);
    }

    public LiveData<List<BudgetTotal>> getAllNotes() {
        return repository.getAllNotes();
    }



//    public void insert(BudgetTotal budgetTotal) {
//        repository.insert(budgetTotal);
//    }

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