package jp.co.jpmobile.coolguidejapan.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;



import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;

/**
 * Created by wicors on 2016/7/13.
 */
public class GPSLanuchService extends Service {

    private GPSLauncher gpsLauncher;
    private Runnable gpsTask;
    private static  final int runTime = 5*1000*60;
    private static  final int sendTime = 2*60*60*1000;
    private Handler handler;
    private Runnable gpsSendTask;


    @Override
    public void onCreate() {
        super.onCreate();
        handler =  BaseApplication.getHandler();
        gpsLauncher = new GPSLauncher(this);

        gpsTask = new Runnable() {
            @Override
            public void run() {

                gpsLauncher.getGPS();
                handler.postDelayed(gpsTask, runTime);
            }
        };

        gpsSendTask = new Runnable() {
             @Override
             public void run() {

                 gpsLauncher.sendGPS();
                 handler.postDelayed(gpsSendTask, sendTime);
             }
         };

        handler.postDelayed(gpsSendTask,sendTime);
        handler.post(gpsTask);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"service start",Toast.LENGTH_SHORT);

        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        AppUtils.saveToPreference(this,AppUtils.GPSSERVICE,"");
        gpsLauncher = null;

    }



}
