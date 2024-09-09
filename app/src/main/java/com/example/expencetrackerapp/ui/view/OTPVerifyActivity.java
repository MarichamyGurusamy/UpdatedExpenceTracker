package com.example.expencetrackerapp.ui.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.databinding.ActivityVerifyBinding;

import java.util.Random;

public class OTPVerifyActivity extends AppCompatActivity {

    private ActivityVerifyBinding binding;
    private static final String CHANNEL_ID = "OTP_CHANNEL";
    private int generatedOtp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create notification channel
        createNotificationChannel();

        // Generate and send OTP notification
        generateAndSendOtp();

        // Set up the TextWatchers for OTP input fields and handle backspace
        setupOtpInputs();

        // Set click listener to verify button
        binding.bvVerify.setOnClickListener(v -> verifyOtp());
    }

    private void generateAndSendOtp() {
        // Generate a 6-digit random OTP
        Random random = new Random();
        generatedOtp = 100000 + random.nextInt(900000);

        // Display the OTP in a notification
        sendNotification(generatedOtp);

        // Display a Toast message (for debugging purposes)
        Toast.makeText(this, "OTP: " + generatedOtp, Toast.LENGTH_SHORT).show();

        // Autofill OTP into the input fields
        autofillOtp(generatedOtp);
    }


    private void autofillOtp(int otp) {
        // Convert the generated OTP to a string
        String otpString = String.valueOf(otp);

        // Set each character to the corresponding input field
        if (otpString.length() == 6) {
            binding.inputCodeOne.setText(String.valueOf(otpString.charAt(0)));
            binding.inputCodeTwo.setText(String.valueOf(otpString.charAt(1)));
            binding.inputCodeThree.setText(String.valueOf(otpString.charAt(2)));
            binding.inputCodeFour.setText(String.valueOf(otpString.charAt(3)));
            binding.inputCodeFive.setText(String.valueOf(otpString.charAt(4)));
            binding.inputCodeSix.setText(String.valueOf(otpString.charAt(5)));
        }
    }


    private void sendNotification(int otp) {
        // Create an intent for the notification
        Intent intent = new Intent(this, OTPVerifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.wallet)  // Ensure this icon is in your drawable resources
                .setContentTitle("Expense Tracker")
                .setContentText( otp + " is your login OTP for Expense Tracker. Do not share it with anyone")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel (required for Android 8.0 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "OTP Channel";
            String description = "Channel for OTP notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void verifyOtp() {
        // Collect the input from EditText fields
        String inputCodeOne = binding.inputCodeOne.getText().toString();
        String inputCodeTwo = binding.inputCodeTwo.getText().toString();
        String inputCodeThree = binding.inputCodeThree.getText().toString();
        String inputCodeFour = binding.inputCodeFour.getText().toString();
        String inputCodeFive = binding.inputCodeFive.getText().toString();
        String inputCodeSix = binding.inputCodeSix.getText().toString();

        // Combine the inputs into a single OTP
        String enteredOtp = inputCodeOne + inputCodeTwo + inputCodeThree + inputCodeFour + inputCodeFive + inputCodeSix;

        // Check if entered OTP is equal to the generated OTP
        if (!enteredOtp.isEmpty()) {
            if (Integer.parseInt(enteredOtp) == generatedOtp) {
                // OTP matches, navigate to the next activity
                Intent intent = new Intent(OTPVerifyActivity.this, AllDetailsActivity.class);
                startActivity(intent);
                finish(); // Optional: Close this activity
            } else {
                // OTP doesn't match, show error message
                Toast.makeText(OTPVerifyActivity.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // If OTP is not completely entered
            Toast.makeText(OTPVerifyActivity.this, "Please enter all digits.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupOtpInputs() {
        // Add TextWatchers and KeyListeners to each input field
        binding.inputCodeOne.addTextChangedListener(new GenericTextWatcher(binding.inputCodeOne, binding.inputCodeTwo));
        binding.inputCodeTwo.addTextChangedListener(new GenericTextWatcher(binding.inputCodeTwo, binding.inputCodeThree));
        binding.inputCodeThree.addTextChangedListener(new GenericTextWatcher(binding.inputCodeThree, binding.inputCodeFour));
        binding.inputCodeFour.addTextChangedListener(new GenericTextWatcher(binding.inputCodeFour, binding.inputCodeFive));
        binding.inputCodeFive.addTextChangedListener(new GenericTextWatcher(binding.inputCodeFive, binding.inputCodeSix));
        binding.inputCodeSix.addTextChangedListener(new GenericTextWatcher(binding.inputCodeSix, null));

        // Set up key listeners to handle backspace
        setupBackspaceHandling(binding.inputCodeOne, null);
        setupBackspaceHandling(binding.inputCodeTwo, binding.inputCodeOne);
        setupBackspaceHandling(binding.inputCodeThree, binding.inputCodeTwo);
        setupBackspaceHandling(binding.inputCodeFour, binding.inputCodeThree);
        setupBackspaceHandling(binding.inputCodeFive, binding.inputCodeFour);
        setupBackspaceHandling(binding.inputCodeSix, binding.inputCodeFive);
    }

    // Generic TextWatcher to handle OTP input
    private class GenericTextWatcher implements TextWatcher {

        private final EditText currentView;
        private final EditText nextView;

        public GenericTextWatcher(EditText currentView, EditText nextView) {
            this.currentView = currentView;
            this.nextView = nextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().isEmpty()) {
                // Move to next field if the current one is filled
                if (nextView != null) {
                    nextView.requestFocus();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    // Method to set up backspace handling
    private void setupBackspaceHandling(EditText currentEditText, EditText previousEditText) {
        currentEditText.setOnKeyListener((View v, int keyCode, KeyEvent event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                if (currentEditText.getText().toString().isEmpty() && previousEditText != null) {
                    // Move to the previous EditText and delete the last character
                    previousEditText.requestFocus();
                    previousEditText.setText(""); // Optionally clear the previous field
                }
            }
            return false;
        });
    }
}