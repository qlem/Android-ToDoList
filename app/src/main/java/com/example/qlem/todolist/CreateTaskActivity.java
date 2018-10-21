package com.example.qlem.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.qlem.todolist.db.dbHelper;
import static com.example.qlem.todolist.db.dbContract.FeedEntry;

/**
 * Class of the "create" activity. Allows to create a new task.
 */
public class CreateTaskActivity extends AppCompatActivity {

    /**
     * Variable who stores database helper instance. Allows to handle database.
     */
    private dbHelper dbHelper = new dbHelper(this);

    /**
     * Method that initializes this activity. It is called when activity is created.
     * Adds a new task in the database then returns this task to main activity when
     * the "add" button is clicked.
     * @param saveInstanceState
     */
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_create_task);

        Button btnAddTask = findViewById(R.id.add_task_btn);
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView taskNameView = findViewById(R.id.add_task_name);
                TextView taskContentView = findViewById(R.id.add_task_content);
                String taskName = taskNameView.getText().toString();
                String taskContent = taskContentView.getText().toString();

                if (taskName.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (taskContent.isEmpty()) {
                    Toast.makeText(CreateTaskActivity.this, "Please enter a task description", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(FeedEntry.COLUMN_TASK_TITLE, taskName);
                    cv.put(FeedEntry.COLUMN_TASK_NOTE, taskContent);
                    cv.put(FeedEntry.COLUMN_TASK_DONE, 0);
                    db.insertOrThrow(FeedEntry.TABLE_NAME, null, cv);
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(CreateTaskActivity.this, "Task name already used", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String task[] = {taskName, taskContent};
                intent.putExtra("task", task);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
