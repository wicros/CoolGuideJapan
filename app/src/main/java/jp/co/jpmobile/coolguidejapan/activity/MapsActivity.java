package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;

import android.os.Bundle;
import android.widget.Toast;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.Point;

import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.CouponInfo;
import jp.co.jpmobile.coolguidejapan.bean.NewsInfo;
import jp.co.jpmobile.coolguidejapan.bean.NewsMapInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.view.PieProgress;

public class MapsActivity extends BaseActivity {

    TextureMapView mMapView = null;

    private Intent intent;
    private BaiduMap mBaiduMap;


    private LocationClient mLocationClient;

    public MyLocationListener mMyLocationListener;

    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;

    private double mCurrentLantitude;
    private double mCurrentLongitude;
    private int mXDirection;
    private MyOrientationListener myOrientationListener;
    private float mCurrentAccracy;
    private BitmapDescriptor bitmap;


    private void initMyLocation()
    {

        mLocationClient = new LocationClient( MapsActivity.this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }


    private void initOritationListener()
    {
        myOrientationListener = new MyOrientationListener(
                MapsActivity.this);
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener()
                {
                    @Override
                    public void onOrientationChanged(float x)
                    {
                        mXDirection = (int) x;


                        MyLocationData locData = new MyLocationData.Builder()
                                .accuracy(mCurrentAccracy)

                                .direction(mXDirection)
                                .latitude(mCurrentLantitude)
                                .longitude(mCurrentLongitude).build();

                        mBaiduMap.setMyLocationData(locData);

                        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                                .fromResource(R.mipmap.icon_marka);
                        MyLocationConfiguration config = new MyLocationConfiguration(
                                mCurrentMode, true, null);
                        mBaiduMap.setMyLocationConfigeration(config);

                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        showBackButon();

        mMapView = (TextureMapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mMapView.showZoomControls(true);

        initMyLocation();

        initOritationListener();

        bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_gcoding);

        intent = getIntent();

        //-------------------------------------------------------------------type------------------------------------------------------

        if (intent.getIntExtra("type",0) == Urlconf.COUPONMAP){

            CouponInfo.Couponbean couponbean = (CouponInfo.Couponbean) intent.getBundleExtra("bundle").getSerializable("couponbean");

            LatLng point = new LatLng(couponbean.getLatitude(), couponbean.getLongitude());

            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);

            mBaiduMap.addOverlay(option);

            MapStatus mMapStatus=new MapStatus.Builder()
                    .target(point).zoom(12
                    ).build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

            mBaiduMap.setMapStatus(mMapStatusUpdate);

        }else if (intent.getIntExtra("type",0) == Urlconf.SHOPMAP){

            LatLng point = new LatLng(intent.getDoubleExtra("lat",0.0),intent.getDoubleExtra("long",0.0));

            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);

            mBaiduMap.addOverlay(option);

            MapStatus mMapStatus=new MapStatus.Builder()
                    .target(point).zoom(12
                    ).build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

            mBaiduMap.setMapStatus(mMapStatusUpdate);
        }else if (intent.getIntExtra("type",0) == Urlconf.NEWSMAP){
            NewsInfo.NewsListBean newsListBean = (NewsInfo.NewsListBean) intent.getBundleExtra("bundle").getSerializable("newsbean");
            setNewsPoint(newsListBean);
        }

    }

    private void setNewsPoint(final NewsInfo.NewsListBean newsListBean) {

        final HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                NewsMapInfo newsMapInfo = (NewsMapInfo) o;
                if (Urlconf.OK.equals(newsMapInfo.getResult())){
                    LatLng point = null;
                    for (NewsMapInfo.NewsMapBean newsMapBean : newsMapInfo.getNewsMap()) {
                        point = new LatLng(newsMapBean.getLatitude(),newsMapBean.getLongitude());
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .icon(bitmap);

                        mBaiduMap.addOverlay(option);
                    }

                    LatLng point1 = new LatLng(mCurrentLantitude,mCurrentLongitude);

                    MapStatus mMapStatus=new MapStatus.Builder()
                            .target(point1).zoom(6).build();
                    MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);

                    mBaiduMap.setMapStatus(mMapStatusUpdate);

                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (mCurrentLantitude == 0.0 || mCurrentLongitude == 0.0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
                map.put("deviceId", AppUtils.getFromPreference(AppUtils.DEVICE_ID));
                map.put("latbegin", String.valueOf(mCurrentLantitude));
                map.put("longbegin", String.valueOf(mCurrentLongitude));
                map.put("News_id", String.valueOf(newsListBean.getId()));

                HttpUtils.sendGetRequet(Urlconf.GET_NEWSMAP, map, onNetResponseListner, NewsMapInfo.class);
            }
        };

         new Thread(runnable).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();

        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();

        mMapView.onPause();
    }

    @Override
    public void onStart()
    {

        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
        {
            mLocationClient.start();
        }

        myOrientationListener.start();
        super.onStart();
    }

    @Override
    public void onStop()
    {

        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

        myOrientationListener.stop();
        super.onStop();
    }
    //-------------------------------------MylocationListener-------------------------------------------------------
    public class MyLocationListener implements BDLocationListener
    {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            if (bdLocation == null || mMapView == null)
                return;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())

                    .direction(mXDirection).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            mCurrentAccracy = bdLocation.getRadius();

            mBaiduMap.setMyLocationData(locData);
            mCurrentLantitude = bdLocation.getLatitude();
            mCurrentLongitude = bdLocation.getLongitude();

            BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_marka);
            MyLocationConfiguration config = new MyLocationConfiguration(
                    mCurrentMode, true, null);

            mBaiduMap.setMyLocationConfigeration(config);
        }
    }


    private void center2myLoc()
    {
        LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

 //------------------------------------------------MyOrientationListener------------------------------------------------------
    public static class MyOrientationListener implements SensorEventListener
    {

        private Context context;
        private SensorManager sensorManager;
        private Sensor sensor;

        private float lastX ;

        private OnOrientationListener onOrientationListener ;

        public MyOrientationListener(Context context)
        {
            this.context = context;
        }


        public void start()
        {

            sensorManager = (SensorManager) context
                    .getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null)
            {

                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            }

            if (sensor != null)
            {
                sensorManager.registerListener(this, sensor,
                        SensorManager.SENSOR_DELAY_UI);
            }

        }

        public void stop()
        {
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {

        }

        @Override
        public void onSensorChanged(SensorEvent event)
        {

            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
            {

                float x = event.values[SensorManager.DATA_X];

                if( Math.abs(x- lastX) > 1.0 )
                {
                    onOrientationListener.onOrientationChanged(x);
                }

                lastX = x ;

            }
        }

        public void setOnOrientationListener(OnOrientationListener onOrientationListener)
        {
            this.onOrientationListener = onOrientationListener ;
        }


        public interface OnOrientationListener
        {
            void onOrientationChanged(float x);
        }

    }


}
