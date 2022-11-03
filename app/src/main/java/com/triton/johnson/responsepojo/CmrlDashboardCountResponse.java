package com.triton.johnson.responsepojo;

public class CmrlDashboardCountResponse {

    /**
     * Status : Success
     * Message : Ticket List
     * Data : {"open_count":0,"inprogress_count":0,"pending_count":0,"completed_count":0,"close_count":2}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * open_count : 0
     * inprogress_count : 0
     * pending_count : 0
     * completed_count : 0
     * close_count : 2
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
        private int open_count;
        private int inprogress_count;
        private int pending_count;
        private int completed_count;
        private int close_count;

        public int getOpen_count() {
            return open_count;
        }

        public void setOpen_count(int open_count) {
            this.open_count = open_count;
        }

        public int getInprogress_count() {
            return inprogress_count;
        }

        public void setInprogress_count(int inprogress_count) {
            this.inprogress_count = inprogress_count;
        }

        public int getPending_count() {
            return pending_count;
        }

        public void setPending_count(int pending_count) {
            this.pending_count = pending_count;
        }

        public int getCompleted_count() {
            return completed_count;
        }

        public void setCompleted_count(int completed_count) {
            this.completed_count = completed_count;
        }

        public int getClose_count() {
            return close_count;
        }

        public void setClose_count(int close_count) {
            this.close_count = close_count;
        }
    }
}
