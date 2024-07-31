package com.example.expencetrackerapp.adapters;

import android.content.Context;
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
    private final Context context;
    private final OnExpenseClickListener onExpenseClickListener;

    public ExpenseAdapter(Context context, List<Expense> expenseList, OnExpenseClickListener onExpenseClickListener) {
        this.context = context;
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

        // Set expense details
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
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // Method to get category icon resource ID
    private int getCategoryIconResId(String category) {
        switch (category) {
            case "Food":
                return R.drawable.food_icon;
            case "Shopping":
                return R.drawable.shopping_icon;
            case "Groceries":
                return R.drawable.groceries_icon;
            case "Transport":
                return R.drawable.transport_icon;
            default:
                return R.drawable.miscellaneous_icon;
        }
    }

    // Method to get bank logo resource ID
    private int getBankLogoResId(String bankName) {
        switch (bankName) {
            case "SBI":
                return R.drawable.sbi_logo;
            case "HDFC":
                return R.drawable.hdfc_logo;
            case "ICICI":
                return R.drawable.icici_logo;
            case "Axis":
                return R.drawable.axis_logo;
            case "Kotak":
                return R.drawable.kotak_logo;
            case "IndusInd":
                return R.drawable.indusind_logo;
            case "Bank of Baroda":
                return R.drawable.bank_of_baroda_logo;
            case "Canara":
                return R.drawable.canara_logo;
            case "Yes Bank":
                return R.drawable.yes_logo;
            case "PNB":
                return R.drawable.pnb_logo;
            default:
                return R.drawable.default_bank_logo;
        }
    }

    // ViewHolder class
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

    // Interface for click handling
    public interface OnExpenseClickListener {
        void onExpenseClick(Expense expense);
    }
}