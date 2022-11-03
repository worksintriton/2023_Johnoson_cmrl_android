package com.triton.johnson.responsepojo;

public class CheckAttendanceResponse {



    /**
     * Status : Success
     * Message : check attendance
     * Data : {"check_in_time":"","check_in_datetime":"","check_out_time":"","check_out_datetime":"","ischecked":false}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * check_in_time :
     * check_in_datetime :
     * check_out_time :
     * check_out_datetime :
     * ischecked : false
     */

    private DataBean Data;
    private int Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public static class DataBean {
        private String check_in_time;
        private String check_in_datetime;
        private String check_out_time;
        private String check_out_datetime;
        private boolean ischecked;

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

        public boolean isIschecked() {
            return ischecked;
        }

        public void setIschecked(boolean ischecked) {
            this.ischecked = ischecked;
        }
    }
}