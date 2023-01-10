package com.triton.johnson.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.triton.johnson.R;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.ApiCall;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.arraylist.IssueList;
import com.triton.johnson.db.CommonUtil;
import com.triton.johnson.db.DbHelper;
import com.triton.johnson.db.DbUtil;
import com.triton.johnson.johnsonlogin.JohnsonTicketCountsActivity;
import com.triton.johnson.johnsonlogin.PartReplacement_Activity;
import com.triton.johnson.johnsonlogin.PartReplacement_Adapter;
import com.triton.johnson.materialspinner.MaterialSpinner;
import com.triton.johnson.model.ImageUploadResponse;
import com.triton.johnson.photoview.PhotoView;
import com.triton.johnson.requestpojo.GetPartListRequest;
import com.triton.johnson.requestpojo.TicketCreateRequest;
import com.triton.johnson.requestpojo.UpdateIsuesStatusRequest;
import com.triton.johnson.responsepojo.GetPartListResponse;
import com.triton.johnson.responsepojo.StationNameResponse;
import com.triton.johnson.responsepojo.SuccessResponse;
import com.triton.johnson.session.SessionManager;
import com.triton.johnson.sweetalertdialog.SweetAlertDialog;

import com.triton.johnson.adapter.IssueAdapter;
import com.triton.johnson.utils.ConnectionDetector;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.triton.johnson.utils.FileUtil;
import com.triton.johnson.utils.RestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;
import static android.view.View.GONE;

/**
 * Created by Iddinesh.
 */

public class UpdateStatusActivity extends AppCompatActivity {

    CharSequence[] statusString;
    CharSequence[] items;

    EditText issuseEditText;

    Spinner spinner_status;

    Typeface normalTypeface, boldTypeface;


    Button submitButton;

    SessionManager sessionManager;

    String empid = "", status = "", ticketId = "", message = "", departmentCode = "", locNmae = "", ticketStatus = "";
    String imagepath = "", userLevel = "";
    String is_status_changed = "", updated_date = "", urlstatus;
    String networkStatus = "";
    String station_code;

    Dialog alertDialog, dialog;

    RequestQueue requestQueue;

    GridView injuryGridView;

    int position = 0;

    ProgressDialog dialogg;

    IssueList issueList;

    ArrayList<IssueList> issueListArrayList = new ArrayList<>();

    IssueAdapter issueAdapter;

    LinearLayout backLayout, addUploadLinearLayout;
    LinearLayout wholeLayout;
    private String issues;
    private String TAG ="UpdateStatusActivity";
    private String Status;
    private String StatusName = "";
    ArrayAdapter<String> spinnerArrayAdapter;
    private List<UpdateIsuesStatusRequest.TicketPhotoBean> image_list = new ArrayList<>();

    private static final String CAMERA_PERMISSION = CAMERA ;
    private static final String READ_EXTERNAL_STORAGE_PERMISSION = READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL_STORAGE_PERMISSION = WRITE_EXTERNAL_STORAGE;

    private static final int REQUEST_READ_FAULT_PIC_PERMISSION = 786;
    private static final int REQUEST_FAULT_CAMERA_PERMISSION_CODE = 785 ;

    private static final int SELECT_FAULT_CAMERA = 1000 ;
    private static final int SELECT_FAULT_PICTURE = 1001 ;
    private MultipartBody.Part filePart;

    LinearLayout ll_Radio;
    private RadioGroup radioGroup;
    RadioButton radioButton;
    int selectedId;
    String TextMaterial ="",type;
    Button btn_Add;
    Context context;
    String jobid,str_Partname,str_Partno,str_Parttype;
    ArrayList<String> arli_Partname,arli_Partno,arli_Parttype;
    TextView txt_Partname;
    RelativeLayout rel_One,rel_Two;
    EditText edt_Search;
    ImageView  imgClearsearch;
    //////////

    RecyclerView recyclerView;
    Button btn_Ok;
    String Status_Name;
    TextView txt_NoRecords;
    List<GetPartListResponse.Datum> databeanlist;
    PartReplacement_Adapter partReplacement_Adapter;
    ProgressDialog progressDialog;

    @SuppressLint({"ClickableViewAccessibility", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_issuse);
        context = this;

        CommonUtil.dbUtil = new DbUtil(context);
        CommonUtil.dbUtil.open();
        CommonUtil.dbHelper = new DbHelper(context);

        sessionManager = new SessionManager(UpdateStatusActivity.this);
        HashMap<String, String> hashMap = sessionManager.getUserDetails();

        empid = hashMap.get(SessionManager.KEY_ID);
        userLevel = hashMap.get(SessionManager.KEY_USER_LEVEL);
        station_code = hashMap.get(SessionManager.KEY_STATION_CODE);
        Log.w(TAG+"  "+"onCreate-->","empid :"+empid+"userLevel :"+userLevel+"station_code :"+station_code);

//        ticketStatus = Objects.requireNonNull(getIntent().getExtras()).getString("ticketStatus");
//        ticketId = getIntent().getExtras().getString("ticketId");
//
//        Log.e(TAG,"ticketStatus : "+ticketStatus+" ticketId : "+ticketId);


//        locNmae = getIntent().getExtras().getString("stationLocation");
//        issues = getIntent().getExtras().getString("issues");
//
//        Log.e(TAG,"ticketStatus : "+locNmae+" ticketId : "+issues);

        CommonUtil.dbUtil.reportdelete(jobid);
        Cursor cur = CommonUtil.dbUtil.getPart(jobid);
        Log.e("COunt",""+cur.getCount());

//        CommonUtil.dbUtil.deleteparttable();
//        Cursor cur = CommonUtil.dbUtil.getPartall();
//        Log.e("Full Deletion",""+cur.getCount());


        boldTypeface = Typeface
                .createFromAsset(UpdateStatusActivity.this.getAssets(), "fonts/bolod_gothici.TTF");
        normalTypeface = Typeface
                .createFromAsset(UpdateStatusActivity.this.getAssets(), "fonts/regular_gothici.TTF");

        addUploadLinearLayout = findViewById(R.id.add_upload_image_layout);
        wholeLayout = findViewById(R.id.whole_layout);
        backLayout = findViewById(R.id.back_layout);

        issuseEditText = findViewById(R.id.complanit_edit);

        spinner_status = findViewById(R.id.spinner_status);

        ll_Radio = findViewById(R.id.ll_radiobtn);
        radioGroup = (RadioGroup)findViewById(R.id.groupradio);
        btn_Add = findViewById(R.id.btn_add);

        submitButton = findViewById(R.id.add_button);

        injuryGridView = findViewById(R.id.add_image_grid_view);

        txt_Partname =findViewById(R.id.txt_partname);
///////////////
        rel_One = findViewById(R.id.rel_one);
        rel_Two = findViewById(R.id.rel_two);
        recyclerView = findViewById(R.id.recycler_view);
        btn_Ok = findViewById(R.id.btn_ok);
        txt_NoRecords = findViewById(R.id.txt_no_records);
        edt_Search = findViewById(R.id.edt_search);
        imgClearsearch = findViewById(R.id.img_clearsearch);

        issuseEditText.setTypeface(normalTypeface);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        type =sharedPreferences.getString("type", "1");
        Log.e("Nish ",""+type);
        Log.e("Nish ",""+ String.valueOf(type));
        jobid = sharedPreferences.getString("jobid", "default value");
        Log.e("JobID",""+jobid);
        ticketStatus = sharedPreferences.getString("ticketStatus","abcd");
        ticketId = sharedPreferences.getString("ticketId","1234");
        Log.e("ticketStatus",""+ticketStatus);
        Log.e("ticketId",""+ticketId);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            StatusName = extras.getString("status");
            Log.e("Nish2",""+StatusName);
        }


        items = new CharSequence[]{"Take Photo", "Choose from Gallery", "Cancel"};

        String[] statuslist = {"Select status","Inprogress"};
        String[] statuslist1 = {"Select status","Pending","Completed"};
        String[] statuslist2 = {"Select status","Completed"};


        if(ticketStatus != null && !ticketStatus.isEmpty()){
            if(ticketStatus.equalsIgnoreCase("Open")){
                spinnerArrayAdapter = new ArrayAdapter<>(UpdateStatusActivity.this, R.layout.spinner_item, statuslist);
            }else if(ticketStatus.equalsIgnoreCase("Inprogress")){
                spinnerArrayAdapter = new ArrayAdapter<>(UpdateStatusActivity.this, R.layout.spinner_item, statuslist1);
            }else if(ticketStatus.equalsIgnoreCase("Pending")){
                spinnerArrayAdapter = new ArrayAdapter<>(UpdateStatusActivity.this, R.layout.spinner_item, statuslist2);
            }
        }
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
        spinner_status.setAdapter(spinnerArrayAdapter);

        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                StatusName = spinner_status.getSelectedItem().toString();
                Log.e("Status Type",""+StatusName);

//                radioGroup.clearCheck();

                if (StatusName.equals("Completed")){
                    ll_Radio.setVisibility(View.VISIBLE);
//                    radioGroup.clearCheck();
//                    radioButton.setId(0);
                }
                else{

                    CommonUtil.dbUtil.reportdelete(jobid);
                    Cursor cur = CommonUtil.dbUtil.getPart(jobid);
                    Log.e("After Pending",""+cur.getCount());
                    txt_Partname.setText("Please Add Part Replacement Items");
                    ll_Radio.setVisibility(GONE);
                    btn_Add.setVisibility(GONE);
                    txt_Partname.setVisibility(GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                radioButton = (RadioButton)group.findViewById(checkedId);

                TextMaterial = radioButton.getText().toString();
                Log.e("Data",""+TextMaterial);

//                Toast.makeText(UpdateStatusActivity.this,
//                                TextMaterial,
//                                Toast.LENGTH_SHORT)
//                        .show();

                if (TextMaterial.equals("Yes")){

                    btn_Add.setVisibility(View.VISIBLE);
                    txt_Partname.setVisibility(View.VISIBLE);

                    btn_Add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Cursor cur = CommonUtil.dbUtil.getPart(jobid);
                            Log.e("Before add",""+cur.getCount());

                            rel_One.setVisibility(GONE);
                            rel_Two.setVisibility(View.VISIBLE);

                            getPartListCall();


//                            Intent intent = new Intent(context, PartReplacement_Activity.class);
//                            intent.putExtra("status",StatusName);
//                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//                            SharedPreferences.Editor editor = sharedPreferences.edit();
//                            editor.putString("status", StatusName);
//                            Log.e("Nish",""+StatusName);
//                            context.startActivity(intent);
                        }
                    });
                }else{
                    CommonUtil.dbUtil.reportdelete(jobid);
                    Cursor cur = CommonUtil.dbUtil.getPart(jobid);
                    Log.e("After NO",""+cur.getCount());
                    txt_Partname.setText("Please Add Part Replacement Items");
                    btn_Add.setVisibility(GONE);
                    txt_Partname.setVisibility(GONE);
                }

              //  selectedId = radioGroup.getCheckedRadioButtonId();

//                Log.e("Data",""+radioGroup.getCheckedRadioButtonId());
            }
        });

        edt_Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String Search = edt_Search.getText().toString();

                if(Search.equals("")){
                    recyclerView.setVisibility(View.VISIBLE);
                    imgClearsearch.setVisibility(View.INVISIBLE);
                } else {

                    filter(Search);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String Search = edt_Search.getText().toString();

                recyclerView.setVisibility(View.VISIBLE);
                txt_NoRecords.setVisibility(GONE);

                filter(Search);
            }
        });



        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_Search.setText("");
                recyclerView.setVisibility(GONE);
                Cursor cur = CommonUtil.dbUtil.getPart(jobid);
                Log.e("After Select",""+cur.getCount());
                rel_Two.setVisibility(GONE);
                rel_One.setVisibility(View.VISIBLE);
                getPartdata();
            }
        });



        Log.e("userLevel", "" + userLevel);
      /*  if (userLevel.equalsIgnoreCase("5")) {
            if (issues.equalsIgnoreCase("Open")) {
                statusString = new CharSequence[]{"Select status", "In progress", "Pending", "Completed"};
            }
            if (issues.equalsIgnoreCase("In Progress")) {
                statusString = new CharSequence[]{"Select status", "Pending", "Completed"};
            }
            if (issues.equalsIgnoreCase("Pending")) {
                statusString = new CharSequence[]{"Select status", "In progress", "Completed"};
            }
        }
        else if (userLevel.equalsIgnoreCase("4")) {
            if (issues.equalsIgnoreCase("Completed")) {
                statusString = new CharSequence[]{"Select status", "Pending", "Closed"};
            }
        }*/


        issuseEditText.setFocusable(false);

        issuseEditText.setOnTouchListener((view, motionEvent) -> {

            issuseEditText.setFocusableInTouchMode(true);

            return false;
        });

        backLayout.setOnClickListener(view -> onBackPressed());

        addUploadLinearLayout.setOnClickListener(view -> {

          /*  items = new CharSequence[]{"Take Photo", "Choose from Gallery", "Cancel"};
            selectImage(position);*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkMultiplePermissions(UpdateStatusActivity.this);
            }else{
                chooseFaultImage();

            }


        });

        injuryGridView.setOnItemClickListener((adapterView, v, i, l) -> {

            items = new CharSequence[]{"Remove Image", "View Image", "Cancel"};
            selectImage(i);
        });


        // check whether internet is on or not
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(wholeLayout, "No internet connection!", Snackbar.LENGTH_LONG)
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



        wholeLayout.setOnClickListener(view -> {

            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (in != null) {
                in.hideSoftInputFromWindow(wholeLayout.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        });

        submitButton.setOnClickListener(view -> {


            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (in != null) {
                in.hideSoftInputFromWindow(submitButton.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
            if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {
                Snackbar snackbar = Snackbar
                        .make(wholeLayout, "No internet connection!", Snackbar.LENGTH_LONG)
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


            } else {
                String title;
                StringBuilder issuseImages = new StringBuilder();


                if (StatusName.equalsIgnoreCase("")) {
                    new SweetAlertDialog(UpdateStatusActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Johnson")
                            .setContentText("Please select status")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(Dialog::dismiss)
                            .show();

                } else if (StatusName.equalsIgnoreCase("Select status")) {
                    new SweetAlertDialog(UpdateStatusActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Johnson")
                            .setContentText("Please select status")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(Dialog::dismiss)
                            .show();

                } else if (issuseEditText.getText().toString().trim().equalsIgnoreCase("")) {
                    new SweetAlertDialog(UpdateStatusActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Johnson")
                            .setContentText("Please enter message")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(Dialog::dismiss)
                            .show();
                }
                else {
                  //  7305194349
                    UpdateIsuesStatusResponseCall();
                }

            }
        });
    }

    private void filter(String search) {

        List<GetPartListResponse.Datum> filterlist = new ArrayList<>();

        try {
            for (GetPartListResponse.Datum item : databeanlist) {
                if (item.getPart_name().toLowerCase().contains(search.toLowerCase())) {
                    Log.w(TAG, "filter----" + item.getPart_name().toLowerCase().contains(search.toLowerCase()));
                    filterlist.add(item);

                }
            }
        }catch (NullPointerException e){
            e.printStackTrace();
            Log.e("Hi ",""+e.toString());
        }


        if(filterlist.isEmpty()) {
            //Toast.makeText(this,"No Data Found ... ",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            txt_NoRecords.setVisibility(View.VISIBLE);
            txt_NoRecords.setText("No Data Found");
        }else
        {
            partReplacement_Adapter.filterrList(filterlist);
        }

    }
    private void getPartListCall() {

        progressDialog = new ProgressDialog(UpdateStatusActivity.this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetPartListResponse> call = apiInterface.PartListCall(RestUtils.getContentType(), partlistRequest());
        Log.w(TAG, "Get Part List url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<GetPartListResponse>() {
            @Override
            public void onResponse(Call<GetPartListResponse> call, Response<GetPartListResponse> response) {
                Log.e(TAG, "Get Part List Response" + new Gson().toJson(response.body()));

                progressDialog.dismiss();

                if (response.body() != null){

                    if (response.body().getCode() == 200){


                        if (response.body().getData() != null) {
                            databeanlist = response.body().getData();

                            if (databeanlist.size() == 0){

                                recyclerView.setVisibility(GONE);
                                txt_NoRecords.setVisibility(View.VISIBLE);
                                txt_NoRecords.setText("No Records Found");

                            }
                            recyclerView.setVisibility(View.VISIBLE);

                            setView(databeanlist);
                            Log.d("dataaaaa", String.valueOf(databeanlist));

                        } else if (400 == response.body().getCode()) {
                            progressDialog.dismiss();
                            if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {
                                recyclerView.setVisibility(GONE);
                                txt_NoRecords.setVisibility(View.VISIBLE);
                                txt_NoRecords.setText("Error 404 Found..!");
                            }
                        }else {
                            progressDialog.dismiss();
                            recyclerView.setVisibility(GONE);
                            txt_NoRecords.setVisibility(View.VISIBLE);
                            txt_NoRecords.setText("Error 404 Found..!");
                            Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                        }
                    }


                }
                else{

                    progressDialog.dismiss();
                    recyclerView.setVisibility(GONE);
                    txt_NoRecords.setVisibility(View.VISIBLE);
                    txt_NoRecords.setText("Something went wrong..! Try agin");

                }

            }

            @Override
            public void onFailure(Call<GetPartListResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(GONE);
                txt_NoRecords.setVisibility(View.VISIBLE);
                txt_NoRecords.setText("Something went wrong..! Try agin");
            }
        });

    }

    private void setView(List<GetPartListResponse.Datum> databeanlist) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        partReplacement_Adapter = new PartReplacement_Adapter(this, databeanlist);
        recyclerView.setAdapter(partReplacement_Adapter);
    }

    private GetPartListRequest partlistRequest() {

        GetPartListRequest partRequest = new GetPartListRequest();
        partRequest.setPart_type(type);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(partRequest));
        return partRequest;
    }

    @SuppressLint("SetTextI18n")
    private void getPartdata() {

        arli_Partname = new ArrayList<>();
        arli_Partno = new ArrayList<>();
        arli_Parttype = new ArrayList<>();

        Cursor cur = CommonUtil.dbUtil.getPart(jobid);
        Log.e("COunt",""+cur.getCount());

        if (cur.getCount()>0 && cur.moveToFirst()) {

            txt_Partname.setVisibility(View.VISIBLE);

            txt_Partname.setText(cur.getCount()+"item(s) Selected for Part Replacement");

            do{
                str_Partname = cur.getString(cur.getColumnIndex(DbHelper.PART_NAME));
                str_Partno = cur.getString(cur.getColumnIndex(DbHelper.PART_NO));
                str_Parttype = cur.getString(cur.getColumnIndex(DbHelper.PART_TYPE));
                arli_Partname.add(str_Partname);
                arli_Partno.add(str_Partno);
                arli_Parttype.add(str_Parttype);

                Log.e("Partname",""+ arli_Partname);
                Log.e("Partno",""+ arli_Partno);
                Log.e("PartType",""+ arli_Parttype);
            }while (cur.moveToNext());

        }
    }

    @SuppressLint("SimpleDateFormat")
    public void bitmapConvertToFile(Bitmap bitmap) {
        FileOutputStream fileOutputStream = null;
        File bitmapFile;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory("image_crop_sample"), "");
            if (!file.exists()) {
                file.mkdir();
            }

            bitmapFile = new File(file, "IMG_" + (new SimpleDateFormat("yyyyMMddHHmmss")).format(Calendar.getInstance().getTime()) + ".jpg");
            fileOutputStream = new FileOutputStream(bitmapFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            MediaScannerConnection.scanFile(UpdateStatusActivity.this, new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(final String path, final Uri uri) {

                    new Thread(() -> uploadFile(path)).start();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception ignored) {
                }
            }
        }

    }

/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            //addImagLinearLayout.setImageBitmap(bitmap);
            dialogg = ProgressDialog.show(UpdateStatusActivity.this, "", "Uploading Image...", true);
            bitmapConvertToFile(bitmap);

        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : Objects.requireNonNull(f.listFiles())) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            try {

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                options.inSampleSize = 2;
                options.inJustDecodeBounds = false;
                options.inTempStorage = new byte[16 * 1024];

                Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath(), options);
                dialogg = ProgressDialog.show(UpdateStatusActivity.this, "", "Uploading Image...", true);
                bitmapConvertToFile(bmp);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = UpdateStatusActivity.this.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void cameraIntent() {
        File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        Intent install = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkURI = FileProvider.getUriForFile(
                UpdateStatusActivity.this,
                UpdateStatusActivity.this.getApplicationContext()
                        .getPackageName() + ".provider", file);
        install.putExtra(MediaStore.EXTRA_OUTPUT, apkURI);

        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        UpdateStatusActivity.this.startActivityForResult(install, 2);
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, 2);*/
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    private void selectImage(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
        builder.setTitle("ADD PHOTO");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                cameraIntent();

            } else if (items[item].equals("Choose from Gallery")) {
                galleryIntent();

            } else if (items[item].equals("Cancel")) {

                dialog.dismiss();

            } else if (items[item].equals("Remove Image")) {

                issueListArrayList.remove(position);

                issueAdapter = new IssueAdapter(UpdateStatusActivity.this, issueListArrayList);
                injuryGridView.setAdapter(issueAdapter);

            } else if (items[item].equals("View Image")) {

                alertDialog = new Dialog(UpdateStatusActivity.this, R.style.DialogTheme);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                alertDialog.setContentView(R.layout.full_screen_popup_layout);

                PhotoView photoView = alertDialog.findViewById(R.id.iv_photo);
                Glide.with(UpdateStatusActivity.this)
                        .load(issueListArrayList.get(position).getImageUrl())
                        .centerCrop()
                        .placeholder(R.drawable.no_image)
                        .into(photoView);
                alertDialog.show();

                photoView.setOnClickListener(view -> alertDialog.cancel());


            }
        });
        builder.show();
    }

   /* public int uploadFile1(String sourceFileUri) {

        //sourceFileUri.replace(sourceFileUri, "ashifaq");
        //
        int day, month, year;
        int second, minute, hour;
        GregorianCalendar date = new GregorianCalendar();

        day = date.get(Calendar.DAY_OF_MONTH);
        month = date.get(Calendar.MONTH);
        year = date.get(Calendar.YEAR);

        second = date.get(Calendar.SECOND);
        minute = date.get(Calendar.MINUTE);
        hour = date.get(Calendar.HOUR);

        String name = (hour + "" + minute + "" + second + "" + day + "" + (month + 1) + "" + year);
        String tag = name + ".jpg";
        final String fileName = sourceFileUri.replace(sourceFileUri, tag);

        HttpURLConnection conn;
        DataOutputStream dos;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            dialogg.dismiss();

            Log.e("uploadFile", "Source File not exist :" + imagepath);

            runOnUiThread(() -> {

                Toast.makeText(UpdateStatusActivity.this, "Source File not exist :" + imagepath, Toast.LENGTH_LONG).show();
                // messageText.setText("Source File not exist :"+ imagepath);
            });

            return 0;

        } else {
            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(ApiCall.API_URL+"image_upload");

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(() -> {

                        final String ImageUrl = ApiCall.BASE_URL+"assets/uploads/" + fileName;
                        Log.e("ImageUrl", "" + ImageUrl);

                        issueList = new IssueList(ImageUrl);
                        issueListArrayList.add(issueList);

                        // Calculate width and height of the horizontal GridView
                        int size = issueListArrayList.size();
                        injuryGridView.setNumColumns(size);
                        int Imagewith = size * 100;
                        final float Image_COL_WIDTH = UpdateStatusActivity.this.getResources().getDisplayMetrics().density * Imagewith;
                        int Image_width = Math.round(Image_COL_WIDTH);

                        LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(Image_width, ViewGroup.LayoutParams.MATCH_PARENT);
                        injuryGridView.setLayoutParams(lpp);

                        issueAdapter = new IssueAdapter(UpdateStatusActivity.this, issueListArrayList);
                        injuryGridView.setAdapter(issueAdapter);
                    });
                }
                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                dialogg.dismiss();
                ex.printStackTrace();

                runOnUiThread(() -> {
                    //  messageText.setText("MalformedURLException Exception : check script url.");
                    Toast.makeText(UpdateStatusActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                dialogg.dismiss();
                e.printStackTrace();

                runOnUiThread(() -> {
                    //  messageText.setText("Got Exception : see logcat ");
                    Toast.makeText(UpdateStatusActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                });
                Log.e("UploadException", "Exception : " + e.getMessage(), e);
            }
            dialogg.dismiss();
            return serverResponseCode;

        }
    }*/

    public void AddIssuseURL(String url) {
        dialog = new Dialog(UpdateStatusActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        requestQueue = Volley.newRequestQueue(UpdateStatusActivity.this);

        Log.e("url", "" + url);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {

                    try {

                        JSONArray ja = response.getJSONArray("response");

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject jsonObject = ja.getJSONObject(i);
                            urlstatus = jsonObject.getString("status");
                            message = jsonObject.getString("message");
                            is_status_changed = jsonObject.getString("message");
                            updated_date = jsonObject.getString("message");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    dialog.dismiss();

                    if (urlstatus.equalsIgnoreCase("1")) {

                        new SweetAlertDialog(UpdateStatusActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(getResources().getString(R.string.app_name))
                                .setContentText(message)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(sDialog -> {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(UpdateStatusActivity.this, DepartmentStatusListClass.class);
                                    //  Intent intent = new Intent(UpdateStatusActivity.this, IssuseStatus.class);
                                    intent.putExtra("code", departmentCode);
                                    intent.putExtra("name", locNmae);
                                    intent.putExtra("stationcode",station_code);
                                    Log.w(TAG,"stationcode"+station_code);
                                    Log.w(TAG,"departmentCode :"+departmentCode+"locNmae :"+locNmae+"station_code :"+station_code);


                                    intent.putExtra("ticket_status", ticketStatus);
                                    intent.putExtra("ticket", ticketId);
                                    intent.putExtra("issues", issues);


                                    Log.w(TAG,"ticket_status :"+ticketStatus+"ticketId :"+ticketId+"issues :"+issues);


                                    overridePendingTransition(R.anim.new_right, R.anim.new_left);

                                    startActivity(intent);
                                    finish();

                                })
                                .show();
                    } else {

                        new SweetAlertDialog(UpdateStatusActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(getResources().getString(R.string.app_name))
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

    protected void onResume() {

        super.onResume();
        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());
        if (networkStatus.equalsIgnoreCase("Not connected to Internet")) {

            Snackbar snackbar = Snackbar
                    .make(wholeLayout, "No internet connection!", Snackbar.LENGTH_LONG)
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
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

//        Intent intent = new Intent(context, JohnsonTicketCountsActivity.class);
//        context.startActivity(intent);
    }

    public void uploadFile(final String path) {
        APIInterface ApiService = RetrofitClient.getImageClient().create(APIInterface.class);
        Call<ImageUploadResponse> call = ApiService.getImageStroeResponse(getProfileImagePicMultipart(path));
        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImageUploadResponse> call, @NonNull retrofit2.Response<ImageUploadResponse> response) {
                dialogg.dismiss();
                Log.w(TAG,"ImageUploadResponse"+ "--->" + new Gson().toJson(response.body()));


                Log.w("Status :",""+status);
                if (response.body().getData() != null ) {

                    Log.w(TAG,"ImageUploadResponse"+"--->" + new Gson().toJson(response.body()));

                    // String fileName = path.substring(path.lastIndexOf("/") + 1);
                    String fileName = response.body().getData();

                    UpdateIsuesStatusRequest.TicketPhotoBean imageListBean =  new UpdateIsuesStatusRequest.TicketPhotoBean();
                    imageListBean.setImage_path(fileName);
                    image_list.add(imageListBean);
                    Log.w("fileName", fileName);



                    String ImageUrl = ApiCall.API_URL + fileName;
                    // String ImageUrl = response.body().getResponse().get(0).getFilepath();
                    Log.w(TAG,"ImageURL-->"+ImageUrl);
                    issueList = new IssueList(ImageUrl);
                    issueListArrayList.add(issueList);

                    int size = issueListArrayList.size();
                    injuryGridView.setNumColumns(size);
                    int Imagewith = size * 100;
                    final float Image_COL_WIDTH = UpdateStatusActivity.this.getResources().getDisplayMetrics().density * Imagewith;
                    int Image_width = Math.round(Image_COL_WIDTH);

                    LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(Image_width, ViewGroup.LayoutParams.MATCH_PARENT);
                    injuryGridView.setLayoutParams(lpp);
                    issueAdapter = new IssueAdapter(UpdateStatusActivity.this, issueListArrayList);
                    injuryGridView.setAdapter(issueAdapter);


                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageUploadResponse> call, @NonNull Throwable t) {
                dialogg.dismiss();
                Log.w("Profile", "On failure working"+t.getMessage());
            }
        });

    }
    private MultipartBody.Part getProfileImagePicMultipart(String path) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("text/plain"), "");
        if (path != null && !path.isEmpty()) {
            File file = new File(path);
            requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        }
        Log.w(TAG,"PATH:"+path+"requestFile :"+requestFile);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("sampleFile", path, requestFile);
        Log.w("ImageStorepath", "getProfileMultiPartRequest: " + new Gson().toJson(filePart));
        return filePart;
    }


    @SuppressLint("LogNotTimber")
    private void UpdateIsuesStatusResponseCall() {
        dialog = new Dialog(UpdateStatusActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.UpdateIsuesStatusResponseCall(RestUtils.getContentType(), updateIsuesStatusRequest());
        Log.w(TAG,"TicketCreateRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"SuccessResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        CommonUtil.dbUtil.reportdelete(jobid);

                        startActivity(new Intent(getApplicationContext(),JohnshonLoginDashboardActivity.class));

                    }
                }

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<SuccessResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("SuccessResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private UpdateIsuesStatusRequest updateIsuesStatusRequest() {

        /*
         * ticket_no : 1
         * ticket_status : 611511214f912e1856fc6d46
         * ticket_comments : 61151dbaac7f9e21e2963133
         * ticket_photo : [{"image_path":"/uploads/1628847534691.jpg"}]
         * user_id :
         * date_of_create :
         */

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        UpdateIsuesStatusRequest updateIsuesStatusRequest = new UpdateIsuesStatusRequest();
        updateIsuesStatusRequest.setDate_of_create(currentDateandTime);
        updateIsuesStatusRequest.setTicket_no(ticketId);
        updateIsuesStatusRequest.setTicket_status(StatusName);
        updateIsuesStatusRequest.setTicket_comments(issuseEditText.getText().toString());
        updateIsuesStatusRequest.setTicket_photo(image_list);
        updateIsuesStatusRequest.setUser_id(empid);
        updateIsuesStatusRequest.setPart_no_req(TextMaterial);

        if (TextMaterial.equals("Yes")){

            if (ticketStatus.equals("Inprogress") && StatusName.equals("Completed")){

                List<UpdateIsuesStatusRequest.partDetails> part_details = new ArrayList<>();

                for(int j =0; j <arli_Partname.size(); j++){

                    UpdateIsuesStatusRequest.partDetails myfield = new UpdateIsuesStatusRequest.partDetails();

                    myfield.setPart_name(arli_Partname.get(j));
                    myfield.setPart_no(arli_Partno.get(j));
                    myfield.setPart_type(arli_Parttype.get(j));

                    part_details.add(myfield);

                }

                Log.e("Nish",""+ part_details.size());
                Log.e(TAG," Update Issue"+ new Gson().toJson(part_details));

                updateIsuesStatusRequest.setPart_det(part_details);
            }
        }
        else{

            List<UpdateIsuesStatusRequest.partDetails> part_details = new ArrayList<>();

                UpdateIsuesStatusRequest.partDetails myfield = new UpdateIsuesStatusRequest.partDetails();

                myfield.setPart_name("-");
                myfield.setPart_no("-");
                myfield.setPart_type("-");
                part_details.add(myfield);

            Log.e(TAG," Update Issue"+ new Gson().toJson(part_details));

            updateIsuesStatusRequest.setPart_det(part_details);
        }

        Log.w(TAG,"updateIsuesStatusRequest "+ new Gson().toJson(updateIsuesStatusRequest));
        return updateIsuesStatusRequest;
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void checkMultiplePermissions(Context context) {

        String[] PERMISSIONS = {CAMERA_PERMISSION, READ_EXTERNAL_STORAGE_PERMISSION, WRITE_EXTERNAL_STORAGE_PERMISSION};
        if (!hasPermissions(context, PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, 1);
        } else {
            chooseFaultImage();
            // Open your camera here.
        }
    }
    private boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void chooseFaultImage() {


        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        //AlertDialog.Builder alert=new AlertDialog.Builder(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
        builder.setTitle("Choose option");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(UpdateStatusActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_FAULT_CAMERA_PERMISSION_CODE);
                }
                else
                {


                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, SELECT_FAULT_CAMERA);
                }

            }

            else if (items[item].equals("Choose from Gallery"))
            {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(UpdateStatusActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_FAULT_PIC_PERMISSION);
                }

                else{

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FAULT_PICTURE);


                }
            }

            else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //	Toast.makeText(getActivity(),"kk",Toast.LENGTH_SHORT).show();
        if(requestCode== SELECT_FAULT_PICTURE || requestCode == SELECT_FAULT_CAMERA)
        {

            if(requestCode == SELECT_FAULT_CAMERA)
            {
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                File file = new File(getFilesDir(), "Petfolio1" + ".jpg");

                OutputStream os;
                try {
                    os = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());

                RequestBody requestFile = RequestBody.create(MediaType.parse("image*/"), file);

                filePart = MultipartBody.Part.createFormData("sampleFile",  empid+currentDateandTime+file.getName(), requestFile);

                uploadFaultImage();

            }

            else{

                try {
                    if (resultCode == Activity.RESULT_OK)
                    {

                        Log.w("VALUEEEEEEE1111", " " + data);

                        Uri selectedImageUri = data.getData();

                        Log.w("selectedImageUri", " " + selectedImageUri);

                        String filename = getFileName(selectedImageUri);

                        Log.w("filename", " " + filename);

                        String filePath = FileUtil.getPath(UpdateStatusActivity.this,selectedImageUri);

                        assert filePath != null;

                        File file = new File(filePath); // initialize file here

                        long length = file.length() / 1024; // Size in KB

                        Log.w("filesize", " " + length);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
                        String currentDateandTime = sdf.format(new Date());

                        filePart = MultipartBody.Part.createFormData("sampleFile", empid+currentDateandTime+file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

                        uploadFaultImage();


                    }
                } catch (Exception e) {

                    Log.w("Exception", " " + e);
                }

            }

        }



    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    private void uploadFaultImage() {
        dialog = new Dialog(UpdateStatusActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();
        APIInterface ApiService = RetrofitClient.getImageClient().create(APIInterface.class);
        Call<ImageUploadResponse> call = ApiService.getImageStroeResponse(filePart);
        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImageUploadResponse> call, @NonNull retrofit2.Response<ImageUploadResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"ImageUploadResponse"+"--->" + new Gson().toJson(response.body()));
                if (response.body().getData() != null ) {

                    //String fileName = path.substring(path.lastIndexOf("/") + 1);
                    String fileName = response.body().getData();
                    Log.w("fileName", fileName);
                    UpdateIsuesStatusRequest.TicketPhotoBean imageListBean =  new UpdateIsuesStatusRequest.TicketPhotoBean();
                    imageListBean.setImage_path(fileName);
                    image_list.add(imageListBean);



                    String ImageUrl = ApiCall.API_URL+ fileName;
                    //String ImageUrl = response.body().getResponse().get(0).getFilepath();
                    Log.w(TAG,"ImageURL"+ImageUrl);
                    issueList = new IssueList(ImageUrl);
                    issueListArrayList.add(issueList);

                    int size = issueListArrayList.size();
                    injuryGridView.setNumColumns(size);
                    int Imagewith = size * 100;
                    final float Image_COL_WIDTH = UpdateStatusActivity.this.getResources().getDisplayMetrics().density * Imagewith;
                    int Image_width = Math.round(Image_COL_WIDTH);


                    LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(Image_width, ViewGroup.LayoutParams.MATCH_PARENT);
                    injuryGridView.setLayoutParams(lpp);


                    issueAdapter = new IssueAdapter(UpdateStatusActivity.this, issueListArrayList);
                    injuryGridView.setAdapter(issueAdapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageUploadResponse> call, @NonNull Throwable t) {
                dialog.dismiss();

                Log.w("Profile", "On failure working"+t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
