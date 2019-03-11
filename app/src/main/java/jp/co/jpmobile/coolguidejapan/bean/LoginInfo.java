package jp.co.jpmobile.coolguidejapan.bean;

import org.json.JSONObject;

/**
 * Created by wicors on 2016/7/7.
 */
public class LoginInfo extends JSONObject {


    /**
     * result : OK
     * token : 5b457665-ec02-4226-92c6-f4c3f8d2c62b
     * user_id : 116
     * country : zh
     */

    private String result;
    private String token;
    private String user_id;
    private String country;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
