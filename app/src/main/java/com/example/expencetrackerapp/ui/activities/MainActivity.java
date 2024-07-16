package com.example.expencetrackerapp.ui.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewExpenses;
    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenseList;
    private ExpenseDatabase expenseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        recyclerViewExpenses = findViewById(R.id.recyclerViewExpenses);
        Button viewExpenseListButton = findViewById(R.id.viewExpenseListButton);

        // Set up RecyclerView
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(this, expenseList);
        recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewExpenses.setAdapter(expenseAdapter);

        // Initialize database
        expenseDatabase = new ExpenseDatabase(this);

        // Load initial expenses
        loadExpenses();

        // Button click listener to navigate to ExpenseListActivity
        viewExpenseListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ExpenseListActivity.class));
            }
        });

        // Insert test data
        insertTestData();
    }

    private void loadExpenses() {
        expenseList.clear();
        expenseList.addAll(expenseDatabase.getAllExpenses());
        expenseAdapter.notifyDataSetChanged();
    }

    private void insertTestData() {
        Expense expense1 = new Expense("Test Sender 1", "Test Message Body 1", 100.0);
        Expense expense2 = new Expense("Test Sender 2", "Test Message Body 2", 200.0);

        // Insert data into the database
        expenseDatabase.addExpense(expense1);
        expenseDatabase.addExpense(expense2);

        // Reload expenses to see the newly added data
        loadExpenses();
    }
}