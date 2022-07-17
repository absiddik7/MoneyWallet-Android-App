package com.app.moneywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.moneywallet.database.ExpenseDB;
import com.app.moneywallet.database.IncomeDB;

public class ExpenseOverviewActivity extends AppCompatActivity {

    private ExpenseDB expenseDb;
    private ExpenseAdapter expenseAdapter;
    RecyclerView expenseDetails;
    Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_overview);

        expenseDb = new ExpenseDB(ExpenseOverviewActivity.this);
        int tIncome = expenseDb.totalExpense();
        expenseAdapter = new ExpenseAdapter(this,tIncome,expenseDb.getAllExpense());
        TextView totalExpense = findViewById(R.id.all_expense_txt);
        totalExpense.setText(String.valueOf(expenseDb.totalExpense()));

        expenseDetails = findViewById(R.id.all_expense_list);
        expenseDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        expenseDetails.setAdapter(expenseAdapter);
        expenseAdapter.notifyDataSetChanged();

        okBtn = findViewById(R.id.expense_ok_btn);
        navigateToDashboard();
    }

    private void navigateToDashboard(){
        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseOverviewActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}