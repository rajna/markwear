package com.newmark.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.CardFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newmark.activity.R;
import com.newmark.tools.Config;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by guwu on 15/2/13.
 */
public class MarkCardFragment extends CardFragment {
    ImageView imageView;
    TextView  btitleView,bdescView,bpagenum;
    MarkPage bookpage;
    private final Context mContext;

    private static final int SPEECH_REQUEST_CODE = 0;

    public MarkCardFragment(MarkPage b,Context mContext){
        super();
        this.mContext=mContext;
        this.bookpage=b;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer(MarkPage bookpage) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra("user","guwu");
        intent.putExtra("name",bookpage.getName());
        intent.putExtra("bookface",bookpage.getBookface());
        intent.putExtra("pagenum",bookpage.getPagenum());
        intent.putExtra("bookdesc",bookpage.getBookdesc());
        // Start the activity, the intent will be populated with the speech text
        Activity parentActivity= getActivity();
        parentActivity.startActivityForResult(intent, SPEECH_REQUEST_CODE);

    }

    @Override
    protected android.view.View onCreateContentView(final LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookpage, container, false);
        btitleView= (TextView)view.findViewById(R.id.btitle);
        btitleView.setText(bookpage.getName());
        bdescView= (TextView)view.findViewById(R.id.bdesc);
        bdescView.setText(bookpage.getBookdesc());
        bpagenum= (TextView)view.findViewById(R.id.bpagenum);
        bpagenum.setText(bookpage.getPagenum());

        imageView=(ImageView)view.findViewById(R.id.circle);
        ImageLoader.getInstance().displayImage(bookpage.getBookface(),
                imageView, Config.DISPLAYIMAGEOPTIONS, null);
        ImageView imageBtnedit = (ImageView) view.findViewById(R.id.btn_edit);
        imageBtnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displaySpeechRecognizer(bookpage);
            }
        });
        return view;
    }

}