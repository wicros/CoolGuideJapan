package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Intent;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.CouponInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;


/**
 * A login screen that offers login via email/password.
 */
public class WebViewActivity extends BaseActivity {

	private WebView mWebview;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		intent = getIntent();
		setView();
		setActionBar();
	}


	@Override
	protected void setView() {
		showBackButon();
		mWebview = (WebView) findViewById(R.id.native_webview);
		WebSettings webSettings = mWebview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		mWebview.requestFocusFromTouch();
		mWebview.setWebViewClient(webViewClient);
		mWebview.loadUrl(intent.getStringExtra("url"));
	}

	@Override
	protected void setActionBar() {
		TextView titleView = (TextView) findViewById(R.id.actionbar_title);
		String title = getIntent().getStringExtra("title");
		titleView.setText(title);
		TextView action_tv = (TextView) findViewById(R.id.action_tv);
		if (title.equals("Coupon")){
			titleView.setVisibility(View.INVISIBLE);
			action_tv.setVisibility(View.VISIBLE);
			action_tv.setText(R.string.fragement_video_title);
			action_tv.setOnClickListener(new CouponMapOnclickListener());
		}else if (title.equals("News")){
			titleView.setVisibility(View.INVISIBLE);
			action_tv.setVisibility(View.VISIBLE);
			action_tv.setText(R.string.fragement_video_title);
			action_tv.setOnClickListener(new NewsMapOnclickListener());
		}
	}

	private WebViewClient webViewClient = new WebViewClient();

	class CouponMapOnclickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent1 = new Intent(WebViewActivity.this, MapsActivity.class);
			intent1.putExtra("bundle",intent.getBundleExtra("bundle"));
			intent1.putExtra("type", Urlconf.COUPONMAP);
			startActivity(intent1);
		}
	}

	class NewsMapOnclickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			Intent intent1 = new Intent(WebViewActivity.this, MapsActivity.class);
			intent1.putExtra("bundle",intent.getBundleExtra("bundle"));
			intent1.putExtra("type",Urlconf.NEWSMAP);
			startActivity(intent1);
		}
	}
}

