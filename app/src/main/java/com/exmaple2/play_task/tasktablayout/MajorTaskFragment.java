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

public class MajorTaskFragment extends Fragment {
    private ArrayList<TaskName> MajorTasks = new ArrayList<>();
    private TaskAdapter taskAdapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBank dataBank = new DataBank();
        MajorTasks = dataBank.loadTasks(getContext(), "Major_tasks.data");
    }
    public void setTasks(ArrayList<TaskName> tasks) {
        this.MajorTasks = tasks;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_major_task, container, false);

        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recyclerview_major);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        taskAdapter = new TaskAdapter(MajorTasks, this::showCompleteTaskDialog);
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
        TaskName completedTask = MajorTasks.get(position);
        MajorTasks.remove(position);
        taskAdapter.notifyItemRemoved(position);
        if (taskCompletedListener != null) {
            taskCompletedListener.onTaskCompleted(Integer.parseInt(completedTask.getScore()));
        }
        new DataBank().saveTasks(getContext(), MajorTasks, "Major_tasks.data"); // 保存当前任务列表
    }
    public void addTask(TaskName task) {
        MajorTasks.add(task);
        taskAdapter.notifyDataSetChanged();
        new DataBank().saveTasks(getContext(), MajorTasks, "Major_tasks.data"); // 保存当前任务列表
    }
}
