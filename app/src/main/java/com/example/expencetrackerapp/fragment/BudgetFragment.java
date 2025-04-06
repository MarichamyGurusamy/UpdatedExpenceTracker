package com.example.expencetrackerapp.fragment;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.BudgetCategoryAdapter;
import com.example.expencetrackerapp.database.BudgetCategoryDatabase;
import com.example.expencetrackerapp.database.BudgetsDatabase;
import com.example.expencetrackerapp.database.BudgetsTotalDatabase;
import com.example.expencetrackerapp.databinding.BudgetActivityBinding;
import com.example.expencetrackerapp.interfaces.FragmentBottomNavigation;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.BudgetCategory;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.viewmodel.BudgetCategoryViewModel;
import com.example.expencetrackerapp.viewmodel.BudgetTotalViewModel;
import com.example.expencetrackerapp.viewmodel.BudgetViewModel;
import com.example.expencetrackerapp.viewmodel.ExpenseViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BudgetFragment extends Fragment implements BudgetCategoryAdapter.OnExpenseClickListener {

    private FragmentBottomNavigation communicator;
    private BudgetActivityBinding binding;
    BudgetCategoryAdapter budgetCategoryAdapter;
    ArrayList<Expense> expenses = new ArrayList<>();
    ArrayList<BudgetCategory> budgetCategories = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    String specifyItem = "" ,specifyItemName = "" ,specifyItemNameCat = "";
    BudgetCategoryDatabase budgetCategoryDatabase;
    BudgetsTotalDatabase budgetsTotalDatabase;
    BudgetsDatabase budgetsDatabase;
    ExpenseViewModel expenseViewModel;
    int amount;
    double previousAmount = 0.0;
    int budgetId,budgetIdCat;

    Map<String, String> monthMap  = new HashMap<>();

    double totalBudgetCategoryAmount = 0.0;
    double totalBudgetAmount = 0.0;

    private TextView startDateTextView;
    private TextView endDateTextView;


    ArrayList<Integer> budgetIds = new ArrayList<>();
    ArrayList<Double> budgetAmount = new ArrayList<>();
    ArrayList<String> budgetName = new ArrayList<>();

    ArrayList<Double> budgetSh = new ArrayList<>();
    ArrayList<Double> budgetFd = new ArrayList<>();
    ArrayList<Double> budgetEdu = new ArrayList<>();
    ArrayList<Double> budgetTv = new ArrayList<>();
    ArrayList<Double> budgetGro = new ArrayList<>();
    ArrayList<Double> budgetMis= new ArrayList<>();


    double budgetShCat = 0.0;
    double budgetGroCat = 0.0;
    double budgetMisCat = 0.0;
    double budgetEduCat = 0.0;
    double budgetTvCat = 0.0;
    double budgetFdCat = 0.0;

    double budgetShs = 0.0;
    double budgetGros = 0.0;
    double budgetMiss = 0.0;
    double budgetEdus = 0.0;
    double budgetTvs = 0.0;
    double budgetFds = 0.0;


    double totalSpendingAmountSh;
    double totalSpendingAmountFd;
    double totalSpendingAmountTv;
    double totalSpendingAmountMis;
    double totalSpendingAmountEdu;
    double totalSpendingAmountGro;
//    int budgetShCat,budgetFdCat,budgetGroCat,budgetEduCat,budgetMisCat,budgetTvCat = 0;
//    int budgetShs,budgetFds,budgetGros,budgetEdus,budgetMiss,budgetTvs = 0;

    String isMonth = " ";
    String selectedMonth = " ";

    String monthNumber = " ";

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            communicator = (FragmentBottomNavigation) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FragmentBottomNavigation");
        }
    }

    private void newCreateTodo() {
        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.budget_category_popup_item, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireActivity());
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();


        TextView textTitleShopping = dialogView.findViewById(R.id.recipient_text1);
        TextView textTitleGrocery = dialogView.findViewById(R.id.recipient_text2);
        TextView textTitleFood = dialogView.findViewById(R.id.recipient_text3);
        TextView textTitleMicsc = dialogView.findViewById(R.id.recipient_text4);
        TextView textTitleTravel = dialogView.findViewById(R.id.recipient_text5);
        TextView textTitleEducation = dialogView.findViewById(R.id.recipient_text6);


        textTitleShopping.setOnClickListener(v -> {
            if (budgetCategories.isEmpty()){
                SelectedDate("Shopping",1, 0);
            } else {
                SelectedDate("Shopping",1, 1);
            }
            dialog.dismiss();
        });
        textTitleGrocery.setOnClickListener(v -> {
            if (budgetCategories.isEmpty()) {
                SelectedDate("Groceries", 2, 0);
            }else {
                SelectedDate("Groceries", 2, 1);
            }
            dialog.dismiss();
        });
        textTitleFood.setOnClickListener(v -> {
            if (budgetCategories.isEmpty()) {
                SelectedDate("Food", 3, 0);
            }else {
                SelectedDate("Food", 3, 1);
            }
            dialog.dismiss();
        });
        textTitleMicsc.setOnClickListener(v -> {
            if (budgetCategories.isEmpty()) {
                SelectedDate("Miscellaneous", 4, 0);
            } else {
                SelectedDate("Miscellaneous", 4, 1);
            }
            dialog.dismiss();
        });
        textTitleTravel.setOnClickListener(v -> {
            if (budgetCategories.isEmpty()) {
                SelectedDate("Transport", 5, 0);
            } else{
                SelectedDate("Transport", 5, 1);
            }
            dialog.dismiss();
        });
        textTitleEducation.setOnClickListener(v -> {
            if (budgetCategories.isEmpty()) {
                SelectedDate("Education", 6, 0);
            } else {
                SelectedDate("Education", 6, 1);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private void SelectedDate(String shopping, int i, int amountPrv) {

        specifyItem=shopping + " " + "Budget Amount";


        boolean isUpdate = (amountPrv == 1); // Assuming amountPrv is used to determine if it's an update
        showAlertDialogButtonClicked(specifyItem, i, shopping, isUpdate);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BudgetActivityBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);


        // Retrieve the boolean value (default is false if not set)
        isMonth = sharedPreferences.getString("monthnumber",null);

        selectedMonth = sharedPreferences.getString("selectedmonth",null);

       // monthNumber = monthMap.get(selectedMonth);

        if (selectedMonth != null && selectedMonth.equals("October")) {
            monthNumber = "10";
        }

        Log.d("TAG", "Total Spending Amount Shopping 111 " + monthNumber + selectedMonth );
//        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences1.edit();
//
//        editor.putString("monthnumber", isMonth);
//
//        editor.putString("selectedmonth", selectedMonth);
//
//        editor.apply();
        // Initialize ViewModels
        BudgetViewModel budgetViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        BudgetCategoryViewModel budgetCategoryViewModel = new ViewModelProvider(this).get(BudgetCategoryViewModel.class);
        BudgetTotalViewModel budgetTotalViewModel = new ViewModelProvider(this).get(BudgetTotalViewModel.class);
        ExpenseViewModel expenseViewModel1 = new ViewModelProvider(this).get(ExpenseViewModel.class);
        budgetCategoryDatabase  = BudgetCategoryDatabase.getDatabase(getContext());
        budgetsTotalDatabase  = BudgetsTotalDatabase.getDatabase(getContext());
        budgetsDatabase  = BudgetsDatabase.getDatabase(getContext());
        // Navigate to the bottom fragment if needed
        communicator.navigateBottomFrag(3, true);

        startDateTextView = binding.monthStartDate; // Replace with your binding variable if applicable
        endDateTextView = binding.monthEndDate;


        // Call to generate date ranges and set the TextViews
        generateMonthlyDateRanges();
        binding.monthStartDate.setOnClickListener(v -> navigateToPreviousMonth());
        binding.monthEndDate.setOnClickListener(v -> navigateToNextMonth());
        binding.addTaskFABtn.setOnClickListener(v -> newCreateTodo());

       // if (selectedMonth != null) {


            expenseViewModel1.getAllNotes().observe(requireActivity(), notes -> {
                if (notes.isEmpty()) {
                    previousAmount = 0;
                } else {
                    Log.d("tag", "budgetCategoryViewModel" + notes.size());
                    this.expenses = (ArrayList<Expense>) notes;

                    //calculateTotalSpending(this.budgetCategories);
                    for (Expense expenses : expenses) {

                        switch (expenses.getCategory()) {
                            case "Shopping" -> this.budgetShs += expenses.getAmount();
                            case "Groceries" -> this.budgetGros += expenses.getAmount();
                            case "Miscellaneous" -> this.budgetMiss += expenses.getAmount();
                            case "Education" -> this.budgetEdus += expenses.getAmount();
                            case "Transport" -> this.budgetTvs += expenses.getAmount();
                            case "Food" -> this.budgetFds += expenses.getAmount();
                        }

                    }



                }


            });
      //  }

        budgetCategoryViewModel.getAllNotes().observe(requireActivity(), notes -> {
            if (notes.isEmpty()) {
                previousAmount = 0;
            } else {
                this.budgetCategories = (ArrayList<BudgetCategory>) notes;

                // Calculate amounts after a 5-second delay
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    for (BudgetCategory budgetCategory : budgetCategories) {
                        switch (budgetCategory.getCategoryName()) {
                            case "Shopping" -> this.budgetShCat = budgetCategory.getBudgetAmount();
                            case "Groceries" -> this.budgetGroCat = budgetCategory.getBudgetAmount();
                            case "Miscellaneous" -> this.budgetMisCat = budgetCategory.getBudgetAmount();
                            case "Education" -> this.budgetEduCat = budgetCategory.getBudgetAmount();
                            case "Transport" -> this.budgetTvCat = budgetCategory.getBudgetAmount();
                            case "Food" -> this.budgetFdCat = budgetCategory.getBudgetAmount();
                        }
                    }

                    // Calculate total spending amounts
                    this.totalSpendingAmountSh = budgetShCat - budgetShs;
                    this.totalSpendingAmountFd = budgetFdCat - budgetFds;
                    this.totalSpendingAmountEdu = budgetEduCat - budgetEdus;
                    this.totalSpendingAmountGro = budgetGroCat - budgetGros;
                    this.totalSpendingAmountMis = budgetMisCat - budgetMiss; // not working
                    this.totalSpendingAmountTv = budgetTvCat - budgetTvs;

                    // Logging the values after delay
                    Log.d("Tag", "Total Spending Amount Shopping " + " >>> " + totalSpendingAmountSh + " >>> " + budgetShCat + " >>> " + budgetShs);
                    Log.d("Tag", "Total Spending Amount Food: " + " >>> " + totalSpendingAmountFd + " >>> ");
                    Log.d("Tag", "Total Spending Amount Education: " + " >>> " + totalSpendingAmountEdu + " >>> ");
                    Log.d("Tag", "Total Spending Amount Grocery: " + " >>> " + totalSpendingAmountGro + " >>> ");
                    Log.d("Tag", "Total Spending Amount Mis: " + " >>> " + totalSpendingAmountMis + " >>> ");
                    Log.d("Tag", "Total Spending Amount Travel: " + " >>> " + totalSpendingAmountTv + " >>> ");

                    loadBudgets(budgetCategories);
                }, 1000); // 5-second delay
            }
        });





        return binding.getRoot();
    }




    public void showAlertDialogButtonClicked(String specifyItem, int postions, String itemPosition, boolean isUpdate) {
        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(specifyItem);
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        EditText editText = customLayout.findViewById(R.id.editText);
        if (isUpdate) {
            // When updating, show the existing amount
            editText.setText(String.valueOf(previousAmount));
        } else {
            // When adding a new item, initialize to 0.0
            editText.setText("0.0");
        }


        // add a button
        builder.setPositiveButton("OK", (dialog, which) -> {


            if (!editText.getText().toString().trim().isEmpty() && previousAmount > 0) {
                amount = Integer.parseInt(editText.getText().toString().trim());
                if (amount > 0) {
                    BudgetCategory budgetCategory = new BudgetCategory(postions, itemPosition, amount, totalSpendingAmountFd);
                    Budget budget = new Budget();
                    budget.setCatAmount(amount);
                    BudgetsDatabase.databaseWriteExecutor.execute(() -> {
                        budgetsDatabase.budgetDao().insertBudget(budget);

                    });
                    BudgetCategoryDatabase.databaseWriteExecutor.execute(() -> budgetCategoryDatabase.budgetCategoryDao().insertBudgetCategory(budgetCategory));
                    dialog.dismiss();
                }else if(!editText.getText().toString().trim().isEmpty()){
                    // Show a message if the amount is zero or negative
                    Toast.makeText(getContext(), "Please enter an amount greater than 0", Toast.LENGTH_SHORT).show();
                    // Exit the click listener, and do not close the dialog
                }
            }else if(editText.getText().toString().trim().isEmpty()) {

                Toast.makeText(getContext(), "Please enter the amount", Toast.LENGTH_SHORT).show();

            }else {
                amount = Integer.parseInt(editText.getText().toString().trim());
                BudgetCategory budgetCategory = new BudgetCategory(postions, itemPosition, amount, 0);

                BudgetCategoryDatabase.databaseWriteExecutor.execute(() -> budgetCategoryDatabase.budgetCategoryDao().insertBudgetCategory(budgetCategory));
            }




        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void loadBudgets(ArrayList<BudgetCategory> budgetCategories) {

        budgetCategoryAdapter = new BudgetCategoryAdapter(budgetCategories, this,totalSpendingAmountSh, totalSpendingAmountFd, totalSpendingAmountEdu, totalSpendingAmountGro, totalSpendingAmountMis, totalSpendingAmountTv); // Use the correct constructor
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(budgetCategoryAdapter);

        // Set a custom drawable for the divider

        budgetCategoryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onExpenseClick(BudgetCategory budgetCategory, View v) {

        previousAmount = budgetCategory.getBudgetAmount();
        specifyItem=budgetCategory.getCategoryName() + " " + "monthlyData";
        showAlertDialogButtonClicked(specifyItem,budgetCategory.getId(),budgetCategory.getCategoryName(), budgetCategories != null);

    }

    @Override
    public void onDeleteClick(BudgetCategory budgetCategory, View v) {

        BudgetCategoryDatabase.databaseWriteExecutor.execute(() -> budgetCategoryDatabase.budgetCategoryDao().deleteById(budgetCategory.getId()));
    }

    private void generateMonthlyDateRanges() {
        List<String[]> dateRanges = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // Set the start date to October 1, 2024
        calendar.set(2024, Calendar.OCTOBER, 1); // Month is 0-indexed

        // Generate date ranges for 12 months
        for (int i = 0; i < 12; i++) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String startDate = dateFormat.format(calendar.getTime());

            // Move to the last day of the month
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            String endDate = dateFormat.format(calendar.getTime());

            // Store the date range
            dateRanges.add(new String[]{startDate, endDate});

            // Move to the first day of the next month
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        // Output the date ranges for testing
        for (String[] range : dateRanges) {
            System.out.println("Start: " + range[0] + ", End: " + range[1]);
        }

        // Set the start and end dates for the current month (for display)
        if (!dateRanges.isEmpty()) {
            String[] currentRange = dateRanges.get(0); // Assuming you want to display the first month
            startDateTextView.setText( currentRange[0]);
            endDateTextView.setText(currentRange[1]);
        }
    }


    private void navigateToPreviousMonth() {
        // Move the calendar one month back
        calendar.add(Calendar.MONTH, -1);
        updateMonthlyDateRanges();
    }

    private void navigateToNextMonth() {
        // Move the calendar one month forward
        calendar.add(Calendar.MONTH, 1);
        updateMonthlyDateRanges();
    }

    private void updateMonthlyDateRanges() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        // Get the start date (first day of the current month)
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String startDate = dateFormat.format(calendar.getTime());

        // Get the end date (last day of the current month)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        String endDate = dateFormat.format(calendar.getTime());

        // Update the TextViews with the start and end dates
        startDateTextView.setText(startDate);
        endDateTextView.setText(endDate);
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue(); // 1 to 12
        String monthName = currentDate.getMonth().name();

        Log.d("Current Month", "Month: " + currentMonth + " (" + monthName + ")");
    }


}