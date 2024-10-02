package com.example.expencetrackerapp.ui.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expencetrackerapp.databinding.OtpVerificationBinding;

public class MobileNumberActivity extends AppCompatActivity {

    private OtpVerificationBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Set click listener for verifying the mobile number
        binding.etVerifyMobileNumber.setOnClickListener(v -> {
            // Fetch the mobile number from the EditText field
            String mobileNumber = binding.etMobileNumber.getText().toString().trim();

            // Check if the mobile number is exactly 10 digits long
            if (mobileNumber.length() == 10) {
                // If valid, navigate to OTP verification activity
                Intent intent = new Intent(MobileNumberActivity.this, OTPVerifyActivity.class);
                intent.putExtra("mobileNumber", mobileNumber);  // Pass mobile number to the next activity
                startActivity(intent);
                finish();
            } else {
                // Show toast message if the mobile number is invalid
                Toast.makeText(MobileNumberActivity.this, "Please enter a valid 10-digit mobile number", Toast.LENGTH_SHORT).show();
            }
        });
    }
}