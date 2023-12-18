package com.exmaple2.play_task;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.data.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BonusFragment extends Fragment {
    private RecyclerView rewardsRecyclerView;
    private List<RewardItem> rewardsList = new ArrayList<>();
    private RewardsAdapter rewardsAdapter;
    private Button confirmButton;
    private Button cancelButton;
    TextView totalScoreView;
    private SharedViewModel sharedViewModel;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bonus, container, false);

        rewardsRecyclerView = view.findViewById(R.id.rewardsRecyclerView);
        rewardsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rewardsAdapter = new RewardsAdapter(rewardsList);
        rewardsRecyclerView.setAdapter(rewardsAdapter);
        totalScoreView = view.findViewById(R.id.total_score_view);

        // 使用已经初始化的 sharedViewModel
        sharedViewModel.getTotalScore().observe(getViewLifecycleOwner(), score -> {
            totalScoreView.setText("总分: " + score);
        });

        FloatingActionButton addRewardButton = view.findViewById(R.id.addRewardButton);
        addRewardButton.setOnClickListener(v -> showAddRewardDialog());

        confirmButton = view.findViewById(R.id.confirmButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        confirmButton.setOnClickListener(v -> {
            removeCheckedItems();
            checkIfAnyItemChecked(); // 更新按钮可见性
        });

        cancelButton.setOnClickListener(v -> {
            resetCheckedItems();
            checkIfAnyItemChecked(); // 更新按钮可见性
        });

        return view;
    }

    private void showAddRewardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("添加奖励");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_reward, null);
        builder.setView(dialogView);

        EditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        EditText scoreEditText = dialogView.findViewById(R.id.scoreEditText);

        builder.setPositiveButton("添加", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            int score = Integer.parseInt(scoreEditText.getText().toString());
            RewardItem newReward = new RewardItem(name, score);
            rewardsList.add(newReward);
            rewardsAdapter.notifyDataSetChanged();
            checkIfAnyItemChecked(); // 更新按钮可见性
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    private void removeCheckedItems() {
        int scoreToDeduct = 0;
        for (int i = rewardsList.size() - 1; i >= 0; i--) {
            if (rewardsList.get(i).isChecked()) {
                scoreToDeduct += rewardsList.get(i).getScore();
                rewardsList.remove(i);
            }
        }
        rewardsAdapter.notifyDataSetChanged();
        sharedViewModel.setTotalScore(sharedViewModel.getTotalScore().getValue() - scoreToDeduct);
    }


    private void resetCheckedItems() {
        for (RewardItem item : rewardsList) {
            item.setChecked(false);
        }
        rewardsAdapter.notifyDataSetChanged();
    }

    private void checkIfAnyItemChecked() {
        boolean anyChecked = false;
        for (RewardItem item : rewardsList) {
            if (item.isChecked()) {
                anyChecked = true;
                break;
            }
        }
        confirmButton.setVisibility(anyChecked ? View.VISIBLE : View.GONE);
        cancelButton.setVisibility(anyChecked ? View.VISIBLE : View.GONE);
    }

    // RewardItem class
    public class RewardItem {
        private String name;
        private int score;
        private boolean isChecked;

        public RewardItem(String name, int score) {
            this.name = name;
            this.score = score;
            this.isChecked = false;
        }

        // Getters and setters
        public String getName() { return name; }
        public int getScore() { return score; }
        public boolean isChecked() { return isChecked; }
        public void setChecked(boolean checked) { this.isChecked = checked; }
    }

    // RewardsAdapter class
    public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.ViewHolder> {
        private List<RewardItem> rewardsList;

        public RewardsAdapter(List<RewardItem> rewardsList) {
            this.rewardsList = rewardsList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RewardItem item = rewardsList.get(position);
            holder.nameTextView.setText(item.getName());
            holder.scoreTextView.setText(String.valueOf(item.getScore()));
            holder.checkBox.setChecked(item.isChecked());

            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                item.setChecked(isChecked);
                checkIfAnyItemChecked(); // 每当勾选状态改变时，检查是否需要显示或隐藏确认/取消按钮
            });
        }

        @Override
        public int getItemCount() {
            return rewardsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            TextView nameTextView;
            TextView scoreTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkBox);
                nameTextView = itemView.findViewById(R.id.nameTextView);
                scoreTextView = itemView.findViewById(R.id.scoreTextView);
            }
        }
    }
}
