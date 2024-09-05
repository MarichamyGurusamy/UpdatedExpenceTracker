package com.example.expencetrackerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.expencetrackerapp.R;
import com.example.expencetrackerapp.models.Billing;
import java.util.ArrayList;


public class BillingsAdapter extends RecyclerView.Adapter<BillingsAdapter.BillingViewHolder> {
    final ArrayList<Billing> billingArrayList;
    final Context context;
    OnItemClikedListener onClikedListener ;


    public BillingsAdapter(Context context, ArrayList<Billing> expenseList ,OnItemClikedListener onClikedListener) {
        this.context = context;
        this.billingArrayList = expenseList;
        this.onClikedListener = onClikedListener;
    }

    @NonNull
    @Override
    public BillingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billing_item, parent, false);
        return new BillingsAdapter.BillingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillingViewHolder holder, int position) {
        Billing billing = billingArrayList.get(position);
        holder.titleTxt.setText(billing.getTitle());
        holder.titleTxtDate.setText(billing.getDate());
        holder.expenseAmount.setText(billing.getAmount());

        if (billing.isMarkAsRead()){
            holder.editImg3.setImageResource(R.drawable.background_markasread_shape_spc);
        }else {
            holder.editImg3.setImageResource(R.drawable.background_markasunread_shape_spc);
        }

//        holder.titleTxtDate.setText(billing.getDate());

        holder.relative1.setOnClickListener(v -> onClikedListener.onItemClick(billing));

        holder.relative2.setOnClickListener(v -> onClikedListener.onItemClick(billing));

        holder.editImg3.setOnClickListener(v -> onClikedListener.onItemMarkRead(billing));


    }

    @Override
    public int getItemCount() {
        return billingArrayList.size();
    }



    // ViewHolder class
    public static class BillingViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryIcon, bankLogo,editImg3;
        TextView titleTxt, titleTxtDate, expenseAmount,titleTxtpaid;
        RelativeLayout relative1,relative2;

        public BillingViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIcon = itemView.findViewById(R.id.category_icon);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            titleTxtDate = itemView.findViewById(R.id.titleTxtDate);
            expenseAmount = itemView.findViewById(R.id.titleTxtAmount);
            titleTxtpaid = itemView.findViewById(R.id.titleTxtpaid);
            bankLogo = itemView.findViewById(R.id.bank_logo);
            editImg3 = itemView.findViewById(R.id.editImg3);
            relative1 = itemView.findViewById(R.id.relative1);
            relative2 = itemView.findViewById(R.id.relative2);
        }
    }

    public interface OnItemClikedListener{

        void onItemClick(Billing billing);

        void onItemMarkRead(Billing billing);
    }

}