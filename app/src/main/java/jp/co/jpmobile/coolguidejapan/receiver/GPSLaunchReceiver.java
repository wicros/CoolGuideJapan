package jp.co.jpmobile.coolguidejapan.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import jp.co.jpmobile.coolguidejapan.service.GPSLanuchService;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;

/**
 * Created by wicors on 2016/7/13.
 */
public class GPSLaunchReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent(context, GPSLanuchService.class);
            context.startService(intent1);
    }
}
