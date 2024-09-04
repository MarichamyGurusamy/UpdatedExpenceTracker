package com.example.expencetrackerapp.ui.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

        // Set click listener to verify button
        binding.bvVerify.setOnClickListener(v -> {
            Intent intent = new Intent(this, AllDetailsActivity.class);
            startActivity(intent);
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
        // Create an intent for the notification
        Intent intent = new Intent(this, OTPVerifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)  // Ensure this icon is in your drawable resources
                .setContentTitle("Your OTP Code")
                .setContentText("Your OTP is: " + otp)
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
}
