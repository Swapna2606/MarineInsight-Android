package com.marineinsight.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class WebFragment extends Fragment {
    String baseURL;
    String catName;
    WebView webView;
    View rootView;

    //   private static final String TAG = JSONHelper.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseURL = getArguments().getString("catURL");
        catName = getArguments().getString("catNAME");
       // System.out.println("BASEURL:"+baseURL);
        if (isNetworkAvailable()) {
         rootView = inflater.inflate(R.layout.fragment_webivew, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(catName);
        webView = (WebView) rootView.findViewById(R.id.webView1);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());


        webView.loadUrl(baseURL);
        }
        else {
            rootView = inflater.inflate(R.layout.no_network, container, false);
            //    System.out.println("NETWORK NOT AVAILABLE");
            Button mButton = (Button) rootView.findViewById(R.id.ViewSavedStories);
            mButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent myIntent = new Intent(getActivity().getApplicationContext(),
                            OfflineActivity.class);
                    startActivity(myIntent);
                }
            });
        }
        return rootView;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(baseURL);
            return true;
        }
        ProgressDialog progressDialog;
        public void onLoadResource (WebView view, String url) {
            if (progressDialog == null) {
                // in standard case YourActivity.this
                progressDialog = new ProgressDialog(getView().getContext());
                progressDialog.setMessage("Loading...Please Wait");
                progressDialog.show();
            }
        }
        public void onPageFinished(WebView view, String url) {
            try{
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();

                }
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }



}
