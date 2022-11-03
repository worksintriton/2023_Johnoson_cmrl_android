package com.triton.johnson.requestpojo;

public class JohnsonTicketListRequest {

    /**
     * type : 1
     * station_id : 611511214f912e1856fc6d46
     * job_id : 61151dbaac7f9e21e2963133
     * status : "Open
     */

    private String type;
    private String station_id;
    private String job_id;
    private String status;


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
}
