package com.exmaple2.play_task;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class MeFragment extends Fragment {
    public MeFragment() {
        // Required empty public constructor
    }

    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        // 初始化数据
        initializeData(view);

        // 初始化按钮点击事件
        initializeClickListeners(view);

        return view;
    }

    private void initializeData(View view) {
        // ... 现有的初始化数据代码 ...
    }

    private void initializeClickListeners(View view) {
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> logout());
        view.findViewById(R.id.btnHelp).setOnClickListener(v -> showHelpDialog());
        view.findViewById(R.id.btnAuthorInfo).setOnClickListener(v -> showAuthorDialog());
    }

    private void logout() {
        // 执行退出登录的逻辑
    }

    private void showHelpDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("帮助")
                .setMessage("你需要什么帮助，请访问：\nhttps://github.com/CheungKinHin/Android_FinalProject/tree/%E4%BF%AE%E6%94%B9%E5%90%8E")
                .setPositiveButton("确定", null)
                .show();
    }

    private void showAuthorDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("作者信息")
                .setMessage("张键轩")
                .setPositiveButton("确定", null)
                .show();
    }

    // 获取应用版本号
    private String getAppVersion() {
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }
}
