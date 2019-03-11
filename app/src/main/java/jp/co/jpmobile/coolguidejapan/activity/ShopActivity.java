package jp.co.jpmobile.coolguidejapan.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.HashMap;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.CouponInfo;
import jp.co.jpmobile.coolguidejapan.bean.ShopInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;

public class ShopActivity extends BaseActivity implements AdapterView.OnItemClickListener {

            private WebView mWebview;
            private LocationClient mLocationClient;
            private CouponInfo.Couponbean couponbean;
            private List<ShopInfo.ShopBean> mShop;
            private ListView shop_lv;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_shop_ativity);
                couponbean = (CouponInfo.Couponbean) getIntent().getBundleExtra("bundle").getSerializable("couponbean");
                showBackButon();
                TextView titleView = (TextView) findViewById(R.id.actionbar_title);
                titleView.setText(R.string.shop_title);
                shop_lv = (ListView) findViewById(R.id.shop_lv);
                setwebview();
                initMyLocation();
            }

        private void initdata(BDLocation bdLocation) {
            HashMap<String, String> map = new HashMap<>();
            map.put("coupon_id",couponbean.getId());
            map.put("token", AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
            map.put("deviceId", BaseApplication.getDevice_id());
            map.put("latbegin",String.valueOf(bdLocation.getLatitude()));
            map.put("longbegin",String.valueOf(bdLocation.getLongitude()));

            HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Object o) {
                    List<ShopInfo.ShopBean> shop = ((ShopInfo) o).getShop();
                    if (shop != null && shop.size() > 0){
                        mShop = shop;
                        shop_lv.setAdapter(new ShopAdapter());
                        shop_lv.setOnItemClickListener(ShopActivity.this);
                    }
                }

                @Override
                public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                }

                @Override
                public void onFinish(int what) {

                }
            };

            HttpUtils.sendGetRequet(Urlconf.GET_SHOP,map,onNetResponseListner, ShopInfo.class);
        }

        private void setwebview() {
            mWebview = (WebView) findViewById(R.id.native_webview);
            WebViewClient webViewClient = new WebViewClient();
            WebSettings webSettings = mWebview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setSupportZoom(true);
            mWebview.requestFocusFromTouch();
            mWebview.setWebViewClient(webViewClient);
            mWebview.loadUrl(couponbean.getDetailUrl());
    }

    private void initMyLocation() {
        mLocationClient = new LocationClient(ShopActivity.this);
        MyLocationListener mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ShopInfo.ShopBean shopBean = mShop.get(position);
        Intent intent = new Intent(ShopActivity.this, MapsActivity.class);
        intent.putExtra("lat",shopBean.getLatitude());
        intent.putExtra("long",shopBean.getLongitude());
        intent.putExtra("type",Urlconf.SHOPMAP);
        startActivity(intent);
    }

    public class MyLocationListener implements BDLocationListener
    {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (mShop == null || mShop.size() == 0){
                initdata(bdLocation);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if( mLocationClient != null && !mLocationClient.isStarted())
        {
            mLocationClient.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLocationClient != null){
            mLocationClient.stop();
        }
    }

    class ShopAdapter extends BaseAdapter{

        class ViewHolder {
            TextView name;
            TextView distance;
            TextView address;
        }

        @Override
        public int getCount() {
            return mShop == null?0:mShop.size();
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
            ViewHolder holder = null;
            if (convertView == null){
                convertView = View.inflate(ShopActivity.this,R.layout.list_shop_item_view,null);
                holder = new ViewHolder();
                holder.address = (TextView) convertView.findViewById(R.id.address_tv);
                holder.name = (TextView) convertView.findViewById(R.id.name_tv);
                holder.distance = (TextView) convertView.findViewById(R.id.distance_tv);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            ShopInfo.ShopBean shopBean = mShop.get(position);
            holder.address.setText(shopBean.getAddress());
            holder.name.setText(shopBean.getName());
            holder.distance.setText(shopBean.getDistance()+" km");
            return convertView;
        }
    }
}
