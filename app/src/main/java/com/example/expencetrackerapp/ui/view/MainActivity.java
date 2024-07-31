package com.example.expencetrackerapp.ui.view;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

//
//    ArrayList<Expense> expenseList = new ArrayList<>();
//    ArrayList<Expense> expenseDetalisList = new ArrayList<>();
//    ExpenseAdapter expenseAdapter;
//    ExpenseDatabase db;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


//        Intent intent = getIntent();
//        expenseList = intent.getParcelableArrayListExtra("expenseList");
//
//
//        binding.profilePicture.setOnClickListener(v -> {
//            binding.viewAllExpensesButton1.setVisibility(View.VISIBLE);
//            binding.viewAllExpensesButton.setVisibility(View.VISIBLE);
//            binding.recyclerView.setVisibility(View.GONE);
//            binding.profilePicture.setVisibility(View.GONE);
//            binding.profilePictureImageView.setVisibility(View.VISIBLE);
//            binding.lineLogout.setVisibility(View.VISIBLE);
//        });
//
//
//        // Initialize Room database
//        db = ExpenseDatabase.getDatabase(this);
//
//        // Populate initial data if necessary
//        //populateInitialData();
//        // Initialize the button for viewing all expenses
//
//        binding.viewAllExpensesButton1.setOnClickListener(v -> updateCreateTodo());
//
//
//        // Set an OnClickListener on the button
//        binding.viewAllExpensesButton.setOnClickListener(v -> {
//            // Create an Intent to start the ExpenseListActivity
//            Intent intent1 = new Intent(MainActivity.this, ExpenseListActivity.class);
//            startActivity(intent1);
//        });
    }

//    private void updateCreateTodo() {
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View dialogView = inflater.inflate(R.layout.activty_item, null);
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        dialogBuilder.setView(dialogView);
//        AlertDialog dialog = dialogBuilder.create();
//
//        TextView textTitleShopping = dialogView.findViewById(R.id.recipient_text1);
//        TextView textTitleGrocery = dialogView.findViewById(R.id.recipient_text2);
//        TextView textTitleFood = dialogView.findViewById(R.id.recipient_text3);
//        TextView textTitleMicsc = dialogView.findViewById(R.id.recipient_text4);
//        TextView textTitleTravel = dialogView.findViewById(R.id.recipient_text5);
//
//
//        textTitleShopping.setOnClickListener(v -> {
//            SelectedDate("1");
//            dialog.dismiss();
//        });
//        textTitleGrocery.setOnClickListener(v -> {
//            SelectedDate("2");
//            dialog.dismiss();
//        });
//        textTitleFood.setOnClickListener(v -> {
//            SelectedDate("3");
//            dialog.dismiss();
//        });
//        textTitleMicsc.setOnClickListener(v -> {
//            SelectedDate("4");
//            dialog.dismiss();
//        });
//        textTitleTravel.setOnClickListener(v -> {
//            SelectedDate("5");
//            dialog.dismiss();
//        });
//
//
//        dialog.show();
//    }
//
//    private void SelectedDate(String postions) {
//
//        for (Expense expense : expenseList) {
//            if (expense.getCategory().equals("Shopping") && postions.equals("1")) {
//                expenseDetalisList.add(expense);
////                Log.d("TAG", " expenseList >>> 1 " + expenseDetalisList.size());
//            } else if (expense.getCategory().equals("Grocery") && postions.equals("2")) {
//                expenseDetalisList.add(expense);
////                Log.d("TAG", " expenseList >>> 2 " + expenseDetalisList.size());
//            } else if (expense.getCategory().equals("Food") && postions.equals("3")) {
//                expenseDetalisList.add(expense);
////                Log.d("TAG", " expenseList >>> 3 " + expenseDetalisList.size());
//            } else if (expense.getCategory().equals("Micsc") && postions.equals("4")) {
//                expenseDetalisList.add(expense);
////                Log.d("TAG", " expenseList >>> 4 " + expenseDetalisList.size());
//            } else if (expense.getCategory().equals("Travel") && postions.equals("5")) {
//                expenseDetalisList.add(expense);
////                Log.d("TAG", " expenseList >>> 5 " + expenseDetalisList.size());
//            }
//        }
//        if (!expenseDetalisList.isEmpty() && expenseDetalisList != null) {
//            binding.viewAllExpensesButton1.setVisibility(View.GONE);
//            binding.viewAllExpensesButton.setVisibility(View.GONE);
//            binding.recyclerView.setVisibility(View.VISIBLE);
//            binding.profilePicture.setVisibility(View.VISIBLE);
//            binding.profilePictureImageView.setVisibility(View.GONE);
//            binding.lineLogout.setVisibility(View.GONE);
//
//            expenseAdapter = new ExpenseAdapter(this, expenseDetalisList, this); // Use the correct constructor
//            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            binding.recyclerView.setAdapter(expenseAdapter);
//            expenseAdapter.notifyDataSetChanged();
//        } else {
//            binding.viewAllExpensesButton1.setVisibility(View.VISIBLE);
//            binding.viewAllExpensesButton.setVisibility(View.VISIBLE);
//            binding.recyclerView.setVisibility(View.GONE);
//            binding.profilePicture.setVisibility(View.GONE);
//            binding.profilePictureImageView.setVisibility(View.VISIBLE);
//            binding.lineLogout.setVisibility(View.VISIBLE);
//
//        }
//
//
//    }

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