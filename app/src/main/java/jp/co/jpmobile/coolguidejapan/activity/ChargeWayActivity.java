package jp.co.jpmobile.coolguidejapan.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.metaps.analytics.Analytics;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import jp.co.jpmobile.coolguidejapan.R;

import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.bean.ChargePlanInfo;
import jp.co.jpmobile.coolguidejapan.bean.CheckSimInfo;
import jp.co.jpmobile.coolguidejapan.bean.CurrencyInfo;
import jp.co.jpmobile.coolguidejapan.bean.MVNEChargeInfo;
import jp.co.jpmobile.coolguidejapan.bean.ResultInfo;
import jp.co.jpmobile.coolguidejapan.conf.PayResult;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.SignUtils;
import jp.co.jpmobile.coolguidejapan.view.CustomDialog;

public class ChargeWayActivity extends BaseActivity {

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
   // private static final String CONFIG_CLIENT_ID = "AULRQf_4zUya_9ewMRDU4dZmUDfy7PpvjTNQFMlqckD9aVmyYbvZFPuN2BeT_Qm_qkQJdm1IvvxTpwtR";
    private static final String CONFIG_CLIENT_ID = "AaPZ5QPaNLFDW1nWvAj5zWfJWOp_hEWXqGDFwrJIekaHckNZ6SLh3Dsyeack9wJQKre6jUQWCDb1H4Vx";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .merchantName("JPMobile Store");

    private static final int SDK_PAY_FLAG = 1;

    private ImageView alipay_bt;
    private ImageView paypal_bt;
    private CurrencyInfo.ProductBean price;
    private ChargePlanInfo.ProductBean product;
    private String rmbPrice;
    private String payInfo;
    private String sign;
    private HttpUtils.OnNetResponseListner onNetResponseListner;
    private String alipayKey;
    private String mvneChargeInfoResult = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_way);
        setActionBar();
        alipay_bt = (ImageView) findViewById(R.id.alipay_bt);
        alipay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Analytics.trackEvent(Urlconf.MYTAPS+"click", "alipay");
                checkSim(1);
            }
        });
        paypal_bt = (ImageView) findViewById(R.id.paypal_bt);
        paypal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Analytics.trackEvent(Urlconf.MYTAPS+"click", "paypal");
                checkSim(2);
            }
        });
        setPlan();
        initDara();
    }

    private void setPlan() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        price = (CurrencyInfo.ProductBean) bundle.getSerializable("price");
        product = (ChargePlanInfo.ProductBean) bundle.getSerializable("product");
        rmbPrice = getIntent().getStringExtra("RMB");

        onNetResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                if (what == 2){
                    MVNEChargeInfo mvneChargeInfo = (MVNEChargeInfo) o;
                    mvneChargeInfoResult = mvneChargeInfo.getResult();
                    if (mvneChargeInfoResult.equals("000")){
                        CustomDialog.Builder builder = new CustomDialog.Builder(ChargeWayActivity.this);
                        builder.setMessage(getString(R.string.charge_sucess));
                        builder.setPositiveButton(getString(R.string.drop_out_ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivity(new Intent(ChargeWayActivity.this,MainActivity.class));
                            }
                        });
                        builder.create().show();
                    }
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        };

    }

    private void initDara() {

        //Paypal設定
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    @Override
    protected void setActionBar() {
        super.setActionBar();
        TextView titleView = (TextView)findViewById(R.id.actionbar_title);
        titleView.setText(getString(R.string.charge_way_title));
        showBackButon();
    }
//----------------------alipay-----------------------------------------------------------------------
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        simCharge();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            //Toast.makeText(ChargeWayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    saveResult(alipayKey,resultStatus,"01",mvneChargeInfoResult);
                    break;
                }
                default:
                    break;
            }
        };
    };

    public void doAlipay() {
        String orderInfo = getOrderInfo(product.getProduct_name(), "description", rmbPrice);
        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        sign = sign(orderInfo);

        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(ChargeWayActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Urlconf.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Urlconf.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);
        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);

        alipayKey = key;
        return key;
    }

    private String sign(String content) {
        return SignUtils.sign(content, Urlconf.RSA_PRIVATE);
    }

    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    //-------------------------------------------paypal----------------------------------------------

    public void doPayPal() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(ChargeWayActivity.this, PaymentActivity.class);
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                startActivityForResult(intent, REQUEST_CODE_PAYMENT);

            }
        };
        thread.start();
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {




        return new PayPalPayment(new BigDecimal(price.getPrice()), price.getCurrency(), product.getProduct_name(),
                paymentIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                    simCharge();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                showErrorDialog(getString(R.string.error_server),TAG_DEFAULT);
            }


        PaymentConfirmation confirm = data
                .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

        String paymentId;
        try {
            paymentId = confirm.toJSONObject().getJSONObject("response")
                    .getString("id");

            String payment_client = confirm.getPayment().toJSONObject()
                    .toString();

            saveResult(paymentId,String.valueOf(resultCode),"02",mvneChargeInfoResult);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //-------------------------------------------checksim------------------------------------
    private void checkSim(int what){
        HashMap<String, String> map = new HashMap<>();
        map.put("phoneNumber", AppUtils.getFromPreference(ChargeWayActivity.this,AppUtils.TELNUM));
        map.put("token",AppUtils.getFromPreference(ChargeWayActivity.this,AppUtils.KEY_TOKEN));
        map.put("deviceId", BaseApplication.getDevice_id());

        HttpUtils.OnNetResponseListner onStausResponseListner = new HttpUtils.OnNetResponseListner() {
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Object o) {
                    CheckSimInfo checkSimInfo = (CheckSimInfo) o;
                    if (!checkSimInfo.getResult().equals("000")){
                        showErrorDialog(getResources().getString(R.string.charge_failed_error),TAG_DEFAULT);
                        return;
                    }

                    if (what == 1 ){
                        doAlipay();
                    }else if (what == 2){
                        doPayPal();
                    }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                showErrorDialog(getResources().getString(R.string.charge_failed_error),TAG_DEFAULT);
            }

            @Override
            public void onFinish(int what) {

            }
        };

        HttpUtils.sendGetRequet(Urlconf.GET_SIM_STATUS,map,onStausResponseListner, CheckSimInfo.class,what);
    }

    //---------------------------------------------saveResult----------------------------------------------
    private void saveResult(String paymentCode,String paySeverResult,String payType,String mvneResult){
        HashMap<String,String> map = new HashMap<>();
        map.put("paymentCode",paymentCode);
        map.put("planCode",product.getProduct_code());
        map.put("chargeUserid",AppUtils.getFromPreference(AppUtils.KEY_USERID));
        map.put("simcardId",AppUtils.getFromPreference(AppUtils.SIMID));

        map.put("mvneServerResult",mvneResult);
        map.put("payServerResult",paySeverResult);
        map.put("payType",payType);
        map.put("token",AppUtils.getFromPreference(AppUtils.KEY_TOKEN));

        map.put("deviceId",BaseApplication.getDevice_id());
        map.put("product_id",product.getProduct_id());
        map.put("currency",payType.equals("01")?"RMB":price.getCurrency());
        map.put("price",payType.equals("01")?rmbPrice:price.getPrice());

        HttpUtils.sendGetRequet(Urlconf.SAVE_RECHARGE_HISTORY,map,onNetResponseListner,ResultInfo.class,1);
    }

    //-----------------------------------simCharge------------------------------
    private void simCharge(){
        HashMap<String,String> map = new HashMap<>();
        map.put("productCode",product.getProduct_code());
        map.put("token",AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        map.put("deviceId",BaseApplication.getDevice_id());
        map.put("phoneNumber",AppUtils.getFromPreference(AppUtils.TELNUM));

        HttpUtils.sendGetRequet(Urlconf.SIM_CHARGE,map,onNetResponseListner,MVNEChargeInfo.class,2);
    }
}
