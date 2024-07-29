package com.example.expencetrackerapp.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SMSReceiver";
    private final Map<String, String> transactionCategories = new HashMap<>();

    public SMSReceiver() {
        // Initialize transaction categories
        transactionCategories.put("Zomato", "Food");
        transactionCategories.put("Zepto", "Grocery");
        transactionCategories.put("Amazon", "Shopping");
        transactionCategories.put("Store", "Grocery");
        transactionCategories.put("Hotel", "Food");
        // Add more mappings as needed
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;

        if (bundle != null) {
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                if (pdus != null) {
                    messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        String messageBody = messages[i].getMessageBody();
                        String messageDate = getCurrentDate(); // Use current date

                        if (isTransactionMessage(messageBody)) {
                            double amount = extractAmount(messageBody);
                            String recipient = extractRecipientFromMessage(messageBody); // Extract recipient
                            String category = determineCategory(messageBody); // Determine category
                            String bankName = extractBankName(messageBody); // Extract bank name

                            // Save to Room database
                            ExpenseDatabase db = ExpenseDatabase.getDatabase(context); // Use singleton pattern
                            Expense expense = new Expense(recipient, amount, messageDate, category, bankName);
                            ExpenseDatabase.databaseWriteExecutor.execute(() -> db.expenseDao().insertExpense(expense));

                            Log.d(TAG, "Transaction SMS saved: " + messageBody);
                        } else {
                            Log.d(TAG, "Non-transaction SMS ignored: " + messageBody);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in onReceive", e);
            }
        } else {
            Log.e(TAG, "Bundle is null in onReceive");
        }
    }

    private boolean isTransactionMessage(String messageBody) {
        String lowerCaseMessage = messageBody.toLowerCase();
        return lowerCaseMessage.contains("paid") || lowerCaseMessage.contains("debited")
                || lowerCaseMessage.contains("spent") || lowerCaseMessage.contains("transaction")
                || lowerCaseMessage.contains("amt sent") || lowerCaseMessage.contains("sent");
    }

    private double extractAmount(String messageBody) {
        Pattern pattern = Pattern.compile("Amt Sent Rs\\.(\\d+\\.\\d+)|debited by (\\d+\\.\\d+)|debited (\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(messageBody);
        if (matcher.find()) {
            String amountStr = matcher.group(1) != null ? matcher.group(1) :
                    matcher.group(2) != null ? matcher.group(2) :
                            matcher.group(3);
            return Double.parseDouble(amountStr);
        }
        return 0.0;
    }

    private String extractRecipientFromMessage(String messageBody) {
        Pattern pattern = Pattern.compile("To\\s+(\\w+\\s+\\w+)|trf to (\\w+\\s+\\w+)|trf to (\\w+)|to (\\w+)");
        Matcher matcher = pattern.matcher(messageBody);
        if (matcher.find()) {
            String recipient = matcher.group(1) != null ? matcher.group(1) :
                    matcher.group(2) != null ? matcher.group(2) :
                            matcher.group(3) != null ? matcher.group(3) :
                                    matcher.group(4);
            return recipient.trim();
        }
        return "Unknown";
    }

    private String determineCategory(String messageBody) {
        String lowerCaseMessage = messageBody.toLowerCase();
        for (Map.Entry<String, String> entry : transactionCategories.entrySet()) {
            if (lowerCaseMessage.contains(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }
        return "Miscellaneous"; // Default category
    }

    private String extractBankName(String messageBody) {
        if (messageBody.contains("HDFC")) {
            return "HDFC";
        } else if (messageBody.contains("SBI")) {
            return "SBI";
        } else if (messageBody.contains("ICICI")) {
            return "ICICI";
        } else if (messageBody.contains("AXIS")) {
            return "AXIS";
        } else if (messageBody.contains("KOTAK")) {
            return "KOTAK";
        } else if (messageBody.contains("INDUSIND")) {
            return "INDUSIND";
        } else if (messageBody.contains("Bank of Baroda")) {
            return "Bank of Baroda";
        } else if (messageBody.contains("Canara")) {
            return "Canara";
        } else if (messageBody.contains("YES BANK")) {
            return "YES BANK";
        } else if (messageBody.contains("PNB")) {
            return "PNB";
        }
        return "Unknown";
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }
}