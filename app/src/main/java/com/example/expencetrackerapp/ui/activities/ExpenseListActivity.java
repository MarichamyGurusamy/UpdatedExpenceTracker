package com.example.expencetrackerapp.ui.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class ExpenseListActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

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
        expenseAdapter = new ExpenseAdapter(this, expenseList, this); // Use the correct constructor
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

        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> {
            // Create an Intent to navigate to the main activity
            Intent intent = new Intent(ExpenseListActivity.this, MainActivity.class); // Replace MainActivity with your main activity class
            startActivity(intent);
            finish(); // Optional: Close the current activity
        });

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
        totalAmountTextView.setText(String.format("Total Amount: ₹%.2f", totalAmount));
    }

    @Override
    public void onExpenseClick(Expense expense) {
        // Show the popup dialog
        showExpenseDialog(expense);
    }

    private void showExpenseDialog(Expense expense) {
        // Inflate the popup layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_edit_expense, null);

        // Initialize dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        dialog.setTitle("Edit Expense");

        // Initialize UI elements in the dialog
        EditText editRecipient = dialogView.findViewById(R.id.edit_recipient);
        EditText editAmount = dialogView.findViewById(R.id.edit_amount);
        EditText editDate = dialogView.findViewById(R.id.edit_date);
        Spinner categorySpinner = dialogView.findViewById(R.id.category_spinner);
        Spinner bankSpinner = dialogView.findViewById(R.id.bank_spinner);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        // Populate fields with expense data
        editRecipient.setText(expense.getRecipient());
        editAmount.setText(String.valueOf(expense.getAmount()));
        editDate.setText(expense.getDate());

        // Set up category spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        int categoryPosition = categoryAdapter.getPosition(expense.getCategory());
        categorySpinner.setSelection(categoryPosition);

        // Set up bank spinner
        ArrayAdapter<CharSequence> bankAdapter = ArrayAdapter.createFromResource(this,
                R.array.bank_array, android.R.layout.simple_spinner_item);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(bankAdapter);
        int bankPosition = bankAdapter.getPosition(expense.getBankName());
        bankSpinner.setSelection(bankPosition);

        // Update button click listener
        btnUpdate.setOnClickListener(v -> {
            // Update expense logic here
            String updatedRecipient = editRecipient.getText().toString();
            double updatedAmount = Double.parseDouble(editAmount.getText().toString());
            String updatedDate = editDate.getText().toString();
            String updatedCategory = categorySpinner.getSelectedItem().toString();
            String updatedBank = bankSpinner.getSelectedItem().toString();

            Expense updatedExpense = new Expense(expense.getId(), updatedRecipient, expense.getMessage(), updatedAmount, updatedDate, updatedBank, updatedCategory);
            expenseDatabase.updateExpense(updatedExpense);
            loadExpenses();
            dialog.dismiss();
        });

        // Delete button click listener
        btnDelete.setOnClickListener(v -> {
            expenseDatabase.deleteExpense(expense.getId());
            loadExpenses();
            dialog.dismiss();
        });

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();
    }
}