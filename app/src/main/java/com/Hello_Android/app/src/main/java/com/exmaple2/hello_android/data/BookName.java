package com.exmaple2.hello_android.data;

import java.io.Serializable;

public class BookName implements Serializable {

    public int getCoverResourceId() {
        return bookid;
    }

    private final int bookid;
    public String getTitle() {
        return name;
    }

    private String name;
    public BookName(String name_, int bookID) {
        this.name=name_;
        this.bookid =bookID;
    }
    public void setName(String name) {
        this.name = name;
    }
}
