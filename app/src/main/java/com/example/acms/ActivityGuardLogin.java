package com.example.acms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acms.databinding.ActivityGuardLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityGuardLogin extends AppCompatActivity {

    //this activity using the binding layout method...
    ActivityGuardLoginBinding binding;
    ProgressBar progressBar;

    //db reference
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuardLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.INVISIBLE);


        //login btn which calling the loadAccount function below...
        binding.LoginBtnId.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String GuardSupID = binding.GuardSuperIDId.getText().toString();

                if (!GuardSupID.isEmpty()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    loadAccount (GuardSupID);

                } else {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityGuardLogin.this, "Please enter your Guard super ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Backt To the MainActivity Page function
        binding.BackToMainBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityGuardLogin.this, MainActivity.class));
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }


    //loading the specific account according to the staff role that is in db...
    private void loadAccount(String GuardSupID) {
        root = FirebaseDatabase.getInstance().getReference("Users").child("wStaff");
        root.child(GuardSupID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        root.child(GuardSupID).child("staffRole").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int staffRole = snapshot.getValue(Integer.class);
                                if (staffRole == 3) {
                                    Toast.makeText(ActivityGuardLogin.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ActivityGuard.class));
                                    finish();
                                } else {
                                    binding.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(ActivityGuardLogin.this, "Not a valid Guard Super ID!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ActivityGuardLogin.this, "Failed confirmation!", Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else  {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ActivityGuardLogin.this, "Not a valid Guard Super ID!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityGuardLogin.this, "Failed to login as Guard!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}