package com.example.qlem.todolist.task;

import java.util.ArrayList;
import java.util.List;

public class TaskContent {

    public List<Task> TASK_LIST = new ArrayList<>();

    public void addTask(String name, String content, int done) {
        Task task = new Task(name, content, done);
        TASK_LIST.add(task);
    }

    public void removeTask(int position) {
        TASK_LIST.remove(position);
    }

    public void updateTask(String name, String content, int position) {
        Task tmp = TASK_LIST.get(position);
        TASK_LIST.set(position, new Task(name, content, tmp.done));
    }

    public void updateTaskStatus(String name, String content, int position, int status) {
        TASK_LIST.set(position, new Task(name, content, status));
    }

    public class Task {
        public String name;
        public String content;
        public int done;

        Task(String name, String content, int done) {
            this.name = name;
            this.content = content;
            this.done = done;
        }
    }
}
