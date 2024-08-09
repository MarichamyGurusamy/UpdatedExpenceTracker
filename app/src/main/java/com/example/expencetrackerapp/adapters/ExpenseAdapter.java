package com.example.expencetrackerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.models.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final List<Expense> expenseList;
    private final OnExpenseClickListener onExpenseClickListener;

    public ExpenseAdapter(List<Expense> expenseList, OnExpenseClickListener onExpenseClickListener) {
        this.expenseList = expenseList;
        this.onExpenseClickListener = onExpenseClickListener;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expence, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {

        Expense expense = expenseList.get(position);

        holder.categoryIcon.setImageResource(getCategoryIconResId(expense.getCategory()));
        holder.recipientText.setText(expense.getRecipient());
        holder.transactionDate.setText(expense.getDate());
        holder.expenseAmount.setText(String.valueOf(expense.getAmount()));
        holder.bankLogo.setImageResource(getBankLogoResId(expense.getBankName()));

        holder.recipientText.setOnClickListener(v -> {
            if (onExpenseClickListener != null) {
                onExpenseClickListener.onExpenseClick(expense);
            }
        });

        holder.transactionDate.setOnClickListener(v -> {
            if (onExpenseClickListener != null) {
                onExpenseClickListener.onExpenseClick(expense);
            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
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

    private int getBankLogoResId(String bankName) {
        return switch (bankName) {
            case "SBI" -> R.drawable.sbi_logo;
            case "HDFC" -> R.drawable.hdfc_logo;
            case "ICICI" -> R.drawable.icici_logo;
            case "Axis" -> R.drawable.axis_logo;
            case "Kotak" -> R.drawable.kotak_logo;
            case "IndusInd" -> R.drawable.indusind_logo;
            case "Bank of Baroda" -> R.drawable.bank_of_baroda_logo;
            case "Canara" -> R.drawable.canara_logo;
            case "Yes Bank" -> R.drawable.yes_logo;
            case "PNB" -> R.drawable.pnb_logo;
            default -> R.drawable.default_bank_logo;
        };
    }

    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIcon, bankLogo;
        TextView recipientText, transactionDate, expenseAmount;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            recipientText = itemView.findViewById(R.id.recipient_text);
            transactionDate = itemView.findViewById(R.id.transaction_date);
            expenseAmount = itemView.findViewById(R.id.expense_amount);
            bankLogo = itemView.findViewById(R.id.bank_logo);
        }
    }

    public interface OnExpenseClickListener {
        void onExpenseClick(Expense expense);
    }

}