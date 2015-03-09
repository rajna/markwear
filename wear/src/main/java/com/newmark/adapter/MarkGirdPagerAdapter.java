package com.newmark.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import com.newmark.tools.Config;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by guwu on 15/2/9.
 */
public class MarkGirdPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private ArrayList<MarkRow> mPages;

    public MarkGirdPagerAdapter(Context context, FragmentManager fm,ArrayList<MarkRow> page) {
        super(fm);
        mContext = context;
        this.mPages=page;
    }

    @Override
    public Fragment getFragment(int row, int col) {
        final int frow=row;
        final int fcol=col;
        MarkPage page = ((MarkRow)mPages.get(row)).getPages(col);
        CardFragment fragment =new MarkCardFragment(page,mContext);

        return fragment;
    }


    @Override
    public Drawable getBackgroundForPage(int row,int column){
        MarkPage page = ((MarkRow)mPages.get(row)).getPages(column);
        Bitmap bm=ImageLoader.getInstance().loadImageSync(page.getBookface(), Config.DISPLAYIMAGEOPTIONS);
        Drawable drawable = new BitmapDrawable(mContext.getResources(), bm);
        return drawable;
    }

    @Override
    public int getRowCount() {
        return mPages.size();
    }

    @Override
    public int getColumnCount(int row) {
        return mPages.get(row).size();
    }
}