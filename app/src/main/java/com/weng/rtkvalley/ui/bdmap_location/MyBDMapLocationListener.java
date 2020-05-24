package com.weng.rtkvalley.ui.bdmap_location;

import android.annotation.SuppressLint;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;

/**
 * 获取定位数据
 *
 * @author Cheetah
 * @version v 0.1.0
 * @date 2020-05-05 18:23
 */


public class MyBDMapLocationListener extends BDAbstractLocationListener {
    private MapView mMapView;
    private TextView tvLocationText;

    public void initial(MapView mMapView, TextView tvLocationText)
    {

        this.mMapView = mMapView;
        this.tvLocationText = tvLocationText;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        //mapView 销毁后不在处理新接收的位置
        if (bdLocation == null || mMapView == null)
            return;

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                .direction(bdLocation.getDirection()) // 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude())
                .build();

        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
        String coordType = bdLocation.getCoorType();
        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        int locType = bdLocation.getLocType();

        // message out
        // 61, gps; 161, NetWorkLocation
        if(tvLocationText!=null)
            tvLocationText.setText(String.format("%.8f, %.8f, %.2f\ntype: %d",
                    bdLocation.getLongitude(), bdLocation.getLatitude(), bdLocation.getAltitude(), locType));
        mMapView.getMap().setMyLocationData(locData);
    }
}