package com.triton.johnson.requestpojo;

public class LoginRequest {

    /**
     * user_phone : 9876543211
     * user_type : 2
     * password : 12345
     */

    private String user_phone;
    private int user_type;
    private String password;

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
