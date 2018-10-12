package com.example.qlem.todolist.db;

import android.provider.BaseColumns;

public final class dbContract {

    private dbContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_TASK_TITLE = "name";
        public static final String COLUMN_TASK_NOTE = "content";
        public static final String COLUMN_TASK_DONE = "done";
    }
}
