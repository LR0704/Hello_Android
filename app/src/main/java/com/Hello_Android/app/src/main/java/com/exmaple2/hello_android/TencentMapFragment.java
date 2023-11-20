package com.exmaple2.hello_android;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exmaple2.hello_android.data.DataDownload;
import com.exmaple2.hello_android.data.ShopLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TencentMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TencentMapFragment extends Fragment {
    //    private MapView mMapView = null;
    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;
    private TencentMap TCMap;
    public TencentMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment BaiduMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TencentMapFragment newInstance(String param1, String param2) {
        TencentMapFragment fragment = new TencentMapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    public class DataDownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return new DataDownload().download(urls[0]);
        }
        @Override
        protected void onPostExecute(String responseData) {
            super.onPostExecute(responseData);
            if (responseData != null) {
                ArrayList<ShopLocation> shopLocations= new DataDownload().parseJsonObjects(responseData);
                TencentMap tencentMap = mapView.getMap();
                for (ShopLocation shopLocation : shopLocations) {
                    LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions(point1)
                            .title(shopLocation.getName());
                    Marker marker = tencentMap.addMarker(markerOptions);
                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tencent_map, container, false);
        mapView = rootView.findViewById(R.id.mapView);
        new DataDownloadTask().execute("http://file.nidama.net/class/mobile_develop/data/bookstore.json");
        TCMap = mapView.getMap();
//
        // 添加图标型Marker
        LatLng point1 = new LatLng(22.252731, 113.535649);//JNU坐标
        TCMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        // 创建一个Marker对象
        MarkerOptions markerOptions = new MarkerOptions(point1);
        //点击Marker是否弹信息框
        markerOptions.infoWindowEnable(true);//默认为true
        //信息框编辑
        markerOptions.title("暨南大学(珠海校区)")
                .snippet("坐标:(22.252702,113.53562)");
        // 添加标记到地图上
        Marker marker = TCMap.addMarker(markerOptions);
        //显示信息窗口
        marker.showInfoWindow();
        //设置Marker点击事件
        TCMap.setOnInfoWindowClickListener(new TencentMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("TAG","InfoWindow被点击时回调函数");
            }
            //  windowWidth - InfoWindow的宽度
            //windowHigh - InfoWindow的高度
            // x - 点击点在InfoWindow的x坐标点
            //y - 点击点在InfoWindow的y坐标点
            @Override
            public void onInfoWindowClickLocation(int width, int height, int x, int y) {
                Log.i("TAG","当InfoWindow点击时，点击的回调");
            }
        });
        return rootView;
    }
//    MarkerOptions markerOptions = new MarkerOptions()
//            .position(new LatLng(latitude, longitude))
//            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//            .title("Marker Title")
//            .snippet("Marker Snippet");
//    Marker marker = tencentMap.addMarker(markerOptions);

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}