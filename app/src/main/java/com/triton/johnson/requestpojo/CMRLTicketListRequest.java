package com.triton.johnson.requestpojo;

public class CMRLTicketListRequest {

    /**
     * type : 1
     * station_id : 611510c34f912e1856fc6d44
     * break_down_reported_by : 6113acf26ee293224d81081c
     * status
     * job_id
     */

    private String type;
    private String station_id;
    private String break_down_reported_by;
    private String status;
    private String job_id;

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
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

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getBreak_down_reported_by() {
        return break_down_reported_by;
    }

    public void setBreak_down_reported_by(String break_down_reported_by) {
        this.break_down_reported_by = break_down_reported_by;
    }
}
