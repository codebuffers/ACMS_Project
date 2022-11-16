package com.example.acms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStaffLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        Button btn = (Button)findViewById(R.id.goAdminLogin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityStaffLogin.this, ActivityAdminLogin.class));
            }
        });

        Button btn2 = (Button)findViewById(R.id.goOfficerLogin);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityStaffLogin.this, ActivityOfficerLogin.class));
            }
        });

        Button btn3 = (Button)findViewById(R.id.goGuardLogin);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityStaffLogin.this, ActivityGuardLogin.class));
            }
        });

    }

}