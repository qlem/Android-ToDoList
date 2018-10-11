package com.example.qlem.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.qlem.todolist.db.dbHelper;
import static com.example.qlem.todolist.db.dbContrat.FeedEntry;

public class CreateTaskActivity extends AppCompatActivity {

    private dbHelper dbHelper = new dbHelper(this);

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

                // TODO task name must be unique
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(FeedEntry.COLUMN_NAME_TITLE, taskName);
                cv.put(FeedEntry.COLUMN_NAME_SUBTITLE, taskContent);
                db.insert(FeedEntry.TABLE_NAME, null, cv);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                String task[] = {taskName, taskContent};
                intent.putExtra("task", task);
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }
}
