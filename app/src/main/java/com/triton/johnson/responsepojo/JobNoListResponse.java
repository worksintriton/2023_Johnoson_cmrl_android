package com.triton.johnson.responsepojo;

import java.util.List;

public class JobNoListResponse {


    /**
     * Status : Success
     * Message : job no list by station id
     * Data : [{"_id":"6114ed578be7730c8d709b3a","station_id":"6113c5ed895c673878e17719","job_no":"Job No 001","serving_level":"Servering - UP (UP to DOWN)","delete_status":false,"updatedAt":"2021-08-12T09:43:51.023Z","createdAt":"2021-08-12T09:43:51.023Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 6114ed578be7730c8d709b3a
     * station_id : 6113c5ed895c673878e17719
     * job_no : Job No 001
     * serving_level : Servering - UP (UP to DOWN)
     * delete_status : false
     * updatedAt : 2021-08-12T09:43:51.023Z
     * createdAt : 2021-08-12T09:43:51.023Z
     * __v : 0
     */

    private List<DataBean> Data;

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

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String _id;
        private String station_id;
        private String job_no;
        private String serving_level;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getStation_id() {
            return station_id;
        }

        public void setStation_id(String station_id) {
            this.station_id = station_id;
        }

        public String getJob_no() {
            return job_no;
        }

        public void setJob_no(String job_no) {
            this.job_no = job_no;
        }

        public String getServing_level() {
            return serving_level;
        }

        public void setServing_level(String serving_level) {
            this.serving_level = serving_level;
        }

        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
