package jp.co.jpmobile.coolguidejapan.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.metaps.analytics.Analytics;

import java.util.HashMap;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.AdvInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;

public class ADVActivity extends Activity {

    private ImageView iv_adv;
    private AdvInfo.AdvListBean advListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_adv);
        ImageView iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_adv = (ImageView) findViewById(R.id.iv_adv);
      //  iv_adv.setImageResource(R.mipmap.splash_adv);
        initData();
    }

    private void initData() {
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
        map.put("imgcode","03");

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                List<AdvInfo.AdvListBean> adv_list = ((AdvInfo) o).getAdv_list();
                advListBean = adv_list.get(0);
                Picasso.with(ADVActivity.this).load(advListBean.getBanner_url()).into(iv_adv);
                iv_adv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Analytics.trackEvent(Urlconf.MYTAPS+"click", "5m-advertise-detail");
                        UIUtils.goToWebView(ADVActivity.this, advListBean.getAdvertising_url(),"");
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
