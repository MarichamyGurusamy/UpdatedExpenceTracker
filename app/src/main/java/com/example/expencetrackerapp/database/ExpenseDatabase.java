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
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "expenses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RECIPIENT = "recipient";
    private static final String COLUMN_MESSAGE_BODY = "message_body";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_BANK_NAME = "bank_name";
    private static final String COLUMN_CATEGORY = "category";

    private static ExpenseDatabase instance;

    private ExpenseDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ExpenseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new ExpenseDatabase(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_RECIPIENT + " TEXT," +
                COLUMN_MESSAGE_BODY + " TEXT," +
                COLUMN_AMOUNT + " REAL," +
                COLUMN_DATE + " TEXT," +
                COLUMN_BANK_NAME + " TEXT," +
                COLUMN_CATEGORY + " TEXT)";
        db.execSQL(createTable);

        // Populate test data
        populateTestData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_BANK_NAME + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_CATEGORY + " TEXT");
        }
        // Handle further upgrades here if necessary
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPIENT, expense.getRecipient());
        values.put(COLUMN_MESSAGE_BODY, expense.getMessage()); // Use getMessage() for the message body
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_DATE, expense.getDate());
        values.put(COLUMN_BANK_NAME, expense.getBankName());
        values.put(COLUMN_CATEGORY, expense.getCategory());
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

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int recipientIndex = cursor.getColumnIndex(COLUMN_RECIPIENT);
                int messageIndex = cursor.getColumnIndex(COLUMN_MESSAGE_BODY);
                int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int bankNameIndex = cursor.getColumnIndex(COLUMN_BANK_NAME);
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idIndex);
                    String recipient = cursor.getString(recipientIndex);
                    String message = cursor.getString(messageIndex);
                    double amount = cursor.getDouble(amountIndex);
                    String date = cursor.getString(dateIndex);
                    String bankName = cursor.getString(bankNameIndex);
                    String category = cursor.getString(categoryIndex);

                    Expense expense = new Expense(id, recipient, message, amount, date, bankName, category);
                    expenseList.add(expense);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return expenseList;
    }

    public List<Expense> getExpensesByMonth(String month) {
        List<Expense> expenseList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE strftime('%m', " + COLUMN_DATE + ") = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{month});

        if (cursor != null) {
            try {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int recipientIndex = cursor.getColumnIndex(COLUMN_RECIPIENT);
                int messageIndex = cursor.getColumnIndex(COLUMN_MESSAGE_BODY);
                int amountIndex = cursor.getColumnIndex(COLUMN_AMOUNT);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int bankNameIndex = cursor.getColumnIndex(COLUMN_BANK_NAME);
                int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idIndex);
                    String recipient = cursor.getString(recipientIndex);
                    String message = cursor.getString(messageIndex);
                    double amount = cursor.getDouble(amountIndex);
                    String date = cursor.getString(dateIndex);
                    String bankName = cursor.getString(bankNameIndex);
                    String category = cursor.getString(categoryIndex);

                    Expense expense = new Expense(id, recipient, message, amount, date, bankName, category);
                    expenseList.add(expense);
                }
            } finally {
                cursor.close();
            }
        }
        db.close();
        return expenseList;
    }

    public void updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPIENT, expense.getRecipient());
        values.put(COLUMN_MESSAGE_BODY, expense.getMessage()); // Use getMessage() for the message body
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_DATE, expense.getDate());
        values.put(COLUMN_BANK_NAME, expense.getBankName());
        values.put(COLUMN_CATEGORY, expense.getCategory());

        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(expense.getId())});
        if (rowsAffected > 0) {
            Log.d("ExpenseDatabase", "Expense updated successfully with ID: " + expense.getId());
        } else {
            Log.e("ExpenseDatabase", "Failed to update expense with ID: " + expense.getId());
        }
        db.close();
    }

    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (rowsAffected > 0) {
            Log.d("ExpenseDatabase", "Expense deleted successfully with ID: " + id);
        } else {
            Log.e("ExpenseDatabase", "Failed to delete expense with ID: " + id);
        }
        db.close();
    }

    private void populateTestData(SQLiteDatabase db) {
        // Add some test data
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPIENT, "Test Recipient");
        values.put(COLUMN_MESSAGE_BODY, "Test Message Body");
        values.put(COLUMN_AMOUNT, 100.50);
        values.put(COLUMN_DATE, "2024-07-01");
        values.put(COLUMN_BANK_NAME, "Test Bank");
        values.put(COLUMN_CATEGORY, "Test Category");
        db.insert(TABLE_NAME, null, values);
    }
}