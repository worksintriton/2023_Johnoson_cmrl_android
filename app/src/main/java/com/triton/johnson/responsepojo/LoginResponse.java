package com.triton.johnson.responsepojo;

public class LoginResponse {

    /**
     * Status : Success
     * Message : Logged in successfully
     * Data : {"_id":"611631f929d5bf7e07c79930","username":"Dinesh","user_email":"dinesh@gmail.com","password":"12345","user_email_verification":false,"user_phone":"9876543210","employee_id":0,"date_of_reg":"Fri Aug 13 2021 08:48:57 GMT+0000 (Coordinated Universal Time)","profile_img":"","user_type":1,"user_status":"Incomplete","fb_token":"","device_id":"","device_type":"","mobile_type":"","delete_status":false,"updatedAt":"2021-08-13T08:48:57.124Z","createdAt":"2021-08-13T08:48:57.124Z","__v":0}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * _id : 611631f929d5bf7e07c79930
     * username : Dinesh
     * user_email : dinesh@gmail.com
     * password : 12345
     * user_email_verification : false
     * user_phone : 9876543210
     * employee_id : 0
     * date_of_reg : Fri Aug 13 2021 08:48:57 GMT+0000 (Coordinated Universal Time)
     * profile_img :
     * user_type : 1
     * user_status : Incomplete
     * fb_token :
     * device_id :
     * device_type :
     * mobile_type :
     * delete_status : false
     * updatedAt : 2021-08-13T08:48:57.124Z
     * createdAt : 2021-08-13T08:48:57.124Z
     * __v : 0
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
        private String _id;
        private String username;
        private String user_email;
        private String password;
        private boolean user_email_verification;
        private String user_phone;
        private int employee_id;
        private String date_of_reg;
        private String profile_img;
        private int user_type;
        private String user_status;
        private String fb_token;
        private String device_id;
        private String device_type;
        private String mobile_type;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public boolean isUser_email_verification() {
            return user_email_verification;
        }

        public void setUser_email_verification(boolean user_email_verification) {
            this.user_email_verification = user_email_verification;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public int getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(int employee_id) {
            this.employee_id = employee_id;
        }

        public String getDate_of_reg() {
            return date_of_reg;
        }

        public void setDate_of_reg(String date_of_reg) {
            this.date_of_reg = date_of_reg;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public int getUser_type() {
            return user_type;
        }

        public void setUser_type(int user_type) {
            this.user_type = user_type;
        }

        public String getUser_status() {
            return user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getFb_token() {
            return fb_token;
        }

        public void setFb_token(String fb_token) {
            this.fb_token = fb_token;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getMobile_type() {
            return mobile_type;
        }

        public void setMobile_type(String mobile_type) {
            this.mobile_type = mobile_type;
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
