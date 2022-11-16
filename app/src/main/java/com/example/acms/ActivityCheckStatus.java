package com.example.acms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acms.databinding.ActivityCheckStatusBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityCheckStatus extends AppCompatActivity {

    //Using binding method to get Visitor Status Result
    ActivityCheckStatusBinding binding;

    ProgressBar progressBar;
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.INVISIBLE);

        //onclick listener for button to call the visitor status getter function...
        binding.CheckStatusBtnId.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String VisitorTicket = binding.VisitorTicketId.getText().toString();

                if (!VisitorTicket.isEmpty()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    loadStatus(VisitorTicket);

                } else {

                    //in case invalid ticket num key in...
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityCheckStatus.this, "Please enter your saved ticket!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Backt To the MainActivity Page function
        binding.BackToMainBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityCheckStatus.this, MainActivity.class));
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }


    //loading the visitor status from db according to the ticket number...
    private void loadStatus(String VisitorTicket) {
        root = FirebaseDatabase.getInstance().getReference("Users");
        root.child("Visitors").child(VisitorTicket).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        //if the entered ticket is exist in db...
                        Toast.makeText(ActivityCheckStatus.this, "Status Loaded Successfully", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String VisitorName = String.valueOf(dataSnapshot.child("visitorname").getValue());
                        String VisitorStatus = String.valueOf(dataSnapshot.child("visitorstatus").getValue());
                        String StatusReason = String.valueOf(dataSnapshot.child("statusreason").getValue());
                        binding.VisitorName.setText("Name: " + VisitorName);
                        binding.VisitorStatus.setText("Status: " + VisitorStatus);
                        binding.StatusReason.setText("Reason Status: " + StatusReason);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    }
                    else  {

                        //if the entered ticket is not exist in db...
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ActivityCheckStatus.this, "Not a valid request visit ticket!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //if the entered ticket is not exist or error in db...
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityCheckStatus.this, "Failed to load visit request status!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
