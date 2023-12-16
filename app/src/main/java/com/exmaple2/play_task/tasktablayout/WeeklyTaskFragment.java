package com.exmaple2.play_task.tasktablayout;

import androidx.fragment.app.Fragment;

import com.exmaple2.play_task.data.TaskName;

import java.util.ArrayList;

public class WeeklyTaskFragment extends Fragment {
    private ArrayList<TaskName> WeeklyTasks = new ArrayList<>();

    public void addTask(TaskName task) {
        WeeklyTasks.add(task);
        // 更新您的适配器/视图以反映新任务
    }
}
