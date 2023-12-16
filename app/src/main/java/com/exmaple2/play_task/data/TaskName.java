package com.exmaple2.play_task.data;

import java.io.Serializable;

public class TaskName implements Serializable {


    private final String score;
    private String name;
    public String getTitle() {
        return name;
    }
    public String getScore() {
        return score;
    }
    public TaskName(String name_, String s) {
        this.name=name_;
        this.score=s;
    }
    public void setName(String name) {
        this.name = name;
    }

}
