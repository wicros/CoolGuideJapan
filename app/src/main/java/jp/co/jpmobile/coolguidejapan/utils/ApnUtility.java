package jp.co.jpmobile.coolguidejapan.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class ApnUtility {

	protected static ArrayList<HashMap<String, String>> apn_list = null;


	protected static Uri APN_LIST_URI = Uri.parse("content://telephony/carriers");


	protected static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

	private String tag = "ApnUtility";

	private int OLD_APN_ID = -1, OLD_NETWORK_TYPE = -1, NEW_APN_ID = -1,EM_APN_ID = -1;

	private String NUMERIC = "";

	private String[] EM_APN = { "CMWAP", "hnydz.ha", "460", "02","default,supl" };

	private String[] CM_APN = { "CMNET", "cmnet", "460", "02", "default,supl" };

	private ApnNode YIDONG_APN = null, YIDONG_OLD_APN = null,CHINAMOBILE_APN=null;

	private static final int NET_3G = 1, NET_WIFI = 2, NET_OTHER = -1;

	private Context context;

	public ApnUtility(Context context) {
		super();
		this.context = context;
	}

	protected void GetNumeric() {
		TelephonyManager iPhoneManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		NUMERIC = iPhoneManager.getSimOperator();
	}

	protected void InitYidongApn() {
		YIDONG_APN = new ApnNode();
		YIDONG_APN.setName(EM_APN[0]);
		YIDONG_APN.setApn(EM_APN[1]);
		YIDONG_APN.setType(EM_APN[4]);
	}

	protected void InitCMApn() {
		GetNumeric();

		CHINAMOBILE_APN = new ApnNode();
		CHINAMOBILE_APN.setName(CM_APN[0]);
		CHINAMOBILE_APN.setApn(CM_APN[1]);
		CHINAMOBILE_APN.setType(CM_APN[4]);
		CHINAMOBILE_APN.setMcc(NUMERIC.substring(0, 3));
		CHINAMOBILE_APN.setMnc(NUMERIC.substring(3, NUMERIC.length()));
	}

	protected ArrayList<HashMap<String, String>> GetApnList() {
		apn_list = new ArrayList<HashMap<String, String>>();
		Cursor cr = context.getContentResolver().query(APN_LIST_URI, null,
				null, null, null);
		// */
		while (cr != null && cr.moveToNext()) {
			Log.v(tag, cr.getString(cr.getColumnIndex("_id")) + "  "
					+ cr.getString(cr.getColumnIndex("name")) + "  "
					+ cr.getString(cr.getColumnIndex("type")) + "  ");
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", cr.getString(cr.getColumnIndex("_id")));
			map.put("apn_grid", cr.getString(cr.getColumnIndex("name")));
			map.put("name", cr.getString(cr.getColumnIndex("type")));
			apn_list.add(map);
		}

		if (apn_list.size() > 0) {
			return apn_list;
		} else {
			return null;
		}
	}

	private ConnectivityManager getConnectManager() {
		ConnectivityManager m_ConnectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (m_ConnectivityManager != null)
		{
			NetworkInfo[] info = m_ConnectivityManager.getAllNetworkInfo();

		}

		return m_ConnectivityManager;
	}

	private int getNetWorkType() {
		if (getConnectManager() != null) {
			NetworkInfo networkInfo = getConnectManager()
					.getActiveNetworkInfo();
			if (networkInfo != null)
				return networkInfo.getType();
			return -1;
		} else {
			return -1;
		}
	}

	public int GetCurrentNetType() {
		int net_type = getNetWorkType();

		if (net_type == ConnectivityManager.TYPE_MOBILE) {
			return NET_3G;
		} else if (net_type == ConnectivityManager.TYPE_WIFI) {
			return NET_WIFI;
		}

		return NET_OTHER;
	}

	public boolean IsCurrentYidongApn() {

		InitYidongApn();
		YIDONG_OLD_APN = getDefaultAPN();

		if ((YIDONG_APN.getName().equals(YIDONG_OLD_APN.getName()))
				&& (YIDONG_APN.getApn().equals(YIDONG_OLD_APN.getApn()))
				&& (YIDONG_APN.getType().equals(YIDONG_OLD_APN.getType()))) {
			return true;
		}

		return false;
	}

	public ApnNode getDefaultAPN() {
		String id = "";
		String apn = "";
		String name = "";
		String type = "";
		ApnNode apnNode = new ApnNode();
		Cursor mCursor = context.getContentResolver().query(PREFERRED_APN_URI,
				null, null, null, null);

		if (mCursor == null) {
			return null;
		}

		while (mCursor != null && mCursor.moveToNext()) {
			id = mCursor.getString(mCursor.getColumnIndex("_id"));
			name = mCursor.getString(mCursor.getColumnIndex("name"));
			apn = mCursor.getString(mCursor.getColumnIndex("apn_grid"))
					.toLowerCase();
			type = mCursor.getString(mCursor.getColumnIndex("type"))
					.toLowerCase();
		}

		try {
			OLD_APN_ID = Integer.valueOf(id);
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(context, "请配置好APN列表！", Toast.LENGTH_LONG).show();
		}

		apnNode.setName(name);
		apnNode.setApn(apn);
		apnNode.setType(type);

		return apnNode;
	}

	public boolean setDefaultApn(int apnId) {
		boolean res = false;
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put("apn_id", apnId);

		try {
			resolver.update(PREFERRED_APN_URI, values, null, null);
			Cursor c = resolver.query(PREFERRED_APN_URI, new String[] { "name",
					"apn_grid" }, "_id=" + apnId, null, null);
			if (c != null) {
				res = true;
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	public int IsYidongApnExisted(ApnNode apnNode) {
		int apnId = -1;
		Cursor mCursor = context.getContentResolver().query(APN_LIST_URI, null,
				"apn_grid like '%hnydz.ha%'", null, null);

		while (mCursor != null && mCursor.moveToNext()) {
			apnId = mCursor.getShort(mCursor.getColumnIndex("_id"));
			String name = mCursor.getString(mCursor.getColumnIndex("name"));
			String apn = mCursor.getString(mCursor.getColumnIndex("apn_grid"));
			String proxy = mCursor.getString(mCursor.getColumnIndex("proxy"));
			String type = mCursor.getString(mCursor.getColumnIndex("type"));

			if (apnNode.getName().equals(name)
					&& (apnNode.getApn().equals(apn))
					&& (apnNode.getName().equals(name))
					&& (apnNode.getType().equals(type))) {
				return apnId;
			} else {
				apnId = -1;
			}
		}

		return apnId;
	}


	public int IsCMApnExisted(ApnNode apnNode) {
		int apnId = -1;
		Cursor mCursor = context.getContentResolver().query(APN_LIST_URI, null,
				"apn_grid like '%cmnet%' or apn_grid like '%CMNET%'", null, null);
		// CMNET
		if(mCursor == null){
			addCmnetApn();
		}

		while (mCursor != null && mCursor.moveToNext()) {
			apnId = mCursor.getShort(mCursor.getColumnIndex("_id"));
			String name = mCursor.getString(mCursor.getColumnIndex("name"));
			String apn = mCursor.getString(mCursor.getColumnIndex("apn_grid"));
			String proxy = mCursor.getString(mCursor.getColumnIndex("proxy"));
			String type = mCursor.getString(mCursor.getColumnIndex("type"));

			if ((apnNode.getApn().equals(apn)) && (apnNode.getType().indexOf(type) != -1)) {
				return apnId;
			} else {
				apnId = -1;
			}
		}

		return apnId;
	}

	public int AddYidongApn() {
		int apnId = -1;
		GetNumeric();
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();

		values.put("name", EM_APN[0]);
		values.put("apn_grid", EM_APN[1]);
		values.put("type", EM_APN[4]);
		values.put("numeric", NUMERIC);
		values.put("mcc", NUMERIC.substring(0, 3));
		Log.i("mcc", NUMERIC.substring(0, 3));
		values.put("mnc", NUMERIC.substring(3, NUMERIC.length()));
		Log.i("mnc", NUMERIC.substring(3, NUMERIC.length()));
		values.put("proxy", "");
		values.put("port", "");
		values.put("mmsproxy", "");
		values.put("mmsport", "");
		values.put("user", "");
		values.put("server", "");
		values.put("password", "");
		values.put("mmsc", "");

		Cursor c = null;

		try {
			Uri newRow = resolver.insert(APN_LIST_URI, values);
			if (newRow != null) {
				c = resolver.query(newRow, null, null, null, null);
				int idindex = c.getColumnIndex("_id");
				c.moveToFirst();
				apnId = c.getShort(idindex);
				Log.d("Robert", "New ID: " + apnId
						+ ": Inserting new APN succeeded!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (c != null)
			c.close();

		return apnId;

	}

	public int Delete_Apn(int id) {
		int deleteId = -1;
		ContentResolver resolver = context.getContentResolver();
		Uri deleteIdUri = ContentUris.withAppendedId(APN_LIST_URI, id);

		try {
			deleteId = resolver.delete(deleteIdUri, null, null);
		} catch (Exception e) {
			return deleteId;
		}

		return deleteId;
	}


	public void SwitchApn() {
		switch (GetCurrentNetType()) {
			case NET_3G:
				if (!IsCurrentYidongApn()) {
					EM_APN_ID = IsYidongApnExisted(YIDONG_APN);

					if (EM_APN_ID == -1) {
						setDefaultApn(AddYidongApn());
					} else {
						setDefaultApn(EM_APN_ID);
					}
				}
				break;
			case NET_WIFI:
				closeWifiNetwork();
				break;
			case NET_OTHER:
				break;
			default:
				break;
		}
	}

	public void closeWifiNetwork(){
//		WifiManager wifiManager = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
//		Log.v("WIFI_STATE", String.valueOf(wifiManager.getWifiState()));
//
//		if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
//			wifiManager.setWifiEnabled(false);
	}


	public void StopYidongApn() {
		if (IsCurrentYidongApn()) {
			InitCMApn();
			int i = IsCMApnExisted(CHINAMOBILE_APN);

			if (i != -1) {
				setDefaultApn(i);
			}
		}
	}

	public int addCmnetApn() {
		int apnId = -1;
		GetNumeric();
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();

		values.put("name", CM_APN[0]);
		values.put("apn_grid", EM_APN[1]);
		values.put("type", EM_APN[4]);
		values.put("numeric", NUMERIC);
		values.put("mcc", NUMERIC.substring(0, 3));
		values.put("mnc", NUMERIC.substring(3, NUMERIC.length()));
		values.put("proxy", "");
		values.put("port", "");
		values.put("mmsproxy", "");
		values.put("mmsport", "");
		values.put("user", "");
		values.put("server", "");
		values.put("password", "");
		values.put("mmsc", "");

		Cursor c = null;

		try {
			Uri newRow = resolver.insert(APN_LIST_URI, values);

			if (newRow != null) {
				c = resolver.query(newRow, null, null, null, null);
				int idindex = c.getColumnIndex("_id");
				c.moveToFirst();
				apnId = c.getShort(idindex);

				Log.d("Robert", "New ID: " + apnId
						+ ": Inserting new APN succeeded!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (c != null)
			c.close();

		return apnId;
	}

	public int addJPMobileApn() {
		// TODO Auto-generated method stub
		int apnId = -1;
		GetNumeric();
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();

		values.put("name", CM_APN[0]);
		values.put("apn_grid", EM_APN[1]);
		values.put("type", EM_APN[4]);
		values.put("numeric", NUMERIC);
		values.put("mcc", NUMERIC.substring(0, 3));
		values.put("mnc", NUMERIC.substring(3, NUMERIC.length()));
		values.put("proxy", "");
		values.put("port", "");
		values.put("mmsproxy", "");
		values.put("mmsport", "");
		values.put("user", "");
		values.put("server", "");
		values.put("password", "");
		values.put("mmsc", "");

		Cursor c = null;

		try {
			Uri newRow = resolver.insert(APN_LIST_URI, values);

			if (newRow != null) {
				c = resolver.query(newRow, null, null, null, null);
				int idindex = c.getColumnIndex("_id");
				c.moveToFirst();
				apnId = c.getShort(idindex);

				Log.d("Robert", "New ID: " + apnId
						+ ": Inserting new APN succeeded!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (c != null)
			c.close();

		return apnId;
	}
}