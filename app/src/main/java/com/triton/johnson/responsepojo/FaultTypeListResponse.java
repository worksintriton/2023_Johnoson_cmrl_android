package com.triton.johnson.responsepojo;

import java.util.List;

public class FaultTypeListResponse {

    /**
     * Status : Success
     * Message : Fault type list
     * Data : [{"_id":"6113c9d300780341de29a7a8","fault_type":"Lift not working","type":"1","delete_status":false,"updatedAt":"2021-08-11T13:00:03.219Z","createdAt":"2021-08-11T13:00:03.219Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 6113c9d300780341de29a7a8
     * fault_type : Lift not working
     * type : 1
     * delete_status : false
     * updatedAt : 2021-08-11T13:00:03.219Z
     * createdAt : 2021-08-11T13:00:03.219Z
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
        private String fault_type;
        private String type;
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

        public String getFault_type() {
            return fault_type;
        }

        public void setFault_type(String fault_type) {
            this.fault_type = fault_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
