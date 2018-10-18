package com.example.qlem.todolist;

import com.example.qlem.todolist.task.TaskContent.Task;

/**
 * Interface of event listener that will be set on each task of the list.
 */
public interface OnTaskEventListener {

    /**
     * Event listener for the "edit" button.
     * @param task triggered task
     * @param position position of the task in the list
     */
    void onTaskEditListener(Task task, int position);

    /**
     * Event listener for the "delete" button.
     * @param task triggered task
     * @param position position of the task in the list
     */
    void onTaskDeleteListener(Task task, int position);

    /**
     * Event listener for the "done / undone" button.
     * @param task triggered task
     * @param position position of the task in the list
     */
    void onTaskDoneListener(Task task, int position);
}
