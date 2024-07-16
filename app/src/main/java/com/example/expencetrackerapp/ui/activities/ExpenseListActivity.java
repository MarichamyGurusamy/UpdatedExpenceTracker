package com.example.expencetrackerapp.ui.activities;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListActivity extends AppCompatActivity {

    private ExpenseDatabase expenseDatabase;
    private List<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence_list);

        RecyclerView expenseRecyclerView = findViewById(R.id.recyclerViewExpenses);
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setAdapter(expenseAdapter);

        expenseDatabase = new ExpenseDatabase(this);
        loadExpenses();
    }

    private void loadExpenses() {
        expenseList.clear();
        expenseList.addAll(expenseDatabase.getAllExpenses());
        expenseAdapter.notifyDataSetChanged();
    }
}