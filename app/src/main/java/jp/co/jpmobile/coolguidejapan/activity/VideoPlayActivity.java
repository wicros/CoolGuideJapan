package jp.co.jpmobile.coolguidejapan.activity;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseActivity;
import jp.co.jpmobile.coolguidejapan.fragement.VideoFragment;

public class VideoPlayActivity extends BaseActivity implements MediaPlayer.OnPreparedListener {

    private MediaController mediaController;
    private VideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        showBackButon();
        videoview = (VideoView) findViewById(R.id.videoview);

        videoview.setVideoPath(getIntent().getStringExtra("path"));
        mediaController = new MediaController(VideoPlayActivity.this);
        videoview.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoview);

        videoview.resume();
        videoview.requestFocus();
        videoview.setOnPreparedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mediaController.show();
        videoview.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }
}
