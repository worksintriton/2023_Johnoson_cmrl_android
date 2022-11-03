package com.triton.johnson.requestpojo;

public class NotificationSendRequest {

    /**
     * ticket_no : 1
     * notify_title :
     * notify_descri :
     * date_and_time :
     */

    private String ticket_no;
    private String notify_title;
    private String notify_descri;
    private String date_and_time;

    public String getTicket_no() {
        return ticket_no;
    }

    public void setTicket_no(String ticket_no) {
        this.ticket_no = ticket_no;
    }

    public String getNotify_title() {
        return notify_title;
    }

    public void setNotify_title(String notify_title) {
        this.notify_title = notify_title;
    }

    public String getNotify_descri() {
        return notify_descri;
    }

    public void setNotify_descri(String notify_descri) {
        this.notify_descri = notify_descri;
    }

    public String getDate_and_time() {
        return date_and_time;
    }

    public void setDate_and_time(String date_and_time) {
        this.date_and_time = date_and_time;
    }
}
