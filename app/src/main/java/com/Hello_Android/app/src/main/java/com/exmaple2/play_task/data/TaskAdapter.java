package com.exmaple2.play_task.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.hello_android.R;
import com.exmaple2.play_task.data.TaskName;
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        //viewHolder.textViewTaskName.setText(localDataSet.get(position).getTitle());
        TaskName task = localDataSet.get(position);
        viewHolder.textViewTaskName.setText(task.getTitle());
        // 如果 TaskName 类有分数或其他属性，这里设置它们
        // viewHolder.textViewScore.setText("+" + task.getScore());
        // viewHolder.checkBox.setChecked(task.isChecked());
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
}

