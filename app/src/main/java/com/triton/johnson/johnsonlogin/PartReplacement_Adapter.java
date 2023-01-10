package com.triton.johnson.johnsonlogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.triton.johnson.R;
import com.triton.johnson.db.CommonUtil;
import com.triton.johnson.db.DbHelper;
import com.triton.johnson.db.DbUtil;
import com.triton.johnson.responsepojo.GetPartListResponse;
import com.triton.johnson.view.UpdateStatusActivity;

import java.util.List;

public class PartReplacement_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  String TAG = "PartReplacementAdapter",jobid;
    private Context context;
    GetPartListResponse.Datum currentItem;
    SharedPreferences sharedPreferences;
    private List<GetPartListResponse.Datum> breedTypedataBeanList;

    public PartReplacement_Adapter(UpdateStatusActivity context, List<GetPartListResponse.Datum> databeanlist) {

        this.context = context;
        this.breedTypedataBeanList = databeanlist;

        CommonUtil.dbUtil = new DbUtil(context);
        CommonUtil.dbUtil.open();
        CommonUtil.dbHelper = new DbHelper(context);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        jobid = sharedPreferences.getString("jobid", "default value");

        Log.e("JobID",""+jobid);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_partreplacement, parent, false);
        return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        initLayoutOne((ViewHolderOne) holder, position);
    }

    private void initLayoutOne(ViewHolderOne holder, int position) {

        currentItem = breedTypedataBeanList.get(position);

        Log.e("Part Name",""+currentItem.getPart_name());

        holder.txt_Partname.setText(currentItem.getPart_name());

        holder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String str_Partname = "";
                String str_Partno = "";
                String str_Parttype = "";
                String str_Partid ="";
                str_Partname = str_Partname + "\n" + breedTypedataBeanList.get(position).getPart_name();
                str_Partno = str_Partno + "\n" + breedTypedataBeanList.get(position).getPart_no();
                str_Parttype = str_Parttype + "\n" + breedTypedataBeanList.get(position).getPart_type();
                str_Partid = str_Partid + "\n" + breedTypedataBeanList.get(position).get_id();
                Log.e("Type", str_Parttype);
                Log.e("Name", str_Partname);
                Log.e("Number", str_Partno);
                Log.e("ID", str_Partid);

                if (CommonUtil.dbUtil.hasPart(jobid,str_Partid)){
                    Log.e("Nish","inside");
                    Log.e("Hi Nish","Had Data");
                    CommonUtil.dbUtil.deletePart(jobid,str_Partid);
                    Cursor cur = CommonUtil.dbUtil.getPart(jobid);
                    Log.e("COunt",""+cur.getCount());
                }else{
                    CommonUtil.dbUtil.addPart(str_Partid,str_Partname,str_Partno,str_Parttype,jobid);
                    Cursor cur = CommonUtil.dbUtil.getPart(jobid);
                    Log.e("COunt",""+cur.getCount());
                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return breedTypedataBeanList.size();
    }

    public void filterrList(List<GetPartListResponse.Datum> filterlist) {


        breedTypedataBeanList = filterlist;
        notifyDataSetChanged();
    }

    private class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView txt_Partname,txt_Partnumber,txt_Parttype;
        public CheckBox chkSelected;

        public ViewHolderOne(View view) {

            super(view);

            chkSelected = (CheckBox) view.findViewById(R.id.chkSelected);
            txt_Partname = view.findViewById(R.id.txt_partname);
            txt_Partnumber = view.findViewById(R.id.txt_partno);
            txt_Parttype = view.findViewById(R.id.txt_Parttype);

        }
    }
}
