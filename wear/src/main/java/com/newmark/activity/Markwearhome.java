package com.newmark.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.view.GridViewPager;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.newmark.adapter.MarkGirdPagerAdapter;
import com.newmark.adapter.MarkPage;
import com.newmark.adapter.MarkRow;
import com.newmark.item.Book;
import com.newmark.tools.Config;
import com.newmark.tools.HttpUtil;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class Markwearhome extends Activity{
    private static final String EXTRA_VOICE_REPLY = "extra_voice_reply";
    //private MarkListAdapter markdapter;
    private MarkGirdPagerAdapter markGirdPagerAdapter;

    private WearableListView listView;
    private GridViewPager mGridPager;

    private void loadBookData() {
        HttpUtil.get(Config.actionSelfbooklist, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(Markwearhome.this, R.string.mark_neterror, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseString == null) {
                    Toast.makeText(Markwearhome.this, R.string.mark_dataerror, Toast.LENGTH_SHORT).show();
                }
                responseString = responseString.replace("\\n", "").replace(" ", "");
                List<Book> books = JSONArray.parseArray(responseString, Book.class);
                //markdapter = new MarkListAdapter(markwearhome.this, books);
                //listView.setAdapter(markdapter);
                ArrayList<MarkRow> bookpage=new ArrayList<MarkRow>();

                for (Book b : books) {
                    MarkRow row = new MarkRow();
                    row.addPages(new MarkPage(b.getId(),b.getName(),b.getPagenum(), b.getBookdesc(), b.getBookface(),R.drawable.marklogoicon));
                    bookpage.add(row);
                }

                markGirdPagerAdapter=new MarkGirdPagerAdapter(Markwearhome.this, getFragmentManager(),bookpage);
                mGridPager.setAdapter(markGirdPagerAdapter);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_markwearhome);

            Config.initImageLoader(Markwearhome.this);

            // Get the list component from the layout of the activity
            final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

            stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
                @Override
                public void onLayoutInflated(WatchViewStub stub) {
                    //listView = (WearableListView)stub.findViewById(R.id.wearable_list);
                    mGridPager = (GridViewPager)stub.findViewById(R.id.mark_pager);
                    //listView.setClickListener(markwearhome.this);
                    loadBookData();

                }
            });
        }


        // This callback is invoked when the Speech Recognizer returns.
        // This is where you process the intent and extract the speech text from the intent.
        @Override
        protected void onActivityResult(int requestCode, int resultCode,
                                        Intent data) {
            if (requestCode == Config.SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
                List<String> results = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                String user=data.getStringExtra("user");
                String name=data.getStringExtra("name");
                String bookface=data.getStringExtra("bookface");
                String pagenum=results.get(0);
                String bookdesc=data.getStringExtra("bookdesc");

                RequestParams params=new RequestParams();
                params.put("user",user);
                params.put("name",name);
                params.put("bookface",bookface);
                params.put("pagenum",pagenum);
                params.put("bookdesc","读至"+pagenum+"页");
                HttpUtil.post(Config.actionAddMark,params,new TextHttpResponseHandler(){
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(Markwearhome.this, responseString, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Toast.makeText(Markwearhome.this, R.string.mark_success, Toast.LENGTH_SHORT).show();
                        loadBookData();
                    }
                });
            }
            super.onActivityResult(requestCode, resultCode, data);
        }




//        // WearableListView click listener
//        @Override
//        public void onClick(WearableListView.ViewHolder v) {
//            //Integer tag = (Integer) v.itemView.getTag();
//            //displaySpeechRecognizer();
//        }
//
//        @Override
//        public void onTopEmptyRegionClick() {
//        }
//
//    @Override
//    public void onTimerFinished(View view) {
//
//    }
//
//    @Override
//    public void onTimerSelected(View view) {
//
//    }


//    public class MarkListAdapter extends WearableListView.Adapter {
//            private List<Book> books;
//            private final Context mContext;
//            private final LayoutInflater mInflater;
//
//            // Provide a suitable constructor (depends on the kind of dataset)
//            public MarkListAdapter(Context context, List<Book> books) {
//                mContext = context;
//                mInflater = LayoutInflater.from(context);
//                this.books = books;
//            }
//
//            // Provide a reference to the type of views you're using
//            public class ItemViewHolder extends WearableListView.ViewHolder {
//                private TextView btitleView;
//                private TextView bdescView;
//                private TextView bpagenum;
//                private ImageView imageView;
//                public ItemViewHolder(View itemView) {
//                    super(itemView);
//                    // find the text view within the custom item's layout
//                    btitleView = (TextView) itemView.findViewById(R.id.btitle);
//                    bdescView = (TextView) itemView.findViewById(R.id.bdesc);
//                    bpagenum = (TextView) itemView.findViewById(R.id.bpagenum);
//                    imageView = (ImageView) itemView.findViewById(R.id.circle);
//                }
//            }
//
//            // Create new views for list items
//            // (invoked by the WearableListView's layout manager)
//            @Override
//            public WearableListView.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                                  int viewType) {
//                // Inflate our custom layout for list items
//                return new ItemViewHolder(mInflater.inflate(R.layout.list_item, null));
//            }
//
//            // Replace the contents of a list item
//            // Instead of creating new views, the list tries to recycle existing ones
//            // (invoked by the WearableListView's layout manager)
//            @Override
//            public void onBindViewHolder(WearableListView.ViewHolder holder,
//                                         int position) {
//                // retrieve the text view
//                ItemViewHolder itemHolder = (ItemViewHolder) holder;
//                TextView btitleView = itemHolder.btitleView;
//                TextView bdescView = itemHolder.bdescView;
//                TextView bpagenumView = itemHolder.bpagenum;
//                // replace text contents
//                Book book = books.get(position);
//                btitleView.setText(book.getName());
//                bdescView.setText(book.getBookdesc());
//                bpagenumView.setText(book.getPagenum());
//                Log.e("bookface",book.getBookface()+"");
//
//                ImageLoader.getInstance().displayImage(book.getBookface(),
//                        itemHolder.imageView, Constant.DISPLAYIMAGEOPTIONS, null);
//                // replace list item's metadata
//                holder.itemView.setTag(position);
//            }
//            // Return the size of your dataset
//            // (invoked by the WearableListView's layout manager)
//            @Override
//            public int getItemCount() {
//                return books.size();
//            }
//        }


}


