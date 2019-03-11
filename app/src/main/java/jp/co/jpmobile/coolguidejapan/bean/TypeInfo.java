package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/7/8.
 */
public class TypeInfo {



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

    private List<TypeBean> type;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> area) {
        this.type = type;
    }

    public static class TypeBean {
        private String type_code;
        private String english_name;
        private String chinese_name;
        private String traditional_chinese_name;
        private String japanese_name;

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getEnglish_name() {
            return english_name;
        }

        public void setEnglish_name(String english_name) {
            this.english_name = english_name;
        }

        public String getChinese_name() {
            return chinese_name;
        }

        public void setChinese_name(String chinese_name) {
            this.chinese_name = chinese_name;
        }

        public String getTraditional_chinese_name() {
            return traditional_chinese_name;
        }

        public void setTraditional_chinese_name(String traditional_chinese_name) {
            this.traditional_chinese_name = traditional_chinese_name;
        }

        public String getJapanese_name() {
            return japanese_name;
        }

        public void setJapanese_name(String japanese_name) {
            this.japanese_name = japanese_name;
        }
    }
}
