package com.example.expencetrackerapp.ui.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

    ActivityMainBinding binding;
    private static final int SPLASH_DURATION = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        // Get the SharedPreferences object
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve the boolean value (default is false if not set)
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                // Start LoginActivity
                if (isLoggedIn){
                    intent = new Intent(MainActivity.this, AllDetailsActivity.class);
                }else {
                    intent = new Intent(MainActivity.this, MobileNumberActivity.class);
                }
                startActivity(intent);

                // Finish SplashActivity to prevent going back to it
                finish();
            }
        }, SPLASH_DURATION);
    }


    @Override
    public void onExpenseClick(Expense expense) {

    }
}
