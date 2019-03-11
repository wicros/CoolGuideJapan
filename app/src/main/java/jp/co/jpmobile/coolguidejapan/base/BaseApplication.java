package jp.co.jpmobile.coolguidejapan.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.baidu.mapapi.SDKInitializer;

import com.metaps.common.Metaps;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

import jp.co.jpmobile.coolguidejapan.db.GpsdbUtils;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.Base64;
import jp.co.jpmobile.coolguidejapan.utils.StringUtils;


/**
 * @创建者 Administrator
 * @创时间 2015-8-14 下午2:19:53
 * @描述 全局盒子, 里面放置一些全局的变量或者方法, Application其实是一个单例
 * @版本 $Rev: 6 $
 * @更新者 $Author: admin $
 * @更新时间 $Date: 2015-08-14 14:38:24 +0800 (星期五, 14 八月 2015) $
 * @更新描述 TODO
 */
public class BaseApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static long mMainThreadId;
    private static Thread mMainThread;
    private static RequestQueue requestQueue;
    private static String device_id;
    private static GpsdbUtils db;

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static String getDevice_id() {
        return AppUtils.getFromPreference(AppUtils.DEVICE_ID);
    }

    public static GpsdbUtils getDbUtils() {
        return db;
    }

    @Override
    public void onCreate() {

        mContext = getApplicationContext();

        mHandler = new Handler();

        mMainThreadId = android.os.Process.myTid();

        mMainThread = Thread.currentThread();

        super.onCreate();

        NoHttp.initialize(this);

        if (TextUtils.isEmpty(AppUtils.getFromPreference(AppUtils.DEVICE_ID))) {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (TextUtils.isEmpty(tm.getDeviceId()) || tm.getDeviceId().equals("000000000000000")) {
                String s = String.valueOf(System.currentTimeMillis());
                String s1 = StringUtils.string2MD5(s);
                device_id = Base64.encode(s1.getBytes());
            } else {
                device_id = tm.getDeviceId();
            }
            AppUtils.saveToPreference(AppUtils.DEVICE_ID, device_id);
        }

        requestQueue = NoHttp.newRequestQueue();

        Metaps.initialize(this, "ko035caea8ef17933e8ad9f395-97-android-i8oz");

        SDKInitializer.initialize(mContext);

        db = new GpsdbUtils(getContext());
    }
}
