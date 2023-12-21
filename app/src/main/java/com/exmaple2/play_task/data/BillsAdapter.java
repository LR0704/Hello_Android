package com.exmaple2.play_task.data;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.exmaple2.play_task.R;

import java.util.List;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {

    private List<BillsItem> localDataSet;

    public BillsAdapter(List<BillsItem> dataSet) {
        localDataSet = dataSet;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewScore;

        public ViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textView_name);
            textViewScore = view.findViewById(R.id.textView_score);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewScore() {
            return textViewScore;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.bills_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        BillsItem item = localDataSet.get(position);
        viewHolder.getTextViewName().setText(item.getName());

        // 根据分数正负显示不同颜色
        if (item.getScore() >= 0) {
            viewHolder.getTextViewScore().setText("+" + item.getScore());
            viewHolder.getTextViewScore().setTextColor(Color.BLUE);
        } else {
            viewHolder.getTextViewScore().setText(String.valueOf(item.getScore()));
            viewHolder.getTextViewScore().setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}