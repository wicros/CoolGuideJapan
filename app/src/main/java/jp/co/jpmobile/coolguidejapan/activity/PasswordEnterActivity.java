package jp.co.jpmobile.coolguidejapan.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.LoginInfo;
import jp.co.jpmobile.coolguidejapan.bean.UpPasswordInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.StringUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;


/**
 * Created by USER on 2016/06/16.
 */
public class PasswordEnterActivity extends BaseActivity {

    // UI references.
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_enter);
        setActionBar();
        setView();
    }

    @Override
    protected void setActionBar() {
        TextView titleView = (TextView) findViewById(R.id.actionbar_title);
        showBackButon();
        titleView.setText(getString(R.string.password_bar_title));
    }

    @Override
    protected void setView() {
        //mEmailView = (TextView) findViewById(R.id.email_show);
        mPasswordView = (EditText) findViewById(R.id.password_sign_in);
        mPasswordConfirmView = (EditText) findViewById(R.id.password_confirm_in);

        Button mEmailSignInButton = (Button) findViewById(R.id.possword_confirm_ok_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        //mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirmView.setError(null);

        // Store values at the time of the login attempt.
        String email = AppUtils.getFromPreference(this,AppUtils.EMAILADDR);
        String password = mPasswordView.getText().toString();
        String passwordConfrim = mPasswordConfirmView.getText().toString();

        View focusView = null;

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfrim)) {
            mPasswordConfirmView.setError(getString(R.string.error_field_required_regist));
            focusView = mPasswordConfirmView;
            focusView.requestFocus();
            return;
        }

        // Check for a valid password, if the user entered one.
        if (!StringUtils.isPassword(PasswordEnterActivity.this,password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            focusView.requestFocus();
            return;
        }

        if (!StringUtils.isPassword(PasswordEnterActivity.this,password)) {
            mPasswordConfirmView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordConfirmView;
            focusView.requestFocus();
            return;
        }

        if (!password.equals(passwordConfrim)){
            mPasswordConfirmView.setError(getString(R.string.error_password_equal));
            focusView = mPasswordConfirmView;
            focusView.requestFocus();
            return;
        }

        startUpdatePasswrod(email , mPasswordView.getText().toString().trim());
    }

    private void startUpdatePasswrod(final String user, final String password) {
        showProgressDialog();
        HashMap<String, String> stringMap = new HashMap<>();
        stringMap.put("mailAddr",user);
        stringMap.put("password",password);

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {

                UpPasswordInfo upPasswordInfo = (UpPasswordInfo) o;

                switch (((UpPasswordInfo) o).getResult()) {
                    case Urlconf.OK:
                        CustomDialog.Builder builder = new CustomDialog.Builder(PasswordEnterActivity.this);
                        builder.setMessage(getString(R.string.updated_password));
                        builder.setPositiveButton(getString(R.string.drop_out_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                autologin(user,password);
                            }
                        });
                        builder.create().show();

                        break;
                    case Urlconf.NO_SUCH_USER:
                        showErrorDialog(getString(R.string.error_no_such_user), TAG_DEFAULT);
                    default:
                        break;
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                showErrorDialog(getString(R.string.error_server),TAG_DEFAULT);
            }

            @Override
            public void onFinish(int what) {
                hideProgressDialog();
            }
        };

        HttpUtils.sendGetRequet(Urlconf.UPDATE_PASSWORD,stringMap,onNetResponseListner, UpPasswordInfo.class);


    }



    private  void autologin(final String user, String password){


        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("mailAddr",user);
        stringStringHashMap.put("password",password);
        stringStringHashMap.put("deviceId", BaseApplication.getDevice_id());

        HttpUtils.OnNetResponseListner onrl = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {
                showProgressDialog();
            }

            @Override
            public  void onSucceed(int what,Object o) {

                LoginInfo loginInfo = (LoginInfo) o;
                //判断返回结果
                if (loginInfo.getResult().equals(Urlconf.NO_SUCH_USER)){
                    showErrorDialog(getString(R.string.error_no_such_user),TAG_DEFAULT);
                }else if (loginInfo.getResult().equals(Urlconf.PASSWORD_ERROR)){
                    showErrorDialog(getString(R.string.error_invalid_password),TAG_DEFAULT);
                }else{
                    AppUtils.saveToPreference(PasswordEnterActivity.this,AppUtils.KEY_TOKEN,loginInfo.getToken());
                    AppUtils.saveToPreference(PasswordEnterActivity.this,AppUtils.KEY_COUNTRY,loginInfo.getCountry());
                    AppUtils.saveToPreference(PasswordEnterActivity.this,AppUtils.KEY_USERID,String.valueOf(loginInfo.getUser_id()));
                    AppUtils.saveToPreference(PasswordEnterActivity.this,AppUtils.KEY_USERNAME, user);

                    UIUtils.loginSucess(PasswordEnterActivity.this);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                showErrorDialog(getString(R.string.error_server),TAG_DEFAULT);
            }

            @Override
            public void onFinish(int what) {
                hideProgressDialog();
            }
        };

        HttpUtils.sendGetRequet(Urlconf.LOGIN_IN,stringStringHashMap,onrl,LoginInfo.class);

    }



}



