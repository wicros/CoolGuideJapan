package jp.co.jpmobile.coolguidejapan.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.AddCardInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.CalendarUtils;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;


/**
 * A login screen that offers login via email/password.
 */
public class RegistTelActivity extends BaseActivity {

	// UI references.
	public static final String TIME_SERVER = "time-a.nist.gov";
	private Button mStartEditText;
	private Button mEndEditText;
	private int year;
	private int monthOfYear;
	private int dayOfMonth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tel_regist);
		setActionBar();
		setView();

	}

	@Override
	protected void setActionBar(){
		TextView titleView = (TextView) findViewById(R.id.actionbar_title);
		titleView.setText(getString(R.string.regist_tel_bar_title));
		showBackButon();
	}

	//SIM卡服务开始时间和结束时间
	@Override
	protected void setView() {
		mStartEditText = (Button) findViewById(R.id.startdate);
		mEndEditText = (Button) findViewById(R.id.enddate);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			mStartEditText.setStateListAnimator(null);
			mEndEditText.setStateListAnimator(null);
		}

		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		monthOfYear = calendar.get(Calendar.MONTH);
		dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

		mStartEditText.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(mStartEditText.getText())) {

					showDatePicker((Button) v, year, monthOfYear, dayOfMonth);
				}else{
					String[] date = mStartEditText.getText().toString().split("/");
					showDatePicker((Button) v, Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
				}
			}
		});

		mEndEditText.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				if ((mEndEditText.getText() == null)||(mEndEditText.getText().toString().length() == 0)) {

					showDatePicker((Button) v,year, monthOfYear, dayOfMonth);
				}else{
					String[] date = mEndEditText.getText().toString().split("/");
					showDatePicker((Button) v, Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
				}
			}
		});

		Button mEmailSignInButton = (Button) findViewById(R.id.regist_tel_button);
		mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					doRegist();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});

		Button mSkipButton = (Button) findViewById(R.id.skip_tel_button);
		mSkipButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				gotoNextPage();
			}
		});
	}


	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void doRegist() throws ParseException {

		// Reset errors.
		mStartEditText.setError(null);
		mEndEditText.setError(null);

		// Store values at the time of the login attempt.
		String startDate = mStartEditText.getText().toString();
		String endDate = mEndEditText.getText().toString();

		boolean cancel = false;
		View focusView = null;


		// Check for a valid password, if the user entered one.
		if (TextUtils.isEmpty(endDate)) {
			mEndEditText.setError(getString(R.string.error_invalid_date));
			focusView = mEndEditText;
			cancel = true;
		}

		if (TextUtils.isEmpty(startDate)) {
			mStartEditText.setError(getString(R.string.error_invalid_date));
			focusView = mStartEditText;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {

				checkInputInfo(startDate,endDate);

		}
	}
	//进入下一页面
	private void gotoNextPage(){
		goToPageAsTop(MainActivity.class,true);
	}


	//检查SIM卡服务开始日期和结束日期的输入格式
	private void showDatePicker(final Button editText,int year,int month,int day){
		DatePickerDialog datePickerDialog = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
					                      int monthOfYear, int dayOfMonth) {

						editText.setText(String.valueOf(year) + "/" + String.format("%1$02d", monthOfYear + 1) + "/" + String.format("%1$02d", dayOfMonth));

					}
				}, year, month, day);
		datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000*60*60*24);
		datePickerDialog.show();
	}

	private void checkInputInfo(String startDate, String endDate) throws ParseException {
		Intent intent = getIntent();
		String tel = intent.getStringExtra("tel");
		String fristDate = intent.getStringExtra("fristDate");
		String lastDate = intent.getStringExtra("lastDate");


		SimpleDateFormat Format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date firstDa = Format1.parse(fristDate);
		long firstDaTime = firstDa.getTime();
		Date lastDa = Format1.parse(lastDate);
		long lastDaTime = lastDa.getTime();

		SimpleDateFormat Format2 = new SimpleDateFormat("yyyy/MM/dd");
		Date startDa = Format2.parse(startDate);
		long startDaTime = startDa.getTime();
		Date endDa = Format2.parse(endDate);
		long endDaTime = endDa.getTime();

		if (startDaTime > firstDaTime && endDaTime > startDaTime){
			startRegSimInfo(tel,startDate,endDate);
		}else {
			showInputErrorDialog();
		}
	}


	private void startRegSimInfo(String cardNo,String startDate,String endDate){
		HashMap<String, String> map = new HashMap<>();
		map.put("token", AppUtils.getFromPreference(RegistTelActivity.this,AppUtils.KEY_TOKEN));
		map.put("deviceId", BaseApplication.getDevice_id());
		map.put("cardNo",cardNo);
		map.put("outboundDate",startDate);
		map.put("returnDate",endDate);
		map.put("iccid",getIntent().getStringExtra("iccid"));

		HttpUtils.OnNetResponseListner onCardAddResponseListner = new HttpUtils.OnNetResponseListner() {
			@Override
			public void onStart(int what) {

			}

			@Override
			public void onSucceed(int what, Object o) {
				AddCardInfo addCardInfo = (AddCardInfo) o;
				if (addCardInfo.getResult().equals(Urlconf.OK)){

					CustomDialog.Builder builder = new CustomDialog.Builder(RegistTelActivity.this);
					builder.setMessage(getString(R.string.card_regist_complete));
					builder.setPositiveButton(getString(R.string.drop_out_ok), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(RegistTelActivity.this,MainActivity.class));
							dialog.dismiss();
							finish();
						}
					});
					builder.create().show();

				}else if(addCardInfo.getResult().equals(Urlconf.CARD_REGISTERED)){
					DialogUtils.showErrorDialog(getString(R.string.card_registed),DialogUtils.TAG_DEFAULT,RegistTelActivity.this);
				}else {
					DialogUtils.showNetworkErrorDialog(RegistTelActivity.this);
				}

			}

			@Override
			public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

			}

			@Override
			public void onFinish(int what) {

			}
		};
		HttpUtils.sendGetRequet(Urlconf.ADD_USER_CARD,map,onCardAddResponseListner, AddCardInfo.class);
	}



	protected void showInputErrorDialog(){
		DialogUtils.showDialog(RegistTelActivity.this, "エラー", "入力項目不正", "OK", null, null, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}, null, null);
	}
}

