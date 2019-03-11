package jp.co.jpmobile.coolguidejapan.activity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;


/**
 * Created by USER on 2016/06/30.
 */
public class HelpActivity extends BaseActivity implements OnClickListener {

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);
        setActionBar();
        setView();
    }

    @Override
    protected void setActionBar(){
        TextView titleView = (TextView)findViewById(R.id.actionbar_title);
        showBackButon();
        titleView.setText(getString(R.string.help_bar_title));
    }

    @Override
    protected void setView() {
        super.setView();
        mButton1 = (Button) findViewById(R.id.help_list_1);
        mButton2 = (Button) findViewById(R.id.help_list_2);
        mButton3 = (Button) findViewById(R.id.help_list_3);
        mButton4 = (Button) findViewById(R.id.help_list_4);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        String title = (String)view.getTag();
        switch (view.getId()) {
            case R.id.help_list_1:
                goHelpPage1(title);
                break;
            case R.id.help_list_2:
                goHelpPage2(title);
                break;
            case R.id.help_list_3:
                goHelpPage3(title);
                break;
            case R.id.help_list_4:
                goHelpPage4(title);
                break;
            default:
                break;
        }
    }



    public void goHelpPage1(String title) {
        goToWebView("http://sim_grid.jpmob.jp/Faq_pic.aspx?id=236",title);
    }

    public void goHelpPage2(String title) {
        goToWebView("http://www.yokososim.com/Faq_pic.aspx?id=236",title);
    }

    public void goHelpPage3(String title) {
        goToWebView("http://sim_grid.jpmob.jp/Contact.aspx?id=263",title);
    }

    public void goHelpPage4(String title) {
        goToWebView("http://www.yokososim.com/Contact.aspx?id=255",title);
    }
}
