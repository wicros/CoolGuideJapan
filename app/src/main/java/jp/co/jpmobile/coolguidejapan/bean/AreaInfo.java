package jp.co.jpmobile.coolguidejapan.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wicors on 2016/7/8.
 */
public class AreaInfo {



    /**
     * result : OK
     * area : [{"areacode":"CN","englishname":"China","chinesename":"中国","traditional_chinesename":"中國","japanesename":"中国"},{"areacode":"TW","englishname":"Taiwan","chinesename":"台湾","traditional_chinesename":"中國 臺灣","japanesename":"中国 台湾"}]
     */

    private String result;
    /**
     * areacode : CN
     * englishname : China
     * chinesename : 中国
     * traditional_chinesename : 中國
     * japanesename : 中国
     */

    private List<AreaBean> area;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<AreaBean> getArea() {
        return area;
    }

    public void setArea(List<AreaBean> area) {
        this.area = area;
    }

    public static class AreaBean {
        private String areacode;
        private String areaname;

        public String getAreacode() {
            return areacode;
        }

        public void setAreacode(String areacode) {
            this.areacode = areacode;
        }

        public String getAreaname() {
            return areaname;
        }

        public void setAreaname(String areaname) {
            this.areaname = areaname;
        }
    }
}
