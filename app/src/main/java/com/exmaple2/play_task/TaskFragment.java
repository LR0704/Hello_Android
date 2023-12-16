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

import com.exmaple2.play_task.data.SharedViewModel;
import com.exmaple2.play_task.data.TaskName;
import com.exmaple2.play_task.tasktablayout.DailyTaskFragment;
import com.exmaple2.play_task.tasktablayout.MajorTaskFragment;
import com.exmaple2.play_task.tasktablayout.WeeklyTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


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
    public void onAttach(@NonNull Context context) { //初始化
        super.onAttach(context);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        viewPager = rootView.findViewById(R.id.view_pager);
        tabLayout = rootView.findViewById(R.id.tab_layout);
        addTaskButton = rootView.findViewById(R.id.addRewardButton_task);
        totalScoreView = rootView.findViewById(R.id.total_score_view); // 初始化总分数的 TextView
        updateTotalScore(0); // 初始化显示的总分数

        // 现在使用已经初始化的 sharedViewModel
        sharedViewModel.getTotalScore().observe(getViewLifecycleOwner(), score -> {
            totalScoreView.setText("总分: " + score);
        });

        taskPagerAdapter = new TaskPagerAdapter(this);
        viewPager.setAdapter(taskPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(getTask(position))).attach();

        addTaskButton.setOnClickListener(view -> showAddTaskDialog());

        return rootView;
    }
    private void updateTotalScore(int score) {
        Integer currentScoreValue = sharedViewModel.getTotalScore().getValue();
        int currentScore = (currentScoreValue != null) ? currentScoreValue : 0;
        sharedViewModel.setTotalScore(currentScore + score);
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
        public TaskPagerAdapter(Fragment fragment) {
            super(fragment);
            fragments = new Fragment[3]; // 有 3个项目
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (fragments[position] == null) {
                switch (position) {
                    case 0:
                        fragments[position] = new DailyTaskFragment();
                        ((DailyTaskFragment) fragments[position]).setOnTaskCompletedListener(TaskFragment.this::updateTotalScore);
                        break;
                    case 1:
                        fragments[position] = new WeeklyTaskFragment();
                        ((WeeklyTaskFragment) fragments[position]).setOnTaskCompletedListener(TaskFragment.this::updateTotalScore);
                        break;
                    case 2:
                        fragments[position] = new MajorTaskFragment();
                        ((MajorTaskFragment) fragments[position]).setOnTaskCompletedListener(TaskFragment.this::updateTotalScore);
                        break;
                    default:
                        fragments[position] = new Fragment();
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
