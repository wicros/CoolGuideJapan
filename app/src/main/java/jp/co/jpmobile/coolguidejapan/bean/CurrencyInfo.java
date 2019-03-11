package jp.co.jpmobile.coolguidejapan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wicors on 2016/7/14.
 */
public class CurrencyInfo {


    /**
     * result : OK
     * product : [{"product_id":1,"currency":"JPY","price":500},{"product_id":2,"currency":"JPY","price":1000},{"product_id":3,"currency":"JPY","price":1500},{"product_id":4,"currency":"JPY","price":2000},{"product_id":1,"currency":"RMB","price":39},{"product_id":2,"currency":"RMB","price":59},{"product_id":3,"currency":"RMB","price":148},{"product_id":4,"currency":"RMB","price":98}]
     */

    private String result;
    /**
     * product_id : 1
     * currency : JPY
     * price : 500
     */

    private List<ProductBean> product;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ProductBean> getProduct() {
        return product;
    }

    public void setProduct(List<ProductBean> product) {
        this.product = product;
    }

    public static class ProductBean implements Serializable{
        private String product_id;
        private String currency;
        private String price;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
