package com.triton.johnson.responsepojo;

import java.util.List;

public class ServingLevelListResponse {

    /**
     * Status : Success
     * Message : serving level list by job id
     * Data : [{"_id":"6113c7a470b8063b34f558f0","station_id":"6113c5ed895c673878e17719","job_id":"6113c657895c673878e1771a","serving_level_name":"Serving 001","delete_status":false,"updatedAt":"2021-08-11T12:50:44.488Z","createdAt":"2021-08-11T12:50:44.488Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 6113c7a470b8063b34f558f0
     * station_id : 6113c5ed895c673878e17719
     * job_id : 6113c657895c673878e1771a
     * serving_level_name : Serving 001
     * delete_status : false
     * updatedAt : 2021-08-11T12:50:44.488Z
     * createdAt : 2021-08-11T12:50:44.488Z
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
        private String job_id;
        private String serving_level_name;
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

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public String getServing_level_name() {
            return serving_level_name;
        }

        public void setServing_level_name(String serving_level_name) {
            this.serving_level_name = serving_level_name;
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
