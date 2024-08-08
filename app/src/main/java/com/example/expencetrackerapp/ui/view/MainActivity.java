package com.example.expencetrackerapp.ui.view;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements ExpenseAdapter.OnExpenseClickListener {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }



    @Override
    public void onExpenseClick(Expense expense) {

    }

}