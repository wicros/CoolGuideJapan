package jp.co.jpmobile.coolguidejapan.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.bean.CreateUserInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.StringUtils;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;

/**
 * A login screen that offers login via email/password.
 */
public class RegistUserActivity extends BaseActivity {

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private EditText mPasswordConfirmView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_regist);
		setActionBar();
		setView();
	}

	@Override
	protected void setActionBar(){
		TextView titleView = (TextView)findViewById(R.id.actionbar_title);
		showBackButon();
		titleView.setText(getString(R.string.regist_bar_title));
	}

	@Override
	protected void setView() {
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordConfirmView = (EditText) findViewById(R.id.password_confirm);

		Button mEmailSignInButton = (Button) findViewById(R.id.regist_button);
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
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mPasswordConfirmView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();
		String passwordConfrim = mPasswordConfirmView.getText().toString();

		View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegistUserActivity.this,getString(R.string.error_field_required_regist),Toast.LENGTH_SHORT).show();
            focusView = mEmailView;
            focusView.requestFocus();
            return;
        } else if (!StringUtils.isEmailAddress(RegistUserActivity.this,email)) {
            Toast.makeText(RegistUserActivity.this,getString(R.string.error_invalid_email),Toast.LENGTH_SHORT).show();
            focusView = mEmailView;
            focusView.requestFocus();
            return;
        }

		// Check for a valid password, if the user entered one.
		if (TextUtils.isEmpty(password) || TextUtils.isEmpty(passwordConfrim)) {
			Toast.makeText(RegistUserActivity.this,getString(R.string.error_field_required_regist),Toast.LENGTH_SHORT).show();
			focusView = mPasswordView;
            focusView.requestFocus();
			return;
		}

		if ( !isPasswordValid(password)|| !isPasswordValid(passwordConfrim) ) {
			Toast.makeText(RegistUserActivity.this,getString(R.string.error_invalid_password),Toast.LENGTH_SHORT).show();
			focusView = mPasswordConfirmView;
            focusView.requestFocus();
			return;
		}

        if (!password.equals(passwordConfrim)){
            Toast.makeText(RegistUserActivity.this,getString(R.string.error_password_equal),Toast.LENGTH_SHORT).show();
            focusView = mPasswordConfirmView;
            focusView.requestFocus();
            return;
        }

        startRegWithUserInfo(mEmailView.getText().toString().trim(),mPasswordView.getText().toString().trim());

	}

	private void startRegWithUserInfo(String user, String password){
		HashMap<String, String> map = new HashMap<>();
		map.put("mailAddr",user.trim());
		map.put("password",password);

		final HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {

			}

			@Override
			public void onSucceed(int what, Object o) {
				CreateUserInfo createUserInfo = (CreateUserInfo) o;
				if (createUserInfo.getResult().equals(Urlconf.OK)){
					CustomDialog.Builder builder = new CustomDialog.Builder(RegistUserActivity.this);
					builder.setMessage(getString(R.string.go_to_Email));
					builder.setPositiveButton(getString(R.string.drop_out_ok), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							startActivity(new Intent(RegistUserActivity.this,LoginActivity.class));
						}
					});
					builder.create().show();

				}else if(createUserInfo.getResult().equals(Urlconf.USER_DUPLICATED)){
					showErrorDialog(getResources().getString(R.string.regist_user_existed_error),TAG_DEFAULT);
				}else {
					showNetworkErrorDialog();
				}
			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

			}

			@Override
			public void onFinish(int what) {

			}
		};

		HttpUtils.sendGetRequet(Urlconf.CREATE_USER,map,onNetResponseListner, CreateUserInfo.class);
	}


	private boolean isPasswordValid(String password) {
		String regex = Urlconf.PASSWORD_REG;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(password);
		return m.find() && password.length() >= 6;
	}

}

