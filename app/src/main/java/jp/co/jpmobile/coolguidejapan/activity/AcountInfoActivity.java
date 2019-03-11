package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.UserOtherInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;

public class AcountInfoActivity extends BaseActivity {

    private TextView email_title;
    private TextView country_tiltle;
    private TextView gender_title;
    private TextView birth_title;
    private TextView email_tv;
    private TextView country_tv;
    private TextView gender_tv;
    private TextView birth_tv;
    private TextView nick_title;
    private TextView nick_tv;
    private UserOtherInfo userOtherInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_info);
        setActionBar();
        nick_title = (TextView) findViewById(R.id.nick_title);
        email_title = (TextView) findViewById(R.id.email_title);
        country_tiltle = (TextView) findViewById(R.id.country_tiltle);
        gender_title = (TextView) findViewById(R.id.gender_title);
        birth_title = (TextView) findViewById(R.id.birth_title);
        nick_tv = (TextView) findViewById(R.id.nick_tv);
        email_tv = (TextView) findViewById(R.id.email_tv);
        country_tv = (TextView) findViewById(R.id.country_tv);
        gender_tv = (TextView) findViewById(R.id.gender_tv);
        birth_tv = (TextView) findViewById(R.id.birth_tv);
        Button editBt  = (Button) findViewById(R.id.edit);
        editBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcountInfoActivity.this, EditUserInfoActivity.class);
                if (userOtherInfo != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("userinfo",userOtherInfo);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }
            }
        });

        nick_title.setText(R.string.nickname);
        email_title.setText(R.string.acount_user_name);
        country_tiltle.setText(R.string.acount_country);
        birth_title.setText(R.string.acount_birthday);
        gender_title.setText(R.string.acount_gender);
        initData();
    }


    private void initData() {

        HashMap<String, String> getInfoMap = new HashMap<>();

        String laugrage = AppUtils.getFromPreference(AcountInfoActivity.this,AppUtils.LANGRAGE);
        String code ;
        if (laugrage.contains("zh")){
            code = "CH";
        }else if (laugrage.contains("ja")){
            code = "JP";
        }else {
            code = "EN";
        }

        getInfoMap.put("token", AppUtils.getFromPreference(AcountInfoActivity.this,AppUtils.KEY_TOKEN));
        getInfoMap.put("deviceId", BaseApplication.getDevice_id());
        getInfoMap.put("languagecode", code);
        getInfoMap.put("user_id",AppUtils.getFromPreference(AcountInfoActivity.this,AppUtils.KEY_USERID));

        HttpUtils.OnNetResponseListner onGetUserInfoResponseListner = new HttpUtils.OnNetResponseListner() {

            @Override
            public void onStart(int what) {


            }

            @Override
            public void onSucceed(int what, Object o) {
                userOtherInfo = (UserOtherInfo) o;
                    if (userOtherInfo != null && userOtherInfo.getResult().equals(Urlconf.OK)){
                        nick_tv.setText(userOtherInfo.getNickname());
                        email_tv.setText(userOtherInfo.getEmail_address());
                        country_tv.setText(userOtherInfo.getFrom_country());
                        gender_tv.setText(userOtherInfo.getGender());
                        birth_tv.setText(userOtherInfo.getBirthdate());
                    }else {
                        DialogUtils.showNetworkErrorDialog(AcountInfoActivity.this);
                    }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };
        HttpUtils.sendGetRequet(Urlconf.GET_USER_INFORMATION,getInfoMap,onGetUserInfoResponseListner,UserOtherInfo.class);
    }


    @Override
    protected void setActionBar() {
        TextView titleView = (TextView) findViewById(R.id.actionbar_title);
        showBackButon();
        titleView.setText(getString(R.string.acount_info));
    }
}
