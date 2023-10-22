package com.exmaple2.hello_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookItemDetailsActivity extends AppCompatActivity {

    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item_details);

        Intent intent = getIntent();
        if(intent != null){
            String name = intent.getStringExtra("name");


            if(null != name){
                position = intent.getIntExtra("position",-1);
                EditText editTextItemName = findViewById(R.id.edittext_book_name);
                editTextItemName.setText(name);
            }
        }

        Button buttonOk = findViewById(R.id.button_book_details_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText editTextItemName = findViewById(R.id.edittext_book_name);
                intent.putExtra("name",editTextItemName.getText().toString());
                intent.putExtra("key","Some result data");
                intent.putExtra("position",position);
                setResult(Activity.RESULT_OK,intent);
                BookItemDetailsActivity.this.finish();
            }
        });
    }
}