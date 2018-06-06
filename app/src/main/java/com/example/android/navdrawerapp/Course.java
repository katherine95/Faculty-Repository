package com.example.android.navdrawerapp;

/**
 * Created by katherine on 5/30/18.
 */

public class Course {
    private String courseUrl;

    public Course(){}

    public Course(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public void setCourseUrl(String courseUrl) {
        this.courseUrl = courseUrl;
    }

    public String getCourseUrl() {
        return courseUrl;
    }
}
