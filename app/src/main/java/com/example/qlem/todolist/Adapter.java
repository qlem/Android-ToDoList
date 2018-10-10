package com.example.qlem.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.example.qlem.todolist.task.TaskContent.Task;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Task> taskList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public Task task;
        public TextView taskName;
        public TextView taskContent;

        public ViewHolder(View v) {
            super(v);
            view = v;
            taskName = view.findViewById(R.id.task_name);
            taskContent = view.findViewById(R.id.task_content);
        }
    }

    public Adapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.task = taskList.get(position);
        holder.taskName.setText(taskList.get(position).name);
        holder.taskContent.setText(taskList.get(position).content);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
