package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/8/24.
 */
public class NewsMapInfo {

    /**
     * result : OK
     * newsMap : [{"id":1,"news_id":381,"name":"栃木県 華厳の滝","address":"栃木県日光市中宮祠２４７９−２","latitude":36.7379925,"longitude":139.5020685,"distance":224.48},{"id":2,"news_id":381,"name":"栃木県 东照宫","address":"栃木県日光市山内２３０１","latitude":36.7571715,"longitude":139.599984,"distance":232.84},{"id":3,"news_id":381,"name":"京都府 醍醐寺","address":"京都府京都市伏見区醍醐東大路町22","latitude":34.951024,"longitude":135.819563,"distance":251.39},{"id":4,"news_id":381,"name":"東京都 浅草","address":"東京都台東区浅草聖観音宗金龍山浅草寺本堂","latitude":35.7147651,"longitude":139.7966553,"distance":288.34},{"id":5,"news_id":381,"name":"大阪府 大阪城公園","address":"大阪府大阪市中央区大阪城","latitude":34.6823301,"longitude":135.5306297,"distance":289.76},{"id":6,"news_id":381,"name":"和歌山県 那智山","address":"和歌山県那智勝浦町那智山","latitude":33.6700548,"longitude":135.8922678,"distance":383.68}]
     */

    private String result;
    /**
     * id : 1
     * news_id : 381
     * name : 栃木県 華厳の滝
     * address : 栃木県日光市中宮祠２４７９−２
     * latitude : 36.7379925
     * longitude : 139.5020685
     * distance : 224.48
     */

    private List<NewsMapBean> newsMap;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<NewsMapBean> getNewsMap() {
        return newsMap;
    }

    public void setNewsMap(List<NewsMapBean> newsMap) {
        this.newsMap = newsMap;
    }

    public static class NewsMapBean {
        private int id;
        private int news_id;
        private String name;
        private String address;
        private double latitude;
        private double longitude;
        private double distance;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNews_id() {
            return news_id;
        }

        public void setNews_id(int news_id) {
            this.news_id = news_id;
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
