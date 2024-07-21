package com.example.expencetrackerapp.ui.activities;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.R;

public class MainActivity extends AppCompatActivity {
    public static void deleteOldDatabase(Context context) {
        context.deleteDatabase("expense_tracker.db");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //deleteOldDatabase(this);
        // Initialize the button for viewing all expenses
        Button viewAllExpensesButton = findViewById(R.id.view_all_expenses_button);

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
}