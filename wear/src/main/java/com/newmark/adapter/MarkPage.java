package com.newmark.adapter;

import java.util.Date;

/**
 * Created by guwu on 15/2/9.
 */
public class MarkPage {

    private String id;
    private String name;
    private String pagenum;
    private String bookdesc;
    private String user;
    private Date date;
    private String bookface;
    public int mIconId;

    public MarkPage(String id, String name, String pagenum, String bookdesc,String bookface, int mIconId) {
        this.id = id;
        this.name = name;
        this.pagenum = pagenum;
        this.bookdesc = bookdesc;
        this.user = user;
        this.bookface = bookface;
        this.mIconId = mIconId;
    }

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

    public int getmIconId() {
        return mIconId;
    }

    public void setmIconId(int mIconId) {
        this.mIconId = mIconId;
    }
}