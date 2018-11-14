package edu.uga.cs.cs4060.stocksimulator.UIFunctions;

import android.app.Activity;
import android.net.Uri;
import android.widget.VideoView;

import edu.uga.cs.cs4060.stocksimulator.R;

public class backgoundVideo {
    VideoView mVideoView;

    public backgoundVideo(){
        mVideoView = null;
    }

    public void playBackGroundVideo(Activity currentActivity, int videoViewId) {
        mVideoView = currentActivity.findViewById(videoViewId);
        Uri uri = Uri.parse("android.resource://"+currentActivity.getPackageName()+"/"+R.raw.download);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
        mVideoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0f, 0f);
        });
    }

    public void pauseVideo(){
        mVideoView.pause();
    }
}
