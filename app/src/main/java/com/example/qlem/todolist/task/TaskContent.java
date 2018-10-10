package com.example.qlem.todolist.task;

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

    public class Task {
        public String name;
        public String content;

        public Task(String name, String content) {
            this.name = name;
            this.content = content;
        }
    }
}
