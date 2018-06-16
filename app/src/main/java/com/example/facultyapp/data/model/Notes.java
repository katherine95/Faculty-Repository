package com.example.facultyapp.data.model;

public class Notes {
    public String BookName;
    public String BookUrl;
    public String BookYear;
    public String BookSemester;

    public Notes() {
    }

    public Notes(String bookName, String bookUrl, String bookYear, String bookSemester) {
        BookName = bookName;
        BookUrl = bookUrl;
        BookYear = bookYear;
        BookSemester = bookSemester;
    }

    public String getBookName() {
        return BookName;
    }

    public String getBookUrl() {
        return BookUrl;
    }

    public String getBookYear() {
        return BookYear;
    }

    public String getBookSemester() {
        return BookSemester;
    }
}
