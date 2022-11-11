package com.triton.johnson.johnsonlogin;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.triton.johnson.R;
import com.triton.johnson.api.APIInterface;
import com.triton.johnson.api.RetrofitClient;
import com.triton.johnson.db.CommonUtil;
import com.triton.johnson.db.DbHelper;
import com.triton.johnson.db.DbUtil;
import com.triton.johnson.requestpojo.GetPartListRequest;
import com.triton.johnson.responsepojo.GetPartListResponse;
import com.triton.johnson.utils.RestUtils;
import com.triton.johnson.view.UpdateStatusActivity;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PartReplacement_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btn_Ok;
    Context context;
    String type,Status_Name;
    TextView txt_NoRecords;
    List<GetPartListResponse.Datum> databeanlist;
    PartReplacement_Adapter partReplacement_Adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partreplacement);
        context = this;

        CommonUtil.dbUtil = new DbUtil(context);
        CommonUtil.dbUtil.open();
        CommonUtil.dbHelper = new DbHelper(context);

        recyclerView = findViewById(R.id.recycler_view);
        btn_Ok = findViewById(R.id.btn_ok);
        txt_NoRecords = findViewById(R.id.txt_no_records);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        type = String.valueOf(Integer.parseInt(sharedPreferences.getString("type", "default value")));
        Log.e("Nish ",""+type);
        Log.e("Nish ",""+ String.valueOf(type));
//        Status_Name = sharedPreferences.getString("status","name");
//        Log.e("Nish",""+Status_Name);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Status_Name = extras.getString("status");
            Log.e("Nish",""+Status_Name);
        }

        getPartListCall();

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
    }

    private void getPartListCall() {

        APIInterface apiInterface = RetrofitClient.getClient().create(APIInterface.class);
        Call<GetPartListResponse> call = apiInterface.PartListCall(RestUtils.getContentType(), partlistRequest());
        Log.w(TAG, "Get Part List url  :%s" + " " + call.request().url().toString());

        call.enqueue(new Callback<GetPartListResponse>() {
            @Override
            public void onResponse(Call<GetPartListResponse> call, Response<GetPartListResponse> response) {
                Log.e(TAG, "Get Part List Response" + new Gson().toJson(response.body()));

                if (response.body() != null){

                    if (response.body().getCode() == 200){


                        if (response.body().getData() != null) {
                            databeanlist = response.body().getData();

                            if (databeanlist.size() == 0){

                                recyclerView.setVisibility(View.GONE);
                                txt_NoRecords.setVisibility(View.VISIBLE);
                                txt_NoRecords.setText("No Records Found");

                            }

                         //   setView(databeanlist);
                            Log.d("dataaaaa", String.valueOf(databeanlist));

                        } else if (400 == response.body().getCode()) {
                            if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("There is already a user registered with this email id. Please add new email id")) {
                                recyclerView.setVisibility(View.GONE);
                                txt_NoRecords.setVisibility(View.VISIBLE);
                                txt_NoRecords.setText("Error 404 Found..!");
                            }
                        }else {
                            recyclerView.setVisibility(View.GONE);
                            txt_NoRecords.setVisibility(View.VISIBLE);
                            txt_NoRecords.setText("Error 404 Found..!");
                            Toasty.warning(getApplicationContext(), "" + response.body().getMessage(), Toasty.LENGTH_LONG).show();
                        }
                    }


                }
                else{

                    recyclerView.setVisibility(View.GONE);
                    txt_NoRecords.setVisibility(View.VISIBLE);
                    txt_NoRecords.setText("Something went wrong..! Try agin");

                }

            }

            @Override
            public void onFailure(Call<GetPartListResponse> call, Throwable t) {
                Log.e("Jobno Find ", "--->" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                recyclerView.setVisibility(View.GONE);
                txt_NoRecords.setVisibility(View.VISIBLE);
                txt_NoRecords.setText("Something went wrong..! Try agin");
            }
        });

    }

    private void setView(List<GetPartListResponse.Datum> databeanlist) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        partReplacement_Adapter = new PartReplacement_Adapter(this, databeanlist);
        recyclerView.setAdapter(partReplacement_Adapter);
    }

    private GetPartListRequest partlistRequest() {

        GetPartListRequest partRequest = new GetPartListRequest();
        partRequest.setPart_type(type);
        Log.w(TAG, "Jobno Find Request " + new Gson().toJson(partRequest));
        return partRequest;
    }

    @Override
    public void onBackPressed() {

    //    super.onBackPressed();
        Intent intent = new Intent(context, UpdateStatusActivity.class);
        intent.putExtra("status",Status_Name);
        context.startActivity(intent);
    }
}
