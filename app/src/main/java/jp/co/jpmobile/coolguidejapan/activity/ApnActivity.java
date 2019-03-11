package jp.co.jpmobile.coolguidejapan.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.utils.ApnUtility;
import jp.co.jpmobile.coolguidejapan.view.CornerListView;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;

public class ApnActivity extends BaseActivity implements View.OnClickListener {

    private CornerListView cornerListView = null;
    private ArrayList<HashMap<String, String>> mapList = null;
    private SimpleAdapter simpleAdapter = null;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView apnName1;
    private TextView apnName2;
    private TextView apnName3;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apn);

        apnName1 = (TextView) findViewById(R.id.apn_style_input);
        apnName2 = (TextView) findViewById(R.id.apn_user_name_input);
        apnName3 = (TextView) findViewById(R.id.apn_user_password_input);
        button1 = (Button)findViewById(R.id.apn_copy_1);
        button2 = (Button)findViewById(R.id.apn_copy_2);
        button3 = (Button)findViewById(R.id.apn_copy_3);
        button4 = (Button)findViewById(R.id.apn_copy_4);

        cornerListView = (CornerListView) findViewById(R.id.apn_list);

        context = ApnActivity.this;
        new ApnUtility(context);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        simpleAdapter = new SimpleAdapter(
                context,
                getDataSource(),
                R.layout.list_simple_list_item,
                new String[] { "item_title","item_value" },
                new int[] { R.id.item_title});
        cornerListView.setAdapter(simpleAdapter);
        cornerListView.setOnItemClickListener(new OnItemListSelectedListener());

        TextView titleView = (TextView)findViewById(R.id.actionbar_title);
        titleView.setText(getString(R.string.apn_setting_title));
        showBackButon();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.apn_copy_1:
                final ClipboardManager cb1 = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
                cb1.setText(apnName1.getText());
                apnName1.setText(cb1.getText());
                Toast.makeText(context,
                        getString(R.string.copy_success), Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.apn_copy_2:
                final ClipboardManager cb2 = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
                cb2.setText(apnName2.getText());
                apnName2.setText(cb2.getText());
                Toast.makeText(context,
                        getString(R.string.copy_success), Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.apn_copy_3:
                final ClipboardManager cb3 = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
                cb3.setText(apnName3.getText());
                apnName3.setText(cb3.getText());
                Toast.makeText(context,
                        getString(R.string.copy_success), Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.apn_copy_4:
                ConnectivityManager cManager=(ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo network = cManager.getActiveNetworkInfo();
                if (network != null && network.getType() == ConnectivityManager.TYPE_MOBILE){
                    Toast.makeText(context,
                            getString(R.string.data_connection_success), Toast.LENGTH_SHORT)
                            .show();
                }else{
                    CustomDialog.Builder builder = new CustomDialog.Builder(context);
                    builder.setMessage(getString(R.string.check_data_prompt));
                    builder.setTitle(getString(R.string.toast_title));
                    builder.setPositiveButton(getString(R.string.toast_ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            //if network closed ,then open it
                            openNetActivity();
                        }
                    });
                    builder.setNegativeButton(getString(R.string.toast_cancel),new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
                break;
            default:
                break;
        }
    }

    private void openApnActivity(){
        Intent intent = new Intent(Settings.ACTION_APN_SETTINGS);
        startActivity(intent);
    }

    private void openNetActivity(){
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

    class OnItemListSelectedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

            switch(position){
                case 0:
                    openApnActivity();
                    break;
                default:
                    break;
            }
        }
    }

    public ArrayList<HashMap<String, String>> getDataSource() {
        mapList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("item_title", getString(R.string.apn_button));
        mapList.add(map1);
        return mapList;
    }
}
