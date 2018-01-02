package com.marineinsight.android;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 22-08-2016.
 */
public class DetailActivity extends AppCompatActivity {
    private TaskDBHelper helper;
    private ShareActionProvider mShareActionProvider;
    String strTitle = null;
    String strURL = null;
    String strContent = null;
    String strImgURL = null;
    String iframeURL = null;
    String saveTitle;
    Spanned deTitle;
    float m_downX;
    Pattern p;
    WebView desc;
    Matcher m2;
    Matcher m;
    String contToDisplay;
    String value;
    WebSettings ws;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment);
        MobileAds.initialize(getApplicationContext(), String.valueOf(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        strTitle = getIntent().getExtras().getString("title");
        deTitle = Html.fromHtml(Html.fromHtml((String) strTitle).toString());
        saveTitle = String.valueOf(deTitle);
        strURL = getIntent().getExtras().getString("url");
        strContent = getIntent().getExtras().getString("content");
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final String s = strContent;
        final String regex = "(?<=<iframe src=\")[^\"]*";
        final String regex2 = "<\\s*iframe[^>]*><\\s*/\\s*iframe>";
        p = Pattern.compile(regex);
        final Pattern p2 = Pattern.compile(regex2);
        m = p.matcher(s);
        m2 = p2.matcher(s);
        evaluate(s,strContent);
        final TextView title = (TextView) findViewById(R.id.title);
        desc = (WebView) findViewById(R.id.desc);

        desc.setHorizontalScrollBarEnabled(false);
        desc.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;

                }

                return false;
            }
        });

        desc.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg)
            {
                WebView newWebView = new WebView(getApplicationContext());
                view.addView(newWebView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();

                newWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                        return true;
                    }

                });
                return true;
            }
        });
        title.setText(deTitle);

        desc.loadDataWithBaseURL("file:///android_asset/", getHtmlData(value), "text/html", "utf-8", null);
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img,iframe{max-width: 100%; width:auto; height: auto;}</style></head>";
        contToDisplay = "<html>" + head + "<body>" + bodyHTML + "</body></html>";

        return contToDisplay;
    }

    String evaluate(String token, String defaultValue){
        value=null;
        Matcher matcher=p.matcher(token);
        if (matcher.find()) {
            iframeURL=matcher.group();
            String newURL = "<a href=\"" + iframeURL + "\">Watch the Video Here</a>";
            value = m2.replaceAll(newURL);
        }
        if (value == null) {
            value=defaultValue;
        }
        return value != null ? value : "";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:{
                this.finish();
                return true;
            }
            case R.id.bookmark: {
                saveOffline();
                return true;
            }

            case R.id.menu_share:
                shareContent();
                break;

            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void shareContent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, strTitle + "\n" + strURL);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share using"));
    }

    private void saveOffline(){
        final byte[][] byteImage1 = {null};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add For Offline Reading?");
        builder.setMessage(saveTitle);
        // final EditText inputField = new EditText(getActivity());
        // builder.setView(inputField);
  // builder.setPositiveButton("Add",null);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //String task = inputField.getText().toString();
                String task = saveTitle;
                String content = strContent;
                String postURL = strURL;

                helper = new TaskDBHelper(getApplicationContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.clear();
                values.put(TaskContract.Columns.TITLE, task);
                values.put(TaskContract.Columns.CONTENT, content);
                values.put(TaskContract.Columns.URL, postURL);


                db.insertWithOnConflict(TaskContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                System.out.println("DB Updated");
               updateDB();
            }
        });

        builder.setNegativeButton("No", null);
        builder.create().show();
    }
    public void updateDB() {

        //System.out.println("DB Updated");
        Intent intent = new Intent(getApplicationContext(),OfflineActivity.class);
        //System.out.println("Reached 2");
        startActivity(intent);
       System.out.println("Reached 3");
    }

}
