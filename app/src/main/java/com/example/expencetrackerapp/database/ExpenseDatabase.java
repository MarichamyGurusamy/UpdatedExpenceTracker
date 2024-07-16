package com.example.expencetrackerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "expense_tracker.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "expenses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SENDER = "sender";
    private static final String COLUMN_MESSAGE_BODY = "message_body";
    private static final String COLUMN_AMOUNT = "amount";

    public ExpenseDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_SENDER + " TEXT," +
                COLUMN_MESSAGE_BODY + " TEXT," +
                COLUMN_AMOUNT + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SENDER, expense.getSender());
        values.put(COLUMN_MESSAGE_BODY, expense.getMessageBody());
        values.put(COLUMN_AMOUNT, expense.getAmount());
        long newRowId = db.insert(TABLE_NAME, null, values);
        if (newRowId == -1) {
            Log.e("ExpenseDatabase", "Failed to insert expense");
        } else {
            Log.d("ExpenseDatabase", "Expense inserted successfully with ID: " + newRowId);
        }
        db.close();
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            try {
                // Get column indexes
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int senderIndex = cursor.getColumnIndex(COLUMN_SENDER);
                int messageIndex = cursor.getColumnIndex(COLUMN_MESSAGE_BODY);
                int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);

                // Check if all required columns exist
                if (idIndex != -1 && senderIndex != -1 && messageIndex != -1 && amountIndex != -1) {
                    if (cursor.moveToFirst()) {
                        do {
                            // Retrieve data from cursor
                            Expense expense = new Expense(
                                    cursor.getString(senderIndex),
                                    cursor.getString(messageIndex),
                                    cursor.getDouble(amountIndex)
                            );
                            expense.setId(cursor.getInt(idIndex));
                            expenseList.add(expense);
                        } while (cursor.moveToNext());
                    }
                } else {
                    // Log error or handle missing columns
                    Log.e("ExpenseDatabase", "One or more column indexes are invalid");
                }
            } finally {
                cursor.close();
            }
        } else {
            Log.e("ExpenseDatabase", "Cursor is null while retrieving expenses");
        }

        //db.close();
        return expenseList;
    }
}