package com.example.qlem.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.example.qlem.todolist.task.TaskContent.Task;
import com.example.qlem.todolist.task.TaskContent;
import com.example.qlem.todolist.db.dbHelper;
import com.example.qlem.todolist.db.dbContract.FeedEntry;

/**
 * Main activity class. Displays the list of task. Allows to add a new task, etc.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Instance of the global task object. Contains the task list
     * and the methods to handle this list.
     */
    private TaskContent taskContent = new TaskContent();

    /**
     * Variable that contains database helper instance allowing to insert / update / remove task.
     */
    private dbHelper dbHelper = new dbHelper(this);

    /**
     * Variable that contains the adapter of the recycler view. Adapter takes a task list and
     * a task event listener interface as parameter.
     */
    private Adapter adapter = new Adapter(taskContent.TASK_LIST, new OnTaskEventListener() {

        /**
         * Event method that starts "edit task" activity when the edit button of a task element in
         * the list is clicked. Provides name, content and position of the task
         * to the "edit task" activity.
         * @param task task object
         * @param position task position in the list
         */
        @Override
        public void onTaskEditListener(Task task, int position) {
            Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
            intent.putExtra("task", new String[]{task.name, task.content});
            intent.putExtra("position", position);
            startActivityForResult(intent, 2);
        }

        /**
         * Event method that removes the task from the database and the task list
         * when the delete button of a task element in the list is clicked.
         * @param task task object
         * @param position task position in the list
         */
        @Override
        public void onTaskDeleteListener(Task task, int position) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String selection = FeedEntry.COLUMN_TASK_TITLE + " LIKE ?";
            String[] selectionArgs = { task.name };
            db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
            taskContent.removeTask(position);
            adapter.notifyItemRemoved(position);
        }

        /**
         * Event method that switch the status of a task from database and the task list
         * when the done / undone button of a task element in the list is clicked.
         * @param task task object
         * @param position task position in the list
         */
        @Override
        public void onTaskDoneListener(Task task, int position) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            int status = 0;
            if (task.done == 0) {
                status = 1;
            }
            cv.put(FeedEntry.COLUMN_TASK_DONE, status);
            String selection = FeedEntry.COLUMN_TASK_TITLE + " LIKE ?";
            String[] selectionArgs = { task.name };
            db.update(FeedEntry.TABLE_NAME, cv, selection, selectionArgs);
            taskContent.updateTaskStatus(task.name, task.content, position, status);
            adapter.notifyItemChanged(position);
        }
    });

    /**
     * Method who allows to get all saved tasks from database and to add these tasks
     * into the task list.
     */
    private void getTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_TASK_TITLE,
                FeedEntry.COLUMN_TASK_NOTE,
                FeedEntry.COLUMN_TASK_DONE
        };
        Cursor cursor = db.query(FeedEntry.TABLE_NAME, projection, null,
                null, null, null, null);

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_TASK_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_TASK_NOTE));
            int done = cursor.getInt(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_TASK_DONE));
            taskContent.addTask(name, content, done);
        }
        cursor.close();
    }

    /**
     * Method that initializes the main activity. Sets the recycler view of the task list.
     * Starts "create task" activity when "add" button is clicked.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        // Get task from db.
        getTasks();

        // Initialize recycler view.
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * TODO
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * TODO
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // TODO remove all task here
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method that is called when the "create task" or "edit task" activity is finished.
     * Adding or update one task in the task list. Depends on which activity has been called.
     * @param requestCode allows to identify the finished activity
     * @param resultCode describes the status of the finished activity (OK or CANCELED)
     * @param data transmitted data from finished activity to main activity
     */
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String task[] = data.getStringArrayExtra("task");
            taskContent.addTask(task[0], task[1], 0);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            String task[] = data.getStringArrayExtra("task");
            int position = data.getIntExtra("position", 0);
            taskContent.updateTask(task[0], task[1], position);
            adapter.notifyItemChanged(position);
        }
    }

    /**
     * Method who is called when the activity is destroy. Close the database helper instance.
     */
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
