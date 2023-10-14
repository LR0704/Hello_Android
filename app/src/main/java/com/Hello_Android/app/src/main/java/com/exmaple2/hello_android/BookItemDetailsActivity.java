package com.exmaple2.hello_android;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_item_details);

        Button buttonok = findViewById(R.id.button_book_details_ok);
        buttonok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EditText editTextItemName = findViewById(R.id.edittext_book_name);
                intent.putExtra("name",editTextItemName.getText().toString());
                intent.putExtra("key","Some result data");
                setResult(Activity.RESULT_OK,intent);
                BookItemDetailsActivity.this.finish();
            }
        });
    }
}