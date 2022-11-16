package com.example.acms;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

//different adapter for officer&admin due to no button function
public class MainAdapterOfficerApproved extends FirebaseRecyclerAdapter<Model, MainAdapterOfficerApproved.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapterOfficerApproved(@NonNull FirebaseRecyclerOptions<Model> options) {
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

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visitor_data_approved_officer,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, ic, phone, date, time, extime, status, ticket,reason, sreason;


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

        }
    }
}