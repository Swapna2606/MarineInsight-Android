package com.marineinsight.android;

/**
 * Created by User on 16-08-2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


/**
 * Demonstrates the use of {@link RecyclerView} with a {@link LinearLayoutManager} and a
 * {@link GridLayoutManager}.
 */

public class NewsFragment extends Fragment {
    RecyclerView rv;
    List<PostValue> postList;
    String baseURL = "https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=shipping-news&count=10";
    String json;
    JSONObject mJsonObject;
    SwipeRefreshLayout swipeRefreshLayout;
    View rootView;

    private static final String TAG = JSONHelper.class.getSimpleName();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.news_layout, container, false);
    //      System.out.println("NETWORK AVAILABLE");

            MobileAds.initialize(getContext(), String.valueOf(R.string.banner_ad_unit_id));
            AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
            rv.setHasFixedSize(true);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            rv.setLayoutManager(llm);

            rv.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new JSONAsync().execute(baseURL);
            }
        });
            rv.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv, new RecyclerClickListener() {
                @Override
                public void onClick(View view, int position) {

                    //  Toast.makeText(view.getContext(), "You have clicked " + postList.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                    Intent mIntent = new Intent(getActivity(), DetailActivity.class);
                    Bundle args = new Bundle();
                    args.putString("title", postList.get(position).getTitle());
                    args.putString("url", postList.get(position).getGuid());
                    args.putString("content", postList.get(position).getContent());
                    mIntent.putExtras(args);
                    startActivity(mIntent);

                }
            }));

        new JSONAsync().execute(baseURL);

        return rootView;
    }


    class JSONAsync extends AsyncTask<String, Void, Void> {
        ProgressDialog pd = new ProgressDialog(getActivity());


        @Override
        protected Void doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    builder.append(line).append("\n");
                }
                 json = builder.toString();
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

            }


            try {
                // Convert the JSON String from InputStream to a JSONObject
                  mJsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
          //  jsonObject = new JSONHelper().getJSONFromUrl();
            postList = new JSONParser().parse(mJsonObject);
            return null;
        }

        @Override
        protected void onPreExecute() {
           pd = ProgressDialog.show(getActivity(), null, "Please wait...", true, false);
           // pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }


        @Override
        protected void onPostExecute(Void result) {
            PostAdapter postAdapter = new PostAdapter(getActivity(), postList);
            rv.setAdapter(postAdapter);
            swipeRefreshLayout.setRefreshing(false);
            pd.dismiss();
        }
    }
}