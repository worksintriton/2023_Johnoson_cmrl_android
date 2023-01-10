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
import com.triton.johnson.R;
import com.triton.johnson.api.ApiCall;
import com.triton.johnson.responsepojo.ViewTicketsResponse;
import com.triton.johnson.view.ViewTicketDetailsActivity;
import com.triton.johnson.view.ViewTickets;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iddinesh.
 */

public class TicketViewAdapter extends RecyclerView.Adapter<TicketViewAdapter.MyViewHolder> {

    List<ViewTicketsResponse.DataBean> ticketList;
    List<ViewTicketsResponse.DataBean.PartDet> partDetails;

    ViewTicketsResponse.DataBean.PartDet partDet;

    private FragmentActivity context;

    private String title;

    private String TAG ="TicketViewAdapter";


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_status, txt_ticketid, txt_empname, txt_empid, txt_dateofcreate,txt_comments,txt_Partname;
        CardView cv_viewtickets;
        LinearLayout ll_Partname;
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
            txt_Partname = view.findViewById(R.id.txt_partname);
            ll_Partname = view.findViewById(R.id.ll_partname);

        }
    }


    public TicketViewAdapter(FragmentActivity context, List<ViewTicketsResponse.DataBean> ticketList, String title, List<ViewTicketsResponse.DataBean.PartDet> partDet) {
        this.ticketList = ticketList;
        this.context = context;
        this.title = title;
        this.partDetails = partDet;
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
    public void onBindViewHolder(@NonNull TicketViewAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ViewTicketsResponse.DataBean dataBean = ticketList.get(position);
       // Log.w(TAG,"empid : "+dataBean.getUser_id().getEmployee_id());

        holder.txt_status.setText(dataBean.getTicket_status());
        holder.txt_ticketid.setText(dataBean.getTicket_no());
        holder.txt_empname.setText(dataBean.getUser_id().getUsername());
        holder.txt_empid.setText("EMP_"+dataBean.getUser_id().getEmployee_id());
        holder.txt_dateofcreate.setText(dataBean.getDate_of_create());
        holder.txt_comments.setText(dataBean.getTicket_comments());


        if(dataBean.getTicket_status().equals("Completed")){

            holder.ll_Partname.setVisibility(View.VISIBLE);

            Log.e("Nish",""+dataBean.getPart_no_req());

            holder.txt_Partname.setText(dataBean.getPart_no_req());

        }

//        if(dataBean.getPart_det().size() >0){
//
//            holder.ll_Partname.setVisibility(View.VISIBLE);
//
//            for (int i = 0; i< dataBean.getPart_det().size();i++) {
//
//                Log.e("Nish",""+dataBean.getPart_det().get(i).getPart_name());
//
//                holder.txt_Partname.setText(dataBean.getPart_det().get(0).getPart_name());
//            }
//
//        }


       // Log.e("Nish",""+dataBean.getPart_det().get(0).getPart_name());
       // Log.e("Nish",""+dataBean.getPart_det().);

//        if(dataBean.getTicket_status().equals("Completed")){
//            Log.e("Nish",""+dataBean.getPart_det().get(position).getPart_name());
//            holder.txt_Partname.setText(dataBean.getPart_det().get(position).getPart_name());
//
//        }

    //    Log.e("Nish",""+dataBean.getPart_det().size());
      //  Log.e("Nish 1",""+partDetails.size());

//        if(dataBean.getPart_det().size() >0){
//
//            holder.ll_Partname.setVisibility(View.VISIBLE);
//
//             for (int i = 0; i< dataBean.getPart_det().size();i++){
//
//                 Log.e("Nish",""+dataBean.getPart_det().get(i).getPart_name());
////                 String partname = dataBean.getPart_det().get(i).getPart_name();
////                 Log.e("Nish 1",""+partname);
////                 ArrayList<String> arlipartname = new ArrayList<>();
////                 arlipartname.add(dataBean.getPart_det().get(i).getPart_name());
////                 Log.e("Nish 2",""+arlipartname);
//                 holder.txt_Partname.setText(dataBean.getPart_det().get(i).getPart_name());
//
//             }
//        }

        //      String imageURL = ApiCall.API_URL+ticketphotolist.get(position).getImage_path();
       // ticketList = response.body().getData().get(position).getTicket_photo();



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


