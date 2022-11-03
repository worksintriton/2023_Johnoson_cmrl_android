package com.triton.johnson.requestpojo;

import java.util.List;

public class TicketCreateRequest {

    /**
     * date_of_create : 23-10-2021 11:00 AM
     * station_id :
     * esc_id :
     * job_id :
     * break_down_time :
     * break_down_reported_by :
     * break_down_observed :
     * type : 2
     * fault_type
     * image_list : [{"image_path":"/upload/float1"},{"image_path":"/upload/float2"},{"image_path":"/upload/float3"},{"image_path":"/upload/float4"}]
     */

    private String date_of_create;
    private String station_id;
    private String esc_id;
    private String job_id;
    private String break_down_time;
    private String break_down_reported_by;
    private String break_down_observed;
    private String type;
    private String fault_type;

    public String getFault_type() {
        return fault_type;
    }

    public void setFault_type(String fault_type) {
        this.fault_type = fault_type;
    }

    /**
     * image_path : /upload/float1
     */

    private List<ImageListBean> image_list;

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

    public String getBreak_down_time() {
        return break_down_time;
    }

    public void setBreak_down_time(String break_down_time) {
        this.break_down_time = break_down_time;
    }

    public String getBreak_down_reported_by() {
        return break_down_reported_by;
    }

    public void setBreak_down_reported_by(String break_down_reported_by) {
        this.break_down_reported_by = break_down_reported_by;
    }

    public String getBreak_down_observed() {
        return break_down_observed;
    }

    public void setBreak_down_observed(String break_down_observed) {
        this.break_down_observed = break_down_observed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ImageListBean> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ImageListBean> image_list) {
        this.image_list = image_list;
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
