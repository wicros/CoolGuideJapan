package jp.co.jpmobile.coolguidejapan.bean;

/**
 * Created by wicors on 2016/7/15.
 */
public class CheckSimInfo {

    /**
     * phoneNumber : 08080336606
     * systemStatus : CANCELLED
     * dataAmount : -1
     * firstLoginDate : NULL
     * result : 002
     */

    private String phoneNumber;
    private String systemStatus;
    private String dataAmount;
    private String firstLoginDate;
    private String result;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(String systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getDataAmount() {
        return dataAmount;
    }

    public void setDataAmount(String dataAmount) {
        this.dataAmount = dataAmount;
    }

    public String getFirstLoginDate() {
        return firstLoginDate;
    }

    public void setFirstLoginDate(String firstLoginDate) {
        this.firstLoginDate = firstLoginDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
