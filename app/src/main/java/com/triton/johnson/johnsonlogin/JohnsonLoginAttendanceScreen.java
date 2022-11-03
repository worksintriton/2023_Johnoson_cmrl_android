package com.triton.johnson.johnsonlogin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnson.R;

import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.requestpojo.AttendanceCreateRequest;
import com.triton.johnson.requestpojo.CheckAttendanceRequest;

import com.triton.johnson.responsepojo.CheckAttendanceResponse;

import com.triton.johnson.responsepojo.SuccessResponse;
import com.triton.johnson.session.SessionManager;
import com.triton.johnson.utils.ConnectionDetector;
import com.triton.johnson.utils.RestUtils;
import com.triton.johnson.view.DepartmentListClass;
import com.triton.johnson.view.JohnshonLoginDashboardActivity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iddinesh.
 */

public class JohnsonLoginAttendanceScreen extends Fragment {



    private String TAG = "JohnsonLoginAttendanceScreen";
    private String networkStatus = "";
    private Dialog dialog;

    TextView txt_checkin,txt_checkout;
    Button btn_checkin,btn_checkout;

    private String user_id;
    private String date = "";
    private String check_in_time = "";
    private String check_in_datetime = "";
    private String check_out_time = "";
    private String check_out_datetime = "";

    @SuppressLint("RestrictedApi")
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle arg) {

        Log.w(TAG,"onCreateView--->");
        final View view = inflater.inflate(R.layout.johnson_attendance_dashboard, container,
                false);

        //private LinearLayout changePasswordLayout;
        SessionManager sessionManager = new SessionManager(getActivity());
        HashMap<String, String> hashMap = sessionManager.getUserDetails();
        user_id = hashMap.get(SessionManager.KEY_ID);


        txt_checkin = view.findViewById(R.id.txt_checkin);
        txt_checkout = view.findViewById(R.id.txt_checkout);
        btn_checkin = view.findViewById(R.id.btn_checkin);
        btn_checkout = view.findViewById(R.id.btn_checkout);

        txt_checkout.setVisibility(View.GONE);
        btn_checkout.setVisibility(View.GONE);


        LinearLayout sideMenuLayout = view.findViewById(R.id.back_layout);


        sideMenuLayout.setOnClickListener(view12 -> JohnshonLoginDashboardActivity.drawerLayout.openDrawer(JohnshonLoginDashboardActivity.nvDrawer));
        // check whether internet is on or not
        networkStatus = ConnectionDetector.getConnectivityStatusString(getActivity());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {
        }else{
            CheckAttendanceRequestCall();
        }

        btn_checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdfdateandtime = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                SimpleDateFormat sdftime = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                check_in_time = sdftime.format(new Date());
                check_in_datetime = sdfdateandtime.format(new Date());
                AttendanceCreateRequestCall();
            }
        });
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdfdateandtime = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                SimpleDateFormat sdftime = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                check_out_time = sdftime.format(new Date());
                check_out_datetime = sdfdateandtime.format(new Date());
                AttendanceCreateRequestCall();
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
    private void CheckAttendanceRequestCall() {
        dialog = new Dialog(getActivity(), R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<CheckAttendanceResponse> call = apiInterface.CheckAttendanceRequestCall(RestUtils.getContentType(), checkAttendanceRequest());
        Log.w(TAG,"JohnsonTicketListResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<CheckAttendanceResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<CheckAttendanceResponse> call, @NonNull Response<CheckAttendanceResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"JohnsonTicketListResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            if(response.body().getData().isIschecked()){
                                btn_checkin.setAlpha(.5f);
                                btn_checkin.setClickable(false);
                                check_in_time = response.body().getData().getCheck_in_time();
                                check_in_datetime = response.body().getData().getCheck_in_datetime();
                                check_out_time = response.body().getData().getCheck_out_time();
                                check_out_datetime = response.body().getData().getCheck_out_datetime();
                                txt_checkout.setVisibility(View.VISIBLE);
                                btn_checkout.setVisibility(View.VISIBLE);
                                txt_checkin.setText("Check In : "+response.body().getData().getCheck_in_datetime());
                                txt_checkout.setText("Check Out : "+response.body().getData().getCheck_out_datetime());

                                if(check_out_time != null && !check_out_time.isEmpty()){
                                    btn_checkout.setAlpha(.5f);
                                    btn_checkout.setClickable(false);
                                }

                            }else{
                                btn_checkin.setAlpha(1);
                                btn_checkin.setClickable(true);
                                btn_checkout.setClickable(true);
                             /*   txt_checkout.setVisibility(View.GONE);
                                btn_checkout.setVisibility(View.GONE);*/

                            }

                        }





                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<CheckAttendanceResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("CheckAttendanceResponse flr", "--->" + t.getMessage());

            }
        });

    }
    private CheckAttendanceRequest checkAttendanceRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());

        /*
         * user_id : 60ae2c0c48ffef65a41bc546
         * date : 24-10-2021
         */
        CheckAttendanceRequest checkAttendanceRequest = new CheckAttendanceRequest();
        checkAttendanceRequest.setUser_id(user_id);
        checkAttendanceRequest.setDate(currentDate);
        Log.w(TAG,"checkAttendanceRequest "+ new Gson().toJson(checkAttendanceRequest));
        return checkAttendanceRequest;
    }

    @SuppressLint("LogNotTimber")
    private void AttendanceCreateRequestCall() {
        dialog = new Dialog(getActivity(), R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.AttendanceCreateRequestCall(RestUtils.getContentType(), attendanceCreateRequest());
        Log.w(TAG,"AttendanceCreateRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint({"LogNotTimber", "SetTextI18n"})
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"SuccessResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            CheckAttendanceRequestCall();
                        }





                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("CheckAttendanceResponse flr", "--->" + t.getMessage());

            }
        });

    }
    private AttendanceCreateRequest attendanceCreateRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        /*
         * user_id : 60ae2c0c48ffef65a41bc546
         * date : 22-10-2021
         * check_in_time : 08:00 AM
         * check_in_datetime : 22-10-2021 08:00 AM
         * check_out_time : 09:00 AM
         * check_out_datetime : 22-10-2021 09:00 AM
         */
        AttendanceCreateRequest attendanceCreateRequest = new AttendanceCreateRequest();
        attendanceCreateRequest.setUser_id(user_id);
        attendanceCreateRequest.setDate(currentDate);
        attendanceCreateRequest.setCheck_in_time(check_in_time);
        attendanceCreateRequest.setCheck_in_datetime(check_in_datetime);
        attendanceCreateRequest.setCheck_out_time(check_out_time);
        attendanceCreateRequest.setCheck_out_datetime(check_out_datetime);
        Log.w(TAG,"attendanceCreateRequest "+ new Gson().toJson(attendanceCreateRequest));
        return attendanceCreateRequest;
    }

}

