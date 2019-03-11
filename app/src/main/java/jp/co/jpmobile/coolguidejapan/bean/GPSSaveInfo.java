package jp.co.jpmobile.coolguidejapan.bean;

/**
 * Created by wicors on 2016/7/14.
 */
public class GPSSaveInfo {
    private String result;
    private String insert_user_gps_local;

    public static final String success = "1";
    public static final String failed = "0";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getInsert_user_gps_local() {
        return insert_user_gps_local;
    }

    public void setInsert_user_gps_local(String insert_user_gps_local) {
        this.insert_user_gps_local = insert_user_gps_local;
    }
}
