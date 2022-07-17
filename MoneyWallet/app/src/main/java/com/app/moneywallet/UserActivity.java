package com.app.moneywallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.moneywallet.database.UserDB;

public class UserActivity extends AppCompatActivity {

    private UserDB userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userDb = new UserDB(UserActivity.this);

        EditText nameTxt = findViewById(R.id.name_editTxt);
        Button saveBtn = findViewById(R.id.user_save_Btn);

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userName = nameTxt.getText().toString();
                userDb.insert(userName );
                Toast.makeText(getApplicationContext(),"Name Saved",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }
}