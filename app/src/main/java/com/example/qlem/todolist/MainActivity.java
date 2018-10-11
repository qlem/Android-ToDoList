package com.example.qlem.todolist;

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
import android.widget.Toast;
import com.example.qlem.todolist.task.TaskContent.Task;
import com.example.qlem.todolist.task.TaskContent;
import com.example.qlem.todolist.db.dbHelper;
import com.example.qlem.todolist.db.dbContrat.FeedEntry;

public class MainActivity extends AppCompatActivity {

    private TaskContent taskList = new TaskContent();
    private dbHelper dbHelper = new dbHelper(this);

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

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_TITLE,
                FeedEntry.COLUMN_NAME_SUBTITLE
        };
        Cursor cursor = db.query(FeedEntry.TABLE_NAME, projection, null,
                null, null, null, null);

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SUBTITLE));
            taskList.addTask(name, content);
        }
        cursor.close();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final Adapter adapter = new Adapter(taskList.TASK_LIST, new OnTaskClickListener() {
            @Override
            public void onTaskClickListener(Task task) {
                Toast.makeText(MainActivity.this, task.name, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTaskEditClickListener(Task task) {
                Toast.makeText(MainActivity.this, "update " + task.name, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTaskDeleteClickListener(Task task) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
                String[] selectionArgs = { task.name };
                db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String task[] = data.getStringArrayExtra("task");
            taskList.addTask(task[0], task[1]);
        }
    }

    public interface OnTaskClickListener {
        void onTaskClickListener(Task task);
        void onTaskEditClickListener(Task task);
        void onTaskDeleteClickListener(Task task);
    }
}
