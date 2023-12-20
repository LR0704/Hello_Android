package com.exmaple2.play_task.data;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskName> localDataSet;
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(int position);
        void onTaskDelete(int position);
    }

    public TaskAdapter(ArrayList<TaskName> dataSet) {
        localDataSet = dataSet;
    }

    public void setOnTaskClickListener(OnTaskClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_name_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public CheckBox checkBox;
        public TextView textViewTaskName;
        public TextView textViewScore;

        public ViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            textViewTaskName = itemView.findViewById(R.id.textView_task_name);
            textViewScore = itemView.findViewById(R.id.textView_score);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, 0, 0, "Delete").setOnMenuItemClickListener(item -> {
                if (listener != null) {
                    listener.onTaskDelete(getAdapterPosition());
                    return true;
                }
                return false;
            });
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        TaskName task = localDataSet.get(position);
        viewHolder.textViewTaskName.setText(task.getTitle());
        viewHolder.textViewScore.setText(task.getScore());

        // 设置点击监听器
        viewHolder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskClick(position);
            }
        });
    }
}
