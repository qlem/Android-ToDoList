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
import com.example.qlem.todolist.db.dbContract.FeedEntry;
import com.example.qlem.todolist.db.dbHelper;

/**
 * Class of the "edit task" activity. Allows to edit a task.
 */
public class EditTaskActivity extends AppCompatActivity {

    /**
     * Variable who stores database helper instance. Allows to handle database.
     */
    private dbHelper dbHelper = new dbHelper(this);

    /**
     * Method that initializes this activity. It is called when activity is created.
     * Updates the passed task into the database then return this task to the main activity
     * when the "edit" button is clicked.
     * @param saveInstanceState
     */
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_edit_task);

        Intent intent = getIntent();
        final String task[] = intent.getStringArrayExtra("task");
        final int position = intent.getIntExtra("position", 0);

        TextView taskNameView = findViewById(R.id.edit_task_name);
        TextView taskContentView = findViewById(R.id.edit_task_content);
        taskNameView.setText(task[0]);
        taskContentView.setText(task[1]);

        Button editButton = findViewById(R.id.edit_task_btn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView taskNameView = findViewById(R.id.edit_task_name);
                TextView taskContentView = findViewById(R.id.edit_task_content);
                String taskName = taskNameView.getText().toString();
                String taskContent = taskContentView.getText().toString();

                if (taskName.isEmpty()) {
                    Toast.makeText(EditTaskActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
                    return;
                } else if (taskContent.isEmpty()) {
                    Toast.makeText(EditTaskActivity.this, "Please enter a task description", Toast.LENGTH_SHORT).show();
                    return;
                } else if (task[0].equals(taskName) && task[1].equals(taskContent)) {
                    Toast.makeText(EditTaskActivity.this, "No change", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(FeedEntry.COLUMN_TASK_TITLE, taskName);
                    cv.put(FeedEntry.COLUMN_TASK_NOTE, taskContent);
                    String selection = FeedEntry.COLUMN_TASK_TITLE + " LIKE ?";
                    String[] selectionArgs = { task[0] };
                    db.update(FeedEntry.TABLE_NAME, cv, selection, selectionArgs);
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(EditTaskActivity.this, "Task name already used", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String task[] = {taskName, taskContent};
                intent.putExtra("task", task);
                intent.putExtra("position", position);
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
