package com.newmark.adapter;

import java.util.ArrayList;

/**
 * Created by guwu on 15/2/9.
 */
public class MarkRow {

    ArrayList<MarkPage> mPagesRow = new ArrayList<MarkPage>();

    public void addPages(MarkPage page) {
        mPagesRow.add(page);
    }

    public MarkPage getPages(int index) {
        return mPagesRow.get(index);
    }

    public int size(){
        return mPagesRow.size();
    }
}