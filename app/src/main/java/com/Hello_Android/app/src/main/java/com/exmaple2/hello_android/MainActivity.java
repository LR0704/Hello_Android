package com.exmaple2.hello_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.exmaple2.hello_android.data.BookName;
import com.exmaple2.hello_android.data.DataBank;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String []tabHeaderStrings  = {"图书","地图","新闻","时钟"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取ViewPager2和TabLayout
        ViewPager2 viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        //创建适配器
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(fragmentAdapter);


        //TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout,viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ) .attach();
    }

    private class FragmentAdapter extends FragmentStateAdapter {
        private static final int NUM_TABS = 4;
        public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle){
            super(fragmentManager , lifecycle);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position){
        // 根据位置返回对应的Fragoent实例
             switch (position) {
                 case 0:
                     return new BookFragment();
                 case 1:
                     return new TencentMapFragment();
                 case 2:
                     return new WebViewFragment();
                 case 3:
                     return new ClockViewFragment();
                 default:
                     return null;
            }
        }
        @Override
        public int getItemCount(){
            return NUM_TABS;
        }


    }




        /*Button button = findViewById(R.id.button_change);
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
                //交换文字*/

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