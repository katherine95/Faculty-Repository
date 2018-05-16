package com.example.android.navdrawerapp.Model;

/**
 * Created by katherine on 5/15/18.
 */

public class users {
    private String regNo;
    private String username;
    private String password;

    public users(String regNo, String password, String username){
        regNo = regNo;
        username = username;
        password = password;
    }

    public String getpassword() {
        return password;
    }
}
