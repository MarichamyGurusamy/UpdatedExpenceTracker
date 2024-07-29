package com.example.expencetrackerapp.ui.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {


    ArrayList<Expense> expenseList = new ArrayList<>();
    ArrayList<Expense> expenseDetalisList = new ArrayList<>();
    private ExpenseAdapter expenseAdapter;
    private RecyclerView expenseRecyclerView;
    Button viewAllExpensesButton, ExpensesButton;
    ImageView profileArrow, profileImageView;
    LinearLayout lineLogoutView;


    public static void deleteOldDatabase(Context context) {
        context.deleteDatabase("expense_tracker.db");
    }

    private ExpenseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        expenseList = intent.getParcelableArrayListExtra("expenseList");

        expenseRecyclerView = findViewById(R.id.recyclerView);
        profileArrow = findViewById(R.id.profile_picture);
        profileImageView = findViewById(R.id.profile_picture_image_view);
        lineLogoutView = findViewById(R.id.lineLogout);

        profileArrow.setOnClickListener(v -> {
            ExpensesButton.setVisibility(View.VISIBLE);
            viewAllExpensesButton.setVisibility(View.VISIBLE);
            expenseRecyclerView.setVisibility(View.GONE);
            profileArrow.setVisibility(View.GONE);
            profileImageView.setVisibility(View.VISIBLE);
            lineLogoutView.setVisibility(View.VISIBLE);
        });


//        intent.getStringExtra();
        // Initialize Room database
        db = ExpenseDatabase.getDatabase(this);

        // Populate initial data if necessary
        //populateInitialData();

        // Initialize the button for viewing all expenses
        viewAllExpensesButton = findViewById(R.id.view_all_expenses_button);

        ExpensesButton = findViewById(R.id.view_all_expenses_button1);

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

        TextView textTitleShopping = dialogView.findViewById(R.id.recipient_text1);
        TextView textTitleGrocery = dialogView.findViewById(R.id.recipient_text2);
        TextView textTitleFood = dialogView.findViewById(R.id.recipient_text3);
        TextView textTitleMicsc = dialogView.findViewById(R.id.recipient_text4);
        TextView textTitleTravel = dialogView.findViewById(R.id.recipient_text5);


        textTitleShopping.setOnClickListener(v -> {
            SelectedDate("1");
            dialog.dismiss();
        });
        textTitleGrocery.setOnClickListener(v -> {
            SelectedDate("2");
            dialog.dismiss();
        });
        textTitleFood.setOnClickListener(v -> {
            SelectedDate("3");
            dialog.dismiss();
        });
        textTitleMicsc.setOnClickListener(v -> {
            SelectedDate("4");
            dialog.dismiss();
        });
        textTitleTravel.setOnClickListener(v -> {
            SelectedDate("5");
            dialog.dismiss();
        });


        dialog.show();
    }

    private void SelectedDate(String postions) {

        for (Expense expense : expenseList) {
            if (expense.getCategory().equals("Shopping") && postions.equals("1")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 1 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Grocery") && postions.equals("2")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 2 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Food") && postions.equals("3")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 3 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Micsc") && postions.equals("4")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 4 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Travel") && postions.equals("5")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 5 " + expenseDetalisList.size());
            }
        }
        if (!expenseDetalisList.isEmpty() && expenseDetalisList != null) {
            ExpensesButton.setVisibility(View.GONE);
            viewAllExpensesButton.setVisibility(View.GONE);
            expenseRecyclerView.setVisibility(View.VISIBLE);
            profileArrow.setVisibility(View.VISIBLE);
            profileImageView.setVisibility(View.GONE);
            lineLogoutView.setVisibility(View.GONE);

            expenseAdapter = new ExpenseAdapter(this, expenseDetalisList, this); // Use the correct constructor
            expenseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            expenseRecyclerView.setAdapter(expenseAdapter);
            expenseAdapter.notifyDataSetChanged();
        } else {
            ExpensesButton.setVisibility(View.VISIBLE);
            viewAllExpensesButton.setVisibility(View.VISIBLE);
            expenseRecyclerView.setVisibility(View.GONE);
            profileArrow.setVisibility(View.GONE);
            profileImageView.setVisibility(View.VISIBLE);
            lineLogoutView.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onExpenseClick(Expense expense) {

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