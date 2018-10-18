package com.example.qlem.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.example.qlem.todolist.task.TaskContent.Task;
import static android.graphics.Color.rgb;

/**
 * Class that defines the adapter of the recycler view.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    /**
     * Variable that contains the task list.
     */
    private List<Task> taskList;

    /**
     * Variable that contains the event listener object.
     */
    private OnTaskEventListener listener;

    /**
     * Constructor of the adapter.
     * @param taskList the list of tasks
     * @param listener the event listener object
     */
    Adapter(List<Task> taskList, OnTaskEventListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    /**
     * Class that sets the content of the view of each task.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The view of a task element.
         */
        public View view;

        /**
         * The task object.
         */
        public Task task;

        /**
         * The text view that contains the task name.
         */
        private TextView taskName;

        /**
         * the text view that contains the content of the task.
         */
        private TextView taskContent;

        /**
         * The "edit" button of a task view.
         */
        private AppCompatImageButton editButton;

        /**
         * The "delete" button of a task view.
         */
        private AppCompatImageButton deleteButton;

        /**
         * The "done / undone" button of a task view.
         */
        private AppCompatImageButton doneButton;

        /**
         * Constructor of the view holder.
         * @param v
         */
        ViewHolder(View v) {
            super(v);
            view = v;
            taskName = view.findViewById(R.id.task_name);
            taskContent = view.findViewById(R.id.task_content);
            editButton = view.findViewById(R.id.task_edit_btn);
            deleteButton = view.findViewById(R.id.task_delete_btn);
            doneButton = view.findViewById(R.id.task_done_btn);
        }
    }

    /**
     * Method that is called at the creation of the adapter.
     * Sets the view holder for each task of the list.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Method that binds data and event listeners of each task to their view.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.task = taskList.get(position);
        holder.taskName.setText(taskList.get(position).name);
        holder.taskContent.setText(taskList.get(position).content);

        if (holder.task.done == 0) {
            holder.view.setBackgroundColor(rgb(99, 218, 255));
        } else {
            holder.view.setBackgroundColor(rgb(156, 255, 99));
        }

        holder.editButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Method that sets the event listener for the "edit" button.
             * @param v
             */
            @Override
            public void onClick(View v) {
                listener.onTaskEditListener(holder.task, holder.getAdapterPosition());
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Method that sets the event listener for the "delete" button.
             * @param v
             */
            @Override
            public void onClick(View v) {
                listener.onTaskDeleteListener(holder.task, holder.getAdapterPosition());
            }
        });

        holder.doneButton.setOnClickListener(new View.OnClickListener() {

            /**
             *  Method that sets the event listener for the "done / undone" button.
             * @param v
             */
            @Override
            public void onClick(View v) {
                listener.onTaskDoneListener(holder.task, holder.getAdapterPosition());
            }
        });
    }

    /**
     * Method that return the number of task in the list.
     * @return
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
