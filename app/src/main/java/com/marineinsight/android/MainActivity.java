package com.marineinsight.android;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ActionBarDrawerToggle mDrawerToggle;
    String navUrl;


    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
     //  getSupportActionBar().hide();
        /**
         *Setup the DrawerLayout and NavigationView
         */

             mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
             mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

             mFragmentManager = getSupportFragmentManager();
             mFragmentTransaction = mFragmentManager.beginTransaction();
             mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

             mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {

                 mDrawerLayout.closeDrawers();


                 if (menuItem.getItemId() == R.id.nav_item_news) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=shipping-news&count=10";

                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Latest News");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_videos) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=videos&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Videos");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_tech) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=tech&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Marine Tech");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_navigation) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=marine-navigation&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Navigation");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_arch) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=naval-architecture&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Naval Arch");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_safety) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=marine-safety&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Marine Safety");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_career) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=careers-2&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Marine Careers");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_life) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=life-at-sea&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Life at Sea");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_piracy) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=marine-piracy-marine&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Marine Piracy");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_case) {
                     CategoryFragment categoryFragment = new CategoryFragment();
                     navUrl ="https://www.marineinsight.com/api/get_recent_posts/?json=get_category_posts&slug=case-studies&count=10";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Case Studies");
                     categoryFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, categoryFragment).commit();

                 }
                 if (menuItem.getItemId() == R.id.nav_item_saved) {
                   Intent intent= new Intent(MainActivity.this,OfflineActivity.class);
                     startActivity(intent);
                 }
//                 if (menuItem.getItemId() == R.id.nav_item_free) {
//                     WebFragment webFragment = new WebFragment();
//                     navUrl ="http://www.marineinsight.com/subscribe/";
//                     Bundle args = new Bundle();
//                     args.putString("catURL", navUrl);
//                     args.putString("catNAME","Free eBooks");
//                     webFragment.setArguments(args);
//                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                     fragmentTransaction.replace(R.id.containerView, webFragment);
//                     fragmentTransaction.commit();
//
//                 }

                 if (menuItem.getItemId() == R.id.nav_item_disclaimer) {
                     WebFragment webFragment = new WebFragment();
                     navUrl ="https://www.marineinsight.com/app-disclaimer/";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Disclaimer");
                     webFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView,webFragment).commit();

                 }

                 if (menuItem.getItemId() == R.id.nav_item_privacy) {
                     WebFragment webFragment = new WebFragment();
                     navUrl ="https://www.marineinsight.com/app-privacy-policy/";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","Privacy Policy");
                     webFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView,webFragment).commit();

                 }

                 if (menuItem.getItemId() == R.id.nav_item_about) {
                     WebFragment webFragment = new WebFragment();
                     navUrl ="https://www.marineinsight.com/app-about-us/";
                     Bundle args = new Bundle();
                     args.putString("catURL", navUrl);
                     args.putString("catNAME","About Us");
                     webFragment.setArguments(args);
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView,webFragment).commit();

                 }

                 if (menuItem.getItemId() == R.id.nav_item_contact) {
                     Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                             "mailto", "info@marineinsight.com", null));
                     String appSubject = "Contacting from  Android App";
                     emailIntent.putExtra(Intent.EXTRA_SUBJECT, appSubject);
                     emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                     startActivity(Intent.createChooser(emailIntent, "Complete action using"));
                 }


                 return false;
            }

        });



        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
             mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name, R.string.app_name);

             mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
    @Override
    public void onResume() {
        super.onResume();
        // Set title
        getSupportActionBar().setTitle(R.string.app_name);
    }


    @Override
       public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    query = query.replaceAll("\\s","+");
                    String searchURL = "https://www.marineinsight.com/?json=1&count=10&s="+query;
                   // Log.i("onQueryTextSubmit", searchURL);
                    SearchFragment searchFragment = new SearchFragment();

                    Bundle args = new Bundle();
                    args.putString("catURL", searchURL);
                   // args.putString("catNAME","SEARCH RESULTS");
                    searchFragment.setArguments(args);
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, searchFragment).addToBackStack("Marine Insight").commit();
                    searchView.clearFocus();
                    return true;

                }
            };
            searchView.setOnQueryTextListener(queryTextListener);

        }
        return super.onCreateOptionsMenu(menu);
    }

}