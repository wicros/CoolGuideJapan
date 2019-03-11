package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.metaps.analytics.Analytics;

import java.util.HashMap;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.AdvInfo;
import jp.co.jpmobile.coolguidejapan.bean.SaveUserInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.service.GPSLanuchService;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;
import jp.co.jpmobile.coolguidejapan.view.PieProgress;

import static java.lang.Thread.sleep;

public class SplashActivity extends BaseActivity {

	private PieProgress pieProgress;
	private TextView progress_tv;
	int count = 3;
	int a = 0;
    private Thread thread2;
    private ImageView splash_iv;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		pieProgress = (PieProgress) findViewById(R.id.pie_progress);
		progress_tv = (TextView) findViewById(R.id.progress_tv);
        splash_iv = (ImageView) findViewById(R.id.splash_tv);
        initData();
    }

	@Override
	protected void onResume() {
		super.onResume();
        count = 3;

        progress_tv.setText(count+"");
        //autoLogin();
        thread2 = new Thread() {


            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                while (true){
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long disTime = System.currentTimeMillis() - startTime;

                    if (disTime >= 1000 ){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (count >0){
                                    count --;
                                }
                                progress_tv.setText(count+"");
                                pieProgress.setProgress(0);


                            }
                        });

                        if (count <= 1 ){

							//startActivity(new Intent(SplashActivity.this, UserInfoActivity.class));
							autoLogin();
                            break;
                        }

                        startTime = startTime + disTime;
                    }else {
                        a = (int) ((double)disTime/(double) 1000 * 360);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pieProgress.setProgress(a);
                            }
                        });
                    }

                }

            }
        };
		thread2.start();
	}



	private void setService() {
		String preference = AppUtils.getFromPreference(this, AppUtils.GPSSERVICE);
		Intent intent1 = new Intent(this, GPSLanuchService.class);
		startService(intent1);


	}

	private void autoLogin()
	{
        setService();
		String s2 = AppUtils.getFromPreference(this, AppUtils.KEY_TOKEN);
		String s3 = AppUtils.getFromPreference(this, AppUtils.KEY_USERID);
		String s4 = AppUtils.getFromPreference(this, AppUtils.KEY_USERNAME);

		if ( !TextUtils.isEmpty(s2) && !TextUtils.isEmpty(s3) && !TextUtils.isEmpty(s4)){
		;
			UIUtils.loginSucess(SplashActivity.this);
		}else {
			startActivity(new Intent(SplashActivity.this,LoginActivity.class));
            finish();
		}
	}

    @Override
    protected void onStop() {
        super.onStop();
        thread2 = null;
    }

    private void initData() {
        if (TextUtils.isEmpty(AppUtils.getFromPreference(AppUtils.KEY_TOKEN))){
            autoLogin();
            return;
        }


        HashMap<String, String> map = new HashMap<>();
        String code;
        String laugrage = AppUtils.getFromPreference(AppUtils.LANGRAGE);
        if (laugrage.contains("zh")){
            code = "CH";
        }else if (laugrage.contains("ja")){
            code = "JP";
        }else {
            code = "EN";
        }



        map.put("languagecode",code);
        map.put("token", AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        map.put("deviceId", BaseApplication.getDevice_id());
        map.put("imgcode","02");

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                List<AdvInfo.AdvListBean> adv_list = ((AdvInfo) o).getAdv_list();
                final AdvInfo.AdvListBean advListBean = adv_list.get(0);
                Picasso.with(SplashActivity.this).load(advListBean.getBanner_url()).into(splash_iv);
                splash_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Analytics.trackEvent(Urlconf.MYTAPS+"click", "splah imageview");
                        UIUtils.goToWebView(SplashActivity.this,advListBean.getAdvertising_url(),"");
                    }
                });
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };
        HttpUtils.sendGetRequet(Urlconf.GET_HOME_PAGE_ADV,map,onNetResponseListner, AdvInfo.class);
    }

}

