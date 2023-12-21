package com.exmaple2.play_task.tasktablayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.MainActivity;
import com.exmaple2.play_task.R;
import com.exmaple2.play_task.data.DataBank;
import com.exmaple2.play_task.data.ScoreHistoryItem;
import com.exmaple2.play_task.data.SharedViewModel;
import com.exmaple2.play_task.data.TaskAdapter;
import com.exmaple2.play_task.data.TaskName;

import java.util.ArrayList;

public class MajorTaskFragment extends Fragment {
    private ArrayList<TaskName> majorTasks = new ArrayList<>();
    private TaskAdapter taskAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBank dataBank = new DataBank();
        majorTasks = dataBank.loadTasks(getContext(), "major_tasks.data");
    }

    public void setTasks(ArrayList<TaskName> tasks) {
        this.majorTasks = tasks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_major_task, container, false);
        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recyclerview_major);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        taskAdapter = new TaskAdapter(majorTasks);
        taskAdapter.setOnTaskClickListener(new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(int position) {
                showCompleteTaskDialog(position);
            }

            @Override
            public void onTaskDelete(int position) {
                removeTask(position);
            }
        });

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
    // 方法处理任务删除
    public void removeTask(int position) {
        majorTasks.remove(position);
        taskAdapter.notifyItemRemoved(position);
        new DataBank().saveTasks(getContext(), majorTasks, "major_tasks.data"); // 更新数据
    }
    private void completeTask(int position) {
        TaskName completedTask = majorTasks.get(position);
        // 删除任务
        majorTasks.remove(position);
        taskAdapter.notifyItemRemoved(position);
        // 保存更新后的任务列表
        new DataBank().saveTasks(getContext(), majorTasks, "major_tasks.data");
        // 更新分数
        int scoreToAdd = Integer.parseInt(completedTask.getScore());
        updateScoreAndHistory(completedTask.getTitle(), scoreToAdd); // 现在传递任务名称和分数

        if (getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).refreshChartFragment();
        }
    }

    private void updateScoreAndHistory(String name, int scoreChange) {
        DataBank dataBank = new DataBank();
        // 加载当前分数
        int currentScore = dataBank.loadScore(getContext());
        // 计算新的分数
        currentScore += scoreChange;
        // 保存新的分数
        dataBank.saveScore(getContext(), currentScore);

        // 创建历史记录项
        ScoreHistoryItem historyItem = new ScoreHistoryItem(name, scoreChange);
        // 加载当前分数历史
        ArrayList<ScoreHistoryItem> scoreHistory = dataBank.loadScoreHistory(getContext());
        // 添加新的历史记录
        scoreHistory.add(historyItem);
        // 保存更新后的分数历史
        dataBank.saveScoreHistory(getContext(), scoreHistory);
        // 更新SharedViewModel中的总分数
        if (getActivity() instanceof MainActivity) {
            SharedViewModel viewModel = new ViewModelProvider((MainActivity)getActivity()).get(SharedViewModel.class);
            viewModel.setTotalScore(currentScore); // 这里设置新的总分数
        }

        // 更新分数后刷新图表
        if (getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).refreshChartFragment();
        }
    }


    public void addTask(TaskName task) {
        majorTasks.add(task);
        taskAdapter.notifyDataSetChanged();
        new DataBank().saveTasks(getContext(), majorTasks, "major_tasks.data");
    }

    public interface OnTaskCompletedListener {
        void onTaskCompleted(int score);
    }

    private OnTaskCompletedListener taskCompletedListener;

    public void setOnTaskCompletedListener(OnTaskCompletedListener listener) {
        this.taskCompletedListener = listener;
    }
}
