package com.example.qlem.todolist.db;

import android.provider.BaseColumns;

/**
 * Class who describes how the database is organized. Contains the names of table and columns.
 */
public final class dbContract {

    /**
     * Empty constructor.
     */
    private dbContract() {}

    /**
     * Class that contains the names of table and columns.
     */
    public static class FeedEntry implements BaseColumns {

        /**
         * Variable of the name of the table.
         */
        public static final String TABLE_NAME = "task";

        /**
         * Variable of the name of the task title column.
         */
        public static final String COLUMN_TASK_TITLE = "name";

        /**
         * Variable of the name of the task content column.
         */
        public static final String COLUMN_TASK_NOTE = "content";

        /**
         * Variable of the name of the task status column.
         */
        public static final String COLUMN_TASK_DONE = "done";
    }
}
