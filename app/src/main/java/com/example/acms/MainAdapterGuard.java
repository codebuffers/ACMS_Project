package com.example.acms;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


//different adapter for guard due to different button function
public class MainAdapterGuard extends FirebaseRecyclerAdapter<Model, MainAdapterGuard.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterGuard(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Model model) {
        holder.name.setText(model.getVisitorname());
        holder.ic.setText("IC: "+model.getVisitoric());
        holder.phone.setText("Phone: "+model.getVisitorphone());
        holder.date.setText("Visit Date: "+model.getVisitdate());
        holder.time.setText("Visit Time: "+model.getVisittime());
        holder.extime.setText("Visit Exit Time: "+model.getVisitexittime());
        holder.status.setText("Status: "+model.getVisitorstatus());
        holder.ticket.setText("Ticket: "+model.getVisitorticket());
        holder.reason.setText(model.getVisitreason());
        holder.sreason.setText(model.getStatusreason());

        Glide.with(holder.img.getContext())
                .load(model.getVisitorimage())
                .placeholder(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.storage.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        //visitor put did not show function below
        holder.btnNoShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.noshow_popup))
                        .setExpanded(true, 480)
                        .create();

                View v = dialogPlus.getHolderView();

                String sreason = "Visitor didn't show up on designated time period!";
                String statusA = "Did not turn up";

                Button btnConfirmApprove = v.findViewById(R.id.btnConfirmNoShow);

                dialogPlus.show();

                btnConfirmApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("statusreason",sreason);
                        map.put("visitorstatus",statusA);

                        FirebaseDatabase.getInstance().getReference().child("Users").child("Visitors")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.sreason.getContext(), "Request Visit Approved Successfully!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.sreason.getContext(), "Failed to Approve!", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitor_data_approved_guard,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, ic, phone, date, time, extime, status, ticket,reason, sreason;

        Button btnNoShow;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.visitor_name);
            ic = (TextView)itemView.findViewById(R.id.visitor_ic);
            phone = (TextView)itemView.findViewById(R.id.visitor_phone);
            date = (TextView)itemView.findViewById(R.id.visit_date);
            time = (TextView)itemView.findViewById(R.id.visit_time);
            extime = (TextView)itemView.findViewById(R.id.visit_exit_time);
            status = (TextView)itemView.findViewById(R.id.visitor_status);
            ticket = (TextView)itemView.findViewById(R.id.visitor_ticket);
            reason = (TextView)itemView.findViewById(R.id.visit_reason);
            sreason = (TextView)itemView.findViewById(R.id.status_reason);

            btnNoShow = (Button)itemView.findViewById(R.id.btnDTU);

        }
    }
}