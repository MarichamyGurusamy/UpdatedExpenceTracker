package com.example.expencetrackerapp.ui.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.databinding.ActivityVerifyBinding;

public class OTPVerifyActivity extends AppCompatActivity {

    ActivityVerifyBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bvVerify.setOnClickListener(v -> {
        Intent intent = new Intent(this , AllDetailsActivity.class);
        startActivity(intent);

        });




    }
}
