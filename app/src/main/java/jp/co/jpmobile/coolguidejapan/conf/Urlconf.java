package jp.co.jpmobile.coolguidejapan.conf;

/**
 * Created by wicors on 2016/7/7.
 */
public interface Urlconf {


   String HOME_URL = "http://ec2-52-68-119-217.ap-northeast-1.compute.amazonaws.com/api";
   //String HOME_URL = "http://192.168.10.204:8080/api";

     //String MYTAPS ="googleplay:";
    String MYTAPS ="selfservice:";

    String EMAIL_REG = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
    String PASSWORD_REG = "^[0-9A-Za-z]{6,20}$";

//---------------result-----------------------------------------------------
    String OK = "OK";
    String BAD_REQUEST = "BAD_REQUEST";
    String PASSWORD_ERROR = "INVALID_PASSWORD";
    String NO_SUCH_USER = "NO_SUCH_USER";
    String CARD_REGISTERED = "CARD_REGISTERED";
    String USER_DUPLICATED ="USER_DUPLICATED";
//---------------url-----------------------------------------------------------
    String LOGIN_IN = "/login";
    String UPDATE_PASSWORD = "/update_password";
    String GET_TYPE = "/get_type";
    String GET_AREA = "/get_area";
    String SAVE_USER_OTHERINFO = "/save_user_otherinfo";
    String GET_USER_INFO = "/get_user_info";
    String GET_USER_INFORMATION = "/get_user_information";
    String GET_COUPON_INFO = "/get_coupon_by_gps";
    String ADD_USER_CARD ="/user_card/add";
    String DELETE_USER_CARD ="/user_card/delete";
    String CREATE_USER ="/create_user";
    String SAVE_USER_LOCAL ="/save_user_local";
    String GET_PRODUCT_PRICE ="/get_product_price";
    String GET_PRODUCT_CURRENCY ="/get_product_currency";
    String VERFICATION_USER ="/verfication_user";
    String GET_SIM_STATUS ="/get_sim_status";
    String SAVE_RECHARGE_HISTORY ="/save_recharge_history";
    String GET_RECHARGE_HISTORY ="/get_recharge_history";
    String SIM_CHARGE ="/sim_charge";
    String SAVE_USER_PHONEINFO ="/save_user_phoneinfo";
    String GET_HOME_PAGE_ADV ="/get_home_page_adv";
    String GET_NEWS ="/get_news";
    String GET_VIDEO ="/get_video";
    String GET_COUPON_BY_FAVOURITE ="/get_coupon_by_favourite";
    String FAV_SETTING ="/fav_setting";
    String GET_SHOP ="/get_shop";
    String GET_SEND_LOCAL_GPS ="/get_send_local_gps";
    String GET_NEWSMAP ="/get_NewsMap";
    //----------------------------alipay-------------------------
    // 商户PID
    String PARTNER = "2088421312788705";
    // 商户收款账号
    String SELLER = "zhouyikai@jpmob.jp";
    // 商户私钥，pkcs8格式
    String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALJthUY8e7usowLZ" +
            "h7gqtnLJJiC2sOJCxBplGbwI5BgwAt/36DBym4VrgZhaaHiys6qBnrLHc9n8X99J" +
            "gyGNUTgfMuvX6Ugdx8/6TrkW4ZAyVbM77uRvRaHZm+bOsU51EqJRfsKNOKfS1H+N" +
            "aHeXFJvh6ncn2bCIn7esZNn+qnIdAgMBAAECgYA5g44TLsnfm+1LrNzPo4z+ARK2" +
            "Ns1bkZPuwioCb4c+uLMkiJ+ihj7VSesTNgk45rl9O5zxGdOhL3Wj3ulS9l94QsGn" +
            "0HIUCkgt90MTSQ3qmgwyp+7DiYKJyQeaMvlQk6teCjUIqa5PqAY4giwOhGwK8gzD" +
            "NBQHvZNMQ9+YH05BTQJBAN+YDnTRVAZHU+ZaJ/UTgikQVwkLHGef3ZReMPTfpWXk" +
            "mev2+6umx3aiUb18/lwvNIX27yqZjzjrTES8EZQoRScCQQDMSa1WLT1RjucKjQ65" +
            "mMlpFLua3C1uz2CjgURwvyk/UIZuujDV09UcOYpa70IcK1irrnEDyqUdf30SRnFM" +
            "agEbAkAiqp+rKv1dD9gJQfcp/0FR/o+P/mIECvw+0a/Dd51cQ+o9dIimn3ATi60i" +
            "tOt2/XamQpfoQfC7wXeMvJ/5GZlHAkA7TbQdDTr8vyBBBtvccwcdEyC2/u3NejQS" +
            "owcMN+IweB7ea/ybRkhaTjXk2gn2BXtTM2upbx1pp8XWFU70oN07AkEA3KjO365s" +
            "xrg45iQPCJ/rGvpRtilgbOPFa6xV3YOk5wlT0vw5GhIun/0ab2M5m8r9fPGdQiBo" +
            "vKB5II5jYSlXww==";
    // 支付宝公钥
    String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCybYVGPHu7rKMC2Ye4KrZyySYg" +
            "trDiQsQaZRm8COQYMALf9+gwcpuFa4GYWmh4srOqgZ6yx3PZ/F/fSYMhjVE4HzLr" +
            "1+lIHcfP+k65FuGQMlWzO+7kb0Wh2ZvmzrFOdRKiUX7CjTin0tR/jWh3lxSb4ep3" +
            "J9mwiJ+3rGTZ/qpyHQIDAQAB";
    //-----------------------map---------------------------------------------------
    int COUPONMAP = 1<<1;
    int SHOPMAP = 1<<2;
    int NEWSMAP = 1<<3;
}
