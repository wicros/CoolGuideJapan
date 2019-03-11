package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.paypal.android.sdk.payments.PayPalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.CurrencyInfo;
import jp.co.jpmobile.coolguidejapan.bean.ChargePlanInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;


/**
 * A login screen that offers login via email/password.
 */
public class ChargePlanSelectListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

	private ListView mListView;
	private PlanSelectListAdapter mAdapter;
	private Button mChargeButton;
	private int planValue = 0;


	private List<CurrencyInfo.ProductBean> prices = new ArrayList<>();
	private List<ChargePlanInfo.ProductBean> products = new ArrayList<>();
	private String lag;
	private String cu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charge_planselect_list);
		setView();
		setActionBar();

		String laugrage = AppUtils.getFromPreference(ChargePlanSelectListActivity.this,AppUtils.LANGRAGE);
		cu = null;
		lag = null;
		if (laugrage.contains("zh")){
			lag = "CH";
			cu = "RMB";
		}else if (laugrage.contains("ja")){
			lag = "JP";
			cu = "JPY";
		}else {
			lag = "EN";
			cu = "JPY";
		}


		getData();
	}

	@Override
	protected void setView() {
		super.setView();
		mListView = (ListView)findViewById(R.id.charge_plan_listview);
		mAdapter = new PlanSelectListAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mChargeButton = (Button)findViewById(R.id.btn_charge);
		mChargeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			Intent intent = new Intent(ChargePlanSelectListActivity.this, ChargeWayActivity.class);
				Bundle bundle = new Bundle();
				ChargePlanInfo.ProductBean product = products.get(planValue);
				bundle.putSerializable("product",product);

				for (CurrencyInfo.ProductBean price : prices) {
					if (price.getProduct_id().equals(product.getProduct_id())){
						if (cu.equals(price.getCurrency())){
							bundle.putSerializable("price",price);
						}else if ("RMB".equals(price.getCurrency())){
							intent.putExtra("RMB",price.getPrice());
						}
					}
				}


				intent.putExtra("bundle",bundle);
				startActivity(intent);
			}
		});

	}

	private  void getData(){
		HashMap<String, String> map = new HashMap<>();
		map.put("token", AppUtils.getFromPreference(ChargePlanSelectListActivity.this,AppUtils.KEY_TOKEN));
		map.put("deviceId", BaseApplication.getDevice_id());

		HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {
				showProgressDialog();
			}

			@Override
			public void onSucceed(int what, Object o) {
				if (o == null){
					showNetworkErrorDialog();
					return;
				}

				if (what == 1){
					CurrencyInfo currencyInfo = (CurrencyInfo) o;
					if (currencyInfo.getProduct() != null && currencyInfo.getProduct().size() != 0){
						prices = currencyInfo.getProduct();
					}

				}else if(what == 2){
					ChargePlanInfo chargeProInfo = (ChargePlanInfo) o;
					if (chargeProInfo.getProduct() != null && chargeProInfo.getProduct().size() != 0){
						products = chargeProInfo.getProduct();
					}
				}
				if (products.size()>0&&prices.size()>0){
					mAdapter.notifyDataSetChanged();
				}


			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
				showNetworkErrorDialog();
			}

			@Override
			public void onFinish(int what) {
				hideProgressDialog();
			}
		};

		HttpUtils.sendGetRequet(Urlconf.GET_PRODUCT_CURRENCY,map,onNetResponseListner, CurrencyInfo.class,1);


		map.put("languagecode",lag);

		HttpUtils.sendGetRequet(Urlconf.GET_PRODUCT_PRICE,map,onNetResponseListner,ChargePlanInfo.class,2);
	}









	@Override
	protected void setActionBar() {
		super.setActionBar();
		TextView titleView = (TextView)findViewById(R.id.actionbar_title);
		titleView.setText(getString(R.string.sim_plan_select_title));
		showBackButon();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		planValue = position;
		mAdapter.notifyDataSetChanged();
	}


	public final class ViewHolder {
		public TextView remainTextView;
		public TextView moneyTextView;
		public RadioButton planSelectRadioButton;
	}

	public class PlanSelectListAdapter extends BaseAdapter{
		private LayoutInflater mInflater;
		private Context mContext;
		public PlanSelectListAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
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
			return products==null?0:products.size();
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.list_charge_plan_view,null);
				holder.remainTextView = (TextView) convertView.findViewById(R.id.remain_textview);
				holder.moneyTextView = (TextView) convertView.findViewById(R.id.money_textview);
				holder.planSelectRadioButton = (RadioButton)convertView.findViewById(R.id.radio_button_plan);
				holder.planSelectRadioButton.setFocusable(false);
				holder.planSelectRadioButton.setClickable(false);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.planSelectRadioButton.setChecked(position == planValue);

			ChargePlanInfo.ProductBean product = products.get(position);
			holder.remainTextView.setText(product.getProduct_name());
			String p ="";
			for (CurrencyInfo.ProductBean price : prices) {
				if (price.getProduct_id().equals(product.getProduct_id())){
					if (cu.equals(price.getCurrency())){
						if (cu.equals("USD")){
							p = "$ "+price.getPrice();
						}else if (cu.equals("RMB")){
							p = "¥ "+price.getPrice();
						}else {
							p = "￥ "+price.getPrice();
						}
						holder.moneyTextView.setText(p);
					}
				}
			}

			return convertView;
		}
	}

	@Override
	protected void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}


}

