package com.triton.johnson.responsepojo;

public class FBTokenUpdateResponse {


    /**
     * Status : Success
     * Message : FB Updated
     * Data : {"_id":"5fc61b82b750da703e48da78","first_name":"Dinesh","last_name":"Deva","user_email":"dinesh@gmail.com","user_phone":"6383791451","date_of_reg":"01/12/2020 04:01 PM","user_type":1,"user_status":"complete","otp":364231,"fb_token":"d9ump8AOR-OIqISPxFh65V:APA91bGRhCKML1_62HzcMCqqDp1ku2kR4qrEKnNSkE2F3N19bKtZ6TB2weM3f-dkhSOn6DNCxeCG6ja2ueROH3GUKRY43U7ANKI_PPzFRQGt5iE9vZ2pIXnb6YEx8-cms4AOn6YHlZe_","device_id":"","device_type":"","mobile_type":"Android","__v":0}
     * Code : 200
     */

    private String Status;
    private String Message;
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

    public static class DataBean  {
        /**
         * _id : 5fc61b82b750da703e48da78
         * first_name : Dinesh
         * last_name : Deva
         * user_email : dinesh@gmail.com
         * user_phone : 6383791451
         * date_of_reg : 01/12/2020 04:01 PM
         * user_type : 1
         * user_status : complete
         * otp : 364231
         * fb_token : d9ump8AOR-OIqISPxFh65V:APA91bGRhCKML1_62HzcMCqqDp1ku2kR4qrEKnNSkE2F3N19bKtZ6TB2weM3f-dkhSOn6DNCxeCG6ja2ueROH3GUKRY43U7ANKI_PPzFRQGt5iE9vZ2pIXnb6YEx8-cms4AOn6YHlZe_
         * device_id :
         * device_type :
         * mobile_type : Android
         * __v : 0
         * profile_img
         */

        private String _id;
        private String first_name;
        private String last_name;
        private String user_email;
        private String user_phone;
        private String date_of_reg;
        private int user_type;
        private String user_status;
        private int otp;
        private String fb_token;
        private String device_id;
        private String device_type;
        private String mobile_type;
        private String profile_img;
        private int __v;
        private boolean user_email_verification;
        private String ref_code;
        private String my_ref_code;

        public String getMy_ref_code() {
            return my_ref_code;
        }

        public void setMy_ref_code(String my_ref_code) {
            this.my_ref_code = my_ref_code;
        }

        public String getRef_code() {
            return ref_code;
        }

        public void setRef_code(String ref_code) {
            this.ref_code = ref_code;
        }

        public boolean isUser_email_verification() {
            return user_email_verification;
        }

        public void setUser_email_verification(boolean user_email_verification) {
            this.user_email_verification = user_email_verification;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;

        }


        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;

        }


        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;

        }


        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;

        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;

        }


        public String getDate_of_reg() {
            return date_of_reg;
        }

        public void setDate_of_reg(String date_of_reg) {
            this.date_of_reg = date_of_reg;

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


        public int getOtp() {
            return otp;
        }

        public void setOtp(int otp) {
            this.otp = otp;

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


        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}
