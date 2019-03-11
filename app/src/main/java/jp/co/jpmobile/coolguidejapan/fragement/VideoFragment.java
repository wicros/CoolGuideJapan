package jp.co.jpmobile.coolguidejapan.fragement;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;


import com.metaps.analytics.Analytics;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.activity.MainActivity;
import jp.co.jpmobile.coolguidejapan.activity.VideoPlayActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.base.BaseFragment;
import jp.co.jpmobile.coolguidejapan.bean.VideoInfo;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;
import jp.co.jpmobile.coolguidejapan.utils.AppUtils;
import jp.co.jpmobile.coolguidejapan.utils.HttpUtils;
import jp.co.jpmobile.coolguidejapan.utils.UIUtils;
import me.maxwin.view.XListView;

/**
 * @创建者 Administrator
 * @创时间 2015-8-14 下午4:00:42
 * @描述 TODO
 * @版本 $Rev: 8 $
 * @更新者 $Author: admin $
 * @更新时间 $Date: 2015-08-14 17:44:25 +0800 (星期五, 14 八月 2015) $
 * @更新描述 TODO
 */
public class VideoFragment extends BaseFragment{

    private int pageNumber;
    private XListView vListview;
    private  List<VideoInfo.VideoListBean> video_list;
    private VideoAdapter videoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_video, null);
        View headview = inflater.inflate(R.layout.videootherview, null);
        vListview = (XListView) view.findViewById(R.id.video_listview);
        vListview.setPullRefreshEnable(false);
        vListview.setPullLoadEnable(true);
        vListview.addFooterView(headview);
        vListview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                Analytics.trackEvent(Urlconf.MYTAPS+"click", "video-onLoadMore");
                initData();
            }
        });
        headview.findViewById(R.id.youtube_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goToWebView(getActivity(),"https://www.youtube.com","YouTube");
            }
        });
        headview.findViewById(R.id.youku_im).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.goToWebView(getActivity(),"http://www.youku.com","YouKu");
            }
        });
        setView();
        return view;
    }

    private void setView() {

    }


    @Override
    public void onStart() {
        super.onStart();
        pageNumber = 1;
        initData();
    }

    @Override
    public void onStop() {
        super.onStop();
        videoAdapter = null;
    }

    private void initData() {
        HashMap<String, String> map = new HashMap<>();

        map.put("pageNumber",pageNumber+"");
        map.put("token", AppUtils.getFromPreference(AppUtils.KEY_TOKEN));
        map.put("deviceId", BaseApplication.getDevice_id());

        HttpUtils.OnNetResponseListner onNetResponseListner = new HttpUtils.OnNetResponseListner() {



            @Override
            public void onStart(int what) {
                if (videoAdapter == null){
                    ((MainActivity) getActivity()).showProgressDialog();
                }
            }

            @Override
            public void onSucceed(int what, Object o) {
                List<VideoInfo.VideoListBean> list = ((VideoInfo) o).getVideo_list();
                if (list != null && list.size() > 0){
                    if (videoAdapter == null){
                        video_list = list;
                        videoAdapter = new VideoAdapter();
                        vListview.setAdapter(videoAdapter);
                    }else {
                        video_list.addAll(list);
                        videoAdapter.notifyDataSetChanged();
                    }
                    pageNumber++;
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {
                ((MainActivity) getActivity()).hideProgressDialog();
                vListview.stopLoadMore();
            }
        };

        HttpUtils.sendGetRequet(Urlconf.GET_VIDEO,map,onNetResponseListner, VideoInfo.class);
    }

    class VideoAdapter extends BaseAdapter{

        class VideoViewHolder{
            public ImageView videocover;
            public TextView videoname;
            public TextView videonote;
            public TextView date_time;
        }

        class CoverOnClickListener implements View.OnClickListener{

            private String holder;

            public CoverOnClickListener(String holder){
                this.holder = holder;
            };
            @Override
            public void onClick(View v) {
                Analytics.trackEvent(Urlconf.MYTAPS+"click", "videoplay");
                Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                intent.putExtra("path",holder);
                startActivity(intent);
            }
        }


        @Override
        public int getCount() {
            return video_list == null?0:video_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VideoViewHolder holder = null;
            if (convertView == null){
                convertView = View.inflate(getActivity(),R.layout.list_video_item,null);
                holder = new VideoViewHolder();
                holder.videocover = (ImageView) convertView.findViewById(R.id.videocover);
                holder.videoname = (TextView) convertView.findViewById(R.id.video_name);
                holder.videonote = (TextView) convertView.findViewById(R.id.video_note);
                holder.date_time = (TextView) convertView.findViewById(R.id.date_time);
                convertView.setTag(holder);
            }else {
                holder = (VideoViewHolder) convertView.getTag();
            }

            VideoInfo.VideoListBean videoListBean = video_list.get(position);

            String path = videoListBean.getUrl();

            holder.videonote.setText(videoListBean.getVideo_note());
            holder.videonote.setVisibility(View.GONE);
            holder.videoname.setText(videoListBean.getVideo_name());
            holder.date_time.setText(videoListBean.getUpdate_datetime()+" | "+videoListBean.getVideo_size()+" MB");
            Picasso.with(getActivity()).load(videoListBean.getImage_url()).into(holder.videocover);
            convertView.setOnClickListener(new CoverOnClickListener(path));
            return convertView;
        }
    }
}
