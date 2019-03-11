package jp.co.jpmobile.coolguidejapan.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.metaps.analytics.Analytics;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.activity.SplashActivity;
import jp.co.jpmobile.coolguidejapan.activity.WebViewActivity;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;


public class BaseActivity extends AppCompatActivity  {
	protected  static String TAG_DEFAULT = "tag_default";
	protected  static String TAG_FINISH = "tag_finish";
	protected static String TAG_LOGOUT = "tag_logout";
	protected ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setLangrage();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Analytics.start(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		Analytics.stop(this);
	}

	protected void setActionBar(){

	}

	protected void setView() {

	}

	protected void showBackButon(){
		ImageView backButton = (ImageView)findViewById(R.id.actionbar_back_button);
		backButton.setVisibility(View.VISIBLE);
		backButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				goBackToPreviousPage();
			}
		});
	}



	protected void goToPage(Class classz,boolean needFinish){
		Intent intent = new Intent(this, classz);
		startActivity(intent);
		if (needFinish) {
			finish();
		}
	}

	protected void goToPageAsTop(Class classz,boolean needFinish){
		Intent intent = new Intent(this, classz);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		if (needFinish) {
			finish();
		}
	}

	protected void goToWebView(String url,String title){
		Intent intent = new Intent(this, WebViewActivity.class);intent.putExtra("url",url);
	intent.putExtra("title", title);
		startActivity(intent);
	}

	protected void goBackToPreviousPage(){
		finish();
	}


	public void showNetworkErrorDialog(){
		DialogUtils.showDialog(BaseActivity.this, getString(R.string.error_title), getString(R.string.network_error_msg),
				getString(R.string.error_button_positive), null, null, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}, null, null);
	}

	public void showErrorDialog(String msg, final String tag){
		DialogUtils.showDialog(BaseActivity.this, getString(R.string.error_title), msg, getString(R.string.error_button_positive), null, null, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (tag.equals(TAG_DEFAULT)){
					dialog.dismiss();
				}else if(tag.equals(TAG_LOGOUT)){
					dialog.dismiss();
				}else if(tag.equals(TAG_FINISH)){
					finish();
				}
			}
		}, null, null);
	}

	public void showDialogMsg(String title,String msg, final String tag){
		DialogUtils.showDialog(BaseActivity.this, title, msg, getString(R.string.error_button_positive), null, null, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (tag.equals(TAG_DEFAULT)){
					dialog.dismiss();
				}else if(tag.equals(TAG_LOGOUT)){
					dialog.dismiss();

				}else if(tag.equals(TAG_FINISH)){
					finish();
				}
			}
		}, null, null);
	}


	public void showProgressDialog(){
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setTitle("処理中...");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setCancelable(false);
		}
		if(!((Activity) this).isFinishing())
		{
			if (!mProgressDialog.isShowing())
				mProgressDialog.show();
		}
	}

	public void hideProgressDialog(){
		if (mProgressDialog != null) {
			if (mProgressDialog.isShowing()) {
				mProgressDialog.dismiss();
			}
		}
	}

	private void setLangrage() {
		if (TextUtils.isEmpty(AppUtils.getFromPreference(BaseActivity.this,AppUtils.LANGRAGE))){
			String language = getResources().getConfiguration().locale.getLanguage();
			if (language.contains("zh")){
				language = "zh";
			}else if (language.contains("ja")){
				language = "ja";
			}else {
				language = "en";
			}
			AppUtils.saveToPreference(BaseActivity.this,AppUtils.LANGRAGE,language);
		}else {
			String s = AppUtils.getFromPreference(BaseActivity.this, AppUtils.LANGRAGE);
			AppUtils.switchLanguage(BaseActivity.this,s);
		}
	}
}
