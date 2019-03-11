package jp.co.jpmobile.coolguidejapan.bean;

import java.io.Serializable;

/**
 * Created by wicors on 2016/7/12.
 */
public class UserOtherInfo implements Serializable {

    /**
     * result : OK
     * gender : Male
     * birthdate : 2012-07-11
     * from_country : Taiwan
     * goto_city : Yamaguchi,Tokyo,Osaka,Kyoto,Hokkaido
     * email_address : wicroswts@gmail.com
     */

    private String result;
    private String gender;
    private String birthdate;
    private String from_country;
    private String goto_city;
    private String email_address;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getFrom_country() {
        return from_country;
    }

    public void setFrom_country(String from_country) {
        this.from_country = from_country;
    }

    public String getGoto_city() {
        return goto_city;
    }

    public void setGoto_city(String goto_city) {
        this.goto_city = goto_city;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}
