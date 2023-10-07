package com.exmaple2.hello_android.data;

public class BookName {
    public int getCoverResourceId() {
        return bookid;
    }

    private final int bookid;
    public String getTitle() {
        return name;
    }

    private final String name;
    public BookName(String name_, int bookID) {
        this.name=name_;
        this.bookid =bookID;
    }
}
