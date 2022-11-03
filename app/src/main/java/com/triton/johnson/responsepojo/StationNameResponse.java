package com.triton.johnson.responsepojo;

import java.util.List;

public class StationNameResponse {

    /**
     * Status : Success
     * Message : Station name List
     * Data : [{"_id":"6113c5ed895c673878e17719","station_name":"Station - 1","delete_status":false,"updatedAt":"2021-08-11T13:03:07.841Z","createdAt":"2021-08-11T12:43:25.271Z","__v":0,"type":"1"}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 6113c5ed895c673878e17719
     * station_name : Station - 1
     * delete_status : false
     * updatedAt : 2021-08-11T13:03:07.841Z
     * createdAt : 2021-08-11T12:43:25.271Z
     * __v : 0
     * type : 1
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
        private String station_name;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private String type;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getStation_name() {
            return station_name;
        }

        public void setStation_name(String station_name) {
            this.station_name = station_name;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
