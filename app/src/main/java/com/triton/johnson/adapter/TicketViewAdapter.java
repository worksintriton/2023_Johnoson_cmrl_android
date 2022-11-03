package com.triton.johnson.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.triton.johnson.arraylist.DeapartmentStatusList;
import com.triton.johnson.R;
import com.triton.johnson.api.ApiCall;
import com.triton.johnson.responsepojo.ViewTicketsResponse;
import com.triton.johnson.view.DepartmentStatusDetail;
import com.triton.johnson.view.DepartmentStatusListClass;
import com.triton.johnson.view.IssuseEditList;
import com.triton.johnson.view.StationEditLayout;
import com.triton.johnson.view.ViewTicketDetailsActivity;
import com.triton.johnson.view.ViewTickets;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Iddinesh.
 */

public class TicketViewAdapter extends RecyclerView.Adapter<TicketViewAdapter.MyViewHolder> {

    List<ViewTicketsResponse.DataBean> ticketList;

    private FragmentActivity context;

    private String title;

    private String TAG ="TicketViewAdapter";


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_status, txt_ticketid, txt_empname, txt_empid, txt_dateofcreate,txt_comments;
        CardView cv_viewtickets;


        ImageView circularImageView;
        ImageView img_tickets;
        public MyViewHolder(View view) {
            super(view);
            txt_status =  view.findViewById(R.id.txt_status);
            img_tickets =  view.findViewById(R.id.img_tickets);
            txt_ticketid =  view.findViewById(R.id.txt_ticketid);
            txt_empname = view.findViewById(R.id.txt_empname);
            txt_empid =  view.findViewById(R.id.txt_empid);
            txt_dateofcreate =  view.findViewById(R.id.txt_dateofcreate);
            txt_comments =  view.findViewById(R.id.txt_comments);
            cv_viewtickets =  view.findViewById(R.id.cv_viewtickets);


        }
    }


    public TicketViewAdapter(FragmentActivity context, List<ViewTicketsResponse.DataBean> ticketList , String title) {
        this.ticketList = ticketList;
        this.context = context;
        this.title = title;
    }

    @NonNull
    @Override
    public TicketViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ticket_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TicketViewAdapter.MyViewHolder holder, int position) {
        final ViewTicketsResponse.DataBean dataBean = ticketList.get(position);
        Log.w(TAG,"empid : "+dataBean.getUser_id().getEmployee_id());

        holder.txt_status.setText(dataBean.getTicket_status());
        holder.txt_ticketid.setText(dataBean.getTicket_no());
        holder.txt_empname.setText(dataBean.getUser_id().getUsername());
        holder.txt_empid.setText("EMP_"+dataBean.getUser_id().getEmployee_id());
        holder.txt_dateofcreate.setText(dataBean.getDate_of_create());
        holder.txt_comments.setText(dataBean.getTicket_comments());

        if (dataBean.getTicket_photo() != null && dataBean.getTicket_photo().size()>0) {
            Glide.with(context)
                    .load(ApiCall.API_URL+dataBean.getTicket_photo().get(0).getImage_path())
                    .into(holder.img_tickets);

        }

        holder.cv_viewtickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewTicketDetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ticketno", dataBean.getTicket_no());
                intent.putExtra("position", position);
                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return ticketList.size();
    }



}


