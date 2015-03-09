package com.newmark.tools;

import android.graphics.Bitmap;

import com.newmark.activity.Markwearhome;
import com.newmark.activity.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by qiudong on 15/2/2.
 */
public class Config {
    public static final int SPEECH_REQUEST_CODE =0 ;
    public static String actionSelfbooklist="http://192.168.1.126:3000/books/guwu/selfbooklist";
    public static String actionAddMark="http://192.168.1.126:3000/books";
    //public static String actionselfbooklist="http://192.168.1.119:3000/books/guwu/selfbooklist";
    public final static DisplayImageOptions DISPLAYIMAGEOPTIONS = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.markbookbacebefault)
            .showImageForEmptyUri(R.drawable.markbookbacebefault)
            .showImageOnFail(R.drawable.markbookbacebefault).cacheInMemory(false)
            .cacheOnDisk(false).considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565).build();


    public static void initImageLoader(Markwearhome markwearhome) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                markwearhome).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(200 * 1024 * 1024)
                        // 200 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                        // .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }
}

