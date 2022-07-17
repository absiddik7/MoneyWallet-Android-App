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

import com.app.moneywallet.database.IncomeDB;

public class AddIncomeActivity extends AppCompatActivity {
    private IncomeDB incomeDb;
    Button cancelBtn;
    Button saveBtn;
    EditText incomeTxt;
    Spinner incomeSpinner;
    String incomeCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        incomeDb = new IncomeDB(AddIncomeActivity.this);
        cancelBtn = findViewById(R.id.income_cancel_btn);
        saveBtn = findViewById(R.id.income_save_btn);
        incomeTxt = findViewById(R.id.add_income_txt);
        incomeSpinner= findViewById(R.id.income_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.income_category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpinner.setAdapter(adapter);

        incomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incomeCategory = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
                Toast.makeText(parent.getContext(), "Selected Category",Toast.LENGTH_LONG).show();
            }
        });

        cancelSaving();
        saveIncome();
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(AddIncomeActivity.this, DashboardActivity.class);
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

    private void saveIncome() {
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String incomeText = incomeTxt.getText().toString();
                int income = Integer.parseInt(incomeText);
                if(incomeDb.isExist(incomeCategory)){
                    int oldMoney = incomeDb.catIncome(incomeCategory);
                    int upAmount = oldMoney + income;
                    incomeDb.update(incomeCategory,upAmount);
                } else{
                    incomeDb.insert(income,incomeCategory);
                }
                incomeTxt.setText("");
                navigateToDashboard();
            }
        });
    }
}