package com.example1.TypeC;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT); // 设置布局中的控件居中显示

        // 找到 TextView 对象
        TextView textViewHelloWorld = findViewById(R.id.text_view_hello_world);

        // 通过资源名称获取字符串
        String helloString = getString(R.string.hello_android);

        // 设置 TextView 的文本内容
        textViewHelloWorld.setText(helloString);

        ImageView imageView = new ImageView(this); // 创建ImageView控件
        imageView.setImageResource(R.drawable.my_image); // 设置ImageView的图像资源

        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageParams.addRule(RelativeLayout.BELOW, textViewHelloWorld.getId()); // 将ImageView放置在TextView下方

        relativeLayout.addView(textViewHelloWorld, params); // 添加TextView对象和TextView的布局属性
        relativeLayout.addView(imageView, imageParams); // 添加ImageView到RelativeLayout
        setContentView(relativeLayout); // 设置在Activity中显示RelativeLayout
    }
}