package com.exmaple2.play_task;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exmaple2.hello_android.R;
import com.exmaple2.play_task.data.TaskName;
import com.exmaple2.play_task.data.DataBank;
import com.exmaple2.play_task.tasktablayout.DailyTaskFragment;
import com.exmaple2.play_task.tasktablayout.MajorTaskFragment;
import com.exmaple2.play_task.tasktablayout.NormalTaskFragment;
import com.exmaple2.play_task.tasktablayout.WeeklyTaskFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_task, container, false);

        viewPager = rootView.findViewById(R.id.view_pager);
        tabLayout = rootView.findViewById(R.id.tab_layout);

        TaskPagerAdapter taskPagerAdapter = new TaskPagerAdapter(this);
        viewPager.setAdapter(taskPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getTitle(position))
        ).attach();

        addTasklauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String name = data.getStringExtra("name");

                        tasknames.add(new TaskName(name, "10"));
                        taskAdapter.notifyItemInserted(tasknames.size());

                        new DataBank().SaveTaskNames(requireActivity(), tasknames);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        updateTasklauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int position = data.getIntExtra("position",0);
                        String name = data.getStringExtra("name");

                        TaskName taskName = tasknames.get(position);
                        taskName.setName(name);
                        taskAdapter.notifyItemChanged(position);

                        new DataBank().SaveTaskNames(requireActivity(), tasknames);

                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {

                    }
                }
        );

        return rootView;
    }
    private String getTitle(int position) {
        switch (position) {
            case 0: return "每日任务";
            case 1: return "每周任务";
            case 2: return "普通任务";
            case 3: return "大任务";
            default: return null;
        }
    }
    private DataBank dataBank;
    private ArrayList<TaskName> tasknames = new ArrayList<>(); // 声明为成员变量
    private TaskAdapter taskAdapter; // 声明为成员变量

    ActivityResultLauncher<Intent> addTasklauncher;
    ActivityResultLauncher<Intent> updateTasklauncher;
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0://添加操作
                Intent intent = new Intent(requireActivity(), TaskDetailsActivity.class);
                addTasklauncher.launch(intent);
                break;
            case 1:// 从数据集中移除对应项
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Data");
                builder.setMessage("You want to delete this data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        tasknames.remove(item.getOrder());
                        taskAdapter.notifyItemRemoved(item.getOrder());

                        new DataBank().SaveTaskNames(requireActivity(), tasknames);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                builder.create().show();
                break;
            case 2:// 修改操作
                Intent intentUpdate = new Intent(requireActivity(), TaskDetailsActivity.class);
                TaskName taskName = tasknames.get(item.getOrder());
                intentUpdate.putExtra("name", taskName.getTitle());
                intentUpdate.putExtra("position",item.getOrder());
                updateTasklauncher.launch(intentUpdate);

                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public static class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
        private ArrayList<TaskName> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewBookName;

            public ViewHolder(View itemView) {
                super(itemView);
                textViewBookName = itemView.findViewById(R.id.textView_task_name);
                itemView.setOnCreateContextMenuListener(this);
            }

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

                menu.setHeaderTitle("具体操作");
                menu.add(0, 0, getAdapterPosition(), "添加" + getAdapterPosition());
                menu.add(0, 1, getAdapterPosition(), "删除" + getAdapterPosition());
                menu.add(0, 2, getAdapterPosition(), "修改" + getAdapterPosition());
            }
        }

        public TaskAdapter(ArrayList<TaskName> dataSet) {
            localDataSet = dataSet;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.task_name_row, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.textViewBookName.setText(localDataSet.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }
    private static class TaskPagerAdapter extends FragmentStateAdapter {
        public TaskPagerAdapter(Fragment fragment) {
            super(fragment);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new DailyTaskFragment(); // 每日任务
                case 1:
                    return new WeeklyTaskFragment(); // 每周任务
                case 2:
                    return new NormalTaskFragment(); // 普通任务
                case 3:
                    return new MajorTaskFragment(); // 大任务
                default:
                    return null; // 或者返回默认的 Fragment
            }
        }

        @Override
        public int getItemCount() {
            return 4; // Tab 的数量
        }
    }
}