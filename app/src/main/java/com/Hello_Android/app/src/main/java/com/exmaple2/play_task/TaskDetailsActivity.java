package com.exmaple2.play_task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.exmaple2.hello_android.R;

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
        }

        Button buttonOk = findViewById(R.id.button_task_details_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText editTextItemName = findViewById(R.id.edittext_task_name);
                intent.putExtra("name",editTextItemName.getText().toString());
                intent.putExtra("key","Some result data");
                intent.putExtra("position",position);
                setResult(Activity.RESULT_OK,intent);
                TaskDetailsActivity.this.finish();
            }
        });
    }
}