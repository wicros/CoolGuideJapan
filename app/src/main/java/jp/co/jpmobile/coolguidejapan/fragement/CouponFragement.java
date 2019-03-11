package jp.co.jpmobile.coolguidejapan.fragement;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.metaps.analytics.Analytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.activity.ShopActivity;
import jp.co.jpmobile.coolguidejapan.activity.WebViewActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.base.BaseFragment;
import jp.co.jpmobile.coolguidejapan.bean.CouponInfo;
import jp.co.jpmobile.coolguidejapan.bean.SetFavInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;
import me.maxwin.view.XListView;


public class CouponFragement extends BaseFragment  {

	private XListView mListView;
	private TextView mDefaultTextView;
	private CouponListAdapter mAdapter;
	private SimpleDateFormat format;
	private int pagenumber = 1;
	private List<CouponInfo.Couponbean> mData = new ArrayList<>();

	private CouponInfo couponInfo;
	private FrameLayout pg;
	private HttpUtils.OnNetResponseListner onCouponResponseListner;

	private int type = 0;
    private boolean isAll = true;
    private TextView fav_tv;
    private TextView all_tv;
	private LocationClient mLocationClient;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragement_coupon_list, null);
		mListView = (XListView) view.findViewById(R.id.sim_regist_listview);
		pg = (FrameLayout) view.findViewById(R.id.progressbar);
		pg.setVisibility(View.GONE);
		mListView.setPullRefreshEnable(false);
		mListView.setPullLoadEnable(true);
        fav_tv = (TextView) view.findViewById(R.id.fav_tv);
        all_tv = (TextView) view.findViewById(R.id.all_tv);
		mDefaultTextView = (TextView) view.findViewById(R.id.default_textView);
		format = new SimpleDateFormat("yyyy/MM/dd");
        initMyLocation();
		if (mAdapter != null){
			mListView.setAdapter(mAdapter);
			mDefaultTextView.setVisibility(View.GONE);
		}
        setlisner();
		return view;
	}

    private void setlisner() {


        XListView.IXListViewListener listener = new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                Analytics.trackEvent(Urlconf.MYTAPS+"click", "coupon-onLoadMore");
                refreshData(2);
            }
        };

        mListView.setXListViewListener(listener);

        fav_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAll){
                    isAll = false;
                    setChange();
                }
            }
        });

        all_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAll) {
                    isAll = true;
                    setChange();
                }
            }
        });

    }

    private void setChange() {
        pagenumber = 1;
        pg.setVisibility(View.VISIBLE);
        all_tv.setBackgroundResource(isAll?R.drawable.toolbar_button_back:R.color.transparent);
        fav_tv.setBackgroundResource(isAll?R.color.transparent:R.drawable.toolbar_button_back);
		mData.clear();
        refreshData(3);
    }

    @Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
			initData();
            setChange();
	}


//-----------------------------------adapter--------------------------------------------------------------------

	public class CouponListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext;
		public CouponListAdapter(Context context) {
			if (context==null){
				context = BaseApplication.getContext();
			}
			this.mContext = context;
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
		public int getCount() {
			return mData.size();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getActivity(),R.layout.list_coupon_item_view, null);
				holder.imageView = (ImageView) convertView.findViewById(R.id.coupon_image);
				holder.textView = (TextView)convertView.findViewById(R.id.coupon_text);
				holder.fav_iv = (ImageView) convertView.findViewById(R.id.fav_iv);
                holder.share_iv = (ImageView) convertView.findViewById(R.id.share_iv);
				holder.distace_tv = (TextView) convertView.findViewById(R.id.distance_tv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			    final CouponInfo.Couponbean couponbean = mData.get(position);
					if (!TextUtils.isEmpty(couponbean.getBannerUrl())){
						Picasso.with(getActivity()).load(couponbean.getBannerUrl()).into( holder.imageView);
					}
				holder.distace_tv.setText(couponbean.getDistance()+" km");
		//----------------------------fav----------------------------------------

			if (couponbean.getIsfavorite() == 0){
				holder.fav_iv.setImageResource(R.mipmap.fav_false);
			}else {
				holder.fav_iv.setImageResource(R.mipmap.fav_true);
			}

			holder.fav_iv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					setFav(couponbean,position);
				}
			});
		//----------------------------------share--------------------------------

			holder.share_iv.setVisibility(View.GONE);
			holder.textView.setText(couponbean.getName() +  "\n"+ couponbean.getStartTime()  + "～" + couponbean.getEndTime());
				holder.imageView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						CouponInfo.Couponbean couponbean = mData.get(position);
						Analytics.trackEvent(Urlconf.MYTAPS+"click", "couponDetail");
						Intent intent;
						if (couponbean.getIs_goto_page() == 1 ){
							intent = new Intent(getActivity(), WebViewActivity.class);
							intent.putExtra("url",couponbean.getDetailUrl());
							intent.putExtra("title", "Coupon");
						}else {
							intent = new Intent(getActivity(), ShopActivity.class);
						}
						Bundle bundle = new Bundle();
						bundle.putSerializable("couponbean",couponbean);
						intent.putExtra("bundle",bundle);
						getActivity().startActivity(intent);
					}
				});

			return convertView;
		}
	}

	public final class ViewHolder {
		public ImageView imageView;
		public TextView textView;
		public ImageView fav_iv;
        public ImageView share_iv;
		public TextView distace_tv;
	}

		private void initData(){
			pg.setVisibility(View.VISIBLE);



 //-------------------------onCouponResponseListner--------------------------------------------------------

			onCouponResponseListner = new HttpUtils.OnNetResponseListner() {

				@Override
				public void onStart(int what) {

				}

				@Override
				public void onSucceed(int what, Object o) {
					couponInfo = (CouponInfo) o;

					if (couponInfo == null || !couponInfo.getResult().equals(Urlconf.OK) ){
						mDefaultTextView.setVisibility(View.VISIBLE);
						return;
					}
					if(couponInfo.getCoupongps()== null || couponInfo.getCoupongps().size() == 0){

					}else if (what == 2){
                        mDefaultTextView.setVisibility(View.GONE);
						mData.addAll(couponInfo.getCoupongps());

						if (mAdapter == null) {
							Log.e("test","mAdapter == null");
							mAdapter = new CouponListAdapter(getActivity());
							mListView.setAdapter(mAdapter);
						} else {
							mAdapter.notifyDataSetChanged();
						}
						pagenumber++;
					}else if(what == 3){
                        mDefaultTextView.setVisibility(View.GONE);
                        mData.clear();
                        mData.addAll(couponInfo.getCoupongps());

                        if (mAdapter == null) {
                            Log.e("test","mAdapter == null");
                            mAdapter = new CouponListAdapter(getActivity());
                            mListView.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                        pagenumber++;
                    }

				}

				@Override
				public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
					mDefaultTextView.setVisibility(View.VISIBLE);
				}

				@Override
				public void onFinish(int what) {
					mListView.stopLoadMore();
					pg.setVisibility(View.GONE);
				}
			};
			refreshData(2);
		};

		private void refreshData(int a){
			type = a;
            if (!mLocationClient.isStarted()){
                mLocationClient.start();
            }
		}

		private void startGetCouponByGPS(String lat, String lng) {

			HashMap<String, String> map = new HashMap<>();
			map.put("latbegin",lat);
			map.put("longbegin",lng);
			map.put("pagenumber",String.valueOf(pagenumber));
			map.put("token",AppUtils.getFromPreference(getActivity(),AppUtils.KEY_TOKEN));
			map.put("deviceId",BaseApplication.getDevice_id());
			map.put("p_user_id",AppUtils.getFromPreference(AppUtils.KEY_USERID));
            String url = isAll?Urlconf.GET_COUPON_INFO:Urlconf.GET_COUPON_BY_FAVOURITE;
			HttpUtils.sendGetRequet(url,map, onCouponResponseListner, CouponInfo.class,type);
		}


	private void setFav(final CouponInfo.Couponbean couponbean, final int position) {
		final HashMap<String, String> map = new HashMap<>();
		map.put("token",AppUtils.getFromPreference(getActivity(),AppUtils.KEY_TOKEN));
		map.put("deviceId",BaseApplication.getDevice_id());
		map.put("user_id",AppUtils.getFromPreference(AppUtils.KEY_USERID));
		map.put("coupon_id",couponbean.getId());

		HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {

			}

			@Override
			public void onSucceed(int what, Object o) {

				SetFavInfo setFavInfo = (SetFavInfo) o;

				if (Urlconf.OK.equals(setFavInfo.getResult()) && setFavInfo.getFavCode() == 1){
                    if (isAll) {
                        couponbean.setIsfavorite(couponbean.getIsfavorite() == 1 ? 0 : 1);
                        mData.set(position, couponbean);
                    }else {
                        mData.remove(position);
                    }
					mAdapter.notifyDataSetChanged();
				}else {
					DialogUtils.showErrorDialog(getResources().getString(R.string.error_server),"",getActivity());
				}
			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

			}

			@Override
			public void onFinish(int what) {

			}
		};

		HttpUtils.sendGetRequet(Urlconf.FAV_SETTING,map, onNetResponseListner, SetFavInfo.class);
	}


	//----------------------gps-------------------------------------------------------------------------

	private void initMyLocation() {
		mLocationClient = new LocationClient(getActivity());
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
		public void onReceiveLocation(BDLocation bdLocation) {
            startGetCouponByGPS(String.valueOf(bdLocation.getLatitude()),String.valueOf(bdLocation.getLongitude()));
			mLocationClient.stop();
		}
	}

}
