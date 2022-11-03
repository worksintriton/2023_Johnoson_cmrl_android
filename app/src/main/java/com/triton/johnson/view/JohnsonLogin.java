package com.triton.johnson.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.triton.johnson.R;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.ApiCall;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.arraylist.StationList;
import com.triton.johnson.materialeditext.MaterialEditText;
import com.triton.johnson.materialspinner.MaterialSpinner;
import com.triton.johnson.requestpojo.FBTokenUpdateRequest;
import com.triton.johnson.requestpojo.LoginRequest;
import com.triton.johnson.responsepojo.FBTokenUpdateResponse;
import com.triton.johnson.responsepojo.LoginResponse;
import com.triton.johnson.session.SessionManager;
import com.triton.johnson.sweetalertdialog.SweetAlertDialog;
import com.triton.johnson.utils.ConnectionDetector;
import com.google.android.material.snackbar.Snackbar;
import com.triton.johnson.utils.NumericKeyBoardTransformationMethod;
import com.triton.johnson.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Iddinesh.
 */

public class JohnsonLogin extends AppCompatActivity {

    private String TAG ="JohnsonLogin";

    MaterialEditText employeeMaterialEditText, userNameMaterialEditText, passwordMaterialEditText;

    MaterialSpinner mainMaterialSpinner;

    LinearLayout forgotLinearLayout, loginMainLinearLayout;

    RequestQueue requestQueue;

    StationList stationList;

    ArrayList<StationList> stationListArrayList = new ArrayList<>();
    TextView mainReasonCustomFontTextView;

    Button loginButton;

    String networkStatus = "", stationId = "";
    String status = "", message = "", user_level = "", station_code = "", station_name = "", empid = "", name = "", username = "", mobile;

    Dialog  dialog;

    SessionManager sessionManager;
    private String role = "";

    private String userid;
    private String token;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        Log.w(TAG,"onCreate-->");

        sessionManager = new SessionManager(getApplicationContext());

        mainMaterialSpinner =  findViewById(R.id.spinner);
        employeeMaterialEditText =  findViewById(R.id.employee_id);
        userNameMaterialEditText =  findViewById(R.id.user_name);
        passwordMaterialEditText =  findViewById(R.id.password);

        userNameMaterialEditText.setTransformationMethod(new NumericKeyBoardTransformationMethod());


        loginButton = findViewById(R.id.loginnnn_button);

        forgotLinearLayout = findViewById(R.id.forgot_layout);
        loginMainLinearLayout = findViewById(R.id.login_main_layout);

        mainReasonCustomFontTextView = findViewById(R.id.request_reason_text);





        try{
            // Initialize Firebase
            FirebaseApp.initializeApp(getApplicationContext());
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        Log.w(TAG,"token--->"+ token);



                    });



        }
        catch (Exception e){
            Log.w(TAG,"FCM : "+e.getLocalizedMessage());
            Log.w(TAG,"FCM Message : "+e.getMessage());
            e.printStackTrace();
        }


        mainMaterialSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {

            mainReasonCustomFontTextView.setText(stationListArrayList.get(position).getName());
            stationId = stationListArrayList.get(position).getCode();

        });

        employeeMaterialEditText.setOnTouchListener((view, motionEvent) -> {

            employeeMaterialEditText.setFocusableInTouchMode(true);
            userNameMaterialEditText.setFocusableInTouchMode(true);
            passwordMaterialEditText.setFocusableInTouchMode(true);
            return false;
        });

        userNameMaterialEditText.setOnTouchListener((view, motionEvent) -> {

            employeeMaterialEditText.setFocusableInTouchMode(true);
            userNameMaterialEditText.setFocusableInTouchMode(true);
            passwordMaterialEditText.setFocusableInTouchMode(true);
            return false;
        });

        passwordMaterialEditText.setOnTouchListener((view, motionEvent) -> {

            employeeMaterialEditText.setFocusableInTouchMode(true);
            userNameMaterialEditText.setFocusableInTouchMode(true);
            passwordMaterialEditText.setFocusableInTouchMode(true);
            return false;
        });

        loginMainLinearLayout.setOnClickListener(view -> {

            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (in != null) {
                in.hideSoftInputFromWindow(loginMainLinearLayout.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            passwordMaterialEditText.setFocusable(false);
            employeeMaterialEditText.setFocusable(false);
            userNameMaterialEditText.setFocusable(false);
        });


        forgotLinearLayout.setOnClickListener(view -> {

            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (in != null) {
                in.hideSoftInputFromWindow(forgotLinearLayout.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            passwordMaterialEditText.setFocusable(false);
            employeeMaterialEditText.setFocusable(false);
            userNameMaterialEditText.setFocusable(false);

            Intent intent = new Intent(JohnsonLogin.this, ForgotPassword.class);
            intent.putExtra("status", "0");
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);
        });


        // check whether internet is on or not
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {

                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    });

            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }

        loginButton.setOnClickListener(view -> {
           /* Intent intent = new Intent(CMRLLogin.this, CmrlLoginDashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);
*/
            networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
            if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {
                Snackbar snackbar = Snackbar
                        .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", view1 -> {

                            Intent intent = new Intent(Settings.ACTION_SETTINGS);
                            startActivity(intent);
                        });

                snackbar.setActionTextColor(Color.RED);

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(R.id.snackbar_text);
                textView.setTextColor(Color.YELLOW);
                snackbar.show();
            }
            else {
                if (Objects.requireNonNull(userNameMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("CMRL")
                            .setContentText("Enter phone number")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(Dialog::dismiss)
                            .show();

                } else if (Objects.requireNonNull(passwordMaterialEditText.getText()).toString().trim().equalsIgnoreCase("")) {
                    new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("CMRL")
                            .setContentText("Enter password")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(Dialog::dismiss)
                            .show();
                } else {

                    LoginResponseCall();
                    //LoginUrl(ApiCall.API_URL + "login_access.php?empid=" + employeeMaterialEditText.getText().toString().replace(" ", "%20") + "&username=" + userNameMaterialEditText.getText().toString().replace(" ", "%20") + "&password=" + passwordMaterialEditText.getText().toString().replace(" ", "%20") + "&user_level=5");

                }
            }


        });

        //StationListUrl();
    }

    // default back button action
    public void onBackPressed() {

        Intent intent = new Intent(JohnsonLogin.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.new_right, R.anim.new_left);
    }

    protected void onResume() {

        super.onResume();
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(loginMainLinearLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", view -> {

                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    });

            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = sbView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();


        } else {

            //StationListUrl();
        }
    }

    /**
     * @param url pass value to server
     */
    public void LoginUrl(String url) {

        dialog = new Dialog(JohnsonLogin.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        requestQueue = Volley.newRequestQueue(JohnsonLogin.this);


        Log.e("url", "" + url);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {

                        JSONArray ja = response.getJSONArray("login");

                        Log.w(TAG,"Response-->"+ja);

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jsonObject = ja.getJSONObject(i);
                            status = jsonObject.getString("status");

                            if (status.equalsIgnoreCase("1")) {

                                message = jsonObject.getString("message");
                                user_level = jsonObject.getString("user_level");
                                station_code = jsonObject.getString("station_code");

                                Log.w(TAG,"station_code-->"+station_code);
                                station_name = jsonObject.getString("station_name");
//                                    role = jsonObject.getString("role");
                                empid = jsonObject.getString("empid");
                                name = jsonObject.getString("name");
                                username = jsonObject.getString("username");
                                mobile = jsonObject.getString("mobile");

                            } else if (status.equalsIgnoreCase("2")) {

                                message = jsonObject.getString("message");

                            } else if (status.equalsIgnoreCase("3")) {

                                message = jsonObject.getString("message");

                            } else if (status.equalsIgnoreCase("4")) {

                                message = jsonObject.getString("message");
                            } else if (status.equalsIgnoreCase("0")) {

                                message = jsonObject.getString("message");
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();
                    if (status.equalsIgnoreCase("1")) {

                        new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("CMRL")
                                .setContentText("Logged in successfully")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(sDialog -> {
                                    sDialog.dismiss();
                                    Log.w(TAG,"station_code-->"+station_code);
                                    sessionManager.createLoginSession(message, user_level, station_code, role, station_name, empid, name, username, mobile);

                                    Intent intent = new Intent(JohnsonLogin.this, StationActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.new_right, R.anim.new_left);
                                })
                                .show();

                    } else if (status.equalsIgnoreCase("2")) {
                        new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("CMRL")
                                .setContentText("Please enter correct password")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(Dialog::dismiss)
                                .show();


                    } else if (status.equalsIgnoreCase("3")) {
                        new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("CMRL")
                                .setContentText("Please enter correct user name")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(Dialog::dismiss)
                                .show();

                    } else if (status.equalsIgnoreCase("4")) {
                        new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("CMRL")
                                .setContentText("Please enter correct employee id")
                                .setConfirmText("Ok")
                                .setConfirmClickListener(Dialog::dismiss)
                                .show();
                    } else if (status.equalsIgnoreCase("0")) {
                        new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("CMRL")
                                .setContentText(message)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(Dialog::dismiss)
                                .show();
                    }
                },

                error -> {
                    Log.e("Volley", "Error");
                    dialog.dismiss();
                }
        );
        jor.setRetryPolicy(new DefaultRetryPolicy(20 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jor);

    }

    String statuss = "";
    String messagee = "";
    String id = "";
    String code = "";
    String namee = "";
    int i = 0;

    public void StationListUrl() {

        String reasonListUrl = ApiCall.API_URL + "stationlist";

        requestQueue = Volley.newRequestQueue(JohnsonLogin.this);

        Log.e("reasonListUrl", "" + reasonListUrl);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, reasonListUrl, null,
                response -> {
                    final ArrayList<String> reasonStrings = new ArrayList<>();

                    try {

                        JSONArray ja = response.getJSONArray("list");

                        if (i == 0) {

                            statuss = "";
                            messagee = "";
                            id = "";
                            code = "";
                            namee = "Select location";

                            reasonStrings.add(namee);
                            stationList = new StationList(statuss, messagee, id, code, namee);
                            stationListArrayList.add(stationList);

                        }
                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jsonObject = ja.getJSONObject(i);

                            statuss = jsonObject.getString("status");
                            messagee = jsonObject.getString("message");
                            id = jsonObject.getString("id");
                            code = jsonObject.getString("code");
                            namee = jsonObject.getString("name");

                            reasonStrings.add(namee);
                            stationList = new StationList(statuss, messagee, id, code, namee);
                            stationListArrayList.add(stationList);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mainMaterialSpinner.setTextColor(Color.parseColor("#000000"));
                    mainMaterialSpinner.setItems(reasonStrings);
                },

                error -> Log.e("Volley", "Error")
        );
        jor.setRetryPolicy(new DefaultRetryPolicy(20 * 30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jor);

    }

    @SuppressLint("LogNotTimber")
    private void LoginResponseCall() {
        dialog = new Dialog(JohnsonLogin.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<LoginResponse> call = apiInterface.LoginResponseCall(RestUtils.getContentType(), loginRequest());
        Log.w(TAG,"SignupResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<LoginResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                Log.w(TAG,"SignupResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {
                    message = response.body().getMessage();
                    if (200 == response.body().getCode()) {
                        if(response.body().getData() != null){
                            userid = response.body().getData().get_id();
                            sessionManager.createSessionLogin(
                                    response.body().getData().get_id(),
                                    String.valueOf(response.body().getData().getEmployee_id()),
                                    response.body().getData().getUsername(),
                                    response.body().getData().getUsername(),
                                    response.body().getData().getUser_email(),
                                    response.body().getData().getUser_phone(),
                                    String.valueOf(response.body().getData().getUser_type())
                            );

                            fBTokenUpdateResponseCall();
                        }


                    } else {
                        dialog.dismiss();
                        new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("JOHNSON")
                                .setContentText(message)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(Dialog::dismiss)
                                .show();

                        //showErrorLoading(response.body().getMessage());
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.e("OTP", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private LoginRequest loginRequest() {
        /*
         * user_phone : 9876543211
         * user_type : 2
         * password : 12345
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUser_phone(userNameMaterialEditText.getText().toString().trim());
        loginRequest.setUser_type(2);
        loginRequest.setPassword(passwordMaterialEditText.getText().toString());
        Log.w(TAG,"loginRequest "+ new Gson().toJson(loginRequest));
        return loginRequest;
    }


    @SuppressLint("LogNotTimber")
    private void fBTokenUpdateResponseCall() {
        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<FBTokenUpdateResponse> call = apiInterface.fBTokenUpdateResponseCall(RestUtils.getContentType(), fbTokenUpdateRequest());
        Log.w(TAG,"fBTokenUpdateResponseCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FBTokenUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<FBTokenUpdateResponse> call, @NonNull Response<FBTokenUpdateResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"fBTokenUpdateResponseCall"+ "--->" + new Gson().toJson(response.body()));

                if (response.body() != null) {
                    if(response.body().getCode() == 200){
                        new SweetAlertDialog(JohnsonLogin.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("JOHNSON")
                                .setContentText(message)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(sDialog -> {

                                    sDialog.dismiss();
                                    sessionManager.setIsLogin(true);

                                    Intent intent = new Intent(JohnsonLogin.this, JohnshonLoginDashboardActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.new_right, R.anim.new_left);

                                })
                                .show();
                    }

                }


            }

            @Override
            public void onFailure(@NonNull Call<FBTokenUpdateResponse> call, @NonNull Throwable t) {
                dialog.dismiss();
                Log.w(TAG,"FBTokenUpdateResponse flr"+"--->" + t.getMessage());
                //Toasty.success(getApplicationContext(),"NotificationUpdateResponse flr : "+t.getMessage(), Toast.LENGTH_SHORT, true).show();

            }
        });

    }
    private FBTokenUpdateRequest fbTokenUpdateRequest() {
        FBTokenUpdateRequest fbTokenUpdateRequest = new FBTokenUpdateRequest();
        fbTokenUpdateRequest.setUser_id(userid);
        fbTokenUpdateRequest.setFb_token(token);
        Log.w(TAG,"fbTokenUpdateRequest"+ "--->" + new Gson().toJson(fbTokenUpdateRequest));
        //  Toasty.success(getApplicationContext(),"fbTokenUpdateRequest : "+new Gson().toJson(fbTokenUpdateRequest), Toast.LENGTH_SHORT, true).show();

        return fbTokenUpdateRequest;
    }
}


