package com.newmark.item;

import java.util.Date;

/**
 * Created by qiudong on 15/2/2.
 */
public class Book {
    private String id;
    private String name;
    private String pagenum;
    private String bookdesc;
    private String user;
    private Date date;
    private String bookface;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPagenum() {
        return pagenum;
    }

    public void setPagenum(String pagenum) {
        this.pagenum = pagenum;
    }

    public String getBookdesc() {
        return bookdesc;
    }

    public void setBookdesc(String bookdesc) {
        this.bookdesc = bookdesc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBookface() {
        return bookface;
    }

    public void setBookface(String bookface) {
        this.bookface = bookface;
    }
}
