package com.example.expencetrackerapp.ui.activities;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.concurrent.ExecutionException;

public class ExpenseListActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

    private ExpenseDatabase expenseDatabase;
     ArrayList<Expense> expenseList = new ArrayList<>();
    private ExpenseAdapter expenseAdapter;
    private RecyclerView expenseRecyclerView;
    private TextView totalAmountTextView;
    Spinner monthFilterSpinner;
    private String selectedMonth = "Current Month"; // Default value
    ArrayList<Expense> expenses = new ArrayList<>();
    ArrayList<Expense> monthExpenses  = new ArrayList<>();;
    private Map<String, String> monthMap  = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence_list); // Ensure this matches the XML layout file name

        // Initialize UI elements
        expenseRecyclerView = findViewById(R.id.recyclerViewExpenses);
        totalAmountTextView = findViewById(R.id.total_amount);
        monthFilterSpinner = findViewById(R.id.month_filter);

        expenseDatabase = ExpenseDatabase.getDatabase(this); // Use the singleton pattern

        // Set up Spinner month mapping
        monthMap = new HashMap<>();
        monthMap.put("Month", "00");
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

//        new Thread(() -> {
//
//        expenseDatabase.expenseDao().insertExpense(new Expense("John Doe",  123.45 ,"29-07-2024","Grocery" ,"SBI"));
//        expenseDatabase.expenseDao().insertExpense(new Expense("Jane Smith",  678.90 ,"30-07-2024","Shopping" ,"AXis"));
//        expenseDatabase.expenseDao().insertExpense(new Expense("Jane Smith",  678.90 ,"31-07-2024","Micsc" ,"ICIC"));
//        expenseDatabase.expenseDao().insertExpense(new Expense(" Smith",  678.90 ,"30-06-2024","Micsc" ,"IOB"));
//        expenseDatabase.expenseDao().insertExpense(new Expense("Jane ",  678.90 ,"30-05-2024","Food" ,"SBI"));
//        expenseDatabase.expenseDao().insertExpense(new Expense("Jane Sm",  678.90 ,"30-04-2024","Travel" ,"AXis"));
//
//        }).start();


//        loadExpenses();

        // Set up RecyclerView

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




        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(v -> {
            // Create an Intent to navigate to the main activity
            Intent intent = new Intent(ExpenseListActivity.this, MainActivity.class);
            intent.putParcelableArrayListExtra("expenseList", expenseList);
            startActivity(intent);
            finish(); // Optional: Close the current activity
        });
    }

    private void loadExpenses() {

        // Perform database operation on a background thread
        ExpenseDatabase.databaseWriteExecutor.execute(() -> {

            String monthNumber = monthMap.get(selectedMonth);
            Log.d("TAG" ," monthNumber >>> " + monthNumber);

            if ( monthNumber != null && monthNumber.equals("00")) {
                // Load expenses based on the selected month number
                expenses = (ArrayList<Expense>) expenseDatabase.expenseDao().getAllExpenses();
            } else {
                // Handle case where selectedMonth is "Current Month" or invalid
                monthExpenses = (ArrayList<Expense>) expenseDatabase.expenseDao().getExpensesByMonth(monthNumber);


            }

            runOnUiThread(() -> {
                expenseList.clear();
                if (!expenses.isEmpty()){
                    expenseList.addAll(expenses);
                }else {
                    expenseList.addAll(monthExpenses);
                }

                updateTotalAmount();
                expenseAdapter = new ExpenseAdapter(this, expenseList, this); // Use the correct constructor
                expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                expenseRecyclerView.setAdapter(expenseAdapter);
                expenseAdapter.notifyDataSetChanged();
            });
        });
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

            Expense updatedExpense = new Expense(updatedRecipient, updatedAmount, updatedDate, updatedCategory, updatedBank);
            updatedExpense.setId(expense.getId()); // Set the existing ID for update

            // Perform update on a background thread
            ExpenseDatabase.databaseWriteExecutor.execute(() -> {
                expenseDatabase.expenseDao().updateExpense(updatedExpense);
                runOnUiThread(() -> {
                    loadExpenses(); // Refresh the expense list
                    dialog.dismiss();
                });
            });
        });

        // Delete button click listener
        btnDelete.setOnClickListener(v -> {
            // Perform delete on a background thread
            ExpenseDatabase.databaseWriteExecutor.execute(() -> {
                expenseDatabase.expenseDao().deleteExpense(expense);
                runOnUiThread(() -> {
                    loadExpenses(); // Refresh the expense list
                    dialog.dismiss();
                });
            });
        });

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        // Show the dialog
        dialog.show();
    }
}