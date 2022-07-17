package com.app.moneywallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.moneywallet.database.IncomeDB;

public class IncomeOverviewActivity extends AppCompatActivity {

    private IncomeDB incomeDb;
    private IncomeAdapter incomeAdapter;
    Button okBtn;
    TextView totalIncome;
    RecyclerView incomeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_overview);

        incomeDb = new IncomeDB(IncomeOverviewActivity.this);
        int tIncome = incomeDb.totalIncome();
        incomeAdapter = new IncomeAdapter(this,tIncome,incomeDb.getAllIncome());

        okBtn = findViewById(R.id.income_ok_btn);
        totalIncome = findViewById(R.id.all_income_txt);
        totalIncome.setText(String.valueOf(incomeDb.totalIncome()));
        incomeDetails = findViewById(R.id.all_income_list);

        incomeDetails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        incomeDetails.setAdapter(incomeAdapter);
        incomeAdapter.notifyDataSetChanged();

        navigateToDashboard();
    }

    private void navigateToDashboard(){
        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IncomeOverviewActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}