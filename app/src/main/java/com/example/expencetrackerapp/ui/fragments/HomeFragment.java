package com.example.expencetrackerapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.models.ExpenseCategory;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    //private ExpenseCategoryAdapter adapter;
    private List<ExpenseCategory> categoryList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recycler_view_categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        categoryList = new ArrayList<>();
        // Populate your category list with sample data (replace with your actual data)
        categoryList.add(new ExpenseCategory("Food", "$50.00"));
        categoryList.add(new ExpenseCategory("Transportation", "$30.00"));
        categoryList.add(new ExpenseCategory("Entertainment", "$20.00"));
        categoryList.add(new ExpenseCategory("Shopping", "$70.00"));

        //adapter = new ExpenseCategoryAdapter(categoryList);
        //recyclerView.setAdapter(adapter);

        return root;
    }
}
