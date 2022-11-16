package com.example.acms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityOfficer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer);


        //officer director activity

        //below btn starts pending visitors activity
        Button btn = (Button)findViewById(R.id.goPending);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityOfficer.this, ActivityOfficerPending.class));
            }
        });


        //below btn starts rejected visitors activity
        Button btn2 = (Button)findViewById(R.id.goRejected);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityOfficer.this, ActivityOfficerRejected.class));
            }
        });

    }
}