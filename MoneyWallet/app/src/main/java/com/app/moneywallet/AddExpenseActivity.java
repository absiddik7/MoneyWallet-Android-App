package com.app.moneywallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.moneywallet.database.ExpenseDB;

public class AddExpenseActivity extends AppCompatActivity {

    private ExpenseDB expenseDb;
    Button cancelBtn;
    Button saveBtn;
    EditText expenseTxt;
    Spinner expenseSpinner;
    String expenseCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_expense);


        expenseDb = new ExpenseDB(AddExpenseActivity.this);
        expenseTxt = findViewById(R.id.add_expense_txt);
        expenseSpinner = findViewById(R.id.expense_spinner);
        saveBtn = findViewById(R.id.expense_save_btn);
        cancelBtn = findViewById(R.id.expense_cancel_btn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expense_category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseSpinner.setAdapter(adapter);

        expenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                expenseCategory = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Selected Category", Toast.LENGTH_LONG).show();
            }
        });

        cancelSaving();
        saveExpense();
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(AddExpenseActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    private void cancelSaving() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToDashboard();
            }
        });
    }

    private void saveExpense() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expenseText = expenseTxt.getText().toString();
                int expense = Integer.parseInt(expenseText);

                if (expenseDb.isExist(expenseCategory)) {
                    int oldMoney = expenseDb.catIncome(expenseCategory);
                    int upAmount = oldMoney + expense;
                    expenseDb.update(expenseCategory, upAmount);
                } else {
                    expenseDb.insert(expense, expenseCategory);
                }

                expenseTxt.setText("");
                navigateToDashboard();
            }
        });
    }
}