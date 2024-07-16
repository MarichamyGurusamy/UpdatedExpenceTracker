package com.example.expencetrackerapp.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

public class SMSReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] messages = null;

        if (bundle != null) {
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                messages = new SmsMessage[pdus.length];
                for (int i = 0; i < messages.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String sender = messages[i].getDisplayOriginatingAddress();
                    String messageBody = messages[i].getMessageBody();

                    // Example logic to detect transaction messages
                    if (isTransactionMessage(messageBody)) {
                        double amount = extractAmountFromMessage(messageBody);

                        // Save to SQLite database
                        ExpenseDatabase db = new ExpenseDatabase(context);
                        Expense expense = new Expense(sender, messageBody, amount);
                        db.addExpense(expense);

                        Log.d(TAG, "Transaction SMS saved: " + messageBody);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception in onReceive", e);
            }
        }
    }

    private boolean isTransactionMessage(String messageBody) {
        // Basic example: Check if the message contains the word "amount" or "transaction"
        return messageBody.toLowerCase().contains("amount") || messageBody.toLowerCase().contains("transaction");
    }

    private double extractAmountFromMessage(String messageBody) {
        // Basic example: Extract the first number found in the message body
        String[] words = messageBody.split(" ");
        for (String word : words) {
            try {
                return Double.parseDouble(word.replaceAll("[^0-9.]", ""));
            } catch (NumberFormatException ignored) {
            }
        }
        return 0.0;
    }
}