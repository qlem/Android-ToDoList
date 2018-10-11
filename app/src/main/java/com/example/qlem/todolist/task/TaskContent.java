package com.example.qlem.todolist.task;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TaskContent {

    public List<Task> TASK_LIST = new ArrayList<>();

    public void addTask(String name, String content) {
        Task task = new Task(name, content);
        TASK_LIST.add(task);
    }

    public void removeTask(int position) {
        TASK_LIST.remove(position);
    }

    public void updateTask(String name, String content, int position) {
        Log.i("DEBUG", name);
        TASK_LIST.set(position, new Task(name, content));
    }

    public class Task {
        public String name;
        public String content;

        Task(String name, String content) {
            this.name = name;
            this.content = content;
        }
    }
}
