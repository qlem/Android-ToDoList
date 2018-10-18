package com.example.qlem.todolist.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains task object, list of task and methods to handle this list.
 */
public class TaskContent {

    /**
     * Variable of the task list.
     */
    public List<Task> TASK_LIST = new ArrayList<>();

    /**
     * Method that adds a new task into the list.
     * @param name task name
     * @param content task content
     * @param done status of the task
     */
    public void addTask(String name, String content, int done) {
        Task task = new Task(name, content, done);
        TASK_LIST.add(task);
    }

    /**
     * Method that removes one task of the list.
     * @param position position of the task in the list
     */
    public void removeTask(int position) {
        TASK_LIST.remove(position);
    }

    /**
     * Method that updates one task in the list.
     * @param name task name
     * @param content task content
     * @param position position of the task in the list
     */
    public void updateTask(String name, String content, int position) {
        Task tmp = TASK_LIST.get(position);
        TASK_LIST.set(position, new Task(name, content, tmp.done));
    }

    /**
     * Method that updates status of a task in the list.
     * @param name task name
     * @param content task content
     * @param position position of the task in the list
     * @param status status of the task
     */
    public void updateTaskStatus(String name, String content, int position, int status) {
        TASK_LIST.set(position, new Task(name, content, status));
    }

    /**
     * Class of the task object.
     */
    public class Task {

        /**
         * Variable of the task name.
         */
        public String name;

        /**
         * Variable of the task content.
         */
        public String content;

        /**
         * Variable of the status of the task.
         */
        public int done;

        /**
         * Constructor that initializes a new task.
         * @param name task name
         * @param content task content
         * @param done status of the task
         */
        Task(String name, String content, int done) {
            this.name = name;
            this.content = content;
            this.done = done;
        }
    }
}
