package edu.uga.cs.cs4060.stocksimulator.UIFunctions;

import android.app.Activity;
import android.net.Uri;
import android.widget.VideoView;

public class Backgoundvideo {
    VideoView mVideoView;

    public Backgoundvideo(){
        mVideoView = null;
    }

    public void playBackGroundVideo(Activity currentActivity, int videoViewId, int videoId) {
        mVideoView = currentActivity.findViewById(videoViewId);
        Uri uri = Uri.parse("android.resource://"+currentActivity.getPackageName()+"/"+ videoId);
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
