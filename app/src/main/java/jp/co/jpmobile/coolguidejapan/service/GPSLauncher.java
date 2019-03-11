package jp.co.jpmobile.coolguidejapan.service;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.GPSSaveInfo;
import jp.co.jpmobile.coolguidejapan.bean.GPSbean;
import jp.co.jpmobile.coolguidejapan.bean.SendGpsLocalInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.db.GpsdbUtils;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;

/**
 * Created by wicors on 2016/7/13.
 */
public class GPSLauncher {

    private Context context;
    private GpsdbUtils dbUtils;
    private final SimpleDateFormat mformat;
    private LocationClient mLocationClient;

    public GPSLauncher(Context context){
        this.context = context;
        initMyLocation();
        dbUtils = BaseApplication.getDbUtils();
        mformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void getGPS(){
        if (!mLocationClient.isStarted()){
            mLocationClient.start();
        }
    }



    public void removeGPS(){
        if (mLocationClient.isStarted()){
            mLocationClient.stop();
        }
    }

    public void sendGPS(){

            List<GPSbean> gpSbeanList = null;

            gpSbeanList = dbUtils.findAll();

        StringBuffer stringBuffer = new StringBuffer();
            if (gpSbeanList == null || gpSbeanList.size() == 0) {
                return;
            }
            for (GPSbean gpSbean : gpSbeanList) {

                String lat = String.valueOf(gpSbean.getLatitude());
                String lon = String.valueOf(gpSbean.getLongtitude());
                String s1 = gpSbean.getDatetime() +","+lat+","+lon;
                stringBuffer.append(s1);
                stringBuffer.append("|");
            }
            String substring = "";
            if (stringBuffer.length() > 1) {
                substring = stringBuffer.substring(0, stringBuffer.length() - 1);
            }

            Request<String> stringRequest = NoHttp.createStringRequest(Urlconf.HOME_URL+Urlconf.SAVE_USER_LOCAL, RequestMethod.POST);
            stringRequest.add("token", AppUtils.getFromPreference(context, AppUtils.KEY_TOKEN));
            stringRequest.add("user_id", AppUtils.getFromPreference(context, AppUtils.KEY_USERID));
            stringRequest.add("datetime_and_gps", substring);
            stringRequest.add("deviceId", BaseApplication.getDevice_id());

            BaseApplication.getRequestQueue().add(1, stringRequest, new OnResponseListener<String>() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Response<String> response) {
                   GPSSaveInfo gpsSaveInfo =  new Gson().fromJson(response.get(),GPSSaveInfo.class);
                    Log.e("test",response.get());
                    if (gpsSaveInfo != null && gpsSaveInfo.getResult().equals(Urlconf.OK)&&gpsSaveInfo.getInsert_user_gps_local().equals(GPSSaveInfo.success)){
                        Log.e("test","send sucess");

                            clearCache();

                    }else {
                        BaseApplication.getHandler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendGPS();
                            }
                        },1000*60*60);
                    }
                }

                @Override
                public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                }

                @Override
                public void onFinish(int what) {

                }
            });


    }
//-------------------------------------clearcache------------------------------------------
    private void clearCache() {
        List<GPSbean> all = null;
            all = dbUtils.findAll();
        if (all.size() > 200){
            for (int i = 0; i < all.size(); i++) {
                    dbUtils.delete(all.get(i));
            }
            clearCache();
        }else {
            return;
        }
    }
//--------------------------------gps--------------------------------------------------------------------
    private void initMyLocation() {
        mLocationClient = new LocationClient(context);
        MyLocationListener mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation loc) {
            GPSbean gpSbean = new GPSbean();
            gpSbean.setLatitude(loc.getLatitude());
            gpSbean.setLongtitude(loc.getLongitude());
            Date date = new Date(System.currentTimeMillis());
            String s = mformat.format(date);
            gpSbean.setDatetime(s);
            dbUtils.save(gpSbean);
            checkGps(loc);
            mLocationClient.stop();
        }
    }

    private void checkGps(BDLocation loc) {

        HashMap<String, String> getInfoMap = new HashMap<>();

        getInfoMap.put("token", AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        getInfoMap.put("deviceId", BaseApplication.getDevice_id());
        getInfoMap.put("latbegin", String.valueOf(loc.getLatitude()));
        getInfoMap.put("longbegin",String.valueOf(loc.getLongitude()));

        HttpUtils.OnNetResponseListner onGetUserInfoResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                SendGpsLocalInfo sendGpsLocalInfo = (SendGpsLocalInfo) o;
                if (Urlconf.OK.equals(sendGpsLocalInfo.getResult()) && sendGpsLocalInfo.getSend_local_gps() == 1){
                    sendGPS();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };
        HttpUtils.sendGetRequet(Urlconf.GET_SEND_LOCAL_GPS,getInfoMap,onGetUserInfoResponseListner,SendGpsLocalInfo.class);
    }
}
