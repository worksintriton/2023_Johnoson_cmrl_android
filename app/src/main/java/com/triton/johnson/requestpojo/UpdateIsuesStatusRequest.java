package com.triton.johnson.requestpojo;

import java.util.List;

public class UpdateIsuesStatusRequest {

    /**
     * ticket_no : 1
     * ticket_status : 611511214f912e1856fc6d46
     * ticket_comments : 61151dbaac7f9e21e2963133
     * ticket_photo : [{"image_path":"/uploads/1628847534691.jpg"}]
     * user_id :
     * date_of_create :
     */

    private String ticket_no;
    private String ticket_status;
    private String ticket_comments;
    private String user_id;
    private String date_of_create;
    /**
     * image_path : /uploads/1628847534691.jpg
     */

    private List<TicketPhotoBean> ticket_photo;

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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_of_create() {
        return date_of_create;
    }

    public void setDate_of_create(String date_of_create) {
        this.date_of_create = date_of_create;
    }

    public List<TicketPhotoBean> getTicket_photo() {
        return ticket_photo;
    }

    public void setTicket_photo(List<TicketPhotoBean> ticket_photo) {
        this.ticket_photo = ticket_photo;
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
}
