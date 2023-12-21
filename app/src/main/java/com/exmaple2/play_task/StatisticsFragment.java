package com.exmaple2.play_task;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.exmaple2.play_task.data.ChartFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private LineChart chart;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_statistics, container, false);

        viewPager = rootView.findViewById(R.id.view_pager);
        tabLayout = rootView.findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        // 找到按钮并设置点击事件监听器
        Button billButton = rootView.findViewById(R.id.btn_show_bills);
        billButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建一个意图并启动 BillsActivity
                Intent intent = new Intent(getActivity(), BillsActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void setupViewPager(ViewPager viewPager) {
        StatisticsPagerAdapter adapter = new StatisticsPagerAdapter(getChildFragmentManager());
        adapter.addFragment(ChartFragment.newInstance("日"), "日");
        adapter.addFragment(ChartFragment.newInstance("周"), "周");
        adapter.addFragment(ChartFragment.newInstance("月"), "月");
        adapter.addFragment(ChartFragment.newInstance("年"), "年");
        viewPager.setAdapter(adapter);
    }

    private void updateChartData(int tabIndex) {
        // 根据选中的标签索引更新图表数据
        // ...
    }
    public class StatisticsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public StatisticsPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }

}
