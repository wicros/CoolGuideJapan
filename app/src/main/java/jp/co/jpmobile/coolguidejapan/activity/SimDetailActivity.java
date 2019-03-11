package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.metaps.analytics.Analytics;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.bean.SimCheckInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;


/**
 * A login screen that offers login via email/password.
 */
public class SimDetailActivity extends BaseActivity {

	// UI references.
	private TextView mPlanTextView;
	private TextView mLeftTextView;
	private TextView mStartDateTextView;
	private TextView mEndDateTextView;
	private TextView mTelNumTextView;
	private ListView mListView;
	private Button mChargeButton;
	private SimDetailListAdapter mAdapter;
	private SimCheckInfo mData;
	private String mTel = "no card";
	long currentTimeMillis = System.currentTimeMillis();
	private TextView off_textview;
	private Button mChargeHisbt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sim_detail);
		setActionBar();
		setView();
		initData();

	}

	private void initData() {


		String url = "https://jpmob.jp/prepaid/en/?tel_no_or_iccid=" + mTel + "&output_format=json";

		HttpUtils.OnNetResponseListner onSimCheckResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {
					showProgressDialog();
			}

			@Override
			public void onSucceed(int what, Object o) {
				 mData = (SimCheckInfo) o;
				if (mData != null) {
					try {
						refreshView();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

			}

			@Override
			public void onFinish(int what) {
				hideProgressDialog();
			}
		};

		HttpUtils.sendGetRequet(url, null, onSimCheckResponseListner, SimCheckInfo.class, HttpUtils.NOURL);

	}

	@Override
	protected void setActionBar(){
		TextView titleView = (TextView)findViewById(R.id.actionbar_title);
		titleView.setText(R.string.sim_detail_title);
		showBackButon();
	}


	@Override
	protected void setView() {
		mTel = getIntent().getStringExtra("tel");
		AppUtils.saveToPreference(SimDetailActivity.this,AppUtils.TELNUM,mTel);
		mPlanTextView = (TextView)findViewById(R.id.sim_detail_plan);
		mLeftTextView = (TextView)findViewById(R.id.sim_detail_left);
		mStartDateTextView = (TextView)findViewById(R.id.sim_detail_startdate);
		mEndDateTextView = (TextView)findViewById(R.id.sim_detail_enddate);
		mChargeHisbt = (Button) findViewById(R.id.btn_charge_history);
		mListView = (ListView)findViewById(R.id.sim_detail_used);
		mAdapter = new SimDetailListAdapter(this);
		mListView.setAdapter(mAdapter);

		off_textview = (TextView) findViewById(R.id.sim_detail_off);

		mChargeButton = (Button)findViewById(R.id.btn_charge);

		mChargeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Analytics.trackEvent(Urlconf.MYTAPS+"click", "charge");
				Intent intent = new Intent(SimDetailActivity.this, ChargePlanSelectListActivity.class);
				intent.putExtra("tel", mTel);
				startActivity(intent);
			}
		});

		mChargeHisbt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Analytics.trackEvent(Urlconf.MYTAPS+"click", "charge_history");
				AppUtils.saveToPreference(AppUtils.TELNUM,mTel);
				startActivity(new Intent(SimDetailActivity.this,ChargeHistoryActivity.class));
			}
		});

		mTelNumTextView = (TextView)findViewById(R.id.tel_num);
		mTelNumTextView.setText(mTel);
	}





	private void refreshView() throws ParseException {
		mAdapter.notifyDataSetChanged();
	    SimCheckInfo.IssuedCardBean issuedCardBean = mData.getIssued_card().get(0);
		//mPlanTextView
		String laugrage = AppUtils.getFromPreference(AppUtils.LANGRAGE);
		String text = null;
		if (laugrage.contains("zh")){
			text = issuedCardBean.getFields().getPlan_type().getPlan_description_chinese();
		}else if (laugrage.contains("ja")){
			text = issuedCardBean.getFields().getPlan_type().getPlan_description();
		}else {
			text = issuedCardBean.getFields().getPlan_type().getPlan_description();
		}
		mPlanTextView.setText(text);

		//mStartDateTextView
		SimCheckInfo.ActivatedCardBean activatedCardBean = mData.getActivated_card().get(0);
		mStartDateTextView.setText
				(activatedCardBean.getFields().getFirst_connection_date());
		//  mEndDateTextView
		String planExpireDate = activatedCardBean.getFields().getPlan_expiry_date();
		SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
		Date endDa = Format.parse(planExpireDate);
		long endDaTime = endDa.getTime();
		if (currentTimeMillis > endDaTime){
			mEndDateTextView.setTextColor(Color.RED);
		}else {
			mEndDateTextView.setTextColor(Color.DKGRAY);
		}
		mEndDateTextView.setText(activatedCardBean.getFields().getPlan_expiry_date());
		//mleftTextView
		if(mData.getRemaining_data() != null){

			String[] customID = {"A","B","C","D","E","F","I","J","L","M","N","O"};
			List<String> customIDs = Arrays.asList(customID);
			String custom_id = issuedCardBean.getFields().getPlan_type().getCustom_id();
			if (customIDs.contains(custom_id)){
				mLeftTextView.setText(R.string.no_limit);
			}else {
				mLeftTextView.setText(mData.getRemaining_data()+" MB");
			}
		}
//-------------------------------------customID--------------------------------------------------------------------------
			double count = 0;
			for (SimCheckInfo.UsagesBean usagesBean : mData.getUsages()) {
				count += Double.parseDouble(usagesBean.getFields().getData_multiplier_usage_in_mb());
			}
			off_textview.setText(count+" MB");
	}


	public class SimDetailListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext;
		public SimDetailListAdapter(Context context) {
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
			int count = 0;
			if (mData != null && mData.getUsages() != null){
				count = mData.getUsages().size();
			}
			return count;
		}

		public final class ViewHolder {
			public TextView dateTextView;
			public TextView netUsedTextView;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.list_sim_detail_item_view,null);
				holder.dateTextView = (TextView) convertView.findViewById(R.id.sim_detail_item_date_textview);
				holder.netUsedTextView = (TextView) convertView.findViewById(R.id.sim_detail_item_used_net);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setBackgroundResource(R.color.color_white);

			SimCheckInfo.UsagesBean usagesBean = mData.getUsages().get(position);
			holder.dateTextView.setText(usagesBean.getFields().getUsage_date());
			holder.netUsedTextView.setText(usagesBean.getFields().getData_multiplier_usage_in_mb() + "MB");
			
			return convertView;
		}
	}
}

