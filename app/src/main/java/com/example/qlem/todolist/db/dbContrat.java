package com.example.qlem.todolist.db;

import android.provider.BaseColumns;

public final class dbContrat {

    private dbContrat() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_TITLE = "name";
        public static final String COLUMN_NAME_SUBTITLE = "content";
    }
}
