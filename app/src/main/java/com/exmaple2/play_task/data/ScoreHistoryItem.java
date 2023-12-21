package com.exmaple2.play_task.data;
import java.io.Serializable;

public class ScoreHistoryItem implements Serializable {
    private String name;
    private int score;

    public ScoreHistoryItem(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}
