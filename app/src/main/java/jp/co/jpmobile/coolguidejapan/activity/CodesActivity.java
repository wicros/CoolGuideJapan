package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.LoginInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;


/**
 * Created by USER on 2016/06/20.
 */
public class CodesActivity extends BaseActivity implements OnClickListener {

    // UI references.
    private Button button;
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codes);
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
        button = (Button) findViewById(R.id.codes_sign_ok_button);
        button.setOnClickListener(this);
        et = (EditText) findViewById(R.id.codes_sign_in);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.codes_sign_ok_button:

                String passNumber = AppUtils.getFromPreference(CodesActivity.this,AppUtils.PASSNUMBER);
                //使用toast信息提示框显示信息
                //Toast.makeText(this, "读取数据如下："+"\n"+"name：" + name , Toast.LENGTH_LONG).show();
                if(et.getText().toString() .trim().equalsIgnoreCase(passNumber)){
                    //Toast.makeText(this, "对比成功："+"\n"+"name：" + name , Toast.LENGTH_LONG).show();
                    openPasswordenterActivity();

                }else{
                    Toast.makeText(this, R.string.verification_code_error, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void openPasswordenterActivity(){
        Intent intent=new Intent();
        intent.setClass(CodesActivity.this, PasswordEnterActivity.class);
        CodesActivity.this.startActivity(intent);
    }






}

