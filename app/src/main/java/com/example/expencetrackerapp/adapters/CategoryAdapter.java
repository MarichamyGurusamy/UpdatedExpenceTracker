package com.example.expencetrackerapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.expencetrackerapp.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<String> mCategoryList; // Example: List of category names

    public CategoryAdapter(Context context, List<String> categoryList) {
        this.mContext = context;
        this.mCategoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = mCategoryList.get(position);
        holder.bind(category);
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCategoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoryName = itemView.findViewById(R.id.textViewCategoryName); // Example TextView ID
        }

        public void bind(String category) {
            textViewCategoryName.setText(category);
        }
    }
}
