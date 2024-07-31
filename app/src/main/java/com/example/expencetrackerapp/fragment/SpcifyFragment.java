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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.adapters.ExpenseAdapter;
import com.example.expencetrackerapp.models.Expense;
import com.example.expencetrackerapp.database.ExpenseDatabase;
import com.example.expencetrackerapp.databinding.ActivityExpenceListBinding;
import com.example.expencetrackerapp.interfaces.FragmentBottomNavigation;

import java.util.ArrayList;

public class SpcifyFragment extends Fragment implements ExpenseAdapter.OnExpenseClickListener  {

    FragmentBottomNavigation communicator;

    ActivityExpenceListBinding binding;

    ExpenseDatabase expenseDatabase;

    ArrayList<Expense> expenseList = new ArrayList<>();

    ArrayList<Expense> expenseDetalisList = new ArrayList<>();

    ExpenseAdapter expenseAdapter;






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

        expenseDatabase = ExpenseDatabase.getDatabase(getContext()); // Use the singleton pattern

        new Thread(() -> {
        expenseList = (ArrayList<Expense>) expenseDatabase.expenseDao().getAllExpenses();
        }).start();


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


        textTitleShopping.setOnClickListener(v -> {
            SelectedDate("1");
            dialog.dismiss();
        });
        textTitleGrocery.setOnClickListener(v -> {
            SelectedDate("2");
            dialog.dismiss();
        });
        textTitleFood.setOnClickListener(v -> {
            SelectedDate("3");
            dialog.dismiss();
        });
        textTitleMicsc.setOnClickListener(v -> {
            SelectedDate("4");
            dialog.dismiss();
        });
        textTitleTravel.setOnClickListener(v -> {
            SelectedDate("5");
            dialog.dismiss();
        });


        dialog.show();
    }

    private void SelectedDate(String postions) {

        for (Expense expense : expenseList) {
            if (expense.getCategory().equals("Shopping") && postions.equals("1")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 1 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Grocery") && postions.equals("2")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 2 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Food") && postions.equals("3")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 3 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Micsc") && postions.equals("4")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 4 " + expenseDetalisList.size());
            } else if (expense.getCategory().equals("Travel") && postions.equals("5")) {
                expenseDetalisList.add(expense);
//                Log.d("TAG", " expenseList >>> 5 " + expenseDetalisList.size());
            }
        }
        if (!expenseDetalisList.isEmpty() && expenseDetalisList != null) {

            expenseAdapter = new ExpenseAdapter(getContext(), expenseDetalisList, this); // Use the correct constructor
            binding.recyclerViewExpenses.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recyclerViewExpenses.setAdapter(expenseAdapter);
            expenseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onExpenseClick(Expense expense) {

    }
}
