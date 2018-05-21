package com.example.android.navdrawerapp.Model;

/**
 * Created by katherine on 5/15/18.
 */

public class users {
    private String regNo, password, IsStaff;

    public users(String regNo, String password){
        regNo = regNo;
        password = password;
        IsStaff = "false";
    }

    public users(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }
}
