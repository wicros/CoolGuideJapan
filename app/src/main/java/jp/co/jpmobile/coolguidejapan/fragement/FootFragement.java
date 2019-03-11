package jp.co.jpmobile.coolguidejapan.fragement;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.base.BaseFragment;
import jp.co.jpmobile.coolguidejapan.bean.GPSbean;
import jp.co.jpmobile.coolguidejapan.db.GpsdbUtils;


/**
 * Created by wicors on 2016/8/17.
 */
public class FootFragement extends BaseFragment implements Spinner.OnItemSelectedListener {

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private LatLng point1;
    private List<GPSbean> gpSbeanList;
    private Spinner spinner;
    private List<String> stringArrayList;
    private BitmapDescriptor bitmap;
    private List<Overlay> overlayList;
    private TextView date_tv;
    private boolean isAll;
    /**
     * 定位的客户端
     */
    private LocationClient mLocationClient;
    /**
     * 定位的监听器
     */
    public MyLocationListener mMyLocationListener;
    /**
     * 当前定位的模式
     */
    private MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
    /***
     * 是否是第一次定位
     */
    private volatile boolean isFristLocation = true;
    private double mCurrentLantitude;
    private double mCurrentLongitude;
    private int mXDirection;
    private MyOrientationListener myOrientationListener;
    private float mCurrentAccracy;

    /**
     * 初始化定位相关代码
     */
    private void initMyLocation() {
        // 定位初始化
        mLocationClient = new LocationClient(getActivity());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        // 设置定位的相关配置
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                getActivity());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_foot, null);
        mMapView = (TextureMapView) view.findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        // 设置是否显示缩放控件
        mMapView.showZoomControls(true);

        spinner = (Spinner) view.findViewById(R.id.spinner);

        date_tv = (TextView) view.findViewById(R.id.date_tv);

        SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date(System.currentTimeMillis());

        date_tv.setText("  Date: " + mformat.format(date));

        initMyLocation();

        initOritationListener();

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_gcoding);

        stringArrayList = new ArrayList<>();

        overlayList = new ArrayList<>();

        Runnable task = new Runnable() {

            @Override
            public void run() {

                List<Overlay> overlayList1 = new ArrayList<>();

                GpsdbUtils dbUtils = BaseApplication.getDbUtils();

                gpSbeanList = dbUtils.findAll();

                if (gpSbeanList == null) {
                    return;
                }

                for (GPSbean gpSbean : gpSbeanList) {

                    LatLng point = new LatLng(gpSbean.getLatitude(), gpSbean.getLongtitude());

                    if (point1 == null) {
                        point1 = point;
                    }

                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);

                    Overlay overlay = mBaiduMap.addOverlay(option);

                    overlayList1.add(overlay);

                    String[] split = gpSbean.getDatetime().split(" ");
                    if (!stringArrayList.contains(split[0])) {
                        stringArrayList.add(split[0]);
                    }
                }
                overlayList = overlayList1;
                spinner.setOnItemSelectedListener(FootFragement.this);
                BaseApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setAdapter(new SpinnerAdapter());
                    }
                });

            }
        };

        new Thread(task).start();


        MapStatus mMapStatus = new MapStatus.Builder()
                .target(point1).zoom(15).build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        isAll = (position == 0);

        if (gpSbeanList != null) {

            for (Overlay overlay : overlayList) {
                overlay.remove();
            }

            overlayList.clear();

            List<Overlay> overlayList1 = new ArrayList<>();

            for (GPSbean gpSbean : gpSbeanList) {

                if (isAll || stringArrayList.get(position - 1).equals(gpSbean.getDatetime().split(" ")[0])) {

                    LatLng point = new LatLng(gpSbean.getLatitude(), gpSbean.getLongtitude());

                    point1 = point;

                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);

                    Overlay overlay = mBaiduMap.addOverlay(option);

                    overlayList1.add(overlay);
                }
            }
            overlayList = overlayList1;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    class SpinnerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stringArrayList == null ? 0 : stringArrayList.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getActivity());
            textView.setPadding(10, 10, 10, 10);
            if (position == 0) {
                textView.setText(R.string.all);
            } else {
                textView.setText(stringArrayList.get(position - 1));
            }

            return textView;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        mMapView.onPause();
    }

    @Override
    public void onStart() {

        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }

        myOrientationListener.start();
        super.onStart();
    }

    @Override
    public void onStop() {

        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

        // 关闭方向传感器
        myOrientationListener.stop();
        super.onStop();
    }

    //-------------------------------------MylocationListener-------------------------------------------------------
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            // map view 销毁后不在处理新接收的位置
            if (bdLocation == null || mMapView == null)
                return;
            // 构造定位数据
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
            if (isFristLocation) {
                isFristLocation = false;
                LatLng ll = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }
    }

    private void center2myLoc() {
        LatLng ll = new LatLng(mCurrentLantitude, mCurrentLongitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(u);
    }

    //------------------------------------------------方向传感器-----------------------------------------------
    public static class MyOrientationListener implements SensorEventListener {

        private Context context;
        private SensorManager sensorManager;
        private Sensor sensor;

        private float lastX;

        private OnOrientationListener onOrientationListener;

        public MyOrientationListener(Context context) {
            this.context = context;
        }

        public void start() {
            sensorManager = (SensorManager) context
                    .getSystemService(Context.SENSOR_SERVICE);
            if (sensorManager != null) {
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            }

            if (sensor != null) {
                sensorManager.registerListener(this, sensor,
                        SensorManager.SENSOR_DELAY_UI);
            }

        }

        public void stop() {
            sensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                float x = event.values[SensorManager.DATA_X];

                if (Math.abs(x - lastX) > 1.0) {
                    onOrientationListener.onOrientationChanged(x);
                }
                lastX = x;

            }
        }

        public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
            this.onOrientationListener = onOrientationListener;
        }


        public interface OnOrientationListener {
            void onOrientationChanged(float x);
        }

    }

}
