package com.example.acms;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityOfficerRejected extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapterOfficerApproved mainAdapterOfficerApproved;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_rejected);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //retrieving the database user data using the same Adapter with Admin Approved List
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users")
                                .child("Visitors").orderByChild("visitorstatus").startAt("Rejected").endAt("Rejected\uf8ff"), Model.class)
                        .build();

        mainAdapterOfficerApproved = new MainAdapterOfficerApproved(options);
        recyclerView.setAdapter(mainAdapterOfficerApproved);
    }

    //on start and on stop for the retriever user data
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapterOfficerApproved.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //here, even if its onStop we put ".startListening", because we want keep the user data even the app goes background
        mainAdapterOfficerApproved.startListening();
    }
}


