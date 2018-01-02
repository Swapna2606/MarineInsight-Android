package com.marineinsight.android;

import android.provider.BaseColumns;


/**
 * Created by User on 18-09-2015.
 */
public class TaskContract {
    public static final String DB_NAME = "com.marineinsight.android.marineinsight.OP10";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "OP10";

    public class Columns {
        public static final String TITLE = "task";
        public static final String CONTENT = "content";
        public static final String URL = "postURL";
        public static final String IMAGE = "image";

        public static final String _ID = BaseColumns._ID;
    }
}
