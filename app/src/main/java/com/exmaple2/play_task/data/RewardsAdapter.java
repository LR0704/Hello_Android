package com.exmaple2.play_task.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.R;

import java.util.List;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.ViewHolder> {
    private List<RewardItem> rewardsList;
    private OnRewardClickListener listener;

    public RewardsAdapter(List<RewardItem> rewardsList) {
        this.rewardsList = rewardsList;
    }

    // 更新接口以包含长按方法
    public interface OnRewardClickListener {
        void onRewardClick(int position); // 点击事件
        void onRewardLongClick(int position); // 长按事件
    }
    public void setOnRewardClickListener(OnRewardClickListener listener) {
        this.listener = listener;
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

        // 如果不需要对 checkbox 状态变化单独处理，可以移除这个监听器
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setChecked(isChecked);
            // 在这里调用 checkIfAnyItemChecked 方法，可能需要将此方法移动到 Fragment 中或者通过接口调用
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
            // 移除上下文菜单的设置
            // 设置长按监听器
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRewardClick(position);
                }
            });

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRewardLongClick(position);
                    return true;
                }
                return false;
            });
        }
    }
}


