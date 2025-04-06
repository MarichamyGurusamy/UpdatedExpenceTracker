package com.example.expencetrackerapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;
import com.example.expencetrackerapp.databinding.ActivityExpenceListBinding;
import com.example.expencetrackerapp.interfaces.FragmentBottomNavigation;
import com.example.expencetrackerapp.viewmodel.ExpenseViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment implements ExpenseAdapter.OnExpenseClickListener {


    FragmentBottomNavigation communicator;

    ActivityExpenceListBinding binding;

    ExpenseDatabase expenseDatabase;

    ExpenseAdapter expenseAdapter;

    String selectedMonth = "Current Month";
    String selectedYear = "Current Year";

    ArrayList<Expense> expenses = new ArrayList<>();

    ArrayList<Expense> monthExpenses = new ArrayList<>();

    Map<String, String> monthMap = new HashMap<>();
    Map<String, String> yearMap = new HashMap<>();
    String selectedItem;


    String[] catgNames = {"Food", "Shopping", "Groceries", "Transport", "Miscellaneous", "Education"};

    Integer noteId;


    ExpenseViewModel expenseViewModel;


    String monthNumber = " ";

    private final String PREFERENCE_NAME = "MyPrefs";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            communicator = (FragmentBottomNavigation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentToActivityCommunicator");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        binding = ActivityExpenceListBinding.inflate(inflater, container, false);

        communicator.navigateBottomFrag(3, true);

        ViewModelProvider provider = new ViewModelProvider(getActivity());

        handleBackPress();

        expenseViewModel = provider.get(ExpenseViewModel.class);

        expenseDatabase = ExpenseDatabase.getDatabase(getContext()); // Use the singleton pattern

        expenseViewModel.getAllNotes().observe(getActivity(), notes -> {
            if (notes != null) {
                this.expenses = (ArrayList<Expense>) notes;
                double totalAmount = 0;
                for (Expense expense : notes) {
                    totalAmount += expense.getAmount();
                }
                binding.totalAmount.setText(String.format("Total Amount: ₹%.2f", totalAmount));

                loadExpenses(expenses);

            }
        });


        monthMap.put("Month", "00");
        monthMap.put("January", "01");
        monthMap.put("February", "02");
        monthMap.put("March", "03");
        monthMap.put("April", "04");
        monthMap.put("May", "05");
        monthMap.put("June", "06");
        monthMap.put("July", "07");
        monthMap.put("August", "08");
        monthMap.put("September", "09");
        monthMap.put("October", "10");
        monthMap.put("November", "11");
        monthMap.put("December", "12");

        // Adapter for the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set adapter to spinner
        binding.monthFilter.setAdapter(adapter);

        // Get the current month (0 for January, 11 for December)
        Calendar calendar = Calendar.getInstance();
        int currentMonthIndex = calendar.get(Calendar.MONTH);
        String currentMonthName = adapter.getItem(currentMonthIndex).toString();

        // Retrieve stored month from SharedPreferences if it exists
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        String savedMonth = sharedPreferences.getString("selectedmonth", null);

        Log.d("TAG", "Savemonth:" + savedMonth);

        if (savedMonth != null) {
            // If a month is saved, find its position in the adapter and set it as selected
            int savedMonthIndex = adapter.getPosition(savedMonth);
            binding.monthFilter.setSelection(savedMonthIndex);
        } else {
            // If no month is saved, set the current month as the default selected item
            binding.monthFilter.setSelection(currentMonthIndex);
        }

        // Handle selection changes
        binding.monthFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = parent.getItemAtPosition(position).toString();

                monthNumber = monthMap.get(selectedMonth);

                // Save the selected month and month number in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("monthnumber", monthNumber);
                editor.putString("selectedmonth", selectedMonth);
                editor.apply();

                // Call the method for the selected month, if not "00"
                if (!monthNumber.equals("00")) {
                    slecteParticiluarMonth(monthNumber);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection case, if needed
            }
        });

        yearMap = new HashMap<>();
        yearMap.put("Year", "00");
        yearMap.put("2023", "01");
        yearMap.put("2024", "02");
        yearMap.put("2025", "03");
        yearMap.put("2026", "04"); // Changed "03" to "04" for the next year

// Adapter for the year spinner
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.years_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.yearFilter.setAdapter(adapter1);

// Get the current year
        Calendar calendar1 = Calendar.getInstance();
        int currentYear = calendar1.get(Calendar.YEAR);

// Set the default selected year in the spinner
        String currentYearString = String.valueOf(currentYear);
        int currentYearIndex = adapter1.getPosition(currentYearString);

// Retrieve stored year from SharedPreferences if it exists
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        String savedYear = sharedPreferences1.getString("selectedyear", null);

        if (savedYear != null) {
            // If a year is saved, find its position in the adapter and set it as selected
            int savedYearIndex = adapter1.getPosition(savedYear);
            binding.yearFilter.setSelection(savedYearIndex);
        } else {
            // If no year is saved, set the current year as the default selected item
            binding.yearFilter.setSelection(currentYearIndex);
        }

// Handle selection changes for year filter
        binding.yearFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();
                String yearNumber = yearMap.get(selectedYear);

                // Save the selected year in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selectedyear", selectedYear);
                editor.apply();

                // Call the method for the selected year, if not "00"
                if (!yearNumber.equals("00")) {
                    //selectParticularYear(yearNumber);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle no selection case if needed
            }
        });

        binding.addTaskFABtn.setOnClickListener(v -> showExpenseDialog(true));

        binding.backAll.setOnClickListener(v -> {
        });

        return binding.getRoot();

    }

    private void slecteParticiluarMonth(String monthNumber) {

        expenseViewModel.getExpensesByMonth(monthNumber).observe(getActivity(), notes -> {

            if (notes != null) {
                this.monthExpenses = (ArrayList<Expense>) notes;
                double totalAmount = 0;
                for (Expense expense : notes) {
                    totalAmount += expense.getAmount();
                }
                binding.totalAmount.setText(String.format("Total Amount: ₹%.2f", totalAmount));

                loadExpenses(monthExpenses);

            }
        });
    }

    private void loadExpenses(ArrayList<Expense> expenses) {

        expenseAdapter = new ExpenseAdapter(expenses, this); // Use the correct constructor
        binding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewExpenses.setAdapter(expenseAdapter);
        expenseAdapter.notifyDataSetChanged();

    }

    @Override
    public void onExpenseClick(Expense expense) {
        // Show the popup dialog
        showExpenseDialog(expense);
    }

    private void showExpenseDialog(Expense expense) {
        // Inflate the popup layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_edit_expense, null);

        // Initialize dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(dialogView);
        dialog.setTitle("Edit Expense");

        // Initialize UI elements in the dialog
        EditText editRecipient = dialogView.findViewById(R.id.edit_recipient);
        EditText editAmount = dialogView.findViewById(R.id.edit_amount);
        EditText editDate = dialogView.findViewById(R.id.edit_date);
        Spinner categorySpinner = dialogView.findViewById(R.id.category_spinner);
        Spinner bankSpinner = dialogView.findViewById(R.id.bank_spinner);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        // Populate fields with expense data
        editRecipient.setText(expense.getRecipient());
        editAmount.setText(String.valueOf(expense.getAmount()));
        editDate.setText(expense.getDate());

        // Set up category spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
        int categoryPosition = categoryAdapter.getPosition(expense.getCategory());
        categorySpinner.setSelection(categoryPosition);

        // Set up bank spinner
        ArrayAdapter<CharSequence> bankAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.bank_array, android.R.layout.simple_spinner_item);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(bankAdapter);
        int bankPosition = bankAdapter.getPosition(expense.getBankName());
        bankSpinner.setSelection(bankPosition);

        btnUpdate.setOnClickListener(v -> updateDetails(expense, editRecipient, editAmount, editDate, categorySpinner, bankSpinner, dialog));

        btnDelete.setOnClickListener(v -> {
            ExpenseDatabase.databaseWriteExecutor.execute(() -> expenseViewModel.deleteItme(expense.getId()));
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }

    private void updateDetails(Expense expense, EditText editRecipient, EditText editAmount, EditText editDate, Spinner categorySpinner, Spinner bankSpinner, Dialog dialog) {

        String updatedRecipient = editRecipient.getText().toString();
        double updatedAmount = Double.parseDouble(editAmount.getText().toString());
        String updatedDate = editDate.getText().toString();
        String updatedCategory = categorySpinner.getSelectedItem().toString();
        String updatedBank = bankSpinner.getSelectedItem().toString();

        Expense updatedExpense = new Expense(expense.getId(), updatedRecipient, updatedAmount, updatedDate, updatedCategory, updatedBank);

        ExpenseDatabase.databaseWriteExecutor.execute(() -> expenseViewModel.update(updatedExpense));


        dialog.dismiss();

    }

    private void showExpenseDialog(boolean isExpense) {
        // Inflate the popup layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_edit_expense, null);

        // Initialize dialog
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(dialogView);
        dialog.setTitle("Edit Expense");

        // Initialize UI elements in the dialog
        EditText editRecipient = dialogView.findViewById(R.id.edit_recipient);
        EditText editAmount = dialogView.findViewById(R.id.edit_amount);
        EditText editDate = dialogView.findViewById(R.id.edit_date);
        Spinner categorySpinner = dialogView.findViewById(R.id.category_spinner);
        Spinner bankSpinner = dialogView.findViewById(R.id.bank_spinner);
        Button btnUpdate = dialogView.findViewById(R.id.btn_update);
        Button btnDelete = dialogView.findViewById(R.id.btn_delete);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        if (isExpense) {
            btnUpdate.setText("ADD");
            btnDelete.setVisibility(View.INVISIBLE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, catgNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> bankAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.bank_array, android.R.layout.simple_spinner_item);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(bankAdapter);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = simpleDateFormat.format(calendar.getTime());
        editDate.setText(dateTime);

        btnUpdate.setOnClickListener(v -> {
            String updatedRecipient = editRecipient.getText().toString();
            String amountText = editAmount.getText().toString();
            double updatedAmount = 0;
            boolean isValid = true;

            // Check if recipient and amount are not empty
            if (updatedRecipient.isEmpty()) {
                Toast.makeText(getContext(), "Recipient is required", Toast.LENGTH_SHORT).show();
                isValid = false;
            }

            if (amountText.isEmpty()) {
                Toast.makeText(getContext(), "Amount is required", Toast.LENGTH_SHORT).show();
                isValid = false;
            } else {
                try {
                    updatedAmount = Double.parseDouble(amountText);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid amount format", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }
            }

            if (isValid) {
                String updatedDate = editDate.getText().toString();
                String updatedCategory = selectedItem != null ? selectedItem : "";
                String updatedBank = bankSpinner.getSelectedItem().toString();

                Expense updatedExpense = new Expense(noteId != null ? noteId : 0,
                        updatedRecipient, updatedAmount, updatedDate, updatedCategory, updatedBank);

                ExpenseDatabase.databaseWriteExecutor.execute(() -> {
                    expenseDatabase.expenseDao().insertExpense(updatedExpense);
                    dialog.dismiss();
                });
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void handleBackPress() {
        // Callback will only be called when this fragment is visible
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Close the app when back is pressed
                requireActivity().finish();
            }
        };

        // Add the callback to the back press dispatcher
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }


}
