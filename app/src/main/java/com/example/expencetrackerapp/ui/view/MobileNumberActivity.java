package com.example.expencetrackerapp.ui.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.databinding.OtpVerificationBinding;

public class MobileNumberActivity extends AppCompatActivity {

    OtpVerificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etVerifyMobileNumber.setOnClickListener(v -> {
            Intent intent = new Intent(this , OTPVerifyActivity.class);
            startActivity(intent);
        });


    }
}
