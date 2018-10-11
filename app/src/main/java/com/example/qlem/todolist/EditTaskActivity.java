package com.example.qlem.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.qlem.todolist.db.dbContrat.FeedEntry;
import com.example.qlem.todolist.db.dbHelper;

public class EditTaskActivity extends AppCompatActivity {

    private dbHelper dbHelper = new dbHelper(this);

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

                // TODO task name must be unique
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(FeedEntry.COLUMN_NAME_TITLE, taskName);
                cv.put(FeedEntry.COLUMN_NAME_SUBTITLE, taskContent);
                String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
                String[] selectionArgs = { task[0] };
                db.update(FeedEntry.TABLE_NAME, cv, selection, selectionArgs);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String task[] = {taskName, taskContent};
                intent.putExtra("task", task);
                intent.putExtra("position", position);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
