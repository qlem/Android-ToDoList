package com.example.qlem.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import com.example.qlem.todolist.task.TaskContent.Task;
import com.example.qlem.todolist.MainActivity.OnTaskClickListener;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Task> taskList;
    private OnTaskClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public Task task;
        public TextView taskName;
        public TextView taskContent;
        public Button editButton;
        public Button deleteButton;

        public ViewHolder(View v) {
            super(v);
            view = v;
            taskName = view.findViewById(R.id.task_name);
            taskContent = view.findViewById(R.id.task_content);
            editButton = view.findViewById(R.id.btn_edit_task);
            deleteButton = view.findViewById(R.id.btn_delete_task);
        }
    }

    public Adapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    public void removeTask(int position) {
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.task = taskList.get(position);
        holder.taskName.setText(taskList.get(position).name);
        holder.taskContent.setText(taskList.get(position).content);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaskClickListener(holder.task);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaskEditClickListener(holder.task);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTask(holder.getAdapterPosition());
                listener.onTaskDeleteClickListener(holder.task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
