package com.triton.johnson.model;

/**
 * Created by Iddinesh.
 */

public class ImageUploadResponse {

    /**
     * Status : Success
     * Message : file upload success
     * Data : /uploads/1628744735171.png
     * BaseUrl : http://54.212.108.156:3000/api
     * Code : 200
     */

    private String Status;
    private String Message;
    private String Data;
    private String BaseUrl;
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

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getBaseUrl() {
        return BaseUrl;
    }

    public void setBaseUrl(String BaseUrl) {
        this.BaseUrl = BaseUrl;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }
}
