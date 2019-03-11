package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/8/22.
 */
public class ShopInfo {

    /**
     * result : OK
     * shop : [{"rownum":0,"coupon_id":376,"shop_id":420,"name":"bic camera 札幌店","address":"北海道札幌市中央区北五条西2-1","latitude":43.0676618,"longitude":141.3523873,"distance":5938.02}]
     */

    private String result;
    /**
     * rownum : 0
     * coupon_id : 376
     * shop_id : 420
     * name : bic camera 札幌店
     * address : 北海道札幌市中央区北五条西2-1
     * latitude : 43.0676618
     * longitude : 141.3523873
     * distance : 5938.02
     */

    private List<ShopBean> shop;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ShopBean> getShop() {
        return shop;
    }

    public void setShop(List<ShopBean> shop) {
        this.shop = shop;
    }

    public static class ShopBean {
        private int rownum;
        private int coupon_id;
        private int shop_id;
        private String name;
        private String address;
        private double latitude;
        private double longitude;
        private double distance;
        private int id;

        public int getRownum() {
            return rownum;
        }

        public void setRownum(int rownum) {
            this.rownum = rownum;
        }

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }
}
