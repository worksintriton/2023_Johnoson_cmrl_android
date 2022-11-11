package com.triton.johnson.responsepojo;

import java.util.List;

public class GetPartListResponse {

    private String Status;
    private String Message;
    private List<Datum> Data = null;
    private Integer Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Datum> getData() {
        return Data;
    }

    public void setData(List<Datum> data) {
        Data = data;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public class Datum {

        private String _id;
        private String part_type;
        private String part_no;
        private String part_name;
        private Boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private Integer __v;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getPart_type() {
            return part_type;
        }

        public void setPart_type(String part_type) {
            this.part_type = part_type;
        }

        public String getPart_no() {
            return part_no;
        }

        public void setPart_no(String part_no) {
            this.part_no = part_no;
        }

        public String getPart_name() {
            return part_name;
        }

        public void setPart_name(String part_name) {
            this.part_name = part_name;
        }

        public Boolean getDelete_status() {
            return delete_status;
        }

        public void setDelete_status(Boolean delete_status) {
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

        public Integer get__v() {
            return __v;
        }

        public void set__v(Integer __v) {
            this.__v = __v;
        }
    }
}
