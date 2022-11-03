package com.triton.johnson.responsepojo;

import java.util.List;

public class CMRLTicketListResponse {


    /**
     * Status : Success
     * Message : Ticket List
     * Data : [{"image_list":[{"image_path":"/uploads/1628837575366.jpg"}],"_id":"611616cafa37e36c0d7c4f2b","date_of_create":"13-08-2021 12:22 PM","station_id":"611511214f912e1856fc6d46","station_detail":{"_id":"611511214f912e1856fc6d46","station_name":"ARUMBAKKAM","type":"1","delete_status":false,"updatedAt":"2021-08-12T12:16:33.935Z","createdAt":"2021-08-12T12:16:33.935Z","__v":0},"esc_id":"0","job_id":"61151dbaac7f9e21e2963133","job_detail":{"_id":"61151dbaac7f9e21e2963133","station_id":"611511214f912e1856fc6d46","job_no":"MOHM","serving_level":"MOHAMMD","delete_status":false,"updatedAt":"2021-08-12T13:10:18.395Z","createdAt":"2021-08-12T13:10:18.395Z","__v":0},"break_down_time":"13-08-2021 12:22 PM","restored_time":"","down_time":"","break_down_reported_by":"6113acf26ee293224d81081c","break_down_reported_by_first":"","break_down_observed":"Test fault","action_taken":"","action_taken_by":"","verified_by":"","status":"Open","type":"1","delete_status":false,"fault_type":"Lift not working","updatedAt":"2021-08-13T06:52:58.958Z","createdAt":"2021-08-13T06:52:58.958Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * image_list : [{"image_path":"/uploads/1628837575366.jpg"}]
     * _id : 611616cafa37e36c0d7c4f2b
     * ticket_no
     * date_of_create : 13-08-2021 12:22 PM
     * station_id : 611511214f912e1856fc6d46
     * station_detail : {"_id":"611511214f912e1856fc6d46","station_name":"ARUMBAKKAM","type":"1","delete_status":false,"updatedAt":"2021-08-12T12:16:33.935Z","createdAt":"2021-08-12T12:16:33.935Z","__v":0}
     * esc_id : 0
     * job_id : 61151dbaac7f9e21e2963133
     * job_detail : {"_id":"61151dbaac7f9e21e2963133","station_id":"611511214f912e1856fc6d46","job_no":"MOHM","serving_level":"MOHAMMD","delete_status":false,"updatedAt":"2021-08-12T13:10:18.395Z","createdAt":"2021-08-12T13:10:18.395Z","__v":0}
     * break_down_time : 13-08-2021 12:22 PM
     * restored_time :
     * down_time :
     * break_down_reported_by : 6113acf26ee293224d81081c
     * break_down_reported_by_first :
     * break_down_observed : Test fault
     * action_taken :
     * action_taken_by :
     * verified_by :
     * status : Open
     * type : 1
     * delete_status : false
     * fault_type : Lift not working
     * updatedAt : 2021-08-13T06:52:58.958Z
     * createdAt : 2021-08-13T06:52:58.958Z
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
        private String date_of_create;
        private String ticket_no;

        public String getTicket_no() {
            return ticket_no;
        }

        public void setTicket_no(String ticket_no) {
            this.ticket_no = ticket_no;
        }

        private String station_id;
        /**
         * _id : 611511214f912e1856fc6d46
         * station_name : ARUMBAKKAM
         * type : 1
         * delete_status : false
         * updatedAt : 2021-08-12T12:16:33.935Z
         * createdAt : 2021-08-12T12:16:33.935Z
         * __v : 0
         */

        private StationDetailBean station_detail;
        private String esc_id;
        private String job_id;
        /**
         * _id : 61151dbaac7f9e21e2963133
         * station_id : 611511214f912e1856fc6d46
         * job_no : MOHM
         * serving_level : MOHAMMD
         * delete_status : false
         * updatedAt : 2021-08-12T13:10:18.395Z
         * createdAt : 2021-08-12T13:10:18.395Z
         * __v : 0
         */

        private JobDetailBean job_detail;
        private String break_down_time;
        private String restored_time;
        private String down_time;
        private String break_down_reported_by;
        private String break_down_reported_by_first;
        private String break_down_observed;
        private String action_taken;
        private String action_taken_by;
        private String verified_by;
        private String status;
        private String type;
        private boolean delete_status;
        private String fault_type;
        private String updatedAt;
        private String createdAt;
        private int __v;
        /**
         * image_path : /uploads/1628837575366.jpg
         */

        private List<ImageListBean> image_list;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDate_of_create() {
            return date_of_create;
        }

        public void setDate_of_create(String date_of_create) {
            this.date_of_create = date_of_create;
        }

        public String getStation_id() {
            return station_id;
        }

        public void setStation_id(String station_id) {
            this.station_id = station_id;
        }

        public StationDetailBean getStation_detail() {
            return station_detail;
        }

        public void setStation_detail(StationDetailBean station_detail) {
            this.station_detail = station_detail;
        }

        public String getEsc_id() {
            return esc_id;
        }

        public void setEsc_id(String esc_id) {
            this.esc_id = esc_id;
        }

        public String getJob_id() {
            return job_id;
        }

        public void setJob_id(String job_id) {
            this.job_id = job_id;
        }

        public JobDetailBean getJob_detail() {
            return job_detail;
        }

        public void setJob_detail(JobDetailBean job_detail) {
            this.job_detail = job_detail;
        }

        public String getBreak_down_time() {
            return break_down_time;
        }

        public void setBreak_down_time(String break_down_time) {
            this.break_down_time = break_down_time;
        }

        public String getRestored_time() {
            return restored_time;
        }

        public void setRestored_time(String restored_time) {
            this.restored_time = restored_time;
        }

        public String getDown_time() {
            return down_time;
        }

        public void setDown_time(String down_time) {
            this.down_time = down_time;
        }

        public String getBreak_down_reported_by() {
            return break_down_reported_by;
        }

        public void setBreak_down_reported_by(String break_down_reported_by) {
            this.break_down_reported_by = break_down_reported_by;
        }

        public String getBreak_down_reported_by_first() {
            return break_down_reported_by_first;
        }

        public void setBreak_down_reported_by_first(String break_down_reported_by_first) {
            this.break_down_reported_by_first = break_down_reported_by_first;
        }

        public String getBreak_down_observed() {
            return break_down_observed;
        }

        public void setBreak_down_observed(String break_down_observed) {
            this.break_down_observed = break_down_observed;
        }

        public String getAction_taken() {
            return action_taken;
        }

        public void setAction_taken(String action_taken) {
            this.action_taken = action_taken;
        }

        public String getAction_taken_by() {
            return action_taken_by;
        }

        public void setAction_taken_by(String action_taken_by) {
            this.action_taken_by = action_taken_by;
        }

        public String getVerified_by() {
            return verified_by;
        }

        public void setVerified_by(String verified_by) {
            this.verified_by = verified_by;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public String getFault_type() {
            return fault_type;
        }

        public void setFault_type(String fault_type) {
            this.fault_type = fault_type;
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

        public List<ImageListBean> getImage_list() {
            return image_list;
        }

        public void setImage_list(List<ImageListBean> image_list) {
            this.image_list = image_list;
        }

        public static class StationDetailBean {
            private String _id;
            private String station_name;
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

            public String getStation_name() {
                return station_name;
            }

            public void setStation_name(String station_name) {
                this.station_name = station_name;
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

        public static class JobDetailBean {
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

        public static class ImageListBean {
            private String image_path;

            public String getImage_path() {
                return image_path;
            }

            public void setImage_path(String image_path) {
                this.image_path = image_path;
            }
        }
    }
}
