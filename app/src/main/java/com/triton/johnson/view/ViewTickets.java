package com.triton.johnson.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.triton.johnson.R;
import com.triton.johnson.adapter.TicketViewAdapter;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.arraylist.DeapartmentStatusList;
import com.triton.johnson.requestpojo.ViewTicketsRequest;
import com.triton.johnson.responsepojo.ViewTicketsResponse;
import com.triton.johnson.session.SessionManager;
import com.triton.johnson.utils.ConnectionDetector;
import com.triton.johnson.utils.RestUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iddinesh.
 */

public class ViewTickets extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    Toolbar toolbar;



    private RecyclerView recyclerView;


    public static ArrayList<DeapartmentStatusList> departmentListArrayList = new ArrayList<>();



    TextView emptyCustomFontTextView;

    SwipeRefreshLayout mWaveSwipeRefreshLayout;


    Dialog dialog;

    SessionManager sessionManager;

    public static String empid = "";
    public static String station_code = "";
    public static String departmentCode = "";
    public static String ticketStatus = "";
    public static String userLevel = "";
    public static String ticketId = "";
    public static String title = "";
    public static String ticket = "";


    ImageView emptyImageView;

    Button retryButton;

    public static String networkStatus = "", stationLocation;

    private String TAG ="ViewTickets";

    RelativeLayout profile_relative_back;

    TicketViewAdapter ticketViewAdapter;
    private List<ViewTicketsResponse.DataBean> ticketList = new ArrayList<>();
    List<ViewTicketsResponse.DataBean.PartDet> partDet = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.department_status_list);
        setContentView(R.layout.activity_view_tickets);
        Log.w(TAG," onCreate----> View Tickets");

        sessionManager = new SessionManager(ViewTickets.this);
        HashMap<String, String> hashMap = sessionManager.getUserDetails();
        userLevel = hashMap.get(SessionManager.KEY_USER_LEVEL);
        empid = hashMap.get(SessionManager.KEY_EMPID);
        station_code = hashMap.get(SessionManager.KEY_STATION_CODE);

        departmentCode = Objects.requireNonNull(getIntent().getExtras()).getString("departmentCode");

        ticketStatus = getIntent().getExtras().getString("ticket_status");
        ticket = getIntent().getExtras().getString("ticket");
        Log.w(TAG+" onCreate-->","ticketStatus :"+ticketStatus+"ticket :"+ticket);

        ticketId = getIntent().getExtras().getString("ticketId");
        if(ticketId != null && !ticketId.isEmpty()){
            ViewTicketsResponseCall();
        }

        title = getIntent().getExtras().getString("title");
        stationLocation = getIntent().getExtras().getString("stationLocation");

        profile_relative_back = findViewById(R.id.profile_relative_back);
        profile_relative_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar = findViewById(R.id.toolbar);

        recyclerView = findViewById(R.id.recycler_view);

        emptyCustomFontTextView = findViewById(R.id.empty_text);
        emptyImageView = findViewById(R.id.empty_image);

        retryButton = findViewById(R.id.retry_button);

        mWaveSwipeRefreshLayout = findViewById(R.id.main_swipe);

        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);

        emptyCustomFontTextView.setVisibility(View.GONE);



        retryButton.setOnClickListener(view -> {
            networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
            if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

                mWaveSwipeRefreshLayout.setVisibility(View.GONE);
                emptyCustomFontTextView.setVisibility(View.VISIBLE);
                emptyImageView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);

                emptyImageView.setImageResource(R.mipmap.wifi);
                emptyCustomFontTextView.setText("Please check your internet connectivity and try again");


            } else {

                mWaveSwipeRefreshLayout.setVisibility(View.VISIBLE);
                ViewTicketsResponseCall();


            }

        });

        emptyImageView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        emptyCustomFontTextView.setVisibility(View.GONE);

        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());



    }

    @Override
    public void onRefresh() {

       refresh();
    }

    private void refresh() {
        new Handler().postDelayed(() -> mWaveSwipeRefreshLayout.setRefreshing(false), 3000);
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private DepartmentListClass.RecyclerItemClickListener.OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, DepartmentListClass.RecyclerItemClickListener.OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

           /* mWaveSwipeRefreshLayout.setVisibility(View.GONE);
            emptyCustomFontTextView.setVisibility(View.VISIBLE);*/
            emptyImageView.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.VISIBLE);

            emptyImageView.setImageResource(R.mipmap.wifi);
           // emptyCustomFontTextView.setText("Please check your internet connectivity and try again");

        }
        else {

         //   mWaveSwipeRefreshLayout.setVisibility(View.VISIBLE);

          /*  if (userLevel.equalsIgnoreCase("4")) {
                ViewTicketList(ApiCall.API_URL + "viewticket.php?ticket_id=" + ticketId.replace(" ", "%20"));
            } else if (userLevel.equalsIgnoreCase("5")) {
                ViewTicketList(ApiCall.API_URL + "viewticket.php?ticket_id=" + ticketId.replace(" ", "%20"));
            }
            else if (userLevel.equalsIgnoreCase("6")) {
                ViewTicketList(ApiCall.API_URL + "viewticket.php?ticket_id=" + ticketId.replace(" ", "%20"));

            }*/





        }





    }


    @SuppressLint("LogNotTimber")
    private void ViewTicketsResponseCall() {
        dialog = new Dialog(ViewTickets.this, R.style.NewProgressDialog);
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
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        ticketList.clear();
                        if(response.body().getData() != null && response.body().getData().size()>0){
                            ticketList = response.body().getData();
//                            partDet = response.body().getData();
                        }

                        if (ticketList != null && ticketList.size()>0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyImageView.setVisibility(View.GONE);
                            retryButton.setVisibility(View.GONE);
                            ticketViewAdapter = new TicketViewAdapter(ViewTickets.this, ticketList,"",partDet);


                            mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                            recyclerView.setAdapter(ticketViewAdapter);



                        }
                        else {
                            recyclerView.setVisibility(View.GONE);
                            emptyImageView.setVisibility(View.VISIBLE);
                            retryButton.setVisibility(View.GONE);
                            emptyCustomFontTextView.setVisibility(View.VISIBLE);
                            emptyImageView.setImageResource(R.mipmap.empty_icon);
                            emptyCustomFontTextView.setText("No Breakdowns");
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
        viewTicketsRequest.setTicket_no(ticketId);
        Log.w(TAG,"viewTicketsRequest "+ new Gson().toJson(viewTicketsRequest));
        return viewTicketsRequest;
    }
}

