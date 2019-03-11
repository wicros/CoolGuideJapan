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

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.bean.SimCheckInfo;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;

/**
 * A login screen that offers login via email/password.
 */
public class CheckTelActivity extends BaseActivity {

	// UI references.
	public static final String TIME_SERVER = "time-a.nist.gov";
	private AutoCompleteTextView mTelNumlView;
	private EditText mPasswordEditText;
	private String mTel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tel_check);
		setActionBar();
		setView();

	}

	@Override
	protected void setActionBar() {
		TextView titleView = (TextView) findViewById(R.id.actionbar_title);
		titleView.setText(getString(R.string.regist_tel_bar_title));
		showBackButon();
	}

	@Override
	protected void setView() {
		mTelNumlView = (AutoCompleteTextView) findViewById(R.id.tel);
		mPasswordEditText = (EditText) findViewById(R.id.tel_password);

		Button mEmailSignInButton = (Button) findViewById(R.id.check_tel_button);
		mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				doRegist();
			}
		});

		Button mSkipButton = (Button) findViewById(R.id.skip_tel_button);
		mSkipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(CheckTelActivity.this,MainActivity.class));
			}
		});
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void doRegist() {

		// Reset errors.
		mTelNumlView.setError(null);
		mPasswordEditText.setError(null);

		// Store values at the time of the login attempt.
		String tel = mTelNumlView.getText().toString().trim();
		String password = mPasswordEditText.getText().toString().trim();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
			mPasswordEditText.setError(getString(R.string.regist_tel_error_invalid_password));
			focusView = mPasswordEditText;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(tel)) {
			mTelNumlView.setError(getString(R.string.error_field_required));
			focusView = mTelNumlView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {

			startGetSimCardDetailApi(tel);

		}
	}



	private boolean isPasswordValid(String password) {
		return password.length() == 4;
	}

	//电话号，密码输入后，开始读取sim卡信息
	private void startGetSimCardDetailApi(String carNo) {
		mTel = carNo;
		String url = "https://jpmob.jp/prepaid/en/?tel_no_or_iccid=" + mTel + "&output_format=json";

		HttpUtils.OnNetResponseListner onSimCheckResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {

			}

			@Override
			public void onSucceed(int what, Object o) {
				SimCheckInfo simCheckInfo = (SimCheckInfo) o;
				if (simCheckInfo != null) {
					String iccid = simCheckInfo.getIssued_card().get(0).getFields().getIccid();
					String first_connection_date = simCheckInfo.getActivated_card().get(0).getFields().getFirst_connection_date();
					String plan_expiry_date = simCheckInfo.getActivated_card().get(0).getFields().getPlan_expiry_date();
					checkInputInfo(iccid,first_connection_date,plan_expiry_date);
				}
			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

			}

			@Override
			public void onFinish(int what) {

			}
		};

		HttpUtils.sendGetRequet(url, null, onSimCheckResponseListner, SimCheckInfo.class, HttpUtils.NOURL);

	}




	//check 电话号码以及密码
	private void checkInputInfo(String iccid,String fristDate,String lastDate){
		String substring = iccid.substring(iccid.length() - 4);
		String password = mPasswordEditText.getText().toString().trim();
		if (password.equals(substring)){
			Intent intent = new Intent();
			intent.putExtra("tel",mTel);
			intent.putExtra("fristDate",fristDate);
			intent.putExtra("lastDate",lastDate);
			intent.putExtra("iccid",iccid);
			intent.setClass(CheckTelActivity.this,RegistTelActivity.class);
			startActivity(intent);
		}else {
			showInputErrorDialog();
		}
	}



	protected void showInputErrorDialog(){
		DialogUtils.showDialog(CheckTelActivity.this, "エラー", "入力項目不正", "OK", null, null, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}, null, null);
	}
}

