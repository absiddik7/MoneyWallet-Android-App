package com.app.moneywallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private final Context context;
    private final int totalExpense;
    private final ArrayList<IncomeInfo> expenseList;

    public ExpenseAdapter(Context context, int totalExpense, ArrayList<IncomeInfo> expenseList) {
        this.context = context;
        this.totalExpense = totalExpense;
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseAdapter .ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return new ExpenseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
        IncomeInfo data = expenseList.get(position);
        String catName = data.getCategory();
        int catAmount = data.getAmount();
        double val = (double) catAmount / (double) totalExpense;
        int percent = (int) (val*100);
        String showPercent = percent +"%";

        holder.catName.setText(catName);
        holder.amountPercent.setText(showPercent);
        holder.amountProgress.setProgress(percent);
        holder.catAmount.setText(String.valueOf(catAmount));
    }


    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView catName;
        TextView amountPercent;
        TextView catAmount;
        ProgressBar amountProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.category_txt);
            amountPercent = itemView.findViewById(R.id.amount_percent);
            catAmount = itemView.findViewById(R.id.cat_amount);
            amountProgress = itemView.findViewById(R.id.cat_progressBar);
        }
    }
}
