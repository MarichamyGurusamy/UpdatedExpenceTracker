package com.example.expencetrackerapp.ui.view;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.databinding.OtpVerificationBinding;

import java.util.Random;

public class MobileNumberActivity extends AppCompatActivity {

    private OtpVerificationBinding binding;
    private static final String CHANNEL_ID = "OTP_CHANNEL";
    private int generatedOtp;
    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        binding.etVerifyMobileNumber.setOnClickListener(v -> {
            Intent intent = new Intent(this, OTPVerifyActivity.class);
            startActivity(intent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(MobileNumberActivity.this, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MobileNumberActivity.this,
                            new String[]{Manifest.permission.POST_NOTIFICATIONS},
                            REQUEST_CODE_POST_NOTIFICATIONS);
                } else {
                    generateAndSendOtp();
                }
            } else {
                generateAndSendOtp();
            }

        });
    }

    private void generateAndSendOtp() {
        // Generate a 6-digit random OTP
        Random random = new Random();
        generatedOtp = 100000 + random.nextInt(900000);

        // Display the OTP in a notification
        sendNotification(generatedOtp);

        // Display a Toast message (for debugging purposes)
        Toast.makeText(this, "OTP: " + generatedOtp, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification(int otp) {
        Log.d("Notification", "Sending OTP Notification: " + otp);

        Intent intent = new Intent(this, OTPVerifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notification)  // Ensure this icon is in your drawable resources
                .setContentTitle("Your OTP Code")
                .setContentText("Your OTP is: " + otp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

        Log.d("Notification", "Notification sent.");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, generate and send OTP
                generateAndSendOtp();
            } else {
                // Permission denied, notify the user
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
