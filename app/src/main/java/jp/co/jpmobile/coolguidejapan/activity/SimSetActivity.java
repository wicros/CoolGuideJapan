package jp.co.jpmobile.coolguidejapan.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;

public class SimSetActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_set);
        Button simset_bt = (Button) findViewById(R.id.simset_bt);
        TextView action_title = (TextView) findViewById(R.id.actionbar_title);
        simset_bt.setText(R.string.simset_bt);
        simset_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goToWebView(SimSetActivity.this,"http://www.yokososim.com/","");
            }
        });
        action_title.setText(R.string.simset_title);
        showBackButon();
    }
}
