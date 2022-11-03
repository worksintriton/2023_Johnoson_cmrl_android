package com.triton.johnson.requestpojo;

public class AttendanceCreateRequest {

    /**
     * user_id : 60ae2c0c48ffef65a41bc546
     * date : 22-10-2021
     * check_in_time : 08:00 AM
     * check_in_datetime : 22-10-2021 08:00 AM
     * check_out_time : 09:00 AM
     * check_out_datetime : 22-10-2021 09:00 AM
     */

    private String user_id;
    private String date;
    private String check_in_time;
    private String check_in_datetime;
    private String check_out_time;
    private String check_out_datetime;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheck_in_time() {
        return check_in_time;
    }

    public void setCheck_in_time(String check_in_time) {
        this.check_in_time = check_in_time;
    }

    public String getCheck_in_datetime() {
        return check_in_datetime;
    }

    public void setCheck_in_datetime(String check_in_datetime) {
        this.check_in_datetime = check_in_datetime;
    }

    public String getCheck_out_time() {
        return check_out_time;
    }

    public void setCheck_out_time(String check_out_time) {
        this.check_out_time = check_out_time;
    }

    public String getCheck_out_datetime() {
        return check_out_datetime;
    }

    public void setCheck_out_datetime(String check_out_datetime) {
        this.check_out_datetime = check_out_datetime;
    }
}
