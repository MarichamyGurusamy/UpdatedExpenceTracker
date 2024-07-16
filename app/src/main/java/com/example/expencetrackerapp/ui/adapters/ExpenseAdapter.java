package com.example.expencetrackerapp.ui.adapters;

//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.expencetrackerapp.R;
//import com.example.expencetrackerapp.database.Expense;
//
//import java.util.List;
//
//
//public class ExpenseAdapter extends BaseAdapter {
//
//    private Context context;
//    private List<Expense> expenseList;
//
//    public ExpenseAdapter(Context context, List<Expense> expenseList) {
//        this.context = context;
//        this.expenseList = expenseList;
//    }
//
//    @Override
//    public int getCount() {
//        return expenseList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return expenseList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
//        }
//
//        TextView senderTextView = convertView.findViewById(R.id.senderTextView);
//        TextView messageBodyTextView = convertView.findViewById(R.id.messageBodyTextView);
//        TextView amountTextView = convertView.findViewById(R.id.amountTextView);
//
//        Expense expense = expenseList.get(position);
//        senderTextView.setText(expense.getSender());
//        messageBodyTextView.setText(expense.getMessageBody());
//        amountTextView.setText(String.valueOf(expense.getAmount()));
//
//        return convertView;
//    }
//}
