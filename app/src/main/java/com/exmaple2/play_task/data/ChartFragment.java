package com.exmaple2.play_task.data;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.exmaple2.play_task.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartFragment extends Fragment {

    private LineChart chart;
    private String timePeriod; // 这个变量将决定加载哪个时间段的数据

    private void loadChartData(String timePeriod) {
        DataBank dataBank = new DataBank();
        ArrayList<Integer> scoreHistory = dataBank.loadScoreHistory(getContext());

        List<Entry> entries = new ArrayList<>();
        if (timePeriod.equals("日")) {
            entries = getDailyEntries(scoreHistory);
        } else if (timePeriod.equals("周")) {
            entries = getWeeklyEntries(scoreHistory);
        } else if (timePeriod.equals("月")) {
            entries = getMonthlyEntries(scoreHistory);
        } else if (timePeriod.equals("年")) {
            entries = getYearlyEntries(scoreHistory);
        }

        LineDataSet dataSet = new LineDataSet(entries, timePeriod + " 分数变化");
        dataSet.setColor(Color.BLUE);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setLineWidth(2f);
        dataSet.setDrawCircles(true);
        dataSet.setDrawValues(true);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh the chart
    }

    // 根据分数历史和时间段，生成Entry列表
    private List<Entry> getDailyEntries(ArrayList<Integer> scoreHistory) {
        List<Entry> entries = new ArrayList<>();
        int size = Math.min(scoreHistory.size(), 24);
        for (int i = 0; i < size; i++) {
            entries.add(new Entry(i, scoreHistory.get(i)));
        }
        return entries;
    }

    private SharedViewModel sharedViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // 获取对SharedViewModel的引用
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }
    private List<Entry> getWeeklyEntries(ArrayList<Integer> scoreHistory) {
        // 示例代码：假设每天都有一个数据点，且分数历史是按日期排序的
        List<Entry> entries = new ArrayList<>();
        // 这里假设每7个点表示一周
        for (int i = 0; i < scoreHistory.size(); i+=7) {
            int weeklyTotal = 0;
            // 累加一周内的分数
            for (int j = 0; j < 7 && i + j < scoreHistory.size(); j++) {
                weeklyTotal += scoreHistory.get(i + j);
            }
            // 将每周的总分作为数据点加入到entries中
            entries.add(new Entry(i / 7, weeklyTotal));
        }
        return entries;
    }

    private List<Entry> getMonthlyEntries(ArrayList<Integer> scoreHistory) {
        // 示例代码：假设每天都有一个数据点，且分数历史是按日期排序的
        List<Entry> entries = new ArrayList<>();
        // 假设每30个点表示一个月
        int monthlyIndex = 0;
        for (int i = 0; i < scoreHistory.size(); i+=30) {
            int monthlyTotal = 0;
            // 累加一个月内的分数
            for (int j = 0; j < 30 && i + j < scoreHistory.size(); j++) {
                monthlyTotal += scoreHistory.get(i + j);
            }
            // 将每月的总分作为数据点加入到entries中
            entries.add(new Entry(monthlyIndex++, monthlyTotal));
        }
        return entries;
    }

    private List<Entry> getYearlyEntries(ArrayList<Integer> scoreHistory) {
        // 示例代码：假设每月都有一个数据点，且分数历史是按月份排序的
        List<Entry> entries = new ArrayList<>();
        // 假设每12个点表示一年
        int yearlyIndex = 0;
        for (int i = 0; i < scoreHistory.size(); i+=12) {
            int yearlyTotal = 0;
            // 累加一年内的分数
            for (int j = 0; j < 12 && i + j < scoreHistory.size(); j++) {
                yearlyTotal += scoreHistory.get(i + j);
            }
            // 将每年的总分作为数据点加入到entries中
            entries.add(new Entry(yearlyIndex++, yearlyTotal));
        }
        return entries;
    }

    public static ChartFragment newInstance(String timePeriod) {
        ChartFragment fragment = new ChartFragment();
        Bundle args = new Bundle();
        args.putString("timePeriod", timePeriod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            timePeriod = getArguments().getString("timePeriod");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        chart = rootView.findViewById(R.id.chart); // 确保您的布局文件中有一个 id 为 chart 的 LineChart
        setupChart();
        loadChartData(timePeriod); // 这里根据时间段加载数据
        // 添加观察者来响应分数的变化
        sharedViewModel.getTotalScore().observe(getViewLifecycleOwner(), newScore -> {
            // 分数有更新时，重新加载图表数据
            refreshChart();
        });
        return rootView;
    }

    private void setupChart() {
        // 无描述文本
        chart.getDescription().setEnabled(false);

        // 启用触摸手势
        chart.setTouchEnabled(true);

        // 设置缩放和滑动
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDrawGridBackground(false);

        // 启用图表的边框
        chart.setDrawBorders(true);

        // 创建自定义动画
        chart.animateX(1500);

        // 设置背景颜色
        chart.setBackgroundColor(Color.WHITE);

        // 设置图例配置
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.BLACK);

        // 设置X轴配置
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);

        // 设置左侧Y轴配置
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setDrawGridLines(true);

        // 不显示右侧Y轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
    }


    private List<Entry> processDataForTimePeriod(ArrayList<Integer> scoreHistory, String timePeriod) {
        // 首先初始化一个空的 Entry 列表
        List<Entry> entries = new ArrayList<>();

        // 使用日历实例来帮助我们处理日期
        Calendar calendar = Calendar.getInstance();
        int currentDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        int currentWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        // 根据时间段来处理数据
        switch (timePeriod) {
            case "日":
                // 假设每小时收集一次数据
                for (int i = 0; i < 24; i++) {
                    entries.add(new Entry(i, scoreHistory.get(i)));
                }
                break;
            case "周":
                // 假设每天收集一次数据
                int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
                for (int i = 0; i < 7; i++) {
                    // 这里需要根据实际的周开始日来调整索引
                    int index = (currentDayOfYear - weekDay + i + 1) % scoreHistory.size();
                    entries.add(new Entry(i, scoreHistory.get(index)));
                }
                break;
            case "月":
                // 假设每天收集一次数据
                int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                for (int i = 0; i < daysInMonth; i++) {
                    int index = (currentDayOfYear - calendar.get(Calendar.DAY_OF_MONTH) + i) % scoreHistory.size();
                    entries.add(new Entry(i, scoreHistory.get(index)));
                }
                break;
            case "年":
                // 假设每月收集一次数据
                for (int i = 0; i < 12; i++) {
                    // 这里需要根据实际的年开始月份来调整索引
                    int index = (currentMonth - i + 12) % 12;
                    entries.add(new Entry(i, scoreHistory.get(index)));
                }
                break;
        }

        return entries;
    }


    private void styleDataSet(LineDataSet dataSet) {
        dataSet.setColor(Color.BLUE); // 线条颜色
        dataSet.setValueTextColor(Color.BLACK); // 值的文本颜色
        dataSet.setLineWidth(1.5f); // 线条宽度
        dataSet.setDrawCircles(true); // 在每个值处画圆
        dataSet.setDrawValues(true); // 画出每个点的值
        dataSet.setCircleColor(Color.BLUE); // 圆的颜色
        dataSet.setCircleRadius(3f); // 圆的半径
        dataSet.setFillColor(Color.BLUE); // 填充颜色
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // 设置为贝塞尔曲线
        dataSet.setDrawFilled(true); // 填充曲线下方区域
        dataSet.setDrawHighlightIndicators(false); // 不显示高亮指示器
        dataSet.setHighLightColor(Color.rgb(244, 117, 117)); // 高亮颜色
    }
    public void refreshChart() {
        // 重新加载图表数据的逻辑
        loadChartData(timePeriod);
    }
}