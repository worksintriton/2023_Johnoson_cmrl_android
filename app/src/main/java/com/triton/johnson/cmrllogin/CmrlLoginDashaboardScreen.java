package com.triton.johnson.cmrllogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.triton.johnson.R;
import com.triton.johnson.adapter.TicketsListAdapter;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.arraylist.StationList;
import com.triton.johnson.requestpojo.AttendanceCreateRequest;
import com.triton.johnson.requestpojo.CMRLTicketListRequest;
import com.triton.johnson.requestpojo.CmrlDashboardCountRequest;
import com.triton.johnson.requestpojo.StationNameRequest;
import com.triton.johnson.responsepojo.CMRLTicketListResponse;
import com.triton.johnson.responsepojo.CmrlDashboardCountResponse;
import com.triton.johnson.responsepojo.StationNameResponse;
import com.triton.johnson.session.SessionManager;
import com.triton.johnson.utils.ConnectionDetector;
import com.triton.johnson.utils.RestUtils;
import com.triton.johnson.view.AddNewTicketActivity;
import com.triton.johnson.view.CmrlLoginDashboardActivity;
import com.triton.johnson.view.DepartmentListClass;
import com.triton.johnson.view.ProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iddinesh.
 */

public class CmrlLoginDashaboardScreen extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

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
    private LinearLayout elvalorLine1, underLine1;
    private String id;
    private LinearLayoutManager mLayoutManager;

    private String TAG = "CmrlLoginDashaboardScreen";


    ArrayList<StationList> stationListArrayList = new ArrayList<>();
    private int type = 1;
    private String StationName_id = "";
    private List<CMRLTicketListResponse.DataBean> ticketList = new ArrayList<>();
    private List<StationNameResponse.DataBean> stationNameList = new ArrayList<>();
    HashMap<String, String> hashMap_StationId = new HashMap<>();
    Spinner spinner_ticket_status, spinner_stationname;
    private String StationName;

    private String status = "Open";
    ArrayAdapter<String> spinnerArrayAdapter;
    LinearLayout ll_search,ll_clear;
    Button btn_search;

    TextView txt_open_count,txt_inprogress_count,txt_pending_count,txt_completed_count,txt_close_count;
    TextView txt_lbl_open,txt_lbl_inprogres,txt_lbl_pending,txt_lbl_completed,txt_lbl_close;
    LinearLayout openLayout, inprogressLayout, pendingLayout, completeLayout, closeLayout;
    LinearLayout ll_open,ll_inprogress,ll_pending,ll_complete,ll_close;

    private int open_count;
    private int inprogress_count;
    private int pending_count;
    private int completed_count;
    private int close_count;



    @SuppressLint("RestrictedApi")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle arg) {

        Log.w(TAG,"onCreateView--->");
        final View view = inflater.inflate(R.layout.activity_cmrl_login_dashboard_screen, container,
                false);

        //private LinearLayout changePasswordLayout;
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String, String> hashMap = sessionManager.getUserDetails();
        id = hashMap.get(SessionManager.KEY_ID);

        txt_open_count = view.findViewById(R.id.open_count);
        txt_inprogress_count = view.findViewById(R.id.inprogress_count);
        txt_pending_count = view.findViewById(R.id.pending_count);
        txt_completed_count = view.findViewById(R.id.completed_count);
        txt_close_count = view.findViewById(R.id.close_count);

        txt_lbl_open = view.findViewById(R.id.txt_lbl_open);
        txt_lbl_inprogres = view.findViewById(R.id.txt_lbl_inprogres);
        txt_lbl_pending = view.findViewById(R.id.txt_lbl_pending);
        txt_lbl_completed = view.findViewById(R.id.txt_lbl_completed);
        txt_lbl_close = view.findViewById(R.id.txt_lbl_close);

        openLayout = view.findViewById(R.id.back1);
        inprogressLayout = view.findViewById(R.id.back2);
        pendingLayout = view.findViewById(R.id.back3);
        completeLayout = view.findViewById(R.id.back4);
        closeLayout = view.findViewById(R.id.back5);

        ll_open = view.findViewById(R.id.ll_open);
        ll_inprogress = view.findViewById(R.id.ll_inprogress);
        ll_pending = view.findViewById(R.id.ll_pending);
        ll_complete = view.findViewById(R.id.ll_complete);
        ll_close = view.findViewById(R.id.ll_close);



        recyclerView = view.findViewById(R.id.recycler_view);
        emptyCustomFontTextView =  view.findViewById(R.id.empty_text);
        emptyImageView = view.findViewById(R.id.empty_image);
        elvalorLine =  view.findViewById(R.id.elvator_line);
        LinearLayout elvatorLayout = view.findViewById(R.id.elvator_layout);
        LinearLayout underLayout = view.findViewById(R.id.under_layout);
        underLine =  view.findViewById(R.id.under_ground_line);


        elvalorLine1 =  view.findViewById(R.id.elvator_line1);
        LinearLayout elvatorLayout1 = view.findViewById(R.id.elvator_layout1);
        LinearLayout underLayout1 = view.findViewById(R.id.under_layout1);
        underLine1 =  view.findViewById(R.id.under_ground_line1);
        underLine1.setVisibility(View.INVISIBLE);


        mWaveSwipeRefreshLayout = view.findViewById(R.id.main_swipe);
        floatingActionButton =  view.findViewById(R.id.fab_createevent);
        //LinearLayout profileLinearLayout = view.findViewById(R.id.profile_layout);
        ImageView img_new_ticket = view.findViewById(R.id.img_new_ticket);
        ImageView img_profile = view.findViewById(R.id.img_profile);
        retryButton =  view.findViewById(R.id.retry_button);
        LinearLayout sideMenuLayout = view.findViewById(R.id.back_layout);
        //changePasswordLayout =  view.findViewById(R.id.change_password);

        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setOnRefreshListener(this);
        emptyCustomFontTextView.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);

        spinner_ticket_status = view.findViewById(R.id.spinner_ticket_status);

        String[] ticketstatus = {"Open","Inprogress","Pending","Completed","Close"};
        spinnerArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, ticketstatus);

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

        spinner_stationname = view.findViewById(R.id.spinner_stationname);
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

        ll_search = view.findViewById(R.id.ll_search);
        btn_search = view.findViewById(R.id.btn_search);
        ll_clear = view.findViewById(R.id.ll_clear);
        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   CMRLTicketListResponseCall();

            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  CMRLTicketListResponseCall();

            }
        });
        ll_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StationName_id = "";
                status = "Open";
                String[] ticketstatus = {"Open","Inprogress","Pending","Completed","Close"};
                spinnerArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, ticketstatus);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                spinner_ticket_status.setAdapter(spinnerArrayAdapter);
                StationNameResponseCall();
            }
        });

        elvatorLayout.setOnClickListener(view1 -> {
            selectStatus = "0";
            type = 1;
            StationName_id = "";
            elvalorLine.setVisibility(View.VISIBLE);
            underLine.setVisibility(View.INVISIBLE);
            status = "Open";
            spinnerArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, ticketstatus);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_ticket_status.setAdapter(spinnerArrayAdapter);
            StationNameResponseCall();

        });
        underLayout.setOnClickListener(view2 -> {
            selectStatus = "1";
            type = 2;
            StationName_id = "";
            elvalorLine.setVisibility(View.INVISIBLE);
            underLine.setVisibility(View.VISIBLE);
            status = "Open";
            spinnerArrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, ticketstatus);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_ticket_status.setAdapter(spinnerArrayAdapter);
            StationNameResponseCall();


        });

        elvatorLayout1.setOnClickListener(view1 -> {
            selectStatus = "0";
            type = 1;
            StationName_id = "";
            elvalorLine1.setVisibility(View.VISIBLE);
            underLine1.setVisibility(View.INVISIBLE);
            status = "Open";
            CmrlDashboardCountRequestCall();

        });
        underLayout1.setOnClickListener(view2 -> {
            selectStatus = "1";
            type = 2;
            StationName_id = "";
            elvalorLine1.setVisibility(View.INVISIBLE);
            underLine1.setVisibility(View.VISIBLE);
            status = "Open";
            CmrlDashboardCountRequestCall();


        });


        sideMenuLayout.setOnClickListener(view12 -> CmrlLoginDashboardActivity.drawerLayout.openDrawer(CmrlLoginDashboardActivity.nvDrawer));
        retryButton.setOnClickListener(view13 -> {
            networkStatus = ConnectionDetector.getConnectivityStatusString(getActivity());
            if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

                mWaveSwipeRefreshLayout.setVisibility(View.GONE);
                emptyCustomFontTextView.setVisibility(View.VISIBLE);
                emptyImageView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);

                emptyImageView.setImageResource(R.mipmap.wifi);
                emptyCustomFontTextView.setText(R.string.pleasecheckyourinternet);
                floatingActionButton.setVisibility(View.GONE);
            } else {
                if (selectStatus.equalsIgnoreCase("0")) {
                    //CMRLTicketListResponseCall();
                    //DepaartmentUrl(ApiCall.API_URL+"get_estationtickets_new.php?user_id=" + id);
                    StationNameResponseCall();
                } else if (selectStatus.equalsIgnoreCase("1")) {
                    //DepaartmentUrl(ApiCall.API_URL+"get_ustationtickets_new.php?user_id=" + id);
                   // CMRLTicketListResponseCall();
                    StationNameResponseCall();
                }
                mWaveSwipeRefreshLayout.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
            }

        });

        emptyImageView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        emptyCustomFontTextView.setVisibility(View.GONE);

        // check whether internet is on or not
        networkStatus = ConnectionDetector.getConnectivityStatusString(getActivity());

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
                type =1;

                CmrlDashboardCountRequestCall();
                elvalorLine.setVisibility(View.VISIBLE);
                underLine.setVisibility(View.INVISIBLE);
            }
            else if (CmrlLoginDashboardActivity.tabSelects.equalsIgnoreCase("1")) {
                mWaveSwipeRefreshLayout.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
                type =2;

                CmrlDashboardCountRequestCall();
                elvalorLine.setVisibility(View.INVISIBLE);
                underLine.setVisibility(View.VISIBLE);
            }


        }
        img_profile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            startActivity(intent);
        });
        img_new_ticket.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNewTicketActivity.class);
            startActivity(intent);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mWaveSwipeRefreshLayout.setEnabled(mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0); // 0 is for first item position
            }
        });
        return view;
    }

    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {

        refresh();
    }
    private void refresh() {
        new Handler().postDelayed(() -> {
            // Refresh the department list to call api again
            if (selectStatus.equalsIgnoreCase("0")) {
                CmrlDashboardCountRequestCall();
            } else {
                CmrlDashboardCountRequestCall();
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



    @SuppressLint({"LogNotTimber", "UseRequireInsteadOfGet"})
    private void CmrlDashboardCountRequestCall() {
        dialog = new Dialog(Objects.requireNonNull(getActivity()), R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<CmrlDashboardCountResponse> call = apiInterface.CmrlDashboardCountRequestCall(RestUtils.getContentType(), cmrlDashboardCountRequest());
        Log.w(TAG,"CmrlDashboardCountResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CmrlDashboardCountResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<CmrlDashboardCountResponse> call, @NonNull Response<CmrlDashboardCountResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"CmrlDashboardCountResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            open_count = response.body().getData().getOpen_count();
                            inprogress_count = response.body().getData().getInprogress_count();
                            pending_count = response.body().getData().getPending_count();
                            completed_count = response.body().getData().getCompleted_count();
                            close_count = response.body().getData().getClose_count();

                            txt_open_count.setText(response.body().getData().getOpen_count()+"");
                            txt_inprogress_count.setText(response.body().getData().getInprogress_count()+"");
                            txt_pending_count.setText(response.body().getData().getPending_count()+"");
                            txt_completed_count.setText(response.body().getData().getCompleted_count()+"");
                            txt_close_count.setText(response.body().getData().getClose_count()+"");


                            if(open_count != 0){
                                ll_open.getBackground().setColorFilter(Color.parseColor("#FF003F"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_open.setTextColor(Color.parseColor("#FF003F"));
                                openLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(),CMRLTicketCountsActivity.class);
                                        intent.putExtra("ticketstatus","Open");
                                        intent.putExtra("type",type);
                                        startActivity(intent);
                                    }
                                });
                            }else{
                                ll_open.getBackground().setColorFilter(Color.parseColor("#dddddd"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_open.setTextColor(Color.parseColor("#dddddd"));
                            }
                            if(inprogress_count != 0){
                                ll_inprogress.getBackground().setColorFilter(Color.parseColor("#1491FF"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_inprogres.setTextColor(Color.parseColor("#1491FF"));
                                inprogressLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(),CMRLTicketCountsActivity.class);
                                        intent.putExtra("ticketstatus","Inprogress");
                                        intent.putExtra("type",type);
                                        startActivity(intent);
                                    }
                                });
                            }else{ ll_inprogress.getBackground().setColorFilter(Color.parseColor("#dddddd"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_inprogres.setTextColor(Color.parseColor("#dddddd"));}
                            if(pending_count != 0){
                                ll_pending.getBackground().setColorFilter(Color.parseColor("#FF9A00"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_pending.setTextColor(Color.parseColor("#FF9A00"));
                                pendingLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(),CMRLTicketCountsActivity.class);
                                        intent.putExtra("ticketstatus","Pending");
                                        intent.putExtra("type",type);
                                        startActivity(intent);
                                    }
                                });
                            }else{
                                ll_pending.getBackground().setColorFilter(Color.parseColor("#dddddd"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_pending.setTextColor(Color.parseColor("#dddddd"));
                            }
                            if(completed_count != 0){
                                ll_complete.getBackground().setColorFilter(Color.parseColor("#AF4AFF"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_completed.setTextColor(Color.parseColor("#AF4AFF"));
                                completeLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(),CMRLTicketCountsActivity.class);
                                        intent.putExtra("ticketstatus","Completed");
                                        intent.putExtra("type",type);
                                        startActivity(intent);
                                    }
                                });
                            } else{
                                ll_complete.getBackground().setColorFilter(Color.parseColor("#dddddd"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_completed.setTextColor(Color.parseColor("#dddddd"));
                            }
                            if(close_count != 0) {
                                ll_close.getBackground().setColorFilter(Color.parseColor("#00E59E"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_close.setTextColor(Color.parseColor("#00E59E"));
                                closeLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getActivity(), CMRLTicketCountsActivity.class);
                                        intent.putExtra("ticketstatus", "Close");
                                        intent.putExtra("type",type);
                                        startActivity(intent);
                                    }
                                });
                            }else{
                                ll_close.getBackground().setColorFilter(Color.parseColor("#dddddd"), PorterDuff.Mode.SRC_ATOP);
                                txt_lbl_close.setTextColor(Color.parseColor("#dddddd"));}


                        }



                    }

                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<CmrlDashboardCountResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("CMRLTicketListResponse flr", "--->" + t.getMessage());

            }
        });

    }
    private CmrlDashboardCountRequest cmrlDashboardCountRequest() {


        /*
         * break_down_reported_by : 611ba6d611fce741ad463bc7
         */

        CmrlDashboardCountRequest cmrlDashboardCountRequest = new CmrlDashboardCountRequest();
        cmrlDashboardCountRequest.setBreak_down_reported_by(id);
        cmrlDashboardCountRequest.setType(type);

        Log.w(TAG,"cmrlDashboardCountRequest "+ new Gson().toJson(cmrlDashboardCountRequest));
        return cmrlDashboardCountRequest;
    }

    @SuppressLint("LogNotTimber")
    private void StationNameResponseCall() {
        dialog = new Dialog(getActivity(), R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

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
                        stationNameList.clear();
                        if(response.body().getData() != null && response.body().getData().size()>0){
                            stationNameList = response.body().getData();
                            setStationNameList(stationNameList);
                        }else{
                            stationNameList.clear();
                            setStationNameList(stationNameList);
                            Toasty.warning(getActivity(),response.body().getMessage(),Toasty.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<StationNameResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("StationNameResponse flr", "--->" + t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, StationArrayList);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                spinner_stationname.setAdapter(spinnerArrayAdapter);
            }
        } else {
            ArrayList<String> StationArrayList = new ArrayList<>();
            StationArrayList.add("Select Station Name");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, StationArrayList);
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




}

