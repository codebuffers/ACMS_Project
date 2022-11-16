package com.example.acms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acms.databinding.ActivityAdminLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityAdminLogin extends AppCompatActivity {

    ActivityAdminLoginBinding binding;
    ProgressBar progressBar;
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.INVISIBLE);

        binding.LoginBtnId.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String AdminSupID = binding.AdminSuperIDId.getText().toString();

                if (!AdminSupID.isEmpty()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    loadAccount (AdminSupID);

                } else {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityAdminLogin.this, "Please enter your Admin super ID!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Backt To the MainActivity Page function
        binding.BackToMainBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityAdminLogin.this, MainActivity.class));
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void loadAccount(String AdminSupID) {

        root = FirebaseDatabase.getInstance().getReference("Users").child("wStaff");
        root.child(AdminSupID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        root.child(AdminSupID).child("staffRole").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int staffRole = snapshot.getValue(Integer.class);
                                if (staffRole == 1) {
                                    Toast.makeText(ActivityAdminLogin.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), ActivityAdmin.class));
                                    finish();
                                } else {
                                    binding.progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(ActivityAdminLogin.this, "Not a valid Admin Super ID!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(ActivityAdminLogin.this, "Failed confirmation!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else  {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(ActivityAdminLogin.this, "Not a valid Admin Super ID!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ActivityAdminLogin.this, "Failed to login as Admin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}