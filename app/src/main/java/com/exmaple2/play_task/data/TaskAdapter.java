package com.exmaple2.play_task.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskName> localDataSet;

    public TaskAdapter(ArrayList<TaskName> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        //View view = LayoutInflater.from(viewGroup.getContext())
                //.inflate(R.layout.task_name_row, viewGroup, false);
        //return new ViewHolder(view);
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_name_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView textViewTaskName;
        public TextView textViewScore;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            textViewTaskName = itemView.findViewById(R.id.textView_task_name);
            textViewScore = itemView.findViewById(R.id.textView_score);
        }
    }
    public interface OnTaskClickListener {
        void onTaskClick(int position);
    }

    private OnTaskClickListener listener;

    public TaskAdapter(ArrayList<TaskName> dataSet, OnTaskClickListener listener) {
        localDataSet = dataSet;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TaskName task = localDataSet.get(position);
        viewHolder.textViewTaskName.setText(task.getTitle());
        // 设置任务分数和复选框的状态
        // viewHolder.textViewScore.setText("+" + task.getScore());
        viewHolder.textViewScore.setText(task.getScore());

        // 设置点击监听器
        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskClick(position);
            }
        });
    }
}

