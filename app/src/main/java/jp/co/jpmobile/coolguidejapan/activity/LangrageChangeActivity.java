package jp.co.jpmobile.coolguidejapan.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;

public class LangrageChangeActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView mListView;
    private AppSettingListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setting);
        setActionBar();
        mAdapter = new AppSettingListAdapter(LangrageChangeActivity.this);
        mListView = (ListView)findViewById(R.id.app_setting_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    protected void setActionBar() {
        TextView titleView = (TextView) findViewById(R.id.actionbar_title);
        showBackButon();
        titleView.setText(getString(R.string.change_langrage));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String lan = null;
        switch (position){
            case 0:
                lan = "ja";
                break;
            case 1:
               lan = "zh";
                break;
            case 2:
                lan = "en";
                break;
            default:
                break;
        }
        AppUtils.saveToPreference(LangrageChangeActivity.this,AppUtils.LANGRAGE,lan);
        startActivity(new Intent(LangrageChangeActivity.this,SplashActivity.class));
        finish();
    }

    public final class ViewHolder {
        public TextView titleTextView;
    }

    public class AppSettingListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Context mContext;
        public AppSettingListAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            this.mContext = context;
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
        public int getCount() {
            return 3;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null || convertView.getTag() instanceof  String) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_app_setting_view,null);
                holder.titleTextView = (TextView) convertView.findViewById(R.id.app_setting_title);

                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();

            }
            switch (position){
                case 0:
                    holder.titleTextView.setText("日本語");
                    break;
                case 1:
                    holder.titleTextView.setText("繁體中文");
                    break;
                case 2:
                    holder.titleTextView.setText("English");
                    break;
                default:
                    break;
            }

            convertView.setTag(holder.titleTextView.getText());
            return convertView;
        }
    }
}
