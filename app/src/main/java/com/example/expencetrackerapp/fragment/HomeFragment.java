package com.example.expencetrackerapp.fragment;

import android.app.Dialog;
import android.content.Context;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment implements ExpenseAdapter.OnExpenseClickListener {


    FragmentBottomNavigation communicator;

    ActivityExpenceListBinding binding;

    ExpenseDatabase expenseDatabase;

    ExpenseAdapter expenseAdapter;

    String selectedMonth = "Current Month";

    ArrayList<Expense> expenses = new ArrayList<>();

    ArrayList<Expense> monthExpenses  = new ArrayList<>();

    Map<String, String> monthMap  = new HashMap<>();

    String selectedItem ;

    String cageNamePostions;

    String [] catgNames={"Food","Shopping","Groceries","Transport","Miscellaneous"};

    Integer noteId ;

    String dateTime;

    Calendar calendar;

    SimpleDateFormat simpleDateFormat;

    ExpenseViewModel expenseViewModel;


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


        // Set up Spinner month mapping
        monthMap = new HashMap<>();
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
//
//        new Thread(() -> {
//        expenseDatabase.expenseDao().insertExpense(new Expense(noteId != null ? noteId : 0,"John Doe",  123.45 ,"29-07-2024","Grocery" ,"SBI"));
//        expenseDatabase.expenseDao().insertExpense(new Expense(noteId != null ? noteId : 0,"Jane Smith",  678.90 ,"30-07-2024","Shopping" ,"AXis"));
//        expenseDatabase.expenseDao().insertExpense(new Expense(noteId != null ? noteId : 0,"Jane Smith",  678.90 ,"31-07-2024","Micsc" ,"ICIC"));
//        expenseDatabase.expenseDao().insertExpense(new Expense(noteId != null ? noteId : 0," Smith",  678.90 ,"30-06-2024","Micsc" ,"IOB"));
//        expenseDatabase.expenseDao().insertExpense(new Expense(noteId != null ? noteId : 0,"Jane ",  678.90 ,"30-05-2024","Food" ,"SBI"));
//        expenseDatabase.expenseDao().insertExpense(new Expense(noteId != null ? noteId : 0,"Jane Sm",  678.90 ,"30-04-2024","Travel" ,"AXis"));
//        }).start();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.months_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.monthFilter.setAdapter(adapter);
        binding.monthFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = parent.getItemAtPosition(position).toString();
                String monthNumber = monthMap.get(selectedMonth);
                if (!monthNumber.equals("00")) {
                    slecteParticiluarMonth(monthNumber);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.addTaskFABtn.setOnClickListener(v -> showExpenseDialog(true));

        binding.backAll.setOnClickListener(v -> {});

        return binding.getRoot();

    }

    private void slecteParticiluarMonth(String monthNumber) {


        Log.d("TAG" ," loadDate >>> 4 " + monthNumber);

        expenseViewModel.getExpensesByMonth(monthNumber).observe(getActivity(), notes -> {

            Log.d("TAG" ," loadDate >>> 5 " + notes);

            if (notes != null) {
                this.monthExpenses = (ArrayList<Expense>) notes;
                double totalAmount = 0;
                for (Expense expense : notes) {
                    totalAmount += expense.getAmount();
                }
                binding.totalAmount.setText(String.format("Total Amount: ₹%.2f", totalAmount));

                Log.d("TAG" ," loadDate >>> 2 " + monthExpenses);

                loadExpenses(monthExpenses);

            }
        });
    }

    private void loadExpenses(ArrayList<Expense> expenses) {

        expenseAdapter = new ExpenseAdapter( expenses, this); // Use the correct constructor
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

        btnUpdate.setOnClickListener(v -> updateDetails(expense,editRecipient,editAmount,editDate,categorySpinner,bankSpinner,dialog));

        btnDelete.setOnClickListener(v ->{
            ExpenseDatabase.databaseWriteExecutor.execute(() -> expenseViewModel.deleteItme(expense.getId()));
            dialog.dismiss();
                });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }

    private void updateDetails(Expense expense, EditText editRecipient, EditText editAmount, EditText editDate, Spinner categorySpinner, Spinner bankSpinner,  Dialog dialog) {

            String updatedRecipient = editRecipient.getText().toString();
            double updatedAmount = Double.parseDouble(editAmount.getText().toString());
            String updatedDate = editDate.getText().toString();
            String updatedCategory = categorySpinner.getSelectedItem().toString();
            String updatedBank = bankSpinner.getSelectedItem().toString();

            Expense updatedExpense = new Expense(expense.getId(), updatedRecipient, updatedAmount, updatedDate, updatedCategory, updatedBank);

            ExpenseDatabase.databaseWriteExecutor.execute(() -> expenseViewModel.update(updatedExpense));

            dialog.dismiss();

    }

    private void showExpenseDialog(boolean isExpesnse) {
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

        if (isExpesnse){
            btnUpdate.setText("ADD");
            btnDelete.setVisibility(View.GONE);

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




        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();
          editDate.setText(dateTime);



        btnUpdate.setOnClickListener(v -> {
            String updatedRecipient = editRecipient.getText().toString();
            double updatedAmount = Double.parseDouble(editAmount.getText().toString());
            String updatedDate = editDate.getText().toString();
            String updatedCategory = selectedItem.toString();
            String updatedBank = bankSpinner.getSelectedItem().toString();

            Expense updatedExpense = new Expense(noteId != null ? noteId : 0,
                    updatedRecipient, updatedAmount, updatedDate, updatedCategory, updatedBank);

            ExpenseDatabase.databaseWriteExecutor.execute(() -> {
                expenseDatabase.expenseDao().insertExpense(updatedExpense);
                    dialog.dismiss();
                });});


        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

    }

}
