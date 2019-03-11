package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.GetUserInfo;
import jp.co.jpmobile.coolguidejapan.bean.ResultInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;


public class SimRegistActvity extends BaseActivity implements  AdapterView.OnItemClickListener {

	private ListView mListView;
	private LinearLayout mDefaultPage;
	private SimRegisteredListAdapter mAdapter;
	private Context context;
	private List<GetUserInfo.CardInfo> cards;
	private FrameLayout pg;
	private TextView action_tv;
    private boolean editble;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sim_registed);
		context = SimRegistActvity.this;
		mListView = (ListView)findViewById(R.id.sim_regist_listview);
		mDefaultPage = (LinearLayout)findViewById(R.id.default_textView);

		pg = (FrameLayout) findViewById(R.id.progressbar);
		mAdapter = new SimRegisteredListAdapter(context);
		mListView.setAdapter(mAdapter);
		if (cards != null && cards.size()>0){
			pg.setVisibility(View.GONE);
		}

		Button goweb_1 =  (Button) findViewById(R.id.goweb_1);
		goweb_1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIUtils.goToWebView(context,"http://sim_grid.jpmob.jp/Default.aspx","Baidu");
			}
		});
		Button goweb_2 = (Button) findViewById(R.id.goweb_2);
		goweb_2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIUtils.goToWebView(context,"http://shop.yokososim.com/","Yokoso");
			}
		});
		Button goweb_3 =  (Button) findViewById(R.id.goweb_3);
		goweb_3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UIUtils.goToWebView(context,"http://shop.yokososim.com/?&locale=zh-hant","Yokoso");
			}
		});

		mListView.setOnItemClickListener(this);

		TextView title = (TextView) findViewById(R.id.actionbar_title);
		title.setText(R.string.sim_registed_list_bar_title);

		action_tv = (TextView) findViewById(R.id.action_tv);
		action_tv.setText(R.string.edit);
		action_tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editble = !editble;
				if (mAdapter != null){
					mAdapter.notifyDataSetChanged();
				}
			}
		});

		showBackButon();
		initData();
	}

	@Override
	public void onStart() {
		super.onStart();
        editble = false;
	}



	//-------------------------------------------------------------------------------------------

	private void initData() {

		HashMap<String, String> getInfoMap = new HashMap<>();
		getInfoMap.put("token",AppUtils.getFromPreference(context,AppUtils.KEY_TOKEN));
		getInfoMap.put("deviceId",BaseApplication.getDevice_id());

		HttpUtils.OnNetResponseListner onGetUserInfoResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {

			}

			@Override
			public void onSucceed(int what, Object o) {
				GetUserInfo getUserInfo = (GetUserInfo) o;

				cards = getUserInfo.getCards();
				if (Urlconf.OK.equals(getUserInfo.getResult())&& cards != null && cards.size()>0){
					mDefaultPage.setVisibility(View.GONE);
						mAdapter.notifyDataSetChanged();
				}else {
					mDefaultPage.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
				mDefaultPage.setVisibility(View.VISIBLE);
			}

			@Override
			public void onFinish(int what) {
				pg.setVisibility(View.GONE);
			}
		};
		HttpUtils.sendGetRequet(Urlconf.GET_USER_INFO,getInfoMap,onGetUserInfoResponseListner,GetUserInfo.class);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == mAdapter.getCount()-1){
            startActivity(new Intent(context,CheckTelActivity.class));
            return;
        }
		Intent intent = new Intent(context, SimDetailActivity.class);
		intent.putExtra("mode", "simlist");
		intent.putExtra("tel", cards.get(position).getCardNo());
		String id1 = cards.get(position).getId();
		AppUtils.saveToPreference(AppUtils.SIMID,id1);
		startActivity(intent);
	}


	 class ViewHolder {
		 TextView telNumTextView;
         ImageView arrowRight;
         ImageView delete;
	}

	public class SimRegisteredListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext;
		public SimRegisteredListAdapter(Context context) {
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
				return cards == null?1:cards.size()+1;

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (position == (getCount() -1)){
                TextView textView = new TextView(context);
                textView.setPadding(0,20,0,20);
                textView.setGravity(Gravity.CENTER);
                textView.setText(R.string.add_simcard);
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(getResources().getColor(R.color.add_border_color));
                return textView;
            }
			if (convertView == null || convertView instanceof  TextView) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.list_sim_item_view,null);
				holder.telNumTextView = (TextView) convertView.findViewById(R.id.telnum_textview);
                holder.arrowRight = (ImageView) convertView.findViewById(R.id.arrow_right);
                holder.delete = (ImageView) convertView.findViewById(R.id.delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

				holder.telNumTextView.setText(cards.get(position).getCardNo());
                holder.arrowRight.setVisibility(editble?View.GONE:View.VISIBLE);
                holder.delete.setVisibility(editble?View.VISIBLE:View.GONE);
                holder.delete.setOnClickListener(new DeleteClickListener(holder.telNumTextView.getText().toString()));
			return convertView;
		}
	}

    class DeleteClickListener implements View.OnClickListener{

        String number;

        public DeleteClickListener(String number){
            this.number = number;
        }

        @Override
        public void onClick(View v) {

            CustomDialog.Builder builder = new CustomDialog.Builder(context);
            builder.setMessage(getString(R.string.delete_card)+number);
            builder.setTitle(getString(R.string.toast_title));
            builder.setPositiveButton(getString(R.string.drop_out_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                        deleteSim();
                }
            });
            builder.setNegativeButton(getString(R.string.drop_out_cancel),new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

        }

        private void deleteSim() {
            HashMap<String, String> map = new HashMap<>();
            map.put("token",AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
            map.put("deviceId",BaseApplication.getDevice_id());
            map.put("cardNo",number);

            HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
                @Override
                public void onStart(int what) {

                }

                @Override
                public void onSucceed(int what, Object o) {
                    ResultInfo resultInfo = (ResultInfo) o;
                    if (Urlconf.OK.equals(resultInfo.getResult())){
                        initData();
                    }else {
                        DialogUtils.showNetworkErrorDialog(context);
                    }

                }

                @Override
                public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

                }

                @Override
                public void onFinish(int what) {

                }
            };
            HttpUtils.sendGetRequet(Urlconf.DELETE_USER_CARD,map,onNetResponseListner, ResultInfo.class);
        }

    }


}