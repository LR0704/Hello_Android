package com.exmaple2.play_task.data;

import java.io.Serializable;

public class RewardItem implements Serializable {
    private String name;
    private int score;
    private boolean isChecked;

    public RewardItem(String name, int score) {
        this.name = name;
        this.score = score;
        this.isChecked = false;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}