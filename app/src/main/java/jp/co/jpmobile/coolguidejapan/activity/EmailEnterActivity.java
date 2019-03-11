package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Random;

import jp.co.jpmobile.coolguidejapan.R;

import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.bean.ResultInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.StringUtils;


/**
 * Created by USER on 2016/06/15.
 */
public class EmailEnterActivity extends BaseActivity {

        // UI references.
        private EditText mEmailView;
        private Button mEmailSignButton;


        String passNumber = getStringRandom(6);    //随机验证码
        public static final String EMAIL_SIGN_IN = "email_sign_in";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_email_enter);
            setActionBar();
            setView();
        }

    @Override
    protected void setActionBar(){
        TextView titleView = (TextView)findViewById(R.id.actionbar_title);
        showBackButon();
        titleView.setText(getString(R.string.forget_password_title));
    }

    //随机数验证码
    public String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    @Override
    protected void setView() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_sign_in);

        Button mEmailSignButton = (Button) findViewById(R.id.email_sign_ok_button);
        mEmailSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptLogok();
            }
        });
    }

    //尝试登录
    public void attemptLogok() {

        // Reset errors.
        mEmailView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!StringUtils.isEmailAddress(EmailEnterActivity.this,email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

                startLoginWithEmailInfo(mEmailView.getText().toString().trim(), passNumber);

        }
    }




    private void startLoginWithEmailInfo(String user,String passNumber){


        AppUtils.saveToPreference(EmailEnterActivity.this,AppUtils.PASSNUMBER,passNumber);
        AppUtils.saveToPreference(EmailEnterActivity.this,AppUtils.EMAILADDR,user);
        HashMap<String, String> map = new HashMap<>();
        map.put("mailAddr",user);
        map.put("verficationCode",passNumber);

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                    ResultInfo resultInfo = (ResultInfo) o;
                    if (resultInfo.getResult().equals(Urlconf.NO_SUCH_USER)){
                        showErrorDialog(getString(R.string.error_no_such_user),TAG_DEFAULT);
                    }else if (resultInfo.getResult().equals((Urlconf.OK))){
                        startActivity(new Intent(EmailEnterActivity.this,CodesActivity.class));
                    }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };

        HttpUtils.sendGetRequet(Urlconf.VERFICATION_USER,map,onNetResponseListner, ResultInfo.class);

    }

}
