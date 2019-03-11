package jp.co.jpmobile.coolguidejapan.activity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.InputStream;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.StringUtils;

public class AgreementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        TextView textView = (TextView) findViewById(R.id.textview);

        String laugrage = AppUtils.getFromPreference(AgreementActivity.this,AppUtils.LANGRAGE);
        int soruce = 0;
        if (laugrage.contains("zh")){
           soruce = R.raw.policy;
        }else if (laugrage.contains("ja")){
           soruce = R.raw.policyen;
        }else {
            soruce = R.raw.policyen;
        }


        InputStream inputStream = getResources().openRawResource(soruce);
        String string = StringUtils.getString(inputStream);
        textView.setText(string);
        setActionBar();
    }




    public void setActionBar() {
        TextView titleView = (TextView)findViewById(R.id.actionbar_title);
        showBackButon();
        titleView.setText(getString(R.string.app_setting_agreement));
    }
}
