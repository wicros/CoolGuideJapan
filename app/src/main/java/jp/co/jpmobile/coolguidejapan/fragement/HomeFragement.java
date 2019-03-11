package jp.co.jpmobile.coolguidejapan.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metaps.analytics.Analytics;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.activity.ApnActivity;
import jp.co.jpmobile.coolguidejapan.activity.HelpActivity;
import jp.co.jpmobile.coolguidejapan.activity.MainActivity;
import jp.co.jpmobile.coolguidejapan.activity.SimRegistActvity;
import jp.co.jpmobile.coolguidejapan.activity.WebViewActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.base.BaseFragment;
import jp.co.jpmobile.coolguidejapan.bean.AdvInfo;
import jp.co.jpmobile.coolguidejapan.bean.NewsInfo;
import jp.co.jpmobile.coolguidejapan.bean.UserOtherInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.DialogUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;
import jp.co.jpmobile.coolguidejapan.view.NoScrollViewPager;
import me.maxwin.view.XListView;

/**
 * Created by wicors on 2016/7/25.
 */
public class HomeFragement extends BaseFragment{

    private View headView;
    private ViewPager vp;
    private int currentPosition = 1;
    private Handler handler;
    private Runnable runnable;
    private GridView gridView;
    private View view;
    private AutoCompleteTextView actv;
    private ImageView search_bt;
    private String code;
    private List<AdvInfo.AdvListBean> adv_list;
    private XListView hm_listview;
    private int pageNumber;
    private List<NewsInfo.NewsListBean> news_list;
    private NewsAdapter newsAdapter;
    private boolean roll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragement_home,null);
        headView = inflater.inflate(R.layout.view_homepage, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hm_listview = (XListView) view.findViewById(R.id.hm_listview);
        hm_listview.setPullRefreshEnable(false);
        hm_listview.setPullLoadEnable(true);
        hm_listview.addHeaderView(headView);
        hm_listview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                Analytics.trackEvent(Urlconf.MYTAPS+"click", "news-onLoadMore");
                initNews();
            }
        });

        actv = (AutoCompleteTextView) view.findViewById(R.id.hm_actv);
        search_bt = (ImageView) view.findViewById(R.id.hm_search);

        setHeaderView();
        setlistener();
        initData();
    }

    private void setlistener() {
        gridView.setOnItemClickListener(new myGridViewClickListener());
        search_bt.setOnClickListener(new SearchOnclickListener());
    }

    private void startRoll() {
        if (handler == null && runnable == null ){
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (currentPosition > (adv_list == null?0:adv_list.size()-1)){
                        currentPosition = 0;
                    }
                    vp.setCurrentItem(currentPosition);
                    currentPosition++;
                    if (handler != null && runnable != null){

                        handler.postDelayed(runnable,3000);

                    }
                }
            };
            handler = new Handler();
            handler.postDelayed(runnable,3000);
        }

    }


    private void setHeaderView() {
        gridView = (GridView) headView.findViewById(R.id.hm_gridview);
        gridView.setAdapter(new GridAdapter());
        vp = (ViewPager) headView.findViewById(R.id.hm_vp);

        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        int height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240

        float f = ((float) 95/(float)273)*width;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) f);

        vp.setLayoutParams(layoutParams);

    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity) getActivity()).actionBar.setVisibility(View.GONE);
        if (handler == null && adv_list != null && adv_list.size() > 0){
            startRoll();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        newsAdapter = null;
        ((MainActivity) getActivity()).actionBar.setVisibility(View.VISIBLE);
    }

    //-----------------------------------Adapter----------------------------------------------
    class NewsAdapter extends BaseAdapter{

        class NewsViewHolder {
            TextView news_note;
            TextView news_name;
            TextView dateTime;
            ImageView banner;
        }

        @Override
        public int getCount() {
            return news_list==null?0:news_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NewsViewHolder holder = null;
            if (convertView == null){
                convertView = View.inflate(getActivity(), R.layout.list_home_item, null);
                holder = new NewsViewHolder();
                holder.dateTime = (TextView) convertView.findViewById(R.id.date_time);
                holder.news_name = (TextView) convertView.findViewById(R.id.news_name);
                holder.news_note = (TextView) convertView.findViewById(R.id.news_note);
                holder.banner = (ImageView) convertView.findViewById(R.id.banner);
                convertView.setTag(holder);
            }else {
                holder = (NewsViewHolder) convertView.getTag();
            }
            final NewsInfo.NewsListBean newsListBean = news_list.get(position);
            holder.dateTime.setText(newsListBean.getNews_datetime());
            holder.news_name.setText(newsListBean.getNews_name());
            holder.news_note.setText(newsListBean.getNews_note());
            final String banner_url = newsListBean.getBanner_url();
            if (TextUtils.isEmpty(banner_url)){
               holder.banner.setVisibility(View.GONE);
            }else {
                holder.banner.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(newsListBean.getBanner_url()).into(holder.banner);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Analytics.trackEvent(Urlconf.MYTAPS+"click", "homepage-news-detail");
                    if (newsListBean.getIs_have_map() == 1){
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("newsbean",newsListBean);
                        intent.putExtra("bundle",bundle);
                        intent.putExtra("title","News");
                        intent.putExtra("url",newsListBean.getNews_url());
                        startActivity(intent);
                    }else {
                        UIUtils.goToWebView(getActivity(),newsListBean.getNews_url(),"");
                    }

                }
            });
            return convertView;
        }
    }

    class MyPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return adv_list == null?0:adv_list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            AdvInfo.AdvListBean advListBean = adv_list.get(position);
            final String clickUrl = advListBean.getAdvertising_url();
            if (getActivity() == null){
                return null;
            }
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getActivity()).load(advListBean.getBanner_url()).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIUtils.goToWebView(getActivity(),clickUrl,"web");
                    Analytics.trackEvent(Urlconf.MYTAPS+"click", "homepage-advertise-detail");
                }
            });
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

           container.removeView((View) object);

        }
    }

    class GridAdapter extends BaseAdapter{

        private boolean flag = true;

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getActivity(), R.layout.item_gridview, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gv_iv);
            TextView textView = (TextView) view.findViewById(R.id.gv_tv);
            switch (position){
                case 0:imageView.setImageResource(R.mipmap.sim_grid);
                    textView.setText(R.string.sim_registed_list_bar_title);break;
                case 4:imageView.setImageResource(R.mipmap.apn_grid);
                    textView.setText(R.string.apn_setting_title);break;
                case 3:imageView.setImageResource(R.mipmap.coupon_grid);
                    textView.setText(R.string.coupon_list_bar_title);break;
                case 1:imageView.setImageResource(R.mipmap.map_grid);
                    textView.setText(R.string.fragement_video_title);break;
                case 2:imageView.setImageResource(R.mipmap.video_grid);
                    textView.setText(R.string.title_video);break;
                case 5:imageView.setImageResource(R.mipmap.help_grid);
                    textView.setText(R.string.help_bar_title);break;
            }

            if (flag) {
                View viewById = view.findViewById(R.id.gv_ll);
                viewById.measure(0,0);
                int measuredHeight = viewById.getMeasuredHeight();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, measuredHeight * 2);
                gridView.setLayoutParams(layoutParams);
                flag = false;
            }
            return view;
        }
    }
    //----------------------------------Listener-----------------------------------------------

    class myGridViewClickListener implements GridView.OnItemClickListener{

        private NoScrollViewPager nv = ((MainActivity) getActivity()).noscrollViewPager;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0:startActivity(new Intent(getActivity(), SimRegistActvity.class));break;
                case 1:nv.setCurrentItem(1);break;
                case 2:nv.setCurrentItem(2);break;
                case 3:nv.setCurrentItem(3);break;
                case 4:startActivity(new Intent(getActivity(), ApnActivity.class));break;
                case 5:startActivity(new Intent(getActivity(), HelpActivity.class));break;
            }
        }
    }

    class SearchOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            String key = actv.getText().toString().trim();

            String url = "";
            if (!AppUtils.getFromPreference(AppUtils.LANGRAGE).equals("zh")){
                url ="https://www.google.com/#q="+key;
            }else {
                try {
                    key = URLEncoder.encode(key, "gb2312");
                    url = "http://www.baidu.com.cn/s?wd=" + key + "&cl=3";

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            UIUtils.goToWebView(getActivity(),url,"Search Result");
        }
    }

    //--------------------------------------http-----------------------------------------------

    private void initData() {
        String laugrage = AppUtils.getFromPreference(AppUtils.LANGRAGE);
        if (laugrage.contains("zh")){
            code = "CH";
        }else if (laugrage.contains("ja")){
            code = "JP";
        }else {
            code = "EN";
        }
        pageNumber = 1;
        intiAdvImg();
        initUserInfo();
        initNews();

    }

    private void initNews() {
        HashMap<String, String> map = new HashMap<>();

        map.put("pageNumber",pageNumber+"");
        map.put("token",AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        map.put("deviceId",BaseApplication.getDevice_id());
        map.put("languagecode",AppUtils.getFromPreference(AppUtils.LANGRAGE));

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {
                if (newsAdapter == null){
                    ((MainActivity) getActivity()).showProgressDialog();
                }
            }

            @Override
            public void onSucceed(int what, Object o) {
                List<NewsInfo.NewsListBean> list = ((NewsInfo) o).getNews_list();
                if (list != null && list.size() > 0){
                    if (newsAdapter == null){
                        pageNumber++;
                        news_list = list;
                        newsAdapter = new NewsAdapter();
                        hm_listview.setAdapter(newsAdapter);
                    }else {
                        pageNumber++;
                        news_list.addAll(list);
                        newsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {
                ((MainActivity) getActivity()).hideProgressDialog();
                hm_listview.stopLoadMore();
            }
        };

        HttpUtils.sendGetRequet(Urlconf.GET_NEWS,map,onNetResponseListner, NewsInfo.class);
    }

    private void intiAdvImg() {

        HashMap<String, String> map = new HashMap<>();

        map.put("languagecode",code);
        map.put("token",AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        map.put("deviceId",BaseApplication.getDevice_id());
        map.put("imgcode","01");

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                adv_list = ((AdvInfo) o).getAdv_list();
                vp.setAdapter(new MyPagerAdapter());
                startRoll();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };

        HttpUtils.sendGetRequet(Urlconf.GET_HOME_PAGE_ADV,map,onNetResponseListner, AdvInfo.class);
    }

    private void initUserInfo() {
        final ImageView iv = (ImageView) view.findViewById(R.id.acount_iv);
        final TextView nick = (TextView) view.findViewById(R.id.acount_nick);
        final TextView nation = (TextView) view.findViewById(R.id.acount_nation);
        final TextView email = (TextView) view.findViewById(R.id.acount_email);

        HashMap<String, String> getInfoMap = new HashMap<>();

        getInfoMap.put("token", AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        getInfoMap.put("deviceId", BaseApplication.getDevice_id());
        getInfoMap.put("languagecode", code);
        getInfoMap.put("user_id",AppUtils.getFromPreference(AppUtils.KEY_USERID));

        HttpUtils.OnNetResponseListner onGetUserInfoResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {


            }

            @Override
            public void onSucceed(int what, Object o) {
                UserOtherInfo userOtherInfo = (UserOtherInfo) o;
                if (userOtherInfo != null && userOtherInfo.getResult().equals(Urlconf.OK)){
                    nick.setText(userOtherInfo.getNickname());
                    nation.setText(userOtherInfo.getFrom_country());
                    email.setText(userOtherInfo.getEmail_address());
                    String gender = userOtherInfo.getGender();
                    if ("Male".equals(gender)){
                        iv.setImageResource(R.mipmap.male);
                    }else {
                        iv.setImageResource(R.mipmap.female);
                    }

                }else {
                    DialogUtils.showNetworkErrorDialog(getActivity());
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };
        HttpUtils.sendGetRequet(Urlconf.GET_USER_INFORMATION,getInfoMap,onGetUserInfoResponseListner,UserOtherInfo.class);
    }
}
