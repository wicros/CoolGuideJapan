package jp.co.jpmobile.coolguidejapan.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import jp.co.jpmobile.coolguidejapan.BuildConfig;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;


/**
 * Created by monkeyismeme on 16/03/22.
 */
public class AppUtils {
	public static SharedPreferences prefs;
	public static final String KEY_TOKEN = "KEY_TOKEN";
	public static final String KEY_USERID = "KEY_USERID";
	public static final String KEY_COUNTRY = "KEY_COUNTRY";
	public static final String KEY_USERNAME = "KEY_USERNAME";
	public static final String PASSNUMBER = "PASSNUMBER";
	public static final String EMAILADDR = "EMAILADDR";
	public static final String LANGRAGE = "LANGRAGE";
	public static final String GPSSERVICE = "GPSSERVICE";
	public static final String TELNUM = "TELNUM";
	public static final String SIMID = "SIMID";
	public static final String DEVICE_ID = "DEVICE_ID";
	private static LocationManager locationManager;

	public static String printKeyHash(Activity context) {
		PackageInfo packageInfo;
		String key = null;
		try {
			//getting application package name, as defined in manifest
			String packageName = context.getApplicationContext().getPackageName();

			//Retriving package info
			packageInfo = context.getPackageManager().getPackageInfo(packageName,
					PackageManager.GET_SIGNATURES);

			Log.e("Package Name=", context.getApplicationContext().getPackageName());

			for (Signature signature : packageInfo.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				key = new String(Base64.encode(md.digest(), 0));

				// String key = new String(Base64.encodeBytes(md.digest()));
				Log.e("Key Hash=", key);
			}
		} catch (NoSuchAlgorithmException e) {
			Log.e("No such an algorithm", e.toString());
		} catch (Exception e) {
			Log.e("Exception", e.toString());
		}

		return key;
	}

	public static String getDeviceID(Context context) {
		if (context == null) {
			context = BaseApplication.getContext();
		}
		return Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
	}

	public static String getAppVersion(Context context) {
		if (context == null) {
			context = BaseApplication.getContext();
		}
		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return "v" + pInfo.versionName;

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static SharedPreferences getSharedPreferenceInstance(Context context) {
		if (context == null) {
			context = BaseApplication.getContext();
		}
		return context.getSharedPreferences("japanmobile", Context.MODE_PRIVATE);
	}

	public static void saveToPreference(Context context, String key, String value) {
		SharedPreferences preferences = AppUtils.getSharedPreferenceInstance(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void saveToPreference( String key, String value) {
		Context mycontext = BaseApplication.getContext();
		SharedPreferences preferences = AppUtils.getSharedPreferenceInstance(mycontext);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getFromPreference(Context context, String key) {

		SharedPreferences preferences = AppUtils.getSharedPreferenceInstance(context);
		return preferences.getString(key, null);
	}

	public static String getFromPreference( String key) {
		Context mycontext = BaseApplication.getContext();
		SharedPreferences preferences = AppUtils.getSharedPreferenceInstance(mycontext);
		return preferences.getString(key, null);
	}

	public static boolean isDebuggable() {
		if (BuildConfig.DEBUG) {
			return true;
		}
		return false;
	}

	public static void getGPS(Context context, long minTime, float minDistance, LocationListener locationListener) {
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = locationManager.getProviders(true);
		String locationProvider = null;
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// 设置为最大精度
		criteria.setAltitudeRequired(false);//不要求海拔信息
		criteria.setBearingRequired(false);// 不要求方位信息
		criteria.setCostAllowed(true);//是否允许付费
		criteria.setPowerRequirement(Criteria.POWER_LOW);// 对电量的要求
		locationProvider = locationManager.getBestProvider(criteria,true);
		if (locationProvider == null) {
			Toast.makeText(context, "no useful provider", Toast.LENGTH_SHORT).show();
			return;
		}
		Log.e("test", "provider:" + locationProvider);
		if (! (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {


			locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener);
		} else {
			Toast.makeText(BaseApplication.getContext(), "no useful gps service", Toast.LENGTH_SHORT).show();
	     	Intent i = new Intent();
			i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			context.startActivity(i);
		}

	}


	public static void removeGPS(Context context, LocationListener locationListener) {
		if (locationManager != null &&! (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
			locationManager.removeUpdates(locationListener);
		}

	}

	public static void switchLanguage(Context context,String langanguage){
		Resources resources = context.getResources();
		Configuration configuration = resources.getConfiguration();
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		if (langanguage.equals("zh")){
			configuration.locale = Locale.CHINESE;
		}else if (langanguage.equals("ja")){
			configuration.locale = Locale.JAPANESE;
		}else {
			configuration.locale = Locale.ENGLISH;
		}
		resources.updateConfiguration(configuration,displayMetrics);
		AppUtils.saveToPreference(context,AppUtils.LANGRAGE,langanguage);
	}
}
