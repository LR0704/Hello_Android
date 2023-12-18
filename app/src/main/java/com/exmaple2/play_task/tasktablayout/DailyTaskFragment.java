package com.exmaple2.play_task.tasktablayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.R;
import com.exmaple2.play_task.data.DataBank;
import com.exmaple2.play_task.data.TaskAdapter;
import com.exmaple2.play_task.data.TaskName;

import java.util.ArrayList;

public class DailyTaskFragment extends Fragment {
    private ArrayList<TaskName> dailyTasks = new ArrayList<>();
    private TaskAdapter taskAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBank dataBank = new DataBank();
        dailyTasks = dataBank.loadTasks(getContext(), "daily_tasks.data");
    }
    public void setTasks(ArrayList<TaskName> tasks) {
        this.dailyTasks = tasks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily_task, container, false);

        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recyclerview_daily);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        taskAdapter = new TaskAdapter(dailyTasks, this::showCompleteTaskDialog);
        mainRecyclerView.setAdapter(taskAdapter);

        return rootView;
    }

    private void showCompleteTaskDialog(int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("完成任务")
                .setMessage("您确定已完成标记的任务吗？")
                .setPositiveButton("完成", (dialog, which) -> completeTask(position))
                .setNegativeButton("取消", null)
                .show();
    }
    public interface OnTaskCompletedListener {
        void onTaskCompleted(int score);
    }

    private OnTaskCompletedListener taskCompletedListener;

    public void setOnTaskCompletedListener(OnTaskCompletedListener listener) {
        this.taskCompletedListener = listener;
    }

    private void completeTask(int position) {
        TaskName completedTask = dailyTasks.get(position);
        dailyTasks.remove(position);
        taskAdapter.notifyItemRemoved(position);
        if (taskCompletedListener != null) {
            taskCompletedListener.onTaskCompleted(Integer.parseInt(completedTask.getScore()));
        }

        new DataBank().saveTasks(getContext(), dailyTasks, "daily_tasks.data"); // 保存当前任务列表
    }
    public void addTask(TaskName task) {
        dailyTasks.add(task);
        taskAdapter.notifyDataSetChanged();
        new DataBank().saveTasks(getContext(), dailyTasks, "daily_tasks.data"); // 保存当前任务列表
    }
}
