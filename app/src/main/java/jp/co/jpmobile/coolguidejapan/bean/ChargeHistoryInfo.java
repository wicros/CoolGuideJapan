package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/7/15.
 */
public class ChargeHistoryInfo {

    /**
     * phoneNumber : 07041358714
     * planList : [{"productCode":"8R161","chargeDate":"2016/07/14 20:36:23","expirationDate":"2016/07/21"},{"productCode":"8R161","chargeDate":"2016/07/14 20:41:33","expirationDate":"2016/07/21"},{"productCode":"8R161","chargeDate":"2016/07/14 21:47:00","expirationDate":"2016/07/21"},{"productCode":"8R161","chargeDate":"2016/07/14 21:58:18","expirationDate":"2016/07/21"},{"productCode":"8R161","chargeDate":"2016/07/15 13:16:09","expirationDate":"2016/07/22"},{"productCode":"8R161","chargeDate":"2016/07/15 13:18:48","expirationDate":"2016/07/22"},{"productCode":"8R161","chargeDate":"2016/07/15 16:02:04","expirationDate":"2016/07/22"}]
     * result : 000
     */

    private String phoneNumber;
    private String result;
    /**
     * productCode : 8R161
     * chargeDate : 2016/07/14 20:36:23
     * expirationDate : 2016/07/21
     */

    private List<PlanListBean> planList;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<PlanListBean> getPlanList() {
        return planList;
    }

    public void setPlanList(List<PlanListBean> planList) {
        this.planList = planList;
    }

    public static class PlanListBean {
        private String productCode;
        private String chargeDate;
        private String expirationDate;

        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public String getChargeDate() {
            return chargeDate;
        }

        public void setChargeDate(String chargeDate) {
            this.chargeDate = chargeDate;
        }

        public String getExpirationDate() {
            return expirationDate;
        }

        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }
    }
}
