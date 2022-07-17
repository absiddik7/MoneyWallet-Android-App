package com.app.moneywallet;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.moneywallet.database.UserDB;

public class MainActivity extends AppCompatActivity {

    private UserDB userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userDb = new UserDB(MainActivity.this);
        //userDb.insert("init");
        Intent intent;
        if(userDb.isExist()){
            intent = new Intent(MainActivity.this, DashboardActivity.class);
        } else{
            intent = new Intent(MainActivity.this, UserActivity.class);
        }
        startActivity(intent);
    }
}