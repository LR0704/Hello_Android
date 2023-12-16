package com.exmaple2.play_task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class TaskDetailsActivity extends AppCompatActivity {

    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");
            String score = intent.getStringExtra("score");


            if(null != name){
                position = intent.getIntExtra("position",-1);
                EditText editTextItemName = findViewById(R.id.edittext_task_name);
                editTextItemName.setText(name);
            }
            if(null != score){
                position = intent.getIntExtra("position",-1);
                EditText editTextItemScore = findViewById(R.id.edittext_task_score);
                editTextItemScore.setText(score);
            }
        }
    }
}