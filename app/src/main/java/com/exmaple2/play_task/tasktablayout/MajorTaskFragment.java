package com.exmaple2.play_task.tasktablayout;

import androidx.fragment.app.Fragment;

import com.exmaple2.play_task.data.TaskName;

import java.util.ArrayList;

public class MajorTaskFragment extends Fragment {
    private ArrayList<TaskName> MajorTasks = new ArrayList<>();

    public void addTask(TaskName task) {
        MajorTasks.add(task);
        // 更新您的适配器/视图以反映新任务
    }
}
