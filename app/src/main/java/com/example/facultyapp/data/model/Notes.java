package com.example.facultyapp.data.model;

public class Notes {
    public String bookName;
    public String bookUrl;
    public String bookYear;
    public String bookSemester;

    public Notes() {
    }

    public Notes(String bookName, String bookUrl, String bookYear, String bookSemester) {
        this.bookName = bookName;
        this.bookUrl = bookUrl;
        this.bookYear = bookYear;
        this.bookSemester = bookSemester;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public String getBookYear() {
        return bookYear;
    }

    public void setBookYear(String bookYear) {
        this.bookYear = bookYear;
    }

    public String getBookSemester() {
        return bookSemester;
    }

    public void setBookSemester(String bookSemester) {
        this.bookSemester = bookSemester;
    }
}
