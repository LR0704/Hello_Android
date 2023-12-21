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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.data.DataBank;
import com.exmaple2.play_task.data.RewardItem;
import com.exmaple2.play_task.data.RewardsAdapter;
import com.exmaple2.play_task.data.SharedViewModel;
import com.exmaple2.play_task.data.ScoreHistoryItem; // 如果有这个类
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BonusFragment extends Fragment {
    private RecyclerView rewardsRecyclerView;
    private List<RewardItem> rewardsList;
    private RewardsAdapter rewardsAdapter;
    private TextView totalScoreView;
    private SharedViewModel sharedViewModel;
    private OnRewardRedeemedListener rewardRedeemedListener;

    public interface OnRewardRedeemedListener {
        void onRewardRedeemed(int scoreDeducted);
    }

    public void setOnRewardRedeemedListener(OnRewardRedeemedListener listener) {
        this.rewardRedeemedListener = listener;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bonus, container, false);
        initViews(view);
        setupRecyclerView();
        observeTotalScore();
        setupAddRewardButton(view);
        return view;
    }

    private void initViews(View view) {
        rewardsRecyclerView = view.findViewById(R.id.rewardsRecyclerView);
        totalScoreView = view.findViewById(R.id.total_score_view);
    }

    private void observeTotalScore() {
        sharedViewModel.getTotalScore().observe(getViewLifecycleOwner(), score -> {
            totalScoreView.setText(getString(R.string.total_score, score));
            checkIfAnyItemChecked();
        });
    }

    private void setupAddRewardButton(View view) {
        FloatingActionButton addRewardButton = view.findViewById(R.id.addRewardButton);
        addRewardButton.setOnClickListener(v -> showAddRewardDialog());
    }

    private void showAddRewardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("添加奖励");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_reward, null);
        final EditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        final EditText scoreEditText = dialogView.findViewById(R.id.scoreEditText);

        builder.setView(dialogView);
        builder.setPositiveButton("添加", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            int score = Integer.parseInt(scoreEditText.getText().toString());
            addReward(name, score);
        });

        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void setupRecyclerView() {
        rewardsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DataBank dataBank = new DataBank();
        rewardsList = dataBank.loadRewards(getContext());
        rewardsAdapter = new RewardsAdapter(rewardsList);
        rewardsAdapter.setOnRewardClickListener(new RewardsAdapter.OnRewardClickListener() {
            @Override
            public void onRewardClick(int position) {
                confirmRedeemReward(position);
            }

            @Override
            public void onRewardLongClick(int position) {
                showDeleteConfirmationDialog(position);
            }
        });
        rewardsRecyclerView.setAdapter(rewardsAdapter);
    }

    private void showDeleteConfirmationDialog(int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("删除奖励")
                .setMessage("您确定要删除这个奖励吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    removeReward(position);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void confirmRedeemReward(int position) {
        RewardItem rewardToRedeem = rewardsList.get(position);
        new AlertDialog.Builder(getContext())
                .setTitle("兑换奖励")
                .setMessage("您确定要兑换这个奖励吗？")
                .setPositiveButton("兑换", (dialog, which) -> {
                    redeemReward(rewardToRedeem, position);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void redeemReward(RewardItem reward, int position) {
        int scoreToDeduct = reward.getScore();
        rewardsList.remove(position);
        rewardsAdapter.notifyItemRemoved(position);
        new DataBank().saveRewards(getContext(), new ArrayList<>(rewardsList));

        int newTotalScore = sharedViewModel.getTotalScore().getValue() - scoreToDeduct;
        sharedViewModel.setTotalScore(newTotalScore);
        new DataBank().saveScore(getContext(), newTotalScore);

        DataBank dataBank = new DataBank();
        ArrayList<ScoreHistoryItem> scoreHistory = dataBank.loadScoreHistory(getContext());
        scoreHistory.add(new ScoreHistoryItem(reward.getName(), -scoreToDeduct)); // 添加奖励的历史记录
        dataBank.saveScoreHistory(getContext(), scoreHistory);

        if (rewardRedeemedListener != null) {
            rewardRedeemedListener.onRewardRedeemed(scoreToDeduct);
        }
        // 更新SharedViewModel中的总分数
        if (getActivity() instanceof MainActivity) {
            SharedViewModel viewModel = new ViewModelProvider((MainActivity)getActivity()).get(SharedViewModel.class);
            viewModel.setTotalScore(newTotalScore); // 这里设置新的总分数
        }
        if (getActivity() instanceof MainActivity) {
            ((MainActivity)getActivity()).refreshChartFragment(); // 调用MainActivity中的方法刷新图表
        }
    }

    private void addReward(String name, int score) {
        RewardItem newReward = new RewardItem(name, score);
        rewardsList.add(newReward);
        rewardsAdapter.notifyDataSetChanged();
        new DataBank().saveRewards(getContext(), new ArrayList<>(rewardsList));
    }

    private void removeReward(int position) {
        rewardsList.remove(position);
        rewardsAdapter.notifyItemRemoved(position);
        new DataBank().saveRewards(getContext(), new ArrayList<>(rewardsList));
    }

    private void checkIfAnyItemChecked() {
        boolean anyChecked = false;
        for (RewardItem item : rewardsList) {
            if (item.isChecked()) {
                anyChecked = true;
                break;
            }
        }
    }
}
