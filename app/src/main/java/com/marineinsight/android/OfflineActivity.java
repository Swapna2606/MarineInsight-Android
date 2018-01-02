package com.marineinsight.android;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by User on 06-10-2015.
 */
public class OfflineActivity extends ListActivity {
    private ListAdapter listAdapter;
    private TaskDBHelper helper;
    ListView listView;
    String[] OTitle;
    String st= null;
    String st2= null;
    String st1= null;
    Cursor cursor;
    ImageView image2;
    byte[] byteImage2 = null;
    android.support.v7.widget.Toolbar toolbarSave;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.detail_fragment);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_list);


         AppCompatCallback callback = new AppCompatCallback() {
            @Override
            public void onSupportActionModeStarted(ActionMode actionMode) {
            }

            @Override
            public void onSupportActionModeFinished(ActionMode actionMode) {
            }

            @Nullable
            @Override
            public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
                return null;
            }
        };
        AppCompatDelegate delegate = AppCompatDelegate.create(this, callback);

        delegate.onCreate(savedInstanceState);
       // delegate.setContentView(R.layout.offline_list);

        toolbarSave = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_save);
        delegate.setSupportActionBar(toolbarSave);
        delegate.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbarSave.setTitle("SAVED STORIES");

     updateUI();
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    private void updateUI() {
        helper = new TaskDBHelper(OfflineActivity.this);
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TITLE, TaskContract.Columns.CONTENT,TaskContract.Columns.URL,TaskContract.Columns.IMAGE},
                null, null, null, null, null);
        // byteImage2 = cursor.getBlob(cursor.getColumnIndex(TaskContract.Columns.IMAGE));

        OTitle = new String[]{TaskContract.Columns.TITLE,TaskContract.Columns.CONTENT,TaskContract.Columns.URL,TaskContract.Columns.IMAGE};
        listAdapter = new SimpleCursorAdapter(
                this,
                R.layout.offline_item,
                cursor,
                OTitle,
                new int[]{R.id.taskTextView,R.id.contentView,R.id.urlView,R.id.offthumb},
                0
        );

        //setImage(byteImage2);
        setListAdapter(listAdapter);

    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        st = ((TextView) v.findViewById(R.id.taskTextView)).getText().toString();
        st2= ((TextView) v.findViewById(R.id.contentView)).getText().toString();
        st1= ((TextView) getListView().findViewById(R.id.urlView)).getText().toString();

        Intent mIntent = new Intent(getApplicationContext(), OffDetailActivity.class);
        Bundle args = new Bundle();
        args.putString("title",st);
        args.putString("url",st1);
        args.putString("content",st2);
        mIntent.putExtras(args);
        startActivity(mIntent);
    }


    public void onDoneButtonClick(View view) {
        View v = (View) view.getParent();
        TextView taskTextView = (TextView) v.findViewById(R.id.taskTextView);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TITLE,
                task);

        helper = new TaskDBHelper(OfflineActivity.this);
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

}
