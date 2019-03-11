package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import android.widget.TextView;

import com.metaps.analytics.Analytics;

import java.util.ArrayList;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.factory.FragmentFactory;
import jp.co.jpmobile.coolguidejapan.view.NoScrollViewPager;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    public NoScrollViewPager noscrollViewPager;
    private ArrayList<ImageButton> toolBarList;
    private ArrayList<String> titleList;
    private TextView titleView;
    public FragmentManager fragmentManager;
    public FragmentStatePagerAdapter pagerAdapter;
    public TextView action_tv;
    public View actionBar;
    private Handler handler;
    private static final int runTime = 1000*60*5;
    private Runnable adv_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        initActionbar();
        initTooLbar();
        initView();
        initListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        handler = BaseApplication.getHandler();

        adv_task = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,ADVActivity.class));
                handler.postDelayed(adv_task,runTime);
            }
        };

        handler.postDelayed(adv_task,runTime);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null){
            handler.removeCallbacks(adv_task);
            handler = null;
        }
    }





    private void initActionbar() {
        titleView = (TextView)findViewById(R.id.actionbar_title);

        String footTitle = getResources().getString(R.string.title_foot);
        String apnSetTitle = "Home page";
        String videoTitle = getResources().getString(R.string.title_video);
        String couponTitle = getResources().getString(R.string.coupon_list_bar_title);
        String appsetTitle = getResources().getString(R.string.app_setting_bar_title);
        titleList = new ArrayList<String>();

        titleList.add(apnSetTitle);
        titleList.add(footTitle);
        titleList.add(videoTitle);
        titleList.add(couponTitle);
        titleList.add(appsetTitle);
        actionBar = findViewById(R.id.action_bar_rl);
        setActionBar(0);
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        noscrollViewPager = (NoScrollViewPager) findViewById(R.id.mainViewPager);
        pagerAdapter = new HomeFragmentStatePagerAdapter(fragmentManager);
        noscrollViewPager.setAdapter(pagerAdapter);
        action_tv = (TextView) findViewById(R.id.action_tv);

    }

    private void initListener() {
        noscrollViewPager.setOnPageChangeListener(new NoScrollViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setActionBar(position);
                setToolBar(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTooLbar() {
        toolBarList = new ArrayList<ImageButton>();
        ImageButton tool_bar_btn_0 = (ImageButton)findViewById(R.id.tool_bar_btn_0);
        ImageButton tool_bar_btn_1 = (ImageButton)findViewById(R.id.tool_bar_btn_1);
        ImageButton tool_bar_btn_2 = (ImageButton)findViewById(R.id.tool_bar_btn_2);
        ImageButton tool_bar_btn_3 = (ImageButton)findViewById(R.id.tool_bar_btn_3);
        ImageButton tool_bar_btn_4 = (ImageButton)findViewById(R.id.tool_bar_btn_4);

        tool_bar_btn_0.setOnClickListener(this);
        tool_bar_btn_1.setOnClickListener(this);
        tool_bar_btn_2.setOnClickListener(this);
        tool_bar_btn_3.setOnClickListener(this);
        tool_bar_btn_4.setOnClickListener(this);

        toolBarList.add(tool_bar_btn_0);
        toolBarList.add(tool_bar_btn_1);
        toolBarList.add(tool_bar_btn_2);
        toolBarList.add(tool_bar_btn_3);
        toolBarList.add(tool_bar_btn_4);
        setToolBar(0);
    }

    protected void setActionBar(int position) {
        titleView.setText(titleList.get(position));
    }


    protected void setToolBar(int position){

        for (int i = 0; i < toolBarList.size(); i++) {
            if (i == position){
                toolBarList.get(position).setBackgroundResource(R.drawable.toolbar_button_back);
            }else {
                toolBarList.get(i).setBackgroundResource(R.color.transparent);
            }
        }
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < toolBarList.size(); i++) {
            if (v == toolBarList.get(i)){
                Analytics.trackEvent(Urlconf.MYTAPS+"click", "toolbar:"+i);
                noscrollViewPager.setCurrentItem(i);
                return;
            }
        }
    }

    class HomeFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public HomeFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.getFragment(position);
        }

        @Override
        public int getCount() {
            return titleList == null?0:titleList.size();
        }

    }
}
