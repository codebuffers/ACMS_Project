package com.example.acms;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityGuard extends AppCompatActivity {

   RecyclerView recyclerView;
   MainAdapterGuard mainAdapterGuard;

   @SuppressLint("MissingInflatedId")
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_guard);

      recyclerView = (RecyclerView)findViewById(R.id.rv);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      FirebaseRecyclerOptions<Model> options =
              new FirebaseRecyclerOptions.Builder<Model>()
                      .setQuery(FirebaseDatabase.getInstance().getReference().child("Users")
                              .child("Visitors").orderByChild("visitorstatus").startAt("Approved").endAt("Approved\uf8ff"), Model.class)
                      .build();

      mainAdapterGuard = new MainAdapterGuard(options);
      recyclerView.setAdapter(mainAdapterGuard);
   }

   @Override
   protected void onStart() {
      super.onStart();
      mainAdapterGuard.startListening();
   }

   @Override
   protected void onStop() {
      super.onStop();
      mainAdapterGuard.startListening();
   }
}