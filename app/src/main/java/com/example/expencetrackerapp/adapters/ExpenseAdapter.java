package com.example.expencetrackerapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.database.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {
    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expence, parent, false); // Corrected layout name
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        // Set the recipient/store name
        holder.recipientTextView.setText(expense.getRecipient());

        // Set the transaction date
        holder.transactionDateTextView.setText(expense.getDate());

        // Set the amount
        holder.amountTextView.setText(String.format("₹%.2f", expense.getAmount()));

        // Set the category icon
        int categoryIconResId = getCategoryIconResId(expense.getCategory());
        holder.categoryIconImageView.setImageResource(categoryIconResId);

        // Set the bank logo
        int bankLogoResId = getBankLogo(expense.getBankName());
        holder.bankLogoImageView.setImageResource(bankLogoResId);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIconImageView;
        TextView recipientTextView;
        TextView transactionDateTextView; // Added field for date
        TextView amountTextView;
        ImageView bankLogoImageView;

        ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIconImageView = itemView.findViewById(R.id.category_icon);
            recipientTextView = itemView.findViewById(R.id.recipient_text);
            transactionDateTextView = itemView.findViewById(R.id.transaction_date); // Added
            amountTextView = itemView.findViewById(R.id.expense_amount);
            bankLogoImageView = itemView.findViewById(R.id.bank_logo);
        }
    }

    // Helper method to get the category icon resource ID
    private int getCategoryIconResId(String category) {
        switch (category.toLowerCase()) {
            case "food":
                return R.drawable.food_icon;
            case "shopping":
                return R.drawable.shopping_icon;
            case "groceries":
                return R.drawable.groceries_icon;
            case "transport":
                return R.drawable.transport_icon;
            // Added new category
            default:
                return R.drawable.miscellaneous_icon;
        }
    }

    // Helper method to get the bank logo resource ID
    @DrawableRes
    public static int getBankLogo(String bankName) {
        switch (bankName.toLowerCase()) {
            case "sbi":
                return R.drawable.sbi_logo;
            case "hdfc":
                return R.drawable.hdfc_logo;
            case "icici":
                return R.drawable.icici_logo;
            case "axis":
                return R.drawable.axis_logo;
            case "kotak":
                return R.drawable.kotak_logo;
            case "indusind":
                return R.drawable.indusind_logo;
            case "pnb":
                return R.drawable.pnb_logo;
            case "yes":
                return R.drawable.yes_logo;
            case "bank of baroda":
                return R.drawable.bank_of_baroda_logo;
            case "central bank of india":
                return R.drawable.central_bank_logo;
            default:
                return R.drawable.default_bank_logo; // Default logo
        }
    }
}