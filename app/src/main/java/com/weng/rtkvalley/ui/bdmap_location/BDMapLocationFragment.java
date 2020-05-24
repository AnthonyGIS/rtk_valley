package com.weng.rtkvalley.ui.bdmap_location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.weng.rtkvalley.R;

public class BDMapLocationFragment extends Fragment {

    private BDMapLocationViewModel bdmapLocationViewModel;
    private MapView mMapView = null;
    private LocationClient mLocationClient=null;
    private View root = null;
    private Button btn_location = null;
    private TextView tv_location = null;
    private Button btn_changeMapType = null;
    private RadioButton rbtn_mtype_satellite = null;
    private RadioButton rbtn_mtype_normal = null;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bdmapLocationViewModel = ViewModelProviders.of(this).get(BDMapLocationViewModel.class);
        root = inflater.inflate(R.layout.fragment_bdmap_location, container, false);
        final TextView textView = root.findViewById(R.id.tv_bd_map_location);
        bdmapLocationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s);
            }
        });


        // get the controls reference.
        initialControls();

        // map initial
        initialBDMap();

        return root;
    }

    private void initialControls() {

        btn_location =  root.findViewById(R.id.btn_Locate);
        tv_location = root.findViewById(R.id.tv_bd_map_location);
        btn_changeMapType = root.findViewById(R.id.btn_LocationRecord);

        // map type
        rbtn_mtype_satellite = root.findViewById(R.id.rbtn_mtype_satlliate);
        rbtn_mtype_normal = root.findViewById(R.id.rbtn_mtype_vec);
    }

    // initial map
    public void initialBDMap()
    {
        // for baidu map
        mMapView = root.findViewById(R.id.bmapView);
        assert(mMapView != null);
        

        // 对峙地图的状态
        LatLng GEO_Zhejiang = new LatLng(30.217082, 120.158117);
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(GEO_Zhejiang);
        mMapView.getMap().setMapStatus(status);

        // 定位功能
        locationService();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // btn_location =  view.findViewById(R.id.btn_Locate);

        btn_location.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mLocationClient == null) {
                            Toast.makeText(root.getContext(), "location service is null!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!mLocationClient.isStarted()) {
                            mLocationClient.start(); // 开启地图定位图层
                            Toast.makeText(root.getContext(), "start location get from mobile", Toast.LENGTH_SHORT).show();
                            btn_location.setText("Stop Locate");

                            // 设置获取坐标的事件
                            //BDLocation tmp_location = mLocationClient.getLastKnownLocation();
                            //if(tmp_location!=null)
                            //     tv_location.setText(String.format("%f, %f", tmp_location.getLongitude(),tmp_location.getLatitude()));


                        } else {
                            mLocationClient.stop();
                            Toast.makeText(root.getContext(), "stop location get from mobile", Toast.LENGTH_SHORT).show();
                            btn_location.setText("Start");
                        }
                    }
                });

        btn_changeMapType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                // todo: change map type. 20.5.6 0040
            }
        });

        // 地图类型
        OnClickListener tmp_listener = new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                setMapMode(view);
            }
        };
        rbtn_mtype_normal.setOnClickListener(tmp_listener);
        rbtn_mtype_satellite.setOnClickListener(tmp_listener);

    }


    /**
     * 设置底图显示模式
     */
    public void setMapMode(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            // 普通图
            case R.id.rbtn_mtype_vec:
                if (checked) {
                    mMapView.getMap().setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
                break;
            // 卫星图
            case R.id.rbtn_mtype_satlliate:
                if (checked) {
                    mMapView.getMap().setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
                break;
            default:
                break;
        }
    }


    // 定位服务
    public void locationService()
    {
        // 开启定图定位图层
        mMapView.getMap().setMyLocationEnabled(true);

        //定位初始化

        mLocationClient = new LocationClient(root.getContext());
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000); //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setNeedDeviceDirect(true);//可选，设置是否需要设备方向结果
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedAltitude(true);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        //option.setOpenAutoNotifyMode();
        //option.setOpenAutoNotifyMode(1000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT); //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者

        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyBDMapLocationListener myBDMapLocationListener = new MyBDMapLocationListener();
        myBDMapLocationListener.initial(mMapView, tv_location);
        mLocationClient.registerLocationListener(myBDMapLocationListener);

        // 定位样式
        MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,
                true,
                null  // BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
        );
        mMapView.getMap().setMyLocationConfiguration(myLocationConfiguration);


        // 设置获取GPS位置的事件订阅
        // mLocationClient.registerNotify();
    }


    @Override
    public void onResume() {
        super.onResume();

        //在Fragment执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();


    }

    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // destroy location
        if(mLocationClient != null && mLocationClient.isStarted()){
            mLocationClient.stop();
            mLocationClient = null;
        }
        mMapView.getMap().setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;


    }
}
