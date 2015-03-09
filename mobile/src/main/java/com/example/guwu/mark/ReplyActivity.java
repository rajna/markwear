package com.example.guwu.mark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ReplyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Intent intent=getIntent();
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        String reply="";
        if (remoteInput != null) {
            reply= remoteInput.getCharSequence(MarkHomeActivity.EXTRA_VOICE_REPLY).toString();
        }

        TextView text=(TextView)findViewById(R.id.text);
        text.setText(reply);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reply, menu);
        return true;
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
