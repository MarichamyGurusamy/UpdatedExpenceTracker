package com.example.expencetrackerapp.repository;



import androidx.lifecycle.LiveData;

import com.example.expencetrackerapp.dao.ExpenseDao;
import com.example.expencetrackerapp.models.Expense;

import java.util.List;

public class AllDetailsRepository {
    private final ExpenseDao expenseDao;

    public AllDetailsRepository(ExpenseDao expenseDao) {
        this.expenseDao = expenseDao;
    }

    public LiveData<List<Expense>> getAllNotes() {
        return expenseDao.getAllExpenses();
    }

    public LiveData<List<Expense>> getExpensesByMonth(String month) {
        return expenseDao.getExpensesByMonth(month);
    }

    public LiveData<List<Expense>> getExpensesByYear(String year) {
        return expenseDao.getExpensesByYear(year);
    }

    public LiveData<List<Expense>> getExpensesByMonthAndYear(String month, String year) {
        return expenseDao.getExpensesByMonthAndYear(month, year);
    }

    public void insert(Expense expense) {
        expenseDao.insertExpense(expense);
    }

    public void update(Expense expense) {
        expenseDao.updateExpense(expense);
    }

    public void deleteItme(int id) {
        expenseDao.deleteExpense(id);
    }

    public LiveData<List<Expense>> getSpecifityExpenses(String postions) {
        return expenseDao.getSpecifityExpenses(postions);
    }
}
