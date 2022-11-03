package com.triton.johnson.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.triton.johnson.R;
import com.triton.johnson.adapter.TicketViewAdapter;
import com.triton.johnson.alerter.ViewPagerTicketPhotoDetailsAdapter;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.requestpojo.ViewTicketsRequest;
import com.triton.johnson.responsepojo.ViewTicketsResponse;
import com.triton.johnson.utils.RestUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewTicketDetailsActivity extends AppCompatActivity {
    private String TAG = "ViewTicketDetailsActivity";
    private String ticketno;
    private List<ViewTicketsResponse.DataBean> ticketList = new ArrayList<>();


    Dialog dialog;
    int position;
    private List<ViewTicketsResponse.DataBean.TicketPhotoBean> ticketphotolist;

    TabLayout tabLayout;
    ViewPager viewPager;
    RelativeLayout profile_relative_back;


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ticket_details);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        profile_relative_back = findViewById(R.id.profile_relative_back);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ticketno = extras.getString("ticketno");
            position = extras.getInt("position");
        }

        Log.w(TAG,"ticketno : "+ticketno+"position  : "+position);

        if(ticketno != null){
            ViewTicketsResponseCall();
        }
        profile_relative_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    @SuppressLint("LogNotTimber")
    private void ViewTicketsResponseCall() {
        dialog = new Dialog(ViewTicketDetailsActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<ViewTicketsResponse> call = apiInterface.ViewTicketsResponseCall(RestUtils.getContentType(), viewTicketsRequest());
        Log.w(TAG,"ViewTicketsResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<ViewTicketsResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<ViewTicketsResponse> call, @NonNull Response<ViewTicketsResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"ViewTicketsResponse" + new Gson().toJson(response.body()));
                Log.w(TAG,"position" + position);
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        ticketList.clear();
                        if(response.body().getData() != null && response.body().getData().size()>0){
                            ticketList = response.body().getData();
                        }

                        if (ticketList != null && ticketList.size()>0) {
                           ticketphotolist = response.body().getData().get(position).getTicket_photo();
                            if(ticketphotolist != null && ticketphotolist.size()>0){
                                viewpageData(ticketphotolist);
                            }


                        }
                        else {

                        }





                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<ViewTicketsResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("ViewTicketsResponse flr", "--->" + t.getMessage());

            }
        });

    }
    private ViewTicketsRequest viewTicketsRequest() {

        /*
         * ticket_no : TIC-1
         */

        ViewTicketsRequest viewTicketsRequest = new ViewTicketsRequest();
        viewTicketsRequest.setTicket_no(ticketno);
        Log.w(TAG,"viewTicketsRequest "+ new Gson().toJson(viewTicketsRequest));
        return viewTicketsRequest;
    }

    private void viewpageData(List<ViewTicketsResponse.DataBean.TicketPhotoBean> ticketphotolist) {
        tabLayout.setupWithViewPager(viewPager, true);

        ViewPagerTicketPhotoDetailsAdapter viewPagerTicketPhotoDetailsAdapter = new ViewPagerTicketPhotoDetailsAdapter(getApplicationContext(), ticketphotolist);
        viewPager.setAdapter(viewPagerTicketPhotoDetailsAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == ticketphotolist.size()) {
                currentPage = 0;
            }
            viewPager.setCurrentItem(currentPage++, false);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

