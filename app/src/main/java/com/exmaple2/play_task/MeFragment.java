package com.exmaple2.play_task;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        // 初始化数据
        initializeData(view);

        return view;
    }

    private void initializeData(View view) {
        // 获取应用版本号
        String appVersion = getAppVersion();

        // 示例数据 - 替换为你的实际数据
        String username = "张键轩";
        String userBio = "学无止境";

        // 设置文本内容
        TextView tvAppVersion = view.findViewById(R.id.tvAppVersion);
        TextView tvUsername = view.findViewById(R.id.tvUsername);
        TextView tvUserBio = view.findViewById(R.id.tvUserBio);

        tvAppVersion.setText("应用版本: " + appVersion);
        tvUsername.setText("用户名: " + username);
        tvUserBio.setText("个人简介: " + userBio);
    }

    // 示例方法：获取应用版本号
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
