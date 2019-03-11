package jp.co.jpmobile.coolguidejapan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wicors on 2016/7/14.
 */
public class ChargePlanInfo {


    /**
     * result : OK
     * product : [{"product_id":1,"product_code":"8R161","product_name":"使用期限延長7天（活动价）"},{"product_id":2,"product_code":"8R160","product_name":"使用期限延長7天增加1G流量（活动价）"},{"product_id":3,"product_code":"8R163","product_name":"使用期限延長30天（活动价）"},{"product_id":4,"product_code":"8R162","product_name":"使用期限延長30天增加2G流量（活动价）"}]
     */

    private String result;
    /**
     * product_id : 1
     * product_code : 8R161
     * product_name : 使用期限延長7天（活动价）
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

    public static class ProductBean implements Serializable {
        private String product_id;
        private String product_code;
        private String product_name;

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_code() {
            return product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }
    }
}
