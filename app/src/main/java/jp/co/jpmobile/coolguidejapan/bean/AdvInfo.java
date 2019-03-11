package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/8/4.
 */
public class AdvInfo {

    /**
     * result : OK
     * adv_list : [{"banner_url":"","advertising_url":"http://www.baidu.com"},{"banner_url":"","advertising_url":"http://www.baidu.com"},{"banner_url":"","advertising_url":"http://www.baidu.com"},{"banner_url":"","advertising_url":"http://www.baidu.com"},{"banner_url":"","advertising_url":"http://www.baidu.com"}]
     */

    private String result;
    /**
     * banner_url :
     * advertising_url : http://www.baidu.com
     */

    private List<AdvListBean> adv_list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<AdvListBean> getAdv_list() {
        return adv_list;
    }

    public void setAdv_list(List<AdvListBean> adv_list) {
        this.adv_list = adv_list;
    }

    public static class AdvListBean {
        private String banner_url;
        private String advertising_url;

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getAdvertising_url() {
            return advertising_url;
        }

        public void setAdvertising_url(String advertising_url) {
            this.advertising_url = advertising_url;
        }
    }
}
