package jp.co.jpmobile.coolguidejapan.utils;



import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;

import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.activity.CheckTelActivity;
import jp.co.jpmobile.coolguidejapan.activity.LoginActivity;
import jp.co.jpmobile.coolguidejapan.activity.MainActivity;
import jp.co.jpmobile.coolguidejapan.activity.UserInfoActivity;
import jp.co.jpmobile.coolguidejapan.activity.WebViewActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.GetUserInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;

/**
 * @创建者	 Administrator
 * @创时间 	 2015-8-14 下午2:27:07
 * @描述	     和ui相关的一些静态工具方法
 *
 * @版本       $Rev: 8 $
 * @更新者     $Author: admin $
 * @更新时间    $Date: 2015-08-14 17:44:25 +0800 (星期五, 14 八月 2015) $
 * @更新描述    TODO
 */
public class UIUtils {

	private static Context context = BaseApplication.getContext();

	private static GetUserInfo getUserInfo;

	/**得到上下文*/
	public static Context getContext() {
		return BaseApplication.getContext();
	}

	/**得到resouce对象*/
	public static Resources getResources() {
		return getContext().getResources();
	}

	// string.xml-->string-->arr
	/**得到string.xml中的一个字符串*/
	public static String getString(int resId) {
		return getResources().getString(resId);
	}

	/**得到string.xml中的一个字符串数组*/
	public static String[] getStringArr(int resId) {
		return getResources().getStringArray(resId);
	}

	/**得到color.xml中的颜色值*/
	public static int getColor(int colorId) {
		return getResources().getColor(colorId);
	}

	/**得到应用程序的包名*/
	public static String getPackageName() {
		return getContext().getPackageName();
	}

	/**得到主线程id*/
	public static long getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	/**得到一个主线程的handler*/
	public static Handler getMainThreadHandler() {
		return BaseApplication.getHandler();
	}
	/**安全的执行一个task*/
	public static void postTaskSafely(Runnable task) {
		long curThreadId = android.os.Process.myTid();
		long mainThreadId = getMainThreadId();
		// 如果当前线程是主线程
		if (curThreadId == mainThreadId) {
			task.run();
		} else {// 如果当前线程不是主线程
			getMainThreadHandler().post(task);
		}

	}

	public static void  loginSucess(Context context){
		String country = AppUtils.getFromPreference(context, AppUtils.KEY_COUNTRY);
		if (!TextUtils.isEmpty(country))
		{
			CheckSim(context);
		} else {
			context.startActivity(new Intent(context,UserInfoActivity.class));
		}
	}


	public static void CheckSim(final Context context){


		HashMap<String, String> getInfoMap = new HashMap<>();
		getInfoMap.put("token",AppUtils.getFromPreference(context,AppUtils.KEY_TOKEN));
		getInfoMap.put("deviceId",BaseApplication.getDevice_id());

		HttpUtils.OnNetResponseListner onGetUserInfoResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {

			}

			@Override
			public void onSucceed(int what, Object o) {

					getUserInfo = (GetUserInfo) o;
				if (getUserInfo != null  &&  Urlconf.OK.equals(getUserInfo.getResult())){
					if (getUserInfo.getCards() != null ){
						context.startActivity(new Intent(context,MainActivity.class));
					}else {
						context.startActivity(new Intent(context,CheckTelActivity.class));
					}
				}else {
					DialogUtils.showNetworkErrorDialog(context);
					context.startActivity(new Intent(context,LoginActivity.class));
				}

			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

			}

			@Override
			public void onFinish(int what) {

			}
		};
		HttpUtils.sendGetRequet(Urlconf.GET_USER_INFO,getInfoMap,onGetUserInfoResponseListner,GetUserInfo.class);
	}

	public static void goToWebView(Context context ,String url,String title){
		Intent intent = new Intent(context, WebViewActivity.class);
		intent.putExtra("url",url);
		intent.putExtra("title", title);
		context.startActivity(intent);
	}



	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变

	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变

	 */
	public static int dip2px( float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变

	 */
	public static int px2sp(float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变

	 */
	public static int sp2px( float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}
