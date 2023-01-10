package com.triton.johnson.responsepojo;

import java.util.ArrayList;
import java.util.List;

public class ViewTicketsResponse  {


    /**
     * Status : Success
     * Message : Ticket History List
     * Data : [{"ticket_photo":[{"image_path":"/uploads/1628851697801.jpg"}],"_id":"61164df7566dc616584f37b8","ticket_no":"TIC-1","ticket_status":"Open","ticket_comments":"Test fault","user_id":{"_id":"611631f929d5bf7e07c79930","username":"Dinesh","user_email":"dinesh@gmail.com","password":"12345","user_email_verification":false,"user_phone":"9876543210","employee_id":0,"date_of_reg":"Fri Aug 13 2021 08:48:57 GMT+0000 (Coordinated Universal Time)","profile_img":"","user_type":1,"user_status":"Incomplete","fb_token":"","device_id":"","device_type":"","mobile_type":"","delete_status":false,"updatedAt":"2021-08-13T08:48:57.124Z","createdAt":"2021-08-13T08:48:57.124Z","__v":0},"date_of_create":"13-08-2021 04:18 PM","delete_status":false,"updatedAt":"2021-08-13T10:48:23.987Z","createdAt":"2021-08-13T10:48:23.987Z","__v":0},{"ticket_photo":[{"image_path":"/uploads/1628853542500.jpg"}],"_id":"6116552dfc7a3b16f473a9c5","ticket_no":"TIC-1","ticket_status":"Inprogress","ticket_comments":"Tesr","user_id":{"_id":"6116331629d5bf7e07c79931","username":"Sri ram","user_email":"sri@gmail.com","password":"12345","user_email_verification":false,"user_phone":"9876543211","employee_id":0,"date_of_reg":"Fri Aug 13 2021 08:53:42 GMT+0000 (Coordinated Universal Time)","profile_img":"","user_type":2,"user_status":"Incomplete","fb_token":"","device_id":"","device_type":"","mobile_type":"","delete_status":false,"updatedAt":"2021-08-13T08:53:42.294Z","createdAt":"2021-08-13T08:53:42.294Z","__v":0},"date_of_create":"13-08-2021 04:49 PM","delete_status":false,"updatedAt":"2021-08-13T11:19:09.946Z","createdAt":"2021-08-13T11:19:09.946Z","__v":0},{"ticket_photo":[{"image_path":"/uploads/1628853769737.jpg"}],"_id":"6116560c43285e19cf7ecac3","ticket_no":"TIC-1","ticket_status":"Inprogress","ticket_comments":"Tesr","user_id":{"_id":"6116331629d5bf7e07c79931","username":"Sri ram","user_email":"sri@gmail.com","password":"12345","user_email_verification":false,"user_phone":"9876543211","employee_id":0,"date_of_reg":"Fri Aug 13 2021 08:53:42 GMT+0000 (Coordinated Universal Time)","profile_img":"","user_type":2,"user_status":"Incomplete","fb_token":"","device_id":"","device_type":"","mobile_type":"","delete_status":false,"updatedAt":"2021-08-13T08:53:42.294Z","createdAt":"2021-08-13T08:53:42.294Z","__v":0},"date_of_create":"13-08-2021 04:52 PM","delete_status":false,"updatedAt":"2021-08-13T11:22:52.867Z","createdAt":"2021-08-13T11:22:52.867Z","__v":0},{"ticket_photo":[],"_id":"6116562543285e19cf7ecac4","ticket_no":"TIC-1","ticket_status":"Pending","ticket_comments":"Test pending","user_id":{"_id":"6116331629d5bf7e07c79931","username":"Sri ram","user_email":"sri@gmail.com","password":"12345","user_email_verification":false,"user_phone":"9876543211","employee_id":0,"date_of_reg":"Fri Aug 13 2021 08:53:42 GMT+0000 (Coordinated Universal Time)","profile_img":"","user_type":2,"user_status":"Incomplete","fb_token":"","device_id":"","device_type":"","mobile_type":"","delete_status":false,"updatedAt":"2021-08-13T08:53:42.294Z","createdAt":"2021-08-13T08:53:42.294Z","__v":0},"date_of_create":"13-08-2021 04:53 PM","delete_status":false,"updatedAt":"2021-08-13T11:23:17.543Z","createdAt":"2021-08-13T11:23:17.543Z","__v":0},{"ticket_photo":[],"_id":"6116563643285e19cf7ecac5","ticket_no":"TIC-1","ticket_status":"Completed","ticket_comments":"Completed","user_id":{"_id":"6116331629d5bf7e07c79931","username":"Sri ram","user_email":"sri@gmail.com","password":"12345","user_email_verification":false,"user_phone":"9876543211","employee_id":0,"date_of_reg":"Fri Aug 13 2021 08:53:42 GMT+0000 (Coordinated Universal Time)","profile_img":"","user_type":2,"user_status":"Incomplete","fb_token":"","device_id":"","device_type":"","mobile_type":"","delete_status":false,"updatedAt":"2021-08-13T08:53:42.294Z","createdAt":"2021-08-13T08:53:42.294Z","__v":0},"date_of_create":"13-08-2021 04:53 PM","delete_status":false,"updatedAt":"2021-08-13T11:23:34.119Z","createdAt":"2021-08-13T11:23:34.119Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * ticket_photo : [{"image_path":"/uploads/1628851697801.jpg"}]
     * _id : 61164df7566dc616584f37b8
     * ticket_no : TIC-1
     * ticket_status : Open
     * ticket_comments : Test fault
     * user_id : {"_id":"611631f929d5bf7e07c79930","username":"Dinesh","user_email":"dinesh@gmail.com","password":"12345","user_email_verification":false,"user_phone":"9876543210","employee_id":0,"date_of_reg":"Fri Aug 13 2021 08:48:57 GMT+0000 (Coordinated Universal Time)","profile_img":"","user_type":1,"user_status":"Incomplete","fb_token":"","device_id":"","device_type":"","mobile_type":"","delete_status":false,"updatedAt":"2021-08-13T08:48:57.124Z","createdAt":"2021-08-13T08:48:57.124Z","__v":0}
     * date_of_create : 13-08-2021 04:18 PM
     * delete_status : false
     * updatedAt : 2021-08-13T10:48:23.987Z
     * createdAt : 2021-08-13T10:48:23.987Z
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

    public void setData(ArrayList<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean  {
        private String _id;
        private String ticket_no;
        private String ticket_status;
        private String ticket_comments;
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

        private UserIdBean user_id;
        private String date_of_create;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;
        private String part_no_req;
        /**
         * image_path : /uploads/1628851697801.jpg
         */

        private List<TicketPhotoBean> ticket_photo;

        private List<PartDet> part_det;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTicket_no() {
            return ticket_no;
        }

        public void setTicket_no(String ticket_no) {
            this.ticket_no = ticket_no;
        }

        public String getTicket_status() {
            return ticket_status;
        }

        public void setTicket_status(String ticket_status) {
            this.ticket_status = ticket_status;
        }

        public String getTicket_comments() {
            return ticket_comments;
        }

        public void setTicket_comments(String ticket_comments) {
            this.ticket_comments = ticket_comments;
        }

        public UserIdBean getUser_id() {
            return user_id;
        }

        public void setUser_id(UserIdBean user_id) {
            this.user_id = user_id;
        }

        public String getDate_of_create() {
            return date_of_create;
        }

        public void setDate_of_create(String date_of_create) {
            this.date_of_create = date_of_create;
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

        public List<TicketPhotoBean> getTicket_photo() {
            return ticket_photo;
        }

        public void setTicket_photo(List<TicketPhotoBean> ticket_photo) {
            this.ticket_photo = ticket_photo;
        }

        public List<PartDet> getPart_det() {
            return part_det;
        }

        public void setPart_det(List<PartDet> part_det) {
            this.part_det = part_det;
        }

        public String getPart_no_req() {
            return part_no_req;
        }

        public void setPart_no_req(String part_no_req) {
            this.part_no_req = part_no_req;
        }

        public static class UserIdBean {
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

        public static class TicketPhotoBean {
            private String image_path;

            public String getImage_path() {
                return image_path;
            }

            public void setImage_path(String image_path) {
                this.image_path = image_path;
            }
        }

        public static class PartDet {

            private  String part_name;
            private  String part_no;
            private  String part_type;

            public String getPart_name() {
                return part_name;
            }

            public void setPart_name(String part_name) {
                this.part_name = part_name;
            }

            public String getPart_no() {
                return part_no;
            }

            public void setPart_no(String part_no) {
                this.part_no = part_no;
            }

            public String getPart_type() {
                return part_type;
            }

            public void setPart_type(String part_type) {
                this.part_type = part_type;
            }
        }
    }
}
