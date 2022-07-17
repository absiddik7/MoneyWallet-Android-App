package com.app.moneywallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app.moneywallet.database.ExpenseDB;
import com.app.moneywallet.database.IncomeDB;
import com.app.moneywallet.database.UserDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DashboardActivity extends AppCompatActivity {
    private IncomeDB incomeDb;
    private ExpenseDB expenseDb;
    private UserDB userDB;
    FloatingActionButton fab;
    FloatingActionButton incomeFab;
    FloatingActionButton expenseFab;
    CardView incomeView;
    CardView expenseView;
    boolean isVisible;
    TextView totalIncome;
    TextView totalExpense;
    ProgressBar remainingMoneyBar;
    TextView remainingMoneyTxt;
    TextView welcomeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        incomeDb = new IncomeDB(DashboardActivity.this);
        expenseDb = new ExpenseDB(DashboardActivity.this);
        userDB = new UserDB(DashboardActivity.this);

        fab = findViewById(R.id.fab);
        incomeFab = findViewById(R.id.income_fab);
        expenseFab = findViewById(R.id.expense_fab);
        incomeView = findViewById(R.id.income_view);
        expenseView = findViewById(R.id.expense_view);
        totalIncome = findViewById(R.id.total_income);
        totalExpense = findViewById(R.id.total_expense);
        remainingMoneyBar = findViewById(R.id.money_left_progressBar);
        remainingMoneyTxt = findViewById(R.id.money_left);
        welcomeTxt = findViewById(R.id.welcome_txt);

        welcomeTxt.setText("Welcome "+userDB.getUserName());

        incomeFab.setVisibility(View.GONE);
        expenseFab.setVisibility(View.GONE);
        isVisible = false;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isVisible) {
                    incomeFab.show();
                    expenseFab.show();
                    isVisible = true;
                } else {
                    incomeFab.hide();
                    expenseFab.hide();
                    isVisible = false;
                }
            }
        });
        navigateToAddIncome();
        navigateToAddExpense();
        navigateTOIncomeDetails();
        navigateToExpenseDetails();
        showIncome();
        showExpense();
        remainingMoney();

    }

    private void showIncome() {
        int income = incomeDb.totalIncome();
        totalIncome.setText(String.valueOf(income));
    }

    private void showExpense() {
        int expense = expenseDb.totalExpense();
        totalExpense.setText(String.valueOf(expense));
    }

    private void remainingMoney() {
        int totalIncome = incomeDb.totalIncome();
        int totalExpense = expenseDb.totalExpense();
        int rMoney = totalIncome - totalExpense;

        double val = (double) rMoney / (double) totalIncome;
        int percent = (int) (val * 100);
        remainingMoneyBar.setProgress(percent);
        remainingMoneyTxt.setText(String.valueOf(rMoney));
    }

    private void navigateToAddIncome() {
        incomeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeFab.hide();
                expenseFab.hide();
                Intent intent = new Intent(DashboardActivity.this, AddIncomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void navigateToAddExpense() {
        expenseFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incomeFab.hide();
                expenseFab.hide();
                Intent intent2 = new Intent(DashboardActivity.this, AddExpenseActivity.class);
                startActivity(intent2);
            }
        });
    }

    private void navigateTOIncomeDetails() {
        incomeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DashboardActivity.this, IncomeOverviewActivity.class);
                startActivity(intent2);
            }
        });
    }

    private void navigateToExpenseDetails() {
        expenseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(DashboardActivity.this, ExpenseOverviewActivity.class);
                startActivity(intent2);
            }
        });
    }
}