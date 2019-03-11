package jp.co.jpmobile.coolguidejapan.fragement;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.metaps.analytics.Analytics;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.activity.AcountInfoActivity;
import jp.co.jpmobile.coolguidejapan.activity.AgreementActivity;
import jp.co.jpmobile.coolguidejapan.activity.LangrageChangeActivity;
import jp.co.jpmobile.coolguidejapan.activity.SimSetActivity;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;
import jp.co.jpmobile.coolguidejapan.activity.EmailEnterActivity;
import jp.co.jpmobile.coolguidejapan.activity.HelpActivity;
import jp.co.jpmobile.coolguidejapan.activity.LoginActivity;
import jp.co.jpmobile.coolguidejapan.activity.WebViewActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseFragment;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;


public class AppSetFragement extends BaseFragment {

	private ListView mListView;
	private AppSettingListAdapter mAdapter;




	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragement_app_setting,null);
		mAdapter = new AppSettingListAdapter(getContext());
		mListView = (ListView)view.findViewById(R.id.app_setting_list);
		mListView.setAdapter(mAdapter);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	public final class ViewHolder {
		public TextView titleTextView;
	}

	public class AppSettingListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext;
		public AppSettingListAdapter(Context context) {
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
			return 7;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null || convertView.getTag() instanceof  String) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.list_app_setting_view,null);
				holder.titleTextView = (TextView) convertView.findViewById(R.id.app_setting_title);

				convertView.setTag(holder);
			} else {

					holder = (ViewHolder) convertView.getTag();
			}
			switch (position){
				case 0:
					holder.titleTextView.setText(R.string.app_setting_account_info);
					break;
				case 1:
					holder.titleTextView.setText(R.string.simset_title);
					break;
				case 2:
					holder.titleTextView.setText(R.string.app_setting_help);
					break;
				case 3:
					holder.titleTextView.setText(R.string.app_setting_agreement);
					break;
				case 4:
					holder.titleTextView.setText(R.string.app_setting_change_password);
					break;
				case 5:
					holder.titleTextView.setText(R.string.change_langrage);
					break;
				case 6:
					holder.titleTextView.setText(R.string.drop_out_app);
					break;
				default:
					break;
			}

			convertView.setTag(holder.titleTextView.getText());
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					String title = (String)view.getTag();
					switch (position) {
						case 0:
							//账户情报
							Analytics.trackEvent(Urlconf.MYTAPS+"click", "openAcountActivity");
							openAcountActivity();
							break;
						case 1:
							//账户情报
							Analytics.trackEvent(Urlconf.MYTAPS+"click", "openSimSetActivity");
							startActivity(new Intent(getActivity(), SimSetActivity.class));
							break;
						case 2:
							//帮助页面
							Analytics.trackEvent(Urlconf.MYTAPS+"click", "openHelpActivity");
							openHelpActivity();

							break;
						case 3:
							//协议页面
							Analytics.trackEvent(Urlconf.MYTAPS+"click", "goAgreementPage");
							goAgreementPage(title);
							break;
						case 4:
							//变更密码
							Analytics.trackEvent(Urlconf.MYTAPS+"click", "openEmailEnterActivity");
							openEmailEnterActivity();
							break;
						case 5:
							//语言变更
							Analytics.trackEvent(Urlconf.MYTAPS+"click", "openlangrageActivity");
							openlangrageActivity();
							break;
						case 6:
							CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
							builder.setMessage(getString(R.string.drop_out_prompt));
							builder.setTitle(getString(R.string.toast_title));
							builder.setPositiveButton(getString(R.string.drop_out_ok), new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
									closeAllActivity();
								}
							});
							builder.setNegativeButton(getString(R.string.drop_out_cancel),new android.content.DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.dismiss();
								}
							});
							builder.create().show();

							break;
						default:
							break;
					}
				}
			});

			return convertView;
		}
	}



	public void goAgreementPage(String title) {
		startActivity(new Intent(getActivity(), AgreementActivity.class));
	}

	private void openlangrageActivity(){
		startActivity(new Intent(getActivity(), LangrageChangeActivity.class));
	}

	private void openAcountActivity(){
		Intent intent=new Intent();
		intent.setClass(getActivity(), AcountInfoActivity.class);
		startActivity(intent);

	}

	private void closeAllActivity()
	{


		// clear saved login tokens etc
		AppUtils.saveToPreference(getContext(),AppUtils.KEY_TOKEN,"");
		AppUtils.saveToPreference(getContext(),AppUtils.KEY_COUNTRY,"");
		AppUtils.saveToPreference(getContext(),AppUtils.KEY_USERID,"");
		AppUtils.saveToPreference(getContext(),AppUtils.KEY_USERNAME, "");
		Intent intent = new Intent(getActivity(), LoginActivity.class);
		startActivity(intent);
		getActivity().finish();
	}

	private void openEmailEnterActivity(){
		Intent intent=new Intent();
		intent.setClass(getActivity(), EmailEnterActivity.class);
		getActivity().startActivity(intent);
	}

	private void openHelpActivity(){
		Intent intent=new Intent();
		intent.setClass(getActivity(), HelpActivity.class);
		getActivity().startActivity(intent);
	}

	protected void goToWebView(String url,String title){
     	Intent intent = new Intent(getActivity(), WebViewActivity.class);
		intent.putExtra("url",url);
		intent.putExtra("title", title);
		startActivity(intent);
	}


}