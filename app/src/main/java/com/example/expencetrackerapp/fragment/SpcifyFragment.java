package com.example.expencetrackerapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.database.BudgetsDatabase;
import com.example.expencetrackerapp.models.Budget;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;
import com.example.expencetrackerapp.databinding.ActivityExpenceListBinding;
import com.example.expencetrackerapp.interfaces.FragmentBottomNavigation;
import com.example.expencetrackerapp.viewmodel.BudgetViewModel;
import com.example.expencetrackerapp.viewmodel.ExpenseViewModel;

import java.util.ArrayList;

public class SpcifyFragment extends Fragment implements ExpenseAdapter.OnExpenseClickListener  {

    FragmentBottomNavigation communicator;

    ActivityExpenceListBinding binding;

    ArrayList<Expense> expenseList = new ArrayList<>();

    ExpenseAdapter expenseAdapter;

    ExpenseViewModel expenseViewModel;

    BudgetViewModel budgetViewModel;

    String specifyItem;

    double totalAmount = 0;

    BudgetsDatabase budgetsDatabase;


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

        budgetViewModel = provider.get(BudgetViewModel.class);

        budgetsDatabase  = BudgetsDatabase.getDatabase(getContext());



        binding.itemContents.setVisibility(View.VISIBLE);
//        binding.recyclerViewExpenses.setVisibility(View.VISIBLE);

            binding.toolbarTitle.setText("Speify Expenses");



        binding.addTaskFABtn.setOnClickListener(v -> newCreateTodo());

        return binding.getRoot();
    }

    private void newCreateTodo() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.activty_item, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();

        TextView textTitleShopping = dialogView.findViewById(R.id.recipient_text1);
        TextView textTitleGrocery = dialogView.findViewById(R.id.recipient_text2);
        TextView textTitleFood = dialogView.findViewById(R.id.recipient_text3);
        TextView textTitleMicsc = dialogView.findViewById(R.id.recipient_text4);
        TextView textTitleTravel = dialogView.findViewById(R.id.recipient_text5);
        TextView textTitleEducation = dialogView.findViewById(R.id.recipient_text6);


        textTitleShopping.setOnClickListener(v -> {
            SelectedDate("Shopping",1);
            dialog.dismiss();
        });
        textTitleGrocery.setOnClickListener(v -> {
            SelectedDate("Groceries",2);
            dialog.dismiss();
        });
        textTitleFood.setOnClickListener(v -> {
            SelectedDate("Food",3);
            dialog.dismiss();
        });
        textTitleMicsc.setOnClickListener(v -> {
            SelectedDate("Micscellaneous",4);
            dialog.dismiss();
        });
        textTitleTravel.setOnClickListener(v -> {
            SelectedDate("Transport",5);
            dialog.dismiss();
        });
        textTitleEducation.setOnClickListener(v -> {
            SelectedDate("Education",6);
            dialog.dismiss();
        });



        dialog.show();
    }

    private void SelectedDate(String postions,int itemPosition) {

        this.specifyItem = postions;

        if (specifyItem != null){
            binding.toolbarTitle.setText(" " + specifyItem + " " + "Expenses");
        }




        expenseViewModel.getSpecifityExpenses(postions).observe(getActivity(), notes -> {
            if (notes != null) {
                this.expenseList = (ArrayList<Expense>) notes;
                for (Expense expense : notes) {
                    totalAmount += expense.getAmount();
                }
                binding.totalAmount.setText(String.format("Total Amount: ₹%.2f", totalAmount));
                loadExpenses(expenseList);
                specifyBudgetItem(itemPosition,totalAmount,postions);


            }
        });


    }

    private void specifyBudgetItem(int itemPosition, double totalAmount, String postions) {

        Budget budget = new Budget(itemPosition,totalAmount,postions);

        BudgetsDatabase.databaseWriteExecutor.execute(() -> {
            budgetsDatabase.budgetDao().insertBudget(budget);

        });
        

    }


    private void loadExpenses(ArrayList<Expense> expenseList) {

        if (!expenseList.isEmpty() && expenseList != null) {

            binding.itemContents.setVisibility(View.GONE);
            binding.recyclerViewExpenses.setVisibility(View.VISIBLE);

            expenseAdapter = new ExpenseAdapter(expenseList, this); // Use the correct constructor
            binding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerViewExpenses.setAdapter(expenseAdapter);
            expenseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onExpenseClick(Expense expense) {

    }
}
