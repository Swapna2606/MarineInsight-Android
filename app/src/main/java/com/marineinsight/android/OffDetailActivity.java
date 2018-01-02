package com.marineinsight.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 22-08-2016.
 */
public class OffDetailActivity extends AppCompatActivity {
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
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.off_detail_fragment);
//        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        strTitle = getIntent().getExtras().getString("title");
        deTitle = Html.fromHtml(Html.fromHtml((String) strTitle).toString());
        saveTitle = String.valueOf(deTitle);
        strURL = getIntent().getExtras().getString("url");
        strContent = getIntent().getExtras().getString("content");
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
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
        inflater.inflate(R.menu.off_fragment_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:{
                this.finish();
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


}
