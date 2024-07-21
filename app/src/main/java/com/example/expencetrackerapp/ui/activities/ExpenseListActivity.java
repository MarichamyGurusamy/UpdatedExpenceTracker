package com.example.expencetrackerapp.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseListActivity extends AppCompatActivity {

    private ExpenseDatabase expenseDatabase;
    private List<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;
    private RecyclerView expenseRecyclerView;
    private TextView totalAmountTextView;
    private Spinner monthFilterSpinner;
    private String selectedMonth = "Current Month"; // Default value

    private Map<String, String> monthMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence_list); // Ensure this matches the XML layout file name

        // Initialize UI elements
        expenseRecyclerView = findViewById(R.id.recyclerViewExpenses);
        totalAmountTextView = findViewById(R.id.total_amount);
        monthFilterSpinner = findViewById(R.id.month_filter);

        // Set up Spinner month mapping
        monthMap = new HashMap<>();
        monthMap.put("January", "01");
        monthMap.put("February", "02");
        monthMap.put("March", "03");
        monthMap.put("April", "04");
        monthMap.put("May", "05");
        monthMap.put("June", "06");
        monthMap.put("July", "07");
        monthMap.put("August", "08");
        monthMap.put("September", "09");
        monthMap.put("October", "10");
        monthMap.put("November", "11");
        monthMap.put("December", "12");

        // Set up RecyclerView
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenseList);
        expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseRecyclerView.setAdapter(expenseAdapter);

        // Set up Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthFilterSpinner.setAdapter(adapter);
        monthFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = parent.getItemAtPosition(position).toString();
                loadExpenses();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Initialize database and load expenses
        expenseDatabase = ExpenseDatabase.getInstance(this); // Use the singleton pattern
        loadExpenses();
    }

    private void loadExpenses() {
        expenseList.clear();
        String monthNumber = monthMap.get(selectedMonth);
        if (monthNumber != null) {
            // Load expenses based on the selected month number
            expenseList.addAll(expenseDatabase.getExpensesByMonth(monthNumber));
        } else {
            // Handle case where selectedMonth is "Current Month" or invalid
            expenseList.addAll(expenseDatabase.getAllExpenses());
        }
        updateTotalAmount();
        expenseAdapter.notifyDataSetChanged();
    }

    private void updateTotalAmount() {
        double totalAmount = 0;
        for (Expense expense : expenseList) {
            totalAmount += expense.getAmount();
        }
        totalAmountTextView.setText(String.format("Total Amount:    ₹%.2f", totalAmount));
    }
}