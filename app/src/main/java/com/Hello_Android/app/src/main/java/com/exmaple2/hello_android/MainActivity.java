package com.exmaple2.hello_android;

import static android.app.ProgressDialog.show;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Button;
import android.app.AlertDialog;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button_change);
        button.setText(R.string.button1);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Button clickedButton = (Button) view;
                TextView text_hello = findViewById(R.id.text_View_1);
                TextView text_jnu = findViewById(R.id.text_View_2);

                CharSequence text1 = text_hello.getText();
                CharSequence text2 = text_jnu.getText();
                text_jnu.setText(text1);
                text_hello.setText(text2);

                Toast.makeText(MainActivity.this,"交换成功", Toast.LENGTH_SHORT).show();
                showDialog(MainActivity.this, "", "交换成功！");
            };
        });
    };
        private void showDialog(MainActivity context, String title, String message)
        {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("")
                .setMessage(message)
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 在确定按钮点击时执行的逻辑
                    }
                })

                .show();
                //交换文字

        /*// 创建 RelativeLayout
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setBackgroundColor(Color.WHITE);

        // 创建 TextView
        TextView textViewHelloWorld = new TextView(this);
        textViewHelloWorld.setText(R.string.hello_android);

        // 创建 TextView 的布局参数
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //文字大小
        textViewHelloWorld.setTextSize(24);
        // 调整文本视图的位置
        textParams.leftMargin = 50;
        textParams.topMargin = 10;

        // 添加 TextView 到 RelativeLayout
        relativeLayout.addView(textViewHelloWorld, textParams);

        // 创建 ImageView
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.my_image);

        // 创建 ImageView 的布局参数
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.BELOW, textViewHelloWorld.getId());
        imageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        // 添加 ImageView 到 RelativeLayout
        relativeLayout.addView(imageView, imageParams);

        // 设置 RelativeLayout 作为主视图
        setContentView(relativeLayout); */
        //相对布局
    }





}