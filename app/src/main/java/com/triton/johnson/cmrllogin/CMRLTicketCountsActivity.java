package com.triton.johnson.cmrllogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.triton.johnson.R;
import com.triton.johnson.adapter.TicketsListAdapter;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.arraylist.StationList;
import com.triton.johnson.requestpojo.CMRLTicketListRequest;
import com.triton.johnson.requestpojo.StationNameRequest;
import com.triton.johnson.responsepojo.CMRLTicketListResponse;
import com.triton.johnson.responsepojo.JobNumberResponse;
import com.triton.johnson.responsepojo.JohnsonTicketListResponse;
import com.triton.johnson.responsepojo.StationNameResponse;
import com.triton.johnson.session.SessionManager;
import com.triton.johnson.utils.ConnectionDetector;
import com.triton.johnson.utils.RestUtils;
import com.triton.johnson.view.CmrlLoginDashboardActivity;
import com.triton.johnson.view.DepartmentListClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CMRLTicketCountsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {


    private String TAG = "CMRLTicketCountsActivity";
    private String ticketstatus;
    LinearLayout back_layout;
    private String id;

    private RecyclerView recyclerView;

    private TicketsListAdapter ticketsListAdapter;
    private TextView emptyCustomFontTextView;
    private SwipeRefreshLayout mWaveSwipeRefreshLayout;
    private FloatingActionButton floatingActionButton;
    private Dialog  dialog;
    private ImageView emptyImageView;
    private Button retryButton;
    private String networkStatus = "";
    private String selectStatus = "0";
    private LinearLayout elvalorLine, underLine;
    private LinearLayoutManager mLayoutManager;



    ArrayList<StationList> stationListArrayList = new ArrayList<>();
    private int type = 1;
    private String StationName_id = "";
    private List<CMRLTicketListResponse.DataBean> ticketList = new ArrayList<>();
    private List<StationNameResponse.DataBean> stationNameList = new ArrayList<>();
    HashMap<String, String> hashMap_StationId = new HashMap<>();
    HashMap<String, String> hashMap_JoBNoId = new HashMap<>();
    Spinner spinner_ticket_status, spinner_stationname;
    private String StationName;

    ArrayAdapter<String> spinnerArrayAdapter;
    LinearLayout ll_search,ll_clear;
    Button btn_search;
    private List<JobNumberResponse.DataBean> elivatorJobNoList = new ArrayList<>();
    private String JobNo = "";
    private String Job_id = "";

    RelativeLayout rl_filters;
    LinearLayout ll_ticket_status,ll_station_name,ll_search_clear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmrl_ticket_counts);

        SessionManager sessionManager = new SessionManager(CMRLTicketCountsActivity.this);
        HashMap<String, String> hashMap = sessionManager.getUserDetails();
        id = hashMap.get(SessionManager.KEY_ID);

        back_layout = findViewById(R.id.back_layout);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ticketstatus = bundle.getString("ticketstatus");
            type = bundle.getInt("type");
            Log.w(TAG,"ticketstatus : "+ticketstatus+" type : "+type);
        }


        recyclerView = findViewById(R.id.recycler_view);
        emptyCustomFontTextView =  findViewById(R.id.empty_text);
        emptyImageView = findViewById(R.id.empty_image);
        elvalorLine =  findViewById(R.id.elvator_line);
        LinearLayout elvatorLayout = findViewById(R.id.elvator_layout);
        LinearLayout underLayout = findViewById(R.id.under_layout);
        underLine =  findViewById(R.id.under_ground_line);

        mWaveSwipeRefreshLayout = findViewById(R.id.main_swipe);
        floatingActionButton =  findViewById(R.id.fab_createevent);
        //LinearLayout profileLinearLayout = view.findViewById(R.id.profile_layout);
        ImageView img_new_ticket = findViewById(R.id.img_new_ticket);
        ImageView img_profile = findViewById(R.id.img_profile);
        retryButton =  findViewById(R.id.retry_button);
        //changePasswordLayout =  view.findViewById(R.id.change_password);

        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        emptyCustomFontTextView.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);

        spinner_ticket_status = findViewById(R.id.spinner_ticket_status);

      /*  String[] ticketstatus = {"Open","Inprogress","Pending","Completed","Close"};
        spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext().getApplicationContext(), R.layout.spinner_item, ticketstatus);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spinner_ticket_status.setAdapter(spinnerArrayAdapter);
        spinner_ticket_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                status = spinner_ticket_status.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
*/

        spinner_stationname = findViewById(R.id.spinner_stationname);
        spinner_stationname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                StationName = spinner_stationname.getSelectedItem().toString();
                StationName_id = hashMap_StationId.get(StationName);
                Log.w(TAG,"StationName : "+StationName+" StationName_id : "+StationName_id);
                if(StationName_id != null){
                    // CMRLTicketListResponseCall();

                }else{
                    StationName_id = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinner_ticket_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                JobNo = spinner_ticket_status.getSelectedItem().toString();
                Job_id = hashMap_JoBNoId.get(JobNo);
                Log.w(TAG,"JobNo : "+JobNo+" Job_id : "+Job_id);
                if(Job_id == null){
                    Job_id = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        ll_search = findViewById(R.id.ll_search);
        btn_search = findViewById(R.id.btn_search);
        ll_clear = findViewById(R.id.ll_clear);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CMRLTicketListResponseCall();

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CMRLTicketListResponseCall();

            }
        });
        ll_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StationName_id = "";
                if(type ==1){
                    liftJobNoListResponseCall();
                }else{
                    elivatorJobNoListResponseCall();
                }




                /*String[] ticketstatus = {"Open","Inprogress","Pending","Completed","Close"};
                spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext().getApplicationContext(), R.layout.spinner_item, ticketstatus);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                spinner_ticket_status.setAdapter(spinnerArrayAdapter);*/


            }
        });

        rl_filters = findViewById(R.id.rl_filters);
        ll_ticket_status = findViewById(R.id.ll_ticket_status);
        ll_station_name = findViewById(R.id.ll_station_name);
        ll_search_clear = findViewById(R.id.ll_search_clear);

        ll_ticket_status.setVisibility(View.GONE);
        ll_search_clear.setVisibility(View.GONE);
        ll_station_name.setVisibility(View.GONE);

        rl_filters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ll_ticket_status.isShown()){
                    ll_ticket_status.setVisibility(View.VISIBLE);
                    ll_search_clear.setVisibility(View.VISIBLE);
                    ll_station_name.setVisibility(View.VISIBLE);
                }else{
                    ll_ticket_status.setVisibility(View.GONE);
                    ll_search_clear.setVisibility(View.GONE);
                    ll_station_name.setVisibility(View.GONE);

                }
            }
        });

        if(type == 1){
            elvalorLine.setVisibility(View.VISIBLE);
            underLine.setVisibility(View.INVISIBLE);
        }else{
            type = 2;
            elvalorLine.setVisibility(View.INVISIBLE);
            underLine.setVisibility(View.VISIBLE);
        }
        elvatorLayout.setOnClickListener(view1 -> {
            selectStatus = "0";
            type = 1;
            StationName_id = "";
            elvalorLine.setVisibility(View.VISIBLE);
            underLine.setVisibility(View.INVISIBLE);
          /*  status = "Open";
            spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext().getApplicationContext(), R.layout.spinner_item, ticketstatus);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_ticket_status.setAdapter(spinnerArrayAdapter);
            StationNameResponseCall();*/
            liftJobNoListResponseCall();

        });
        underLayout.setOnClickListener(view2 -> {
            selectStatus = "1";
            type = 2;
            StationName_id = "";
            elvalorLine.setVisibility(View.INVISIBLE);
            underLine.setVisibility(View.VISIBLE);
          /*  status = "Open";
            spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext().getApplicationContext(), R.layout.spinner_item, ticketstatus);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_ticket_status.setAdapter(spinnerArrayAdapter);*/
            elivatorJobNoListResponseCall();


        });

        //sideMenuLayout.setOnClickListener(view12 -> CmrlLoginDashboardActivity.drawerLayout.openDrawer(CmrlLoginDashboardActivity.nvDrawer));
        retryButton.setOnClickListener(view13 -> {
            networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
            if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

                mWaveSwipeRefreshLayout.setVisibility(View.GONE);
                emptyCustomFontTextView.setVisibility(View.VISIBLE);
                emptyImageView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);

                emptyImageView.setImageResource(R.mipmap.wifi);
                emptyCustomFontTextView.setText(R.string.pleasecheckyourinternet);
                floatingActionButton.setVisibility(View.GONE);
            } else {
                Log.w(TAG,"selectStatus : "+selectStatus);
                if (selectStatus.equalsIgnoreCase("0")) {
                    //CMRLTicketListResponseCall();
                    //DepaartmentUrl(ApiCall.API_URL+"get_estationtickets_new.php?user_id=" + id);

                    liftJobNoListResponseCall();
                } else if (selectStatus.equalsIgnoreCase("1")) {
                    //DepaartmentUrl(ApiCall.API_URL+"get_ustationtickets_new.php?user_id=" + id);
                    // CMRLTicketListResponseCall();
                   // StationNameResponseCall();
                    elivatorJobNoListResponseCall();
                }
                mWaveSwipeRefreshLayout.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
            }

        });

        emptyImageView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        emptyCustomFontTextView.setVisibility(View.GONE);

        // check whether internet is on or not
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());

        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            mWaveSwipeRefreshLayout.setVisibility(View.GONE);
            emptyCustomFontTextView.setVisibility(View.VISIBLE);
            emptyImageView.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.VISIBLE);

            emptyImageView.setImageResource(R.mipmap.wifi);
            emptyCustomFontTextView.setText(R.string.pleasecheckyourinternet);
            floatingActionButton.setVisibility(View.GONE);

        }
        else {
            if (CmrlLoginDashboardActivity.tabSelects.equalsIgnoreCase("0")) {
                mWaveSwipeRefreshLayout.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
               // type =1;
                // DepaartmentUrl(ApiCall.API_URL+"get_estationtickets_new.php?user_id=" + id);
                // CMRLTicketListResponseCall();
               // StationNameResponseCall();
                liftJobNoListResponseCall();
                //elvalorLine.setVisibility(View.VISIBLE);
               // underLine.setVisibility(View.INVISIBLE);
            }
            else if (CmrlLoginDashboardActivity.tabSelects.equalsIgnoreCase("1")) {
                mWaveSwipeRefreshLayout.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
                //type =2;
                //DepaartmentUrl(ApiCall.API_URL+"get_ustationtickets_new.php?user_id=" + id);
                // CMRLTicketListResponseCall();
               // StationNameResponseCall();
                elivatorJobNoListResponseCall();
                //elvalorLine.setVisibility(View.INVISIBLE);
               // underLine.setVisibility(View.VISIBLE);
            }


        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mWaveSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0); // 0 is for first item position
            }
        });

        back_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onRefresh() {
        refresh();
    }
    private void refresh() {
        new Handler().postDelayed(() -> {
            // Refresh the department list to call api again
            if (selectStatus.equalsIgnoreCase("0")) {
                //  DepaartmentUrl(ApiCall.API_URL+"get_estationtickets_new.php?user_id=" + id);
                CMRLTicketListResponseCall();
            } else {
                //DepaartmentUrl(ApiCall.API_URL+"get_ustationtickets_new.php?user_id=" + id);
                CMRLTicketListResponseCall();
            }

            mWaveSwipeRefreshLayout.setRefreshing(false);
        }, 3000);
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

    @SuppressLint("LogNotTimber")
    private void CMRLTicketListResponseCall() {
        dialog = new Dialog(CMRLTicketCountsActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<CMRLTicketListResponse> call = apiInterface.CMRLTicketListResponseCall(RestUtils.getContentType(), cmrlTicketListRequest());
        Log.w(TAG,"CMRLTicketListResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CMRLTicketListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<CMRLTicketListResponse> call, @NonNull Response<CMRLTicketListResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"SuccessResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        ticketList.clear();
                        if(response.body().getData() != null && response.body().getData().size()>0){
                            ticketList = response.body().getData();
                        }

                        if (ticketList != null && ticketList.size()>0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyImageView.setVisibility(View.GONE);
                            retryButton.setVisibility(View.GONE);
                            emptyCustomFontTextView.setVisibility(View.GONE);
                            ticketsListAdapter = new TicketsListAdapter(CMRLTicketCountsActivity.this, ticketList, "", "");


                            mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());

                            recyclerView.setAdapter(ticketsListAdapter);



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
            public void onFailure(@NonNull Call<CMRLTicketListResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("CMRLTicketListResponse flr", "--->" + t.getMessage());

            }
        });

    }
    private CMRLTicketListRequest cmrlTicketListRequest() {

        /*
         * type : 1
         * station_id : 611510c34f912e1856fc6d44
         * break_down_reported_by : 6113acf26ee293224d81081c
         * status
         * job_id
         */
        CMRLTicketListRequest cmrlTicketListRequest = new CMRLTicketListRequest();
        cmrlTicketListRequest.setStation_id(StationName_id);
        cmrlTicketListRequest.setBreak_down_reported_by(id);
        cmrlTicketListRequest.setType(String.valueOf(type));
        cmrlTicketListRequest.setStatus(ticketstatus);
        cmrlTicketListRequest.setJob_id(Job_id);
        Log.w(TAG,"cmrlTicketListRequest "+ new Gson().toJson(cmrlTicketListRequest));
        return cmrlTicketListRequest;
    }

    @SuppressLint("LogNotTimber")
    private void StationNameResponseCall() {
       /* dialog = new Dialog(CMRLTicketCountsActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();*/

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<StationNameResponse> call = apiInterface.StationNameResponseCall(RestUtils.getContentType(), stationNameRequest(type));
        Log.w(TAG,"StationNameResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<StationNameResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<StationNameResponse> call, @NonNull Response<StationNameResponse> response) {
                dialog.dismiss();


                Log.w(TAG,"StationNameResponseCall" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        CMRLTicketListResponseCall();
                        stationNameList.clear();
                        if(response.body().getData() != null && response.body().getData().size()>0){
                            stationNameList = response.body().getData();
                            setStationNameList(stationNameList);
                        }else{
                            stationNameList.clear();
                            setStationNameList(stationNameList);
                            Toasty.warning(getApplicationContext(),response.body().getMessage(),Toasty.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<StationNameResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("StationNameResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setStationNameList(List<StationNameResponse.DataBean> data) {
        Log.w(TAG, "data : " + new Gson().toJson(data));

        if (data != null && data.size() > 0) {
            ArrayList<String> StationArrayList = new ArrayList<>();
            StationArrayList.add("Select Station Name");
            for (int i = 0; i < data.size(); i++) {
                String StationName = data.get(i).getStation_name();
                hashMap_StationId.put(data.get(i).getStation_name(), data.get(i).get_id());
                StationArrayList.add(StationName);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, StationArrayList);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                spinner_stationname.setAdapter(spinnerArrayAdapter);
            }
        }
        else {
            ArrayList<String> StationArrayList = new ArrayList<>();
            StationArrayList.add("Select Station Name");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, StationArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_stationname.setAdapter(spinnerArrayAdapter);

        }
    }
    private StationNameRequest stationNameRequest(int type) {
        /*
         * type : 1
         * status
         */

        StationNameRequest stationNameRequest = new StationNameRequest();
        stationNameRequest.setType(type);

        Log.w(TAG,"stationNameRequest "+ new Gson().toJson(stationNameRequest));
        return stationNameRequest;
    }

    @SuppressLint("LogNotTimber")
    public void elivatorJobNoListResponseCall(){
        dialog = new Dialog(CMRLTicketCountsActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        //Creating an object of our api interface
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobNumberResponse> call = apiInterface.elivatorJobNoListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"elivatorJobNoListResponseCall url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<JobNumberResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobNumberResponse> call, @NonNull Response<JobNumberResponse> response) {
             // dialog.dismiss();
              if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        StationNameResponseCall();

                        elivatorJobNoList.clear();
                        Log.w(TAG,"JobNumberResponse" + new Gson().toJson(response.body()));

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            elivatorJobNoList = response.body().getData();
                            setElivatorJobNoList(elivatorJobNoList);
                        }else{
                            elivatorJobNoList.clear();
                            setElivatorJobNoList(elivatorJobNoList);
                        }
                    }



                }

            }


            @Override
            public void onFailure(@NonNull Call<JobNumberResponse> call,@NonNull  Throwable t) {
                dialog.dismiss();
                Log.w(TAG,"JobNumberResponse flr"+t.getMessage());
            }
        });

    }
    public void liftJobNoListResponseCall(){
        dialog = new Dialog(CMRLTicketCountsActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        //Creating an object of our api interface
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobNumberResponse> call = apiInterface.liftJobNoListResponseCall(RestUtils.getContentType());
        Log.w(TAG,"liftJobNoListResponseCall url  :%s"+ call.request().url().toString());

        call.enqueue(new Callback<JobNumberResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobNumberResponse> call, @NonNull Response<JobNumberResponse> response) {

                if (response.body() != null) {
                    if(200 == response.body().getCode()){
                        StationNameResponseCall();

                        elivatorJobNoList.clear();
                        Log.w(TAG,"liftJobNoListResponseCall" + new Gson().toJson(response.body()));

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            elivatorJobNoList = response.body().getData();
                            setElivatorJobNoList(elivatorJobNoList);
                        }else{
                            elivatorJobNoList.clear();
                            setElivatorJobNoList(elivatorJobNoList);
                        }
                    }



                }

            }


            @Override
            public void onFailure(@NonNull Call<JobNumberResponse> call,@NonNull  Throwable t) {
                dialog.dismiss();
                Log.w(TAG,"liftJobNoListResponseCall flr"+t.getMessage());
            }
        });

    }
    private void setElivatorJobNoList(List<JobNumberResponse.DataBean> elivatorJobNoList) {
        if (elivatorJobNoList != null && elivatorJobNoList.size() > 0) {
            ArrayList<String> JobNoArrayList = new ArrayList<>();
            JobNoArrayList.add("Select JoB No");
            for (int i = 0; i < elivatorJobNoList.size(); i++) {
                String JobNo = elivatorJobNoList.get(i).getJob_no();
                hashMap_JoBNoId.put(elivatorJobNoList.get(i).getJob_no(), elivatorJobNoList.get(i).get_id());
                JobNoArrayList.add(JobNo);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, JobNoArrayList);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                spinner_ticket_status.setAdapter(spinnerArrayAdapter);
            }
        }
        else {
            ArrayList<String> JobNoArrayList = new ArrayList<>();
            JobNoArrayList.add("Select JoB No");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, JobNoArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_ticket_status.setAdapter(spinnerArrayAdapter);

        }
    }


}