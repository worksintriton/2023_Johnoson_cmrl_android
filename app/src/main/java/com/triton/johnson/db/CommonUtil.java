package com.triton.johnson.db;

import android.content.Context;
import android.content.SharedPreferences;

public class CommonUtil {

    public static final String MyPREFERENCES = "Preference";
    public static SharedPreferences pref;
    public static Context context;
    public static DbUtil dbUtil;
    public static DbHelper dbHelper;


    public CommonUtil(Context context) {
        CommonUtil.context = context;
        pref = CommonUtil.context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
}
