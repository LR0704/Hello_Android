package com.exmaple2.play_task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.exmaple2.hello_android.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    private String []tabHeaderStrings  = {"任务","奖励","统计","我"};
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
        // 根据位置返回对应的Fragment实例
             switch (position) {
                 case 0:
                     return new TaskFragment();
                 case 1:
                     return new BonusFragment();
                 case 2:
                     return new StatisticsFragment();
                 case 3:
                     return new MeFragment();
                 default:
                     return null;
            }
        }
        @Override
        public int getItemCount(){
            return NUM_TABS;
        }


    }
}