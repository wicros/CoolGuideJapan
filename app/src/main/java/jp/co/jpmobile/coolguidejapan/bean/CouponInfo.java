package jp.co.jpmobile.coolguidejapan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wicors on 2016/7/11.
 */
public class CouponInfo {

    private List<Couponbean> coupongps;
    String result;

    public List<Couponbean> getCoupongps() {
        return coupongps;
    }

    public void setCoupongps(List<Couponbean> coupongps) {
        this.coupongps = coupongps;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public class Couponbean implements Serializable {
        String category;
        String name;
        String distance;
        String bannerUrl;
        String detailUrl;
        String startTime;
        String endTime;
        String id;
        int isfavorite;
        double longitude;
        double latitude ;
        int  is_goto_page;

        public int getIs_goto_page() {
            return is_goto_page;
        }

        public void setIs_goto_page(int is_goto_page) {
            this.is_goto_page = is_goto_page;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getIsfavorite() {
            return isfavorite;
        }

        public void setIsfavorite(int isfavorite) {
            this.isfavorite = isfavorite;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }

        public String getDetailUrl() {
            return detailUrl;
        }

        public void setDetailUrl(String detailUrl) {
            this.detailUrl = detailUrl;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
