package com.exmaple2.play_task;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.exmaple2.play_task.data.DataBank;
import com.exmaple2.play_task.data.SharedViewModel;
import com.exmaple2.play_task.data.TaskName;
import com.exmaple2.play_task.tasktablayout.DailyTaskFragment;
import com.exmaple2.play_task.tasktablayout.MajorTaskFragment;
import com.exmaple2.play_task.tasktablayout.WeeklyTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class TaskFragment extends Fragment {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton addTaskButton;
    private TaskPagerAdapter taskPagerAdapter;
    TextView totalScoreView; // 总分数的 TextView
    private SharedViewModel sharedViewModel;

    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        viewPager = rootView.findViewById(R.id.view_pager);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        addTaskButton = rootView.findViewById(R.id.addRewardButton_task);
        totalScoreView = rootView.findViewById(R.id.total_score_view);

        // 使用已经初始化的 sharedViewModel
        sharedViewModel.getTotalScore().observe(getViewLifecycleOwner(), score -> {
            totalScoreView.setText("总分: " + score);
        });

        // 加载任务和分数
        DataBank dataBank = new DataBank();
        ArrayList<TaskName> dailyTasks = dataBank.loadTasks(getContext(), "daily_tasks.data");
        ArrayList<TaskName> weeklyTasks = dataBank.loadTasks(getContext(), "weekly_tasks.data");
        ArrayList<TaskName> majorTasks = dataBank.loadTasks(getContext(), "Major_tasks.data");
        int loadedScore = dataBank.loadScore(getContext());
        sharedViewModel.setTotalScore(loadedScore); // 设置加载的分数

        taskPagerAdapter = new TaskPagerAdapter(this, dailyTasks, weeklyTasks, majorTasks);
        viewPager.setAdapter(taskPagerAdapter); // 设置适配器
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(getTask(position))).attach();

        addTaskButton.setOnClickListener(view -> showAddTaskDialog());

        return rootView;
    }

    private void updateTotalScore(int addedScore) {
        Integer currentScore = sharedViewModel.getTotalScore().getValue();
        if (currentScore == null) {
            currentScore = 0;
        }
        int newTotalScore = currentScore + addedScore;
        sharedViewModel.setTotalScore(newTotalScore); // 更新 ViewModel 中的分数
        new DataBank().saveScore(getContext(), newTotalScore); // 保存新的分数
        totalScoreView.setText("总分: " + newTotalScore); // 更新 UI 显示的总分数
    }



    private void showAddTaskDialog() {
        int currentTab = viewPager.getCurrentItem(); // 获取当前选中的标签索引

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("添加任务");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.activity_task_details, null);
        final EditText taskNameEditText = dialogView.findViewById(R.id.edittext_task_name);
        final EditText taskScoreEditText = dialogView.findViewById(R.id.edittext_task_score);
        builder.setView(dialogView);

        builder.setPositiveButton("添加", (dialog, which) -> {
            String name = taskNameEditText.getText().toString();
            String score = taskScoreEditText.getText().toString();
            TaskName newTask = new TaskName(name, score);

            Fragment currentFragment = taskPagerAdapter.createFragment(currentTab);
            if (currentFragment instanceof DailyTaskFragment) {
                ((DailyTaskFragment) currentFragment).addTask(newTask);
            } else if (currentFragment instanceof WeeklyTaskFragment) {
                ((WeeklyTaskFragment) currentFragment).addTask(newTask);
            } else if (currentFragment instanceof MajorTaskFragment) {
                ((MajorTaskFragment) currentFragment).addTask(newTask);
            }
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private String getTask(int position) {
        switch (position) {
            case 0: return "每日任务";
            case 1: return "每周任务";
            case 2: return "大任务";
            default: return null;
        }
    }

    public interface ScoreUpdater {
        void updateScore(int score);
    }
    private class TaskPagerAdapter extends FragmentStateAdapter {
        private Fragment[] fragments;
        private ArrayList<TaskName> dailyTasks, weeklyTasks, majorTasks;

        public TaskPagerAdapter(Fragment fragment, ArrayList<TaskName> dailyTasks,
                                ArrayList<TaskName> weeklyTasks, ArrayList<TaskName> majorTasks) {
            super(fragment);
            this.dailyTasks = dailyTasks;
            this.weeklyTasks = weeklyTasks;
            this.majorTasks = majorTasks;
            fragments = new Fragment[3];
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (fragments[position] == null) {
                switch (position) {
                    case 0:
                        DailyTaskFragment dailyFragment = new DailyTaskFragment();
                        dailyFragment.setTasks(dailyTasks);
                        dailyFragment.setOnTaskCompletedListener(TaskFragment.this::updateTotalScore);
                        fragments[position] = dailyFragment;
                        break;
                    case 1:
                        WeeklyTaskFragment weeklyFragment = new WeeklyTaskFragment();
                        weeklyFragment.setTasks(weeklyTasks);
                        weeklyFragment.setOnTaskCompletedListener(TaskFragment.this::updateTotalScore);
                        fragments[position] = weeklyFragment;
                        break;
                    case 2:
                        MajorTaskFragment majorFragment = new MajorTaskFragment();
                        majorFragment.setTasks(majorTasks);
                        majorFragment.setOnTaskCompletedListener(TaskFragment.this::updateTotalScore);
                        fragments[position] = majorFragment;
                        break;
                }
            }
            return fragments[position];
        }


        @Override
        public int getItemCount() {
            return fragments.length;
        }

        public Fragment getFragment(int position) {
            return fragments[position];
        }
    }
}
