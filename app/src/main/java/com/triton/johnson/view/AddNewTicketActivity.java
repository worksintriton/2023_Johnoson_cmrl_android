package com.triton.johnson.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
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

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.triton.johnson.R;
import com.triton.johnson.adapter.IssueAdapter;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.ApiCall;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.arraylist.DeparmentSpinnerList;
import com.triton.johnson.arraylist.IssueList;
import com.triton.johnson.arraylist.LocationList;
import com.triton.johnson.arraylist.PriorityList;
import com.triton.johnson.model.ImageUploadResponse;
import com.triton.johnson.photopicker.activity.PickImageActivity;
import com.triton.johnson.photoview.PhotoView;
import com.triton.johnson.requestpojo.FaultTypeListRequest;
import com.triton.johnson.requestpojo.JobNoListRequest;
import com.triton.johnson.requestpojo.StationNameRequest;
import com.triton.johnson.requestpojo.TicketCreateRequest;
import com.triton.johnson.responsepojo.FaultTypeListResponse;
import com.triton.johnson.responsepojo.JobNoListResponse;
import com.triton.johnson.responsepojo.ServingLevelListResponse;
import com.triton.johnson.responsepojo.StationNameResponse;
import com.triton.johnson.responsepojo.SuccessResponse;
import com.triton.johnson.session.SessionManager;
import com.triton.johnson.sweetalertdialog.SweetAlertDialog;
import com.triton.johnson.utils.ConnectionDetector;
import com.triton.johnson.utils.FileUtil;
import com.triton.johnson.utils.RestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

/**
 * Created by Iddinesh.
 */

public class AddNewTicketActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG ="AddNewTicketActivity";

    GridView injuryGridView;
    IssueAdapter issueAdapter;
    IssueList issueList;
    DeparmentSpinnerList deparmentSpinnerList;
    PriorityList priorityList;
    LocationList locationList;
    ArrayList<IssueList> issueListArrayList = new ArrayList<>();
    List<DeparmentSpinnerList> deparmentSpinnerListArrayList = new ArrayList<>();
    ArrayList<PriorityList> priorityListArrayList = new ArrayList<>();
    ArrayList<LocationList> locationListArrayList = new ArrayList<>();

    CharSequence[] items;

    ProgressDialog dialogg;

    String departmentString = "", priorityString = "", locationString = "", status = "";
    String message = "";
    String empid = "", station_code = "";
    Dialog alertDialog, dialog;
    LinearLayout addUploadLinearLayout, backLayout, wholeLayout;
    int position = 0;

    Spinner spinner_stationname,spinner_jobno,spinner_faulttype;
    Typeface boldTypeface, normalTypeface;

    SessionManager sessionManager;
    private static final int READ_STORAGE_CODE = 1001;
    private static final int WRITE_STORAGE_CODE = 1002;
    private ArrayList<String> pathList = new ArrayList<>();




    RadioGroup rgSelectType;
    private String selectedRadioButton;
    private int type = 1;

    HashMap<String, String> hashMap_StationId = new HashMap<>();
    HashMap<String, String> hashMap_JobNoId = new HashMap<>();
    HashMap<String, String> hashMap_ServingName = new HashMap<>();
    HashMap<String, String> hashMap_FaultTypeId = new HashMap<>();


    String networkStatus = "";
    private List<StationNameResponse.DataBean> stationNameList = new ArrayList<>();
    private List<JobNoListResponse.DataBean> JobNoList = new ArrayList<>();

    String StationName,StationName_id;
    String JobName,JobName_id;
    private List<ServingLevelListResponse.DataBean> ServingLevelList = new ArrayList<>();

    TextView txt_location;
    private List<FaultTypeListResponse.DataBean> FaultTypeList = new ArrayList<>();

    EditText edt_compliant;
    Button btn_submit;
    RelativeLayout rl_job_no;
    TextView txt_lbl_location;
    LinearLayout ll_location;

    List<TicketCreateRequest.ImageListBean> image_list = new ArrayList<>();
    private String FaultName;
    private String FaultName_Id;


    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    private static final String CAMERA_PERMISSION = CAMERA ;
    private static final String READ_EXTERNAL_STORAGE_PERMISSION = READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL_STORAGE_PERMISSION = WRITE_EXTERNAL_STORAGE;


    private static final int REQUEST_READ_FAULT_PIC_PERMISSION = 786;
    private static final int REQUEST_FAULT_CAMERA_PERMISSION_CODE = 785 ;

    private static final int SELECT_FAULT_CAMERA = 1000 ;
    private static final int SELECT_FAULT_PICTURE = 1001 ;
    private MultipartBody.Part filePart;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_new_ticket_layout);

        sessionManager = new SessionManager(AddNewTicketActivity.this);
        HashMap<String, String> hashMap = sessionManager.getUserDetails();

        empid = hashMap.get(SessionManager.KEY_ID);
        station_code = hashMap.get(SessionManager.KEY_STATION_CODE);

        boldTypeface = Typeface
                .createFromAsset(AddNewTicketActivity.this.getAssets(), "fonts/bolod_gothici.TTF");
        normalTypeface = Typeface
                .createFromAsset(AddNewTicketActivity.this.getAssets(), "fonts/regular_gothici.TTF");

        addUploadLinearLayout =  findViewById(R.id.add_upload_image_layout);
        backLayout =  findViewById(R.id.back_layout);
        wholeLayout =  findViewById(R.id.main_layout);




        spinner_stationname =  findViewById(R.id.spinner_stationname);
        spinner_jobno =  findViewById(R.id.spinner_jobno);
        spinner_faulttype =  findViewById(R.id.spinner_faulttype);
        txt_location =  findViewById(R.id.txt_location);
        rl_job_no =  findViewById(R.id.rl_job_no);
        rl_job_no.setVisibility(View.GONE);
        txt_lbl_location =  findViewById(R.id.txt_lbl_location);
        txt_lbl_location.setVisibility(View.GONE);
        ll_location =  findViewById(R.id.ll_location);
        ll_location.setVisibility(View.GONE);


        rgSelectType =  findViewById(R.id.rgSelectType);


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
        }
        else{
            StationNameResponseCall(type);

        }

        injuryGridView =  findViewById(R.id.add_image_grid_view);
        items = new CharSequence[]{"Take Photo", "Choose from Gallery", "Cancel"};



        networkStatus = ConnectionDetector.getConnectivityStatusString(getApplicationContext());







        injuryGridView.setOnItemClickListener((adapterView, v, i, l) -> {
            items = new CharSequence[]{"Remove Image", "View Image", "Cancel"};
            selectImage(i);
        });

        wholeLayout.setOnClickListener(view -> {
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (in != null) {
                in.hideSoftInputFromWindow(wholeLayout.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        });

        backLayout.setOnClickListener(view -> {

         /*   InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (in != null) {
                in.hideSoftInputFromWindow(wholeLayout.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            Intent intent = new Intent(AddNewTicketActivity.this, StationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.new_right, R.anim.new_left);*/
            onBackPressed();
        });

        addUploadLinearLayout.setOnClickListener(view -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkMultiplePermissions(AddNewTicketActivity.this);
            }else{
                chooseFaultImage();

            }

           /* items = new CharSequence[]{"Take Photo", "Choose from Gallery", "Cancel"};
            selectImage(position);*/

        });



      /*  addButton.setOnClickListener(view -> {

                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (in != null) {
                        in.hideSoftInputFromWindow(addButton.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    StringBuilder issuseImages = new StringBuilder();
                    if (!issueListArrayList.isEmpty()) {

                        for (int i = 0; i < issueListArrayList.size(); i++) {

                            issuseImages.append(",").append(issueListArrayList.get(i).getImageUrl().replace(ApiCall.BASE_URL + "assets/uploads/", ""));
                        }

                        issuseImages = new StringBuilder(issuseImages.substring(1));
                    }

                });*/



        rgSelectType.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = rgSelectType.getCheckedRadioButtonId();
            RadioButton radioButton = rgSelectType.findViewById(radioButtonID);
            selectedRadioButton = (String) radioButton.getText();
            Log.w(TAG,"selectedRadioButton" + selectedRadioButton);
            if(selectedRadioButton.equalsIgnoreCase("Lifts")){
                type = 1;
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
                }
                else{
                    StationNameResponseCall(type);

                }

            }else{
                type = 2;
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
                }
                else{
                    StationNameResponseCall(type);

                }

            }

        });
        spinner_stationname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                StationName = spinner_stationname.getSelectedItem().toString();
                StationName_id = hashMap_StationId.get(StationName);
                Log.w(TAG,"StationName : "+StationName+" StationName_id : "+StationName_id);
                if(StationName_id != null){
                    rl_job_no.setVisibility(View.VISIBLE);
                    JobNoListResponseCall(StationName_id);

                }else{
                    rl_job_no.setVisibility(View.GONE);

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinner_jobno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                JobName = spinner_jobno.getSelectedItem().toString();
                JobName_id = hashMap_JobNoId.get(JobName);
                String ServingName = hashMap_ServingName.get(JobName);
                Log.w(TAG,"JobName : "+JobName+" JobName_id : "+JobName_id);
                if(JobName_id != null){
                    txt_lbl_location.setVisibility(View.VISIBLE);
                    ll_location.setVisibility(View.VISIBLE);

                }else{
                    txt_lbl_location.setVisibility(View.GONE);
                    ll_location.setVisibility(View.GONE);

                }
                if(ServingName != null){
                    txt_location.setText(ServingName);
                }else{
                    txt_location.setText("");
                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        spinner_faulttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                FaultName = spinner_faulttype.getSelectedItem().toString();
                FaultName_Id = hashMap_FaultTypeId.get(FaultName);




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        btn_submit = findViewById(R.id.btn_submit);
        edt_compliant = findViewById(R.id.edt_compliant);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // ticketCreateRequest();
                if(validdSelectStationName() && validdSelectJobNo() && validdFaultType() ) {
                    TicketCreateRequestCall();
                }
            }
        });




    }

    String departmentStatuss = "";
    String departmentMessagee = "";
    String departmentId = "";
    String departmentCode = "";
    String departmentName = "";

    String priorityStatuss = "";
    String priorityMessagee = "";
    String priorityId = "";
    String priorityName = "";

    String locationStatuss = "";
    String locationMessagee = "";
    String locationId = "";
    String locationName = "";

    int i = 0;
    int j = 0;




    /**
     * @param bitmap convert file to bitmap and compress the image size
     */
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
            MediaScannerConnection.scanFile(AddNewTicketActivity.this, new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(final String path, final Uri uri) {

                    new Thread(() -> {

                        //uploadFile(path);
                        uploadProfileImageRequest(path);

                    }).start();

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

    @SuppressLint("SimpleDateFormat")
    public void bitmapConvert(ArrayList<String> uri) {
        FileOutputStream fileOutputStream = null;
        File bitmapFile;
        for (int i = 0; i < uri.size(); i++) {
            try {
                File file = new File(Environment.getExternalStoragePublicDirectory("image_crop_sample"), "");
                if (!file.exists()) {
                    file.mkdir();
                }

                bitmapFile = new File(file, "IMG_" + i + (new SimpleDateFormat("yyyyMMddHHmmss")).format(Calendar.getInstance().getTime()) + ".jpg");
                fileOutputStream = new FileOutputStream(bitmapFile);
                Bitmap bitmap = BitmapFactory.decodeFile(uri.get(i));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
                MediaScannerConnection.scanFile(AddNewTicketActivity.this, new String[]{bitmapFile.getAbsolutePath()}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
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

    }


/*    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.w("onActivityResult", String.valueOf(requestCode) + resultCode);
        Log.w("PICKER_REQUEST_CODE", String.valueOf(PickImageActivity.PICKER_REQUEST_CODE));

        if (resultCode == -1 && requestCode == PickImageActivity.PICKER_REQUEST_CODE) {
            Log.w("this.pathList", String.valueOf(this.pathList));
            Log.w("KEY_DATA_RESULT", PickImageActivity.KEY_DATA_RESULT);
            this.pathList = Objects.requireNonNull(data.getExtras()).getStringArrayList(PickImageActivity.KEY_DATA_RESULT);
            if (this.pathList != null && !this.pathList.isEmpty()) {
                ArrayList<String> uris = new ArrayList<>(pathList);
                dialogg = ProgressDialog.show(AddNewTicketActivity.this, "", "Uploading Image...", true);
                bitmapConvert(uris);
            }
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
                dialogg = ProgressDialog.show(AddNewTicketActivity.this, "", "Uploading Image...", true);
                bitmapConvertToFile(bmp);

            } catch (Exception e) {
                e.printStackTrace();
                dialogg.dismiss();
            }
        }
    }*/
    private void cameraIntent() {
        File file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        Intent install = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri apkURI = FileProvider.getUriForFile(
                AddNewTicketActivity.this,
                AddNewTicketActivity.this.getApplicationContext()
                        .getPackageName() + ".provider", file);
        install.putExtra(MediaStore.EXTRA_OUTPUT, apkURI);

        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        AddNewTicketActivity.this.startActivityForResult(install, 2);

    }



    private void openImagePickerIntent() {

        if (isPermissionGranted()) {
            Intent mIntent = new Intent(this, PickImageActivity.class);
            mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 60);
            mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 3);
            startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE);
        } else {
            requestPermission();
        }

    }

    private void selectImage(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewTicketActivity.this);
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                cameraIntent();

            } else if (items[item].equals("Choose from Gallery")) {
                openImagePickerIntent();

            } else if (items[item].equals("Cancel")) {

                dialog.dismiss();

            } else if (items[item].equals("Remove Image")) {

                issueListArrayList.remove(position);
                issueAdapter.notifyDataSetChanged();


            } else if (items[item].equals("View Image")) {


                alertDialog = new Dialog(AddNewTicketActivity.this, R.style.DialogTheme);
                alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                alertDialog.setContentView(R.layout.full_screen_popup_layout);

                PhotoView photoView = alertDialog.findViewById(R.id.iv_photo);
                Glide.with(AddNewTicketActivity.this)
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


    private void uploadProfileImageRequest(final String path) {
        APIInterface ApiService = RetrofitClient.getImageClient().create(APIInterface.class);
        Call<ImageUploadResponse> call = ApiService.getImageStroeResponse(getProfileImagePicMultipart(path));
        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImageUploadResponse> call, @NonNull retrofit2.Response<ImageUploadResponse> response) {
                dialogg.dismiss();
                Log.w("path",path);
                Log.w(TAG,"ImageUploadResponse"+"--->" + new Gson().toJson(response.body()));
                if (response.body().getData() != null ) {

                    //String fileName = path.substring(path.lastIndexOf("/") + 1);
                    String fileName = response.body().getData();
                    Log.w("fileName", fileName);
                    TicketCreateRequest.ImageListBean imageListBean =  new TicketCreateRequest.ImageListBean();
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
                    final float Image_COL_WIDTH = AddNewTicketActivity.this.getResources().getDisplayMetrics().density * Imagewith;
                    int Image_width = Math.round(Image_COL_WIDTH);


                    LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(Image_width, ViewGroup.LayoutParams.MATCH_PARENT);
                    injuryGridView.setLayoutParams(lpp);


                    issueAdapter = new IssueAdapter(AddNewTicketActivity.this, issueListArrayList);
                    injuryGridView.setAdapter(issueAdapter);

                }
            }

            @Override
            public void onFailure(@NonNull Call<ImageUploadResponse> call, @NonNull Throwable t) {
                dialogg.dismiss();

                Log.w("Profile", "On failure working"+t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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

                    TicketCreateRequest.ImageListBean imageListBean =  new TicketCreateRequest.ImageListBean();
                    imageListBean.setImage_path(fileName);
                    image_list.add(imageListBean);
                    Log.w("fileName", fileName);

/*
                    http://whitehousecbe.in/cmrlcms/assets/uploads/IMG_020200401191325.jpg
*/                   /* String ImageUrl = ApiCall.BASE_URL + "cmrlcms/assets/uploads/" + fileName;
*/

                    String ImageUrl = ApiCall.API_URL + fileName;
                   // String ImageUrl = response.body().getResponse().get(0).getFilepath();
                    Log.w(TAG,"ImageURL-->"+ImageUrl);
                    issueList = new IssueList(ImageUrl);
                    issueListArrayList.add(issueList);

                    int size = issueListArrayList.size();
                    injuryGridView.setNumColumns(size);
                    int Imagewith = size * 100;
                    final float Image_COL_WIDTH = AddNewTicketActivity.this.getResources().getDisplayMetrics().density * Imagewith;
                    int Image_width = Math.round(Image_COL_WIDTH);

                    LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(Image_width, ViewGroup.LayoutParams.MATCH_PARENT);
                    injuryGridView.setLayoutParams(lpp);
                    issueAdapter = new IssueAdapter(AddNewTicketActivity.this, issueListArrayList);
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.clear_button) {
            issueListArrayList.clear();
            int size = issueListArrayList.size();
            injuryGridView.setNumColumns(size);
            int Imagewith = size * 100;
            final float Image_COL_WIDTH = AddNewTicketActivity.this.getResources().getDisplayMetrics().density * Imagewith;
            int Image_width = Math.round(Image_COL_WIDTH);


            LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(Image_width, ViewGroup.LayoutParams.MATCH_PARENT);
            injuryGridView.setLayoutParams(lpp);


            issueAdapter = new IssueAdapter(AddNewTicketActivity.this, issueListArrayList);
            issueAdapter = new IssueAdapter(AddNewTicketActivity.this, issueListArrayList);
            injuryGridView.setAdapter(issueAdapter);
        }
    }


    private boolean isPermissionGranted() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        return result == PackageManager.PERMISSION_GRANTED;

        //If permission is not granted returning false
    }


    //Requesting permission
    private void requestPermission() {

        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE);//If the user has denied the permission previously your code will come to this block
//Here you can explain why you need this permission
//Explain here why you need this permission
//And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, AddNewTicketActivity.READ_STORAGE_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.w("Request code", String.valueOf(requestCode));
        //Checking the request code of our request
        if (requestCode == READ_STORAGE_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                openImagePickerIntent();

            } else {

                AddNewTicketActivity.this.finish();
            }
        } else if (requestCode == WRITE_STORAGE_CODE) {


            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG,""+grantResults.length);

            } else {

                AddNewTicketActivity.this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @SuppressLint("LogNotTimber")
    private void StationNameResponseCall(int type) {
        dialog = new Dialog(AddNewTicketActivity.this, R.style.NewProgressDialog);
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

                FaultTypeListResponseCall(type);
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

                Log.w(TAG, "StationName-->" + StationName);
                StationArrayList.add(StationName);

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddNewTicketActivity.this, R.layout.spinner_item, StationArrayList);
                spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
                spinner_stationname.setAdapter(spinnerArrayAdapter);
            }
        } else {
            ArrayList<String> StationArrayList = new ArrayList<>();
            StationArrayList.add("Select Station Name");
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddNewTicketActivity.this, R.layout.spinner_item, StationArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_stationname.setAdapter(spinnerArrayAdapter);

        }
    }
    private StationNameRequest stationNameRequest(int type) {
        /*
         * type : 1
         */

        StationNameRequest stationNameRequest = new StationNameRequest();
        stationNameRequest.setType(type);
        Log.w(TAG,"stationNameRequest "+ new Gson().toJson(stationNameRequest));
        return stationNameRequest;
    }


    @SuppressLint("LogNotTimber")
    private void JobNoListResponseCall(String stationName_id) {
        dialog = new Dialog(AddNewTicketActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<JobNoListResponse> call = apiInterface.JobNoListResponseCall(RestUtils.getContentType(), jobNoListRequest(stationName_id));
        Log.w(TAG,"JobNoListResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<JobNoListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<JobNoListResponse> call, @NonNull Response<JobNoListResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"JobNoListResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            JobNoList = response.body().getData();
                            setJobNoList(JobNoList);
                        }else{
                            JobNoList.clear();
                            setJobNoList(JobNoList);
                            Toasty.warning(getApplicationContext(),response.body().getMessage(),Toasty.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<JobNoListResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("JobNoListResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setJobNoList(List<JobNoListResponse.DataBean> jobNoList) {
        ArrayList<String> JobNoArrayList = new ArrayList<>();
        JobNoArrayList.add("Select Job No");
        for (int i = 0; i < jobNoList.size(); i++) {

            String JobNo = jobNoList.get(i).getJob_no();
            hashMap_JobNoId.put(jobNoList.get(i).getJob_no(), jobNoList.get(i).get_id());
            hashMap_ServingName.put(jobNoList.get(i).getJob_no(), jobNoList.get(i).getServing_level());

            Log.w(TAG,"JobNo-->"+JobNo);
            JobNoArrayList.add(JobNo);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddNewTicketActivity.this, R.layout.spinner_item, JobNoArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_jobno.setAdapter(spinnerArrayAdapter);




        }
    }
    private JobNoListRequest jobNoListRequest(String stationName_id) {

        /*
         * station_id : 6113c5ed895c673878e17719
         */

        JobNoListRequest jobNoListRequest = new JobNoListRequest();
        jobNoListRequest.setStation_id(stationName_id);
        Log.w(TAG,"jobNoListRequest "+ new Gson().toJson(jobNoListRequest));
        return jobNoListRequest;
    }





    @SuppressLint("LogNotTimber")
    private void FaultTypeListResponseCall(int type) {
        dialog = new Dialog(AddNewTicketActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<FaultTypeListResponse> call = apiInterface.FaultTypeListResponseCall(RestUtils.getContentType(), faultTypeListRequest(type));
        Log.w(TAG,"FaultTypeListResponse url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<FaultTypeListResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<FaultTypeListResponse> call, @NonNull Response<FaultTypeListResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"FaultTypeListResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {

                        if(response.body().getData() != null && response.body().getData().size()>0){
                            FaultTypeList = response.body().getData();
                            setFaultTypeList(FaultTypeList);
                        }else{
                            FaultTypeList.clear();
                            setFaultTypeList(FaultTypeList);
                            Toasty.warning(getApplicationContext(),response.body().getMessage(),Toasty.LENGTH_SHORT).show();
                        }
                    }
                }


            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Call<FaultTypeListResponse> call,@NonNull Throwable t) {
                dialog.dismiss();
                Log.w("FaultTypeListResponse flr", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void setFaultTypeList(List<FaultTypeListResponse.DataBean> faultTypeList) {
        ArrayList<String> FaultTypeArrayList = new ArrayList<>();
        FaultTypeArrayList.add("Select Fault Type");
        for (int i = 0; i < faultTypeList.size(); i++) {

            String FaultTypeName = faultTypeList.get(i).getFault_type();
            hashMap_FaultTypeId.put(faultTypeList.get(i).getFault_type(), faultTypeList.get(i).get_id());

            Log.w(TAG,"FaultTypeName-->"+FaultTypeName);
            FaultTypeArrayList.add(FaultTypeName);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(AddNewTicketActivity.this, R.layout.spinner_item, FaultTypeArrayList);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner_faulttype.setAdapter(spinnerArrayAdapter);




        }
    }
    private FaultTypeListRequest faultTypeListRequest(int type) {
        /*
         * type : 1
         */

        FaultTypeListRequest faultTypeListRequest = new FaultTypeListRequest();
        faultTypeListRequest.setType(type);
        Log.w(TAG,"faultTypeListRequest "+ new Gson().toJson(faultTypeListRequest));
        return faultTypeListRequest;
    }


    @SuppressLint("LogNotTimber")
    private void TicketCreateRequestCall() {
        dialog = new Dialog(AddNewTicketActivity.this, R.style.NewProgressDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progroess_popup);
        dialog.show();

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<SuccessResponse> call = apiInterface.TicketCreateRequestCall(RestUtils.getContentType(), ticketCreateRequest());
        Log.w(TAG,"TicketCreateRequestCall url  :%s"+" "+ call.request().url().toString());

        call.enqueue(new Callback<SuccessResponse>() {
            @SuppressLint("LogNotTimber")
            @Override
            public void onResponse(@NonNull Call<SuccessResponse> call, @NonNull Response<SuccessResponse> response) {
                dialog.dismiss();
                Log.w(TAG,"SuccessResponse" + new Gson().toJson(response.body()));
                if (response.body() != null) {

                    if (200 == response.body().getCode()) {
                        message = response.body().getMessage();
                        new SweetAlertDialog(AddNewTicketActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(getResources().getString(R.string.app_name))
                                .setContentText(message)
                                .setConfirmText("Ok")
                                .setConfirmClickListener(sDialog -> {
                                    sDialog.dismiss();
                                    Intent intent = new Intent(AddNewTicketActivity.this, CmrlLoginDashboardActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.new_right, R.anim.new_left);

                                })
                                .show();


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
    private TicketCreateRequest ticketCreateRequest() {

        /*
         * date_of_create : 23-10-2021 11:00 AM
         * station_id :
         * esc_id :
         * job_id :
         * break_down_time :
         * break_down_reported_by :
         * break_down_observed :
         * type : 2
         * fault_type
         * image_list : [{"image_path":"/upload/float1"},{"image_path":"/upload/float2"},{"image_path":"/upload/float3"},{"image_path":"/upload/float4"}]
         */


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        TicketCreateRequest ticketCreateRequest = new TicketCreateRequest();
        ticketCreateRequest.setDate_of_create(currentDateandTime);
        ticketCreateRequest.setStation_id(StationName_id);
        ticketCreateRequest.setEsc_id("");
        ticketCreateRequest.setJob_id(JobName_id);
        ticketCreateRequest.setBreak_down_time(currentDateandTime);
        ticketCreateRequest.setBreak_down_reported_by(empid);
        ticketCreateRequest.setBreak_down_observed(edt_compliant.getText().toString());
        ticketCreateRequest.setType(String.valueOf(type));
        ticketCreateRequest.setFault_type(FaultName);
        ticketCreateRequest.setImage_list(image_list);
        Log.w(TAG,"ticketCreateRequest "+ new Gson().toJson(ticketCreateRequest));
        return ticketCreateRequest;
    }


    public boolean validdSelectStationName() {
        if(StationName.equalsIgnoreCase("Select Station Name")){
            new SweetAlertDialog(AddNewTicketActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Alert")
                    .setContentText(getString(R.string.err_msg_type_of_stationname))
                    .setConfirmText("Ok")
                    .setConfirmClickListener(Dialog::dismiss)
                    .show();

           /* final AlertDialog alertDialog = new AlertDialog.Builder(AddNewTicketActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_stationname));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();*/

            return false;
        }

        return true;
    }
    public boolean validdSelectJobNo() {
        if(JobName.equalsIgnoreCase("Select Job No")){
            new SweetAlertDialog(AddNewTicketActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Alert")
                    .setContentText(getString(R.string.err_msg_type_of_jobno))
                    .setConfirmText("Ok")
                    .setConfirmClickListener(Dialog::dismiss)
                    .show();
           /* final AlertDialog alertDialog = new AlertDialog.Builder(AddNewTicketActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_jobno));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();*/

            return false;
        }

        return true;
    }
    public boolean validdFaultType() {
        if(FaultName.equalsIgnoreCase("Select Fault Type")){
            new SweetAlertDialog(AddNewTicketActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Alert")
                    .setContentText(getString(R.string.err_msg_type_of_faulttype))
                    .setConfirmText("Ok")
                    .setConfirmClickListener(Dialog::dismiss)
                    .show();
           /* final AlertDialog alertDialog = new AlertDialog.Builder(AddNewTicketActivity.this).create();
            alertDialog.setMessage(getString(R.string.err_msg_type_of_jobno));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                    (dialog, which) -> alertDialog.cancel());
            alertDialog.show();*/

            return false;
        }

        return true;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewTicketActivity.this);
        builder.setTitle("Choose option");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo"))
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddNewTicketActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(AddNewTicketActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
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
                OutputStream os;
                File file = null;
                try {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    file = new File(getFilesDir(), "Petfolio1" + ".jpg");
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

                        String filePath = FileUtil.getPath(AddNewTicketActivity.this,selectedImageUri);

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
        dialog = new Dialog(AddNewTicketActivity.this, R.style.NewProgressDialog);
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
                    TicketCreateRequest.ImageListBean imageListBean =  new TicketCreateRequest.ImageListBean();
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
                    final float Image_COL_WIDTH = AddNewTicketActivity.this.getResources().getDisplayMetrics().density * Imagewith;
                    int Image_width = Math.round(Image_COL_WIDTH);


                    LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(Image_width, ViewGroup.LayoutParams.MATCH_PARENT);
                    injuryGridView.setLayoutParams(lpp);


                    issueAdapter = new IssueAdapter(AddNewTicketActivity.this, issueListArrayList);
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
