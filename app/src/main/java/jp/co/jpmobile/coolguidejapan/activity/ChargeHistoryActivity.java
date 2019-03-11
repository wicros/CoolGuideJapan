package jp.co.jpmobile.coolguidejapan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.ChargeHistoryInfo;
import jp.co.jpmobile.coolguidejapan.bean.ResultInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;

public class ChargeHistoryActivity extends BaseActivity {

    private List<ChargeHistoryInfo.PlanListBean> planList;
    private ListView mListView;
    private FrameLayout pg;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_history);
        initView();
        initData();
    }



    private void initView() {
        mListView = (ListView) findViewById(R.id.charge_history_listview);
        pg = (FrameLayout) findViewById(R.id.progressbar);
        historyAdapter = new HistoryAdapter();
        TextView titleView = (TextView)findViewById(R.id.actionbar_title);
        titleView.setText(getString(R.string.sim_detail_charge_history));
        showBackButon();
    }

    private class HistoryAdapter extends BaseAdapter{

        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return planList == null?0:planList.size();
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
            if (convertView == null){
                convertView = View.inflate(ChargeHistoryActivity.this,R.layout.list_charge_history_view,null);
                viewHolder = new ViewHolder();
                viewHolder.productCode = (TextView) convertView.findViewById(R.id.productCode);
                viewHolder.chargeDate = (TextView) convertView.findViewById(R.id.chargeDate);
                viewHolder.expirationDate = (TextView) convertView.findViewById(R.id.expirationDate);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ChargeHistoryInfo.PlanListBean planListBean = planList.get(position);
            viewHolder.productCode.setText(getResources().getString(R.string.product_code)+":"+planListBean.getProductCode());
            viewHolder.chargeDate.setText(getResources().getString(R.string.charge_date)+":"+planListBean.getChargeDate());
            viewHolder.expirationDate.setText(getResources().getString(R.string.ex_date)+":"+planListBean.getExpirationDate());
            return convertView;
        }

        class ViewHolder{
            TextView expirationDate;
            TextView chargeDate;
            TextView productCode;
        }
    }

    private void initData() {
        HashMap<String, String> map = new HashMap<>();
        map.put("phoneNumber",AppUtils.getFromPreference(AppUtils.TELNUM));
        map.put("dateFrom","19700101");

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date(System.currentTimeMillis());
        String s = format.format(date);

        map.put("dateTo",s);
        map.put("token", AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        map.put("deviceId", BaseApplication.getDevice_id());

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                ChargeHistoryInfo chargeHistoryInfo = (ChargeHistoryInfo) o;
                if (chargeHistoryInfo.getResult().equals("000")){
                    planList = chargeHistoryInfo.getPlanList();
                    mListView.setAdapter(historyAdapter);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {
                pg.setVisibility(View.GONE);
            }
        };

        HttpUtils.sendGetRequet(Urlconf.GET_RECHARGE_HISTORY,map,onNetResponseListner, ChargeHistoryInfo.class);
    }


}
