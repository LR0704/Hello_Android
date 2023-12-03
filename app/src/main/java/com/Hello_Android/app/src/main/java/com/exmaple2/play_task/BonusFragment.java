package com.exmaple2.play_task;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exmaple2.hello_android.R;
import com.exmaple2.play_task.data.DataDownload;
import com.exmaple2.play_task.data.ShopLocation;



import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BonusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BonusFragment extends Fragment {
    //    private MapView mMapView = null;
    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;
    public BonusFragment() {
        // Required empty public constructor
    }
    public static BonusFragment newInstance(String param1, String param2) {
        BonusFragment fragment = new BonusFragment();
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
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bonus, container, false);

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