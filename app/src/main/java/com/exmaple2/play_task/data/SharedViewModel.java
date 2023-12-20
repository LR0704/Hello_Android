package com.exmaple2.play_task.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<Integer> totalScore = new MutableLiveData<>();

    public void setTotalScore(int score) {
        totalScore.setValue(score); // 这将通知所有观察者
    }

    public LiveData<Integer> getTotalScore() {
        return totalScore;
    }
}
