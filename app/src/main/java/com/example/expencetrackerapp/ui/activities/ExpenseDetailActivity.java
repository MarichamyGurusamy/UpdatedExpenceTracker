package com.example.expencetrackerapp.ui.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.database.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;

public class ExpenseDetailActivity extends AppCompatActivity {

    private ExpenseDatabase database;
    private Expense expense;

    private EditText editRecipient, editAmount, editDate;
    private Spinner categorySpinner, bankSpinner;
    private Button btnUpdate, btnDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expence_list); // Use existing layout

        // Initialize database
        database = ExpenseDatabase.getInstance(this);

        // Retrieve expense details from intent
        if (getIntent().hasExtra("expense")) {
            expense = (Expense) getIntent().getSerializableExtra("expense");
        }

        // Show popup dialog
        showExpenseDetailPopup();
    }

    private void showExpenseDetailPopup() {
        final Dialog popupDialog = new Dialog(this);
        popupDialog.setContentView(R.layout.popup_edit_expense); // Your popup XML

        // Initialize views in the popup
        editRecipient = popupDialog.findViewById(R.id.edit_recipient);
        editAmount = popupDialog.findViewById(R.id.edit_amount);
        editDate = popupDialog.findViewById(R.id.edit_date);
        categorySpinner = popupDialog.findViewById(R.id.category_spinner);
        bankSpinner = popupDialog.findViewById(R.id.bank_spinner);
        btnUpdate = popupDialog.findViewById(R.id.btn_update);
        btnDelete = popupDialog.findViewById(R.id.btn_delete);

        // Set initial values
        if (expense != null) {
            editRecipient.setText(expense.getRecipient());
            editAmount.setText(String.valueOf(expense.getAmount()));
            editDate.setText(expense.getDate());
            // Set category and bank spinner values here
        }

        // Set up category spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Set up bank spinner
        ArrayAdapter<CharSequence> bankAdapter = ArrayAdapter.createFromResource(
                this, R.array.bank_array, android.R.layout.simple_spinner_item);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(bankAdapter);

        // Set up Update button
        btnUpdate.setOnClickListener(v -> updateExpense());

        // Set up Delete button
        btnDelete.setOnClickListener(v -> deleteExpense());

        popupDialog.show();
    }

    private void updateExpense() {
        String recipient = editRecipient.getText().toString();
        String amountStr = editAmount.getText().toString();
        String date = editDate.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        String bankName = bankSpinner.getSelectedItem().toString();

        if (recipient.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);
        expense.setRecipient(recipient);
        expense.setAmount(amount);
        expense.setDate(date);
        expense.setCategory(category);
        expense.setBankName(bankName);

        database.updateExpense(expense);

        Toast.makeText(this, "Expense updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void deleteExpense() {
        if (expense != null) {
            database.deleteExpense(expense.getId());
            Toast.makeText(this, "Expense deleted successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}