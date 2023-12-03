package com.exmaple2.play_task.tasktablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.hello_android.R;
import com.exmaple2.play_task.data.DataBank;
import com.exmaple2.play_task.data.TaskAdapter;
import com.exmaple2.play_task.data.TaskName;

import java.util.ArrayList;

public class DailyTaskFragment extends Fragment {
    private ArrayList<TaskName> taskNames;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily_task, container, false);

        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recyclerview_daily);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        // 加载任务数据
        taskNames = new DataBank().LoadTaskNames(requireActivity());
        if (0 == taskNames.size()) {
            // 添加示例数据
            taskNames.add(new TaskName("阅读课外书30分钟","10"));
            taskNames.add(new TaskName("有氧运动30分钟", "10"));
        }
        // 设置 RecyclerView 的适配器
        taskAdapter = new TaskAdapter(taskNames);
        mainRecyclerView.setAdapter(taskAdapter);

        return rootView;
    }

}
