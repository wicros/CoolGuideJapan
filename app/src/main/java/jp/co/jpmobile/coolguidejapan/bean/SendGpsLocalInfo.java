package jp.co.jpmobile.coolguidejapan.bean;

/**
 * Created by wicors on 2016/8/24.
 */
public class SendGpsLocalInfo {

    /**
     * result : OK
     * send_local_gps : 0
     */

    private String result;
    private int send_local_gps;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSend_local_gps() {
        return send_local_gps;
    }

    public void setSend_local_gps(int send_local_gps) {
        this.send_local_gps = send_local_gps;
    }
}
