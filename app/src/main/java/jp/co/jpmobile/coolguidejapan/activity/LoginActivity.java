package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.LoginInfo;
import jp.co.jpmobile.coolguidejapan.bean.ResultInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.StringUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;

	private Button mOpenRegistButton;
	private TextView mVersionTextView;

	private ImageButton mBaiduButton;
	private ImageButton mWechatButton;
	private ImageButton mFacebookButton;
	private ImageButton mWeiboButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setActionBar();
		setView();
	}

	protected void setView() {
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		mPasswordView = (EditText) findViewById(R.id.password);


		TextView mForgetPassword = (TextView)findViewById(R.id.forget_password);
		mForgetPassword.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				openEmailEnterActivity();
			}
		});

		Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
		mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});

		mOpenRegistButton = (Button) findViewById(R.id.top_regist_user);
		mOpenRegistButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				goUserRegistration();
			}
		});

		mVersionTextView = (TextView)findViewById(R.id.version);
		mVersionTextView.setText(AppUtils.getAppVersion(this));


		mBaiduButton = (ImageButton)findViewById(R.id.top_baidu_button);
		mBaiduButton.setEnabled(false);
		mWechatButton = (ImageButton)findViewById(R.id.top_wechat_button);
		mWechatButton.setEnabled(false);
		mWeiboButton = (ImageButton)findViewById(R.id.top_weibo_button);
		mWeiboButton.setEnabled(false);
		mFacebookButton = (ImageButton)findViewById(R.id.top_facebook_button);
	}


	public void attemptLogin() {


		mEmailView.setError(null);
		mPasswordView.setError(null);


		String email = mEmailView.getText().toString().trim();
		String password = mPasswordView.getText().toString().trim();

		boolean cancel = false;
		View focusView = null;


		if (!isPasswordValid(password)&&!StringUtils.isPassword(LoginActivity.this,password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!StringUtils.isEmailAddress(LoginActivity.this,email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		if (cancel) {

			focusView.requestFocus();
		} else {


				startLoginWithUserInfo(mEmailView.getText().toString(),mPasswordView.getText().toString());

		}
	}

	private void openEmailEnterActivity(){

		LoginActivity.this.startActivity(new Intent(LoginActivity.this, EmailEnterActivity.class));
	}



	private boolean isPasswordValid(String password) {
		if(password.length()<6){
			showErrorDialog(getString(R.string.error_password_length),TAG_DEFAULT);
		}
		return password.length() >= 6;
	}


	private void startLoginWithUserInfo(final String user, String password){

		AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_TOKEN,"");
		AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_COUNTRY,"");
		AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_USERID,"");
		AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_USERNAME,"");

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

				if (loginInfo.getResult().equals(Urlconf.NO_SUCH_USER)){
					showErrorDialog(getString(R.string.error_no_such_user),TAG_DEFAULT);
				}else if (loginInfo.getResult().equals(Urlconf.PASSWORD_ERROR)){
					showErrorDialog(getString(R.string.error_invalid_password),TAG_DEFAULT);
				}else if(loginInfo.getResult().equals(Urlconf.OK)){
					AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_TOKEN,loginInfo.getToken());
					AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_COUNTRY,loginInfo.getCountry());
					AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_USERID,String.valueOf(loginInfo.getUser_id()));
					AppUtils.saveToPreference(LoginActivity.this,AppUtils.KEY_USERNAME, user);
					saveInfo();

					UIUtils.loginSucess(LoginActivity.this);
				}else {
					showErrorDialog(getString(R.string.error_input),TAG_DEFAULT);
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

		HttpUtils.sendGetRequet(Urlconf.LOGIN_IN,stringStringHashMap,onrl,LoginInfo.class);

	}


	//用户注册
	public void goUserRegistration() {
		goToPage(RegistUserActivity.class,false);
	}
	//保存手机信息
	private void saveInfo() {
		HashMap<String, String> map = new HashMap<>();
		String user_id = AppUtils.getFromPreference(AppUtils.KEY_USERID);
		if (TextUtils.isEmpty(user_id)){
			return;
		}
		map.put("user_id",user_id);

		String release = Build.VERSION.RELEASE;
		String model = Build.MODEL;

		map.put("phone_name",model);
		map.put("phone_os_version",release);
		map.put("token",AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
		map.put("deviceId", BaseApplication.getDevice_id());

		HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {

			}

			@Override
			public void onSucceed(int what, Object o) {

			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

			}

			@Override
			public void onFinish(int what) {

			}
		};

		HttpUtils.sendGetRequet(Urlconf.SAVE_USER_PHONEINFO,map,onNetResponseListner, ResultInfo.class);
	}
}

