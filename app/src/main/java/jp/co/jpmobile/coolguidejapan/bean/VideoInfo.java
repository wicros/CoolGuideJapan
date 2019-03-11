package jp.co.jpmobile.coolguidejapan.bean;

import java.util.List;

/**
 * Created by wicors on 2016/8/5.
 */
public class VideoInfo {


    /**
     * result : OK
     * video_list : [{"url":"http://52.68.119.217/videos/sample.mp4","video_name":"sample 290","video_note":"sample 290 note","update_datetime":"2016-08-04"},{"url":"http://52.68.119.217/videos/sample.mp4","video_name":"sample 289","video_note":"sample 289 note","update_datetime":"2016-08-04"}]
     */

    private String result;
    /**
     * url : http://52.68.119.217/videos/sample.mp4
     * video_name : sample 290
     * video_note : sample 290 note
     * update_datetime : 2016-08-04
     */

    private List<VideoListBean> video_list;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<VideoListBean> getVideo_list() {
        return video_list;
    }

    public void setVideo_list(List<VideoListBean> video_list) {
        this.video_list = video_list;
    }

    public static class VideoListBean {
        private String url;
        private String video_name;
        private String video_note;
        private String update_datetime;
        private String image_url ;
        private String video_size ;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getVideo_size() {
            return video_size;
        }

        public void setVideo_size(String video_size) {
            this.video_size = video_size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public String getVideo_note() {
            return video_note;
        }

        public void setVideo_note(String video_note) {
            this.video_note = video_note;
        }

        public String getUpdate_datetime() {
            return update_datetime;
        }

        public void setUpdate_datetime(String update_datetime) {
            this.update_datetime = update_datetime;
        }
    }
}
