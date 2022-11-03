package com.triton.johnson.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.triton.johnson.R;
import com.triton.johnson.api.RetrofitClient;import com.triton.johnson.responsepojo.CMRLTicketListResponse;
import com.triton.johnson.view.CMRLUpdateStatusActivity;

import com.triton.johnson.view.ViewTickets;


import java.text.DateFormat;
import java.text.ParseException;import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Iddinesh.
 */

public class TicketsListAdapter extends RecyclerView.Adapter<TicketsListAdapter.MyViewHolder> {

    private String issues, list;

    private Activity activity;
    //    private final LocalDateTime now = LocalDateTime.now().withTime(0, 0, 0, 0);
    private String updateAt;
    private String outTime;
    private List<CMRLTicketListResponse.DataBean> ticketList;


    private String TAG = "TicketsListAdapter";

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView, txt_status, textTicketID, nameTextView, reasonTextView, amTextView, monthTextView, yearTextview,txt_job_id,txt_fault_type,txt_serving_level;

        LinearLayout rscheduleLinearLayout, wholeLayout;

        ImageView circularImageView;

        ImageView clockImageView;

        public MyViewHolder(View view) {
            super(view);
            dateTextView = view.findViewById(R.id.date_one_text);
            txt_status = view.findViewById(R.id.txt_status);
            textTicketID = view.findViewById(R.id.text_ticketid);
            txt_job_id = view.findViewById(R.id.txt_job_id);
            nameTextView = view.findViewById(R.id.name_text);
            reasonTextView = view.findViewById(R.id.complaint_text);
            amTextView = view.findViewById(R.id.min_text);
            monthTextView = view.findViewById(R.id.month_text);
            yearTextview = view.findViewById(R.id.year_text);
            txt_fault_type = view.findViewById(R.id.txt_fault_type);
            rscheduleLinearLayout = view.findViewById(R.id.reschedule_layout);
            wholeLayout = view.findViewById(R.id.department_status);
            circularImageView = view.findViewById(R.id.profile_image);
            clockImageView = view.findViewById(R.id.image);
            txt_serving_level = view.findViewById(R.id.txt_serving_level);
        }
    }
    public TicketsListAdapter(Activity activity, List<CMRLTicketListResponse.DataBean> ticketList, String issues, String list) {
        this.activity = activity;
        this.ticketList = ticketList;
        this.issues = issues;
        this.list = list;
    }
    @NonNull
    @Override
    public TicketsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_ticket_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TicketsListAdapter.MyViewHolder holder, int position) {

        final CMRLTicketListResponse.DataBean dataBean = ticketList.get(position);

        String dateofcreate = dataBean.getDate_of_create();
        if(dateofcreate != null){
            String[] splitStr = dateofcreate.split("\\s+");
            String strdate =  splitStr[0];
            String time =  splitStr[1];
            Log.w(TAG,"date : "+strdate+" time : "+time);
            @SuppressLint("SimpleDateFormat") DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
            @SuppressLint("SimpleDateFormat") DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            Date date = null;
            try {
                date = inputFormat.parse(strdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String outputDateStr = null;
            if (date != null) {
                outputDateStr = outputFormat.format(date);
                Log.w(TAG,"outputDateStr : "+outputDateStr);
                String[] splitdate = outputDateStr.split("\\s+");
                String strdateone = splitdate[0];
                String strmonthone = splitdate[1];
                String stryearone = splitdate[2];
                Log.w(TAG, "date : " + strdate + " time : " + time);
                holder.dateTextView.setText(strdateone);
                holder.monthTextView.setText(strmonthone);
                holder.yearTextview.setText(stryearone);
            }

        }

       if (dataBean.getImage_list() != null && dataBean.getImage_list().size()>0) {
            Log.w(TAG,"ImagePath : "+RetrofitClient.BASE_URL+dataBean.getImage_list().get(0).getImage_path());
            Glide.with(activity)
                    .load(RetrofitClient.BASE_URL+dataBean.getImage_list().get(0).getImage_path())
                    .into(holder.circularImageView);
        }

        if(dataBean.getTicket_no() != null) {
            holder.textTicketID.setText(dataBean.getTicket_no());
        }
        if(dataBean.getJob_detail().getJob_no() != null) {
          holder.txt_job_id.setText(dataBean.getJob_detail().getJob_no());
        }if(dataBean.getJob_detail().getServing_level() != null) {
          holder.txt_serving_level.setText(dataBean.getJob_detail().getServing_level());
        }
        if(dataBean.getStation_detail().getStation_name() != null) {
         holder.nameTextView.setText(dataBean.getStation_detail().getStation_name());
        }
        if(dataBean.getFault_type() != null) {
         holder.txt_fault_type.setText(dataBean.getFault_type());
        }
        if(dataBean.getBreak_down_observed() != null) {
         holder.reasonTextView.setText(dataBean.getBreak_down_observed());
        }if(dataBean.getStatus() != null) {
         holder.txt_status.setText(dataBean.getStatus());
        }
        if(dataBean.getStatus() != null && dataBean.getStatus().equalsIgnoreCase("Completed")) {
            holder.rscheduleLinearLayout.setVisibility(View.VISIBLE);
        }else{
            holder.rscheduleLinearLayout.setVisibility(View.INVISIBLE);
        }


















      /*  holder.textTicketID.setText("Ticket ID :"+" "+movie.getId());
        holder.nameTextView.setText(movie.getName());
        holder.reasonTextView.setText(movie.getStatus());*/
    // holder.timeTextView.setText(outTime + " " + time[2]);

   /*     String[] ImageString = movie.getPhotos().split(",");
        if (movie.getPhotos().equalsIgnoreCase("")) {

            Log.e("Image", movie.getPhotos());

        }
        else {

            if (ImageString.length >= 2) {

                Log.w(TAG,"Image"+ "" + ApiCall.BASE_URL+"assets/uploads/" + ImageString[0]);

                Glide.with(context)
                        .load(ApiCall.BASE_URL+"assets/uploads/" + ImageString[0])
                        .placeholder(R.drawable.no_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.circularImageView);


            } else {
                Log.w(TAG,"Else Image"+ "" + ApiCall.BASE_URL+"assets/uploads/" + movie.getPhotos());

                Glide.with(context)
                        .load(ApiCall.BASE_URL+"assets/uploads/" + movie.getPhotos())
                        .placeholder(R.drawable.no_image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.circularImageView);


            }
        }*/


        holder.wholeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ViewTickets.class);
            intent.putExtra("ticketId", dataBean.getTicket_no());
           /* intent.putExtra("departmentCode", movie.getName());
            intent.putExtra("stationLocation", movie.getStationType());
            intent.putExtra("ticket_status", movie.getStatus());
            intent.putExtra("ticket", movie.getMessage());
            intent.putExtra("title", list + " - " + movie.getName());*/
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.new_right, R.anim.new_left);
        });
        holder.rscheduleLinearLayout.setOnClickListener(view -> {
            Intent intent = new Intent(activity, CMRLUpdateStatusActivity.class);
            intent.putExtra("ticketId", dataBean.getTicket_no());
            intent.putExtra("ticketStatus", dataBean.getStatus());
           /* intent.putExtra("departmentCode", movie.getName());
            intent.putExtra("stationLocation", movie.getStationType());
            intent.putExtra("ticketStatus", movie.getName());
            intent.putExtra("issues", movie.getMessage());*/
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.new_right, R.anim.new_left);


        });


    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

}
