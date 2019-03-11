package jp.co.jpmobile.coolguidejapan.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wicors on 2016/8/5.
 */
public class NewsInfo {


    /**
     * result : OK
     * news_list : [{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 259","news_note":"news 259 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 258","news_note":"news 258 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 257","news_note":"news 257 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 256","news_note":"news 256 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 255","news_note":"news 255 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 254","news_note":"news 254 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 253","news_note":"news 253 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 252","news_note":"news 252 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 251","news_note":"news 251 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"},{"banner_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg","news_name":"name 250","news_note":"news 250 note","news_datetime":"2016-08-04","news_url":"http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg"}]
     */

    private String result;
    /**
     * banner_url : http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg
     * news_name : name 259
     * news_note : news 259 note
     * news_datetime : 2016-08-04
     * news_url : http://pic.baike.soso.com/p/20131212/20131212124127-693123010.jpg
     */

    private List<NewsListBean> news_list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<NewsListBean> getNews_list() {
        return news_list;
    }

    public void setNews_list(List<NewsListBean> news_list) {
        this.news_list = news_list;
    }

    public static class NewsListBean implements Serializable {
        private String banner_url;
        private String news_name;
        private String news_note;
        private String news_datetime;
        private String news_url;
        private int is_have_map;
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_have_map() {
            return is_have_map;
        }

        public void setIs_have_map(int is_have_map) {
            this.is_have_map = is_have_map;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getNews_name() {
            return news_name;
        }

        public void setNews_name(String news_name) {
            this.news_name = news_name;
        }

        public String getNews_note() {
            return news_note;
        }

        public void setNews_note(String news_note) {
            this.news_note = news_note;
        }

        public String getNews_datetime() {
            return news_datetime;
        }

        public void setNews_datetime(String news_datetime) {
            this.news_datetime = news_datetime;
        }

        public String getNews_url() {
            return news_url;
        }

        public void setNews_url(String news_url) {
            this.news_url = news_url;
        }
    }
}
