package jp.co.jpmobile.coolguidejapan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weidongjian.meitu.wheelviewdemo.view.LoopView;
import com.weidongjian.meitu.wheelviewdemo.view.OnItemSelectedListener;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.AreaInfo;
import jp.co.jpmobile.coolguidejapan.bean.SaveUserInfo;
import jp.co.jpmobile.coolguidejapan.bean.TypeInfo;
import jp.co.jpmobile.coolguidejapan.bean.UserOtherInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;
import jp.co.jpmobile.coolguidejapan.view.WheelView;


/**
 * Created by USER on 2016/06/28.
 */
public class EditUserInfoActivity extends BaseActivity {

    private TextView mGenderView;
    private TextView mBirthdayView;
    private TextView mCountryView;
    private TextView mTourismView;
    private AlertDialog dialog;
    private int year, monthOfYear, dayOfMonth, hourOfDay;
    private List<String> countryStrings = new ArrayList<>();
    private List<String> cityStrings = new ArrayList<>();
    private List<String> gendStrings = new ArrayList<>();
    private List<String> countryCodes = new ArrayList<>();
    private List<String> cityCodes = new ArrayList<>();
    private List<String> gendCodes = new ArrayList<>();


    private String countryCode;
    private String cityCode;
    private String genderCode;
    private String laugrage;
    private String nickname;

    private WheelView wheelView;
    private AlertDialog wheeldialog;
    private LoopView loopView;
    private Button userInfoButton;
    private Drawable drawable;
    private CheckBox policyCb;
    private EditText nickname_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        laugrage = AppUtils.getFromPreference(EditUserInfoActivity.this, AppUtils.LANGRAGE);
        setActionBar();
        setView();
        loadInfo();
    }
    

    @Override
    protected void setActionBar() {
        TextView titleView = (TextView) findViewById(R.id.actionbar_title);
        showBackButon();
        titleView.setText(getString(R.string.userinfo_bar_title));
    }

    @Override
    protected void setView() {

        TextView tv = (TextView) findViewById(R.id.policy_tv);
        tv.setVisibility(View.GONE);

        View view = View.inflate(EditUserInfoActivity.this, R.layout.loop_view, null);
        loopView = (LoopView) view.findViewById(R.id.loopview);
        //设置是否循环播放
        loopView.setNotLoop();
        //设置初始位置
        loopView.setInitPosition(1);
        //设置字体大小
        loopView.setTextSize(20);

//        View outerView = View.inflate(UserInfoActivity.this,R.layout.wheel_view, null);
//        wheelView = (WheelView) outerView.findViewById(R.id.wheel_view_wv);

        //nickname
        nickname_tv = (EditText) findViewById(R.id.nickname_tv);
        nickname_tv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        nickname_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    nickname = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        wheeldialog = new AlertDialog.Builder(EditUserInfoActivity.this)
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        //生日
        mBirthdayView = (TextView) findViewById(R.id.user_birthday_input);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        DatePicker datePicker = new DatePicker(EditUserInfoActivity.this);
        datePicker.setMaxDate(System.currentTimeMillis());
        datePicker.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String text = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                mBirthdayView.setText(text);
            }
        });

        datePicker.setCalendarViewShown(false);

        dialog = new AlertDialog.Builder(EditUserInfoActivity.this)
                .setView(datePicker)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();


        ;
        mBirthdayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });

        //国家
        mCountryView = (TextView) findViewById(R.id.user_country_input);
        mCountryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCountryClick();
            }
        });

        //目的地
        mTourismView = (TextView) findViewById(R.id.user_tourism_input);
        mTourismView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTourismClick();
            }
        });

        //性别
        mGenderView = (TextView) findViewById(R.id.user_gender_input);
        mGenderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGenderClick();
            }
        });

        //保存按钮
        userInfoButton = (Button) findViewById(R.id.user_button);
        userInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(cityCode)){
                    Toast.makeText(EditUserInfoActivity.this,R.string.error_field_required_regist,Toast.LENGTH_SHORT).show();
                    return;
                }

                onSendClick();}});
        drawable = getResources().getDrawable(R.drawable.button_back);
        userInfoButton.setBackground(drawable);
        userInfoButton.setEnabled(true);

        policyCb = (CheckBox) findViewById(R.id.policy_cb);
        policyCb.setVisibility(View.GONE);


    }


        //国家选择
    public void onCountryClick() {
        //设置原始数据
        loopView.setItems(countryStrings);
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
               mCountryView.setText(countryStrings.get(index));
                countryCode = countryCodes.get(index);
            }
        });
        wheeldialog.show();



    }

    //目的地选择
    public void onTourismClick() {
        //设置原始数据
        loopView.setItems(cityStrings);
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mTourismView.setText(cityStrings.get(index));
                cityCode = cityCodes.get(index);
            }
        });
        wheeldialog.show();
    }
    //性别选择
    public void onGenderClick() {
        //设置原始数据
        loopView.setItems(gendStrings);
        //滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mGenderView.setText(gendStrings.get(index));
                genderCode = gendCodes.get(index);
            }
        });
        wheeldialog.show();

    }

    //保存信息
    public void onSendClick() {

        OnResponseListener<String> onSaveNetResponseListner = new  OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response<String> response){
                SaveUserInfo saveUserInfo =  new Gson().fromJson(response.get(),SaveUserInfo.class);
               // SaveUserInfo saveUserInfo = (SaveUserInfo) o;
                if (saveUserInfo.getResult().equals(Urlconf.OK)&&saveUserInfo.getChange_user_otherinfo().equals("1")){
                    CustomDialog.Builder builder = new CustomDialog.Builder(EditUserInfoActivity.this);
                    builder.setMessage(getString(R.string.saved_userinfo));
                    builder.setPositiveButton(getString(R.string.drop_out_ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            AppUtils.saveToPreference(EditUserInfoActivity.this,AppUtils.KEY_COUNTRY,countryCode);
                            UIUtils.loginSucess(EditUserInfoActivity.this);
                        }
                    });
                    builder.create().show();
                }else {
                    showErrorDialog(getString(R.string.error_server),TAG_DEFAULT);
                }

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                showErrorDialog(getString(R.string.error_server),TAG_DEFAULT);
            }

            @Override
            public void onFinish(int what) {

            }
        };


        Request<String> stringRequest = NoHttp.createStringRequest(Urlconf.HOME_URL+Urlconf.SAVE_USER_OTHERINFO, RequestMethod.POST);
        HashMap<String, String> saveMap = new HashMap<>();
        saveMap.put("user_id",AppUtils.getFromPreference(EditUserInfoActivity.this,AppUtils.KEY_USERID));
        saveMap.put("genderCode",genderCode);
        saveMap.put("countryCode",countryCode);
        saveMap.put("birthdate",mBirthdayView.getText().toString().trim());
        saveMap.put("cityCode",cityCode);
        saveMap.put("nickname",nickname);
        saveMap.put("token",AppUtils.getFromPreference(EditUserInfoActivity.this,AppUtils.KEY_TOKEN));
        saveMap.put("deviceId",BaseApplication.getDevice_id());
        stringRequest.add(saveMap);
        BaseApplication.getRequestQueue().add(1,stringRequest,onSaveNetResponseListner);
    }

    private void loadInfo(){
        //加载国家和目的地信息
        HashMap<String, String> areamap = new HashMap<>();
        String lag = null;
        if (laugrage.contains("zh")){
            lag = "CH";
        }else if (laugrage.contains("ja")){
            lag = "JP";
        }else {
            lag = "EN";
        }

        areamap.put("languagecode",lag);
        areamap.put("areacode","0");
        areamap.put("token",AppUtils.getFromPreference(EditUserInfoActivity.this,AppUtils.KEY_TOKEN));
        areamap.put("deviceId", BaseApplication.getDevice_id());

        HttpUtils.OnNetResponseListner areaOnNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                AreaInfo areaInfo = (AreaInfo) o;
                if (areaInfo.getResult().equals(Urlconf.OK)){
                    String country;
                    for (AreaInfo.AreaBean a : areaInfo.getArea()) {
                        country = a.getAreaname();
                        if (what == 1){
                            countryStrings.add(country);
                            countryCodes.add(a.getAreacode());
                        }else if(what == 2){
                            cityCodes.add(a.getAreacode());
                            cityStrings.add(country);
                        }
                    }

                    setEditUserInfo();
                }else {
                    showErrorDialog(getString(R.string.error_server),TAG_DEFAULT);
                }

            }
            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }
            @Override
            public void onFinish(int what) {

            }
        };

        HttpUtils.sendGetRequet(Urlconf.GET_AREA,areamap,areaOnNetResponseListner,AreaInfo.class,1);
        areamap.put("areacode","JP");
        HttpUtils.sendGetRequet(Urlconf.GET_AREA,areamap,areaOnNetResponseListner,AreaInfo.class,2);

        //加载性别信息
        HashMap<String, String> typemap = new HashMap<>();
        typemap.put("type_code","01");
        typemap.put("token",AppUtils.getFromPreference(EditUserInfoActivity.this,AppUtils.KEY_TOKEN));
        typemap.put("deviceId", BaseApplication.getDevice_id());

        HttpUtils.OnNetResponseListner typeOnNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                TypeInfo typeInfo = (TypeInfo) o;
                if (typeInfo.getResult().equals(Urlconf.OK)){
                    String gend;
                    for (TypeInfo.TypeBean a : typeInfo.getType()) {
                        if (laugrage.contains("zh")){
                            gend = a.getTraditional_chinese_name();
                        }else if (laugrage.contains("ja")){
                            gend = a.getJapanese_name();
                        }else {
                            gend = a.getEnglish_name();
                        }
                        gendStrings.add(gend);
                        gendCodes.add(a.getType_code());
                    }
                    setEditUserInfo();
                }else {
                    showErrorDialog(getString(R.string.error_input),TAG_DEFAULT);
                }

            }
            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                showErrorDialog(getString(R.string.error_server),TAG_DEFAULT);
            }
            @Override
            public void onFinish(int what) {

            }
        };

        HttpUtils.sendGetRequet(Urlconf.GET_TYPE,typemap,typeOnNetResponseListner,TypeInfo.class);
    }

    private void setEditUserInfo() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        UserOtherInfo userOtherInfo = (UserOtherInfo) bundle.getSerializable("userinfo");

        mGenderView.setText(userOtherInfo.getGender());
        for (int i = 0; i < gendStrings.size(); i++) {
            if (gendStrings.get(i).equals(userOtherInfo.getGender())){
                genderCode = gendCodes.get(i);
            }
        }

        mCountryView.setText(userOtherInfo.getFrom_country());
        for (int i = 0; i < countryStrings.size(); i++) {
            if (countryStrings.get(i).equals(userOtherInfo.getFrom_country())){
                countryCode = countryCodes.get(i);
            }
        }

        mBirthdayView.setText(userOtherInfo.getBirthdate());

        nickname_tv.setText(userOtherInfo.getNickname());
        nickname = userOtherInfo.getNickname();
    }


}
