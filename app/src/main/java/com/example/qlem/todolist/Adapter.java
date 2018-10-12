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
import com.example.qlem.todolist.MainActivity.OnTaskEventListener;
import static android.graphics.Color.rgb;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Task> taskList;
    private OnTaskEventListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public Task task;
        TextView taskName;
        TextView taskContent;
        AppCompatImageButton editButton;
        AppCompatImageButton deleteButton;
        AppCompatImageButton doneButton;

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

    Adapter(List<Task> taskList, OnTaskEventListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

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

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaskClickListener(holder.task);
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaskEditListener(holder.task, holder.getAdapterPosition());
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaskDeleteListener(holder.task, holder.getAdapterPosition());
            }
        });
        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTaskDoneListener(holder.task, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
