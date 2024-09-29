package com.example.expencetrackerapp.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.ExpenseDao;
import com.example.expencetrackerapp.database.ExpenseDatabase;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.repository.AllDetailsRepository;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private final AllDetailsRepository repository;

    public ExpenseViewModel(@NonNull Application application) {
        super(application);
        ExpenseDao expenseDao = ExpenseDatabase.getDatabase(application).expenseDao();
        repository = new AllDetailsRepository(expenseDao);
    }

    public LiveData<List<Expense>> getAllNotes() {
        return repository.getAllNotes();
    }

    public LiveData<List<Expense>> getExpensesByMonth(String monthNumber) {
        return repository.getExpensesByMonth(monthNumber);
    }

    public LiveData<List<Expense>> getExpensesByYear(String year) {
        return repository.getExpensesByYear(year);
    }

    public LiveData<List<Expense>> getExpensesByMonthAndYear(String month, String year) {
        return repository.getExpensesByMonthAndYear(month, year);
    }

    public void insert(Expense expense) {
        repository.insert(expense);
    }

    public void update(Expense expense) {
        repository.update(expense);
    }

    public void deleteItme(int id) {
        repository.deleteItme(id);
    }

    public LiveData<List<Expense>> getSpecifityExpenses(String postions) {
        return repository.getSpecifityExpenses(postions);
    }


}