package com.example.qlem.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.qlem.todolist.db.dbContract.FeedEntry;

/**
 * Database helper class. Helps to handle database.
 */
public class dbHelper extends SQLiteOpenHelper {

    /**
     * Variable that contains database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Variable that contains database name.
     */
    private static final String DATABASE_NAME = "Task.db";

    /**
     * Database helper constructor.
     * @param context
     */
    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method that is called when database is to be created: creates table.
     * @param db SQLite database
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /**
     * Method that is used for upgrade database.
     * @param db SQLite database
     * @param oldVersion old version
     * @param newVersion new version
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    /**
     * Method that is used for downgrade database.
     * @param db SQLite database
     * @param oldVersion old version
     * @param newVersion new version
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * Variable that is the request for create a table.
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_TASK_TITLE + " TEXT UNIQUE," +
                    FeedEntry.COLUMN_TASK_NOTE + " TEXT," +
                    FeedEntry.COLUMN_TASK_DONE + " INTEGER)"
            ;

    /**
     * Variable that is request for delete a table.
     */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


}
