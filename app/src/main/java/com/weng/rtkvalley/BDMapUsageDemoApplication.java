package com.weng.rtkvalley;

import android.app.Application;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

/**
 * 百度地图的初始化类
 *
 * @author Cheetah
 * @version v 0.1.0
 * @date 2020-05-05 17:41
 */


public class BDMapUsageDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // for bd map. 20.5.5
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }
}