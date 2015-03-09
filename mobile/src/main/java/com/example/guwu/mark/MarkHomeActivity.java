package com.example.guwu.mark;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


public class MarkHomeActivity extends Activity {

    private WebView markview;

    /* Widgets you are going to use */
    //private Button button;
    /*
     * This is the notification id
     * You can use it to dismiss the notification calling the .cancel() method on the notification_manager object
     */
    private int notification_id = 1;
    private final String NOTIFICATION_ID = "notification_id";
    private RemoteInput remoteInput;
    // Key for the string that's delivered in the action's intent
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    /* These are the classes you use to start the notification */
    private NotificationCompat.Builder notification_builder;
    private NotificationManagerCompat notification_manager;

    //Web视图
    private class MarkWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }



    public class WebAppInterface {
        Context mContext;
        PackageManager packageManager;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
            packageManager = c.getPackageManager();
        }

        /** Show a toast from the web page */
        @TargetApi(Build.VERSION_CODES.CUPCAKE)
        @JavascriptInterface
        public void showToast() {
            try {
                Intent intent =packageManager.getLaunchIntentForPackage("com.duokan.reader");
                startActivity(intent);
            } catch (Exception e) {
              Toast.makeText(getApplicationContext(), "未找到多看阅读程序!", Toast.LENGTH_SHORT).show();
            }
        }
        /** Show a toast from the web page */
        @JavascriptInterface
        public void showNotification(String user,String bookname,String pagenum,String bookdesc) {
            try {
                creatNodifition(MarkHomeActivity.this,user,bookname,pagenum,bookdesc);
                notification_manager.notify(notification_id, notification_builder.build());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "系统错误!", Toast.LENGTH_SHORT).show();
            }
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showVoiceinput() {
            try {
                Toast.makeText(getApplicationContext(),"dd", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "系统错误!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markhome);
        markview = (WebView) findViewById(R.id.webview);
        //设置WebView属性，能够执行Javascript脚本
        markview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
        markview.loadUrl("http://192.168.1.107:3000/login");
        //设置Web视图
        markview.setWebViewClient(new MarkWebViewClient ());
        markview.addJavascriptInterface(new WebAppInterface(this), "Android");

    }

    private void creatNodifition(MarkHomeActivity markHomeActivity,String username,String bookname,String pagenum,String bookdesc) {
        /*
         * Step 1
         * Instantiation of the button you use to start the notification
         */
        //button = (Button) findViewById(R.id.notification_button);
        /*
         * Step 2
         * Create the intent you are going to launch when your notification is pressed
         * and let the PendingIntent handle it
         */
        //Intent open_activity_intent = new Intent(this, ActivatedActivity.class);
        //open_activity_intent.putExtra(NOTIFICATION_ID, notification_id);
        //PendingIntent pending_intent = PendingIntent.getActivity(this, 0, open_activity_intent, PendingIntent.FLAG_CANCEL_CURRENT);




        /* The action in the handheld notification must perform some task

        Intent open_mysite = new Intent(Intent.ACTION_VIEW);
        Uri mylocation = Uri.parse("http://www.guwudesigner.sinaapp.com/mysite.html");
        open_mysite.setData(mylocation);
        PendingIntent site_intent = PendingIntent.getActivity(this, 0, open_mysite, 0);
        */

        // Create an intent for the reply action
        Intent replyIntent = new Intent(this, ReplyActivity.class);
        replyIntent.putExtra(NOTIFICATION_ID, notification_id);
        PendingIntent replyPendingIntent =
                PendingIntent.getActivity(this, 0, replyIntent,
                        0);

         /* Here we instantiate the Intent we want to use when the action in the smartwatch is pressed */
        String mark_label = getResources().getString(R.string.mark_label);
        String[] replyChoices = getResources().getStringArray(R.array.reply_choices);
        RemoteInput remoteInput = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(mark_label)
                .build();


        // Create the reply action and add the remote input
        NotificationCompat.Action markaction =
                new NotificationCompat.Action.Builder(R.drawable.ic_action_edit,
                        getString(R.string.wearable_action), replyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

        /* Here we instantiate the Intent we want to use when the action in the smartwatch is pressed */
        // Intent wearable_intent = new Intent(this, ActivatedActivity.class);
        //PendingIntent wearable_pending_intent = PendingIntent.getActivity(this, 0, wearable_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /* Now we have an intent for the wearable created we have to create a wearable action using it*/
        // NotificationCompat.Action wearable_action = new NotificationCompat.Action.Builder(
        //        R.drawable.ic_action_edit,
        //        getString(R.string.wearable_action),
        //        wearable_pending_intent).build();

        Resources res=getResources();

        Bitmap mkbgbmp= BitmapFactory.decodeResource(res, R.drawable.mkbg);
        // Create a WearableExtender to add functionality for wearables
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setBackground(mkbgbmp);
        /*
         * Step 3
         * Here you create the notification and start adding all the attributes you are going to use
         */
        notification_builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.marklogo)
                .setContentTitle(username+"在读《"+bookname+"》")
                .setContentText("第"+pagenum+"页:"+bookdesc)
          /*
           * This method specifies that our notification must have all the default characteristics of a notification
           * like sound and vibration
           */
                        //.setDefaults(Notification.DEFAULT_ALL)
          /* This method is going to dismiss the notification once it is pressed */
                        //.setAutoCancel(true)
                .setContentIntent(replyPendingIntent)
          /*
             * Here you can add actions to your handheld device
             * just take care of the quantity of actions you add
             * in this case, like in many others, less is more
             */
                //.addAction(android.R.drawable.ic_dialog_info, getString(R.string.first_action), site_intent)
                .extend(wearableExtender.addAction(markaction));



        /*
         * Step 4
         * Here we instantiate the Notification Manager object to start/stop the notifications
         */
        notification_manager = NotificationManagerCompat.from(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

    /*
     * Step 5
     * The notification is going to appear when you press the button on the screen

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification_manager.notify(notification_id, notification_builder.build());
            }
        });
         */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.markhome, menu);
        return true;
    }

    @Override
    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && markview.canGoBack()) {
            markview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
