package com.example.expencetrackerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.models.BudgetCategory;

import java.util.List;

public class BudgetCategoryAdapter extends RecyclerView.Adapter<BudgetCategoryAdapter.ExpenseViewHolder> {

    private final List<BudgetCategory> budgetList;
    private final OnExpenseClickListener onExpenseClickListener;

    public BudgetCategoryAdapter(List<BudgetCategory> budgetList, OnExpenseClickListener onExpenseClickListener) {
        this.budgetList = budgetList;
        this.onExpenseClickListener = onExpenseClickListener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_category_item, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        BudgetCategory budgetCategory = budgetList.get(position);

        // Assuming budgetCategory contains fields like categoryName, spentAmount, and reminderAmount
        holder.categoryText.setText(budgetCategory.getCategoryName());  // Set the category name
        holder.spentAmount.setText(String.valueOf(budgetCategory.getBudgetAmount()));  // Set the total budget amount
        holder.reminderAmount.setText(String.valueOf(budgetCategory.getSpentAmount()));  // Set the spent amount
        holder.reminderText.setText("Remaining: " + String.format("%.2f", budgetCategory.getBudgetAmount() - budgetCategory.getSpentAmount()));  // Calculate and set the remaining amount

        // Set an appropriate icon if you have logic for it (you can implement getCategoryIconResId)
        holder.categoryIcon.setImageResource(getCategoryIconResId(budgetCategory.getCategoryName()));

        holder.itemView.setOnClickListener(v -> {
            if (onExpenseClickListener != null) {
                onExpenseClickListener.onExpenseClick(budgetCategory,v); // Adjust the listener to use BudgetCategory if needed
            }
        });
        
        holder.budgetItemDelete.setOnClickListener(v -> {
            if (onExpenseClickListener != null) {
                onExpenseClickListener.onDeleteClick(budgetCategory,v); // Adjust the listener to use BudgetCategory if needed
            }
        });
    }
    
    


    @Override
    public int getItemCount() {
        return budgetList.size();
    }




    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIcon, budgetItemDelete;
        TextView categoryText, reminderText, spentAmount,reminderAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            categoryText = itemView.findViewById(R.id.categoryText);
            reminderText = itemView.findViewById(R.id.reminderText);
            spentAmount = itemView.findViewById(R.id.spentAmount);
            reminderAmount = itemView.findViewById(R.id.reminderAmount);
            budgetItemDelete = itemView.findViewById(R.id.budgetItemDelete);
        }
    }

    public interface OnExpenseClickListener {
        void onExpenseClick(BudgetCategory budgetCategory, View v);

        void onDeleteClick(BudgetCategory budgetCategory, View v);
    }


    private int getCategoryIconResId(String category) {
        return switch (category) {
            case "Food" -> R.drawable.food_icon;
            case "Shopping" -> R.drawable.shopping_icon;
            case "Groceries" -> R.drawable.groceries_icon;
            case "Transport" -> R.drawable.transport_icon;
            default -> R.drawable.miscellaneous_icon;
        };
    }

}