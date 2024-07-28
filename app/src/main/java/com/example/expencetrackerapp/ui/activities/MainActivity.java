package com.example.expencetrackerapp.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

public class MainActivity extends AppCompatActivity {
    public static void deleteOldDatabase(Context context) {
        context.deleteDatabase("expense_tracker.db");
    }

    private ExpenseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Room database
        db = ExpenseDatabase.getDatabase(this);

        // Populate initial data if necessary
        //populateInitialData();

        // Initialize the button for viewing all expenses
        Button viewAllExpensesButton = findViewById(R.id.view_all_expenses_button);

        Button ExpensesButton = findViewById(R.id.view_all_expenses_button1);

        ExpensesButton.setOnClickListener(view -> {
            updateCreateTodo();
        });



        // Set an OnClickListener on the button
        viewAllExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the ExpenseListActivity
                Intent intent = new Intent(MainActivity.this, ExpenseListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void updateCreateTodo() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activty_item, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();




        dialog.show();
    }

//    private void populateInitialData() {
//        ExpenseDatabase.databaseWriteExecutor.execute(() -> {
//            // Check if the database is empty
//            if (db.expenseDao().getAllExpenses().isEmpty()) {
//                // Add sample expenses
//                Expense sampleExpense = new Expense("Sample Recipient", 100.50, "2024-07-01", "Sample Category", "Sample Bank", "Sample Message");
//                db.expenseDao().insertExpense(sampleExpense); // Correct method name
//            }
//        });
//    }
}