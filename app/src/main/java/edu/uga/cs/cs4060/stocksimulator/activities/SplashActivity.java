package edu.uga.cs.cs4060.stocksimulator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import java.lang.reflect.Field;
import java.util.Random;

import edu.uga.cs.cs4060.stocksimulator.R;
import edu.uga.cs.cs4060.stocksimulator.UIFunctions.Backgoundvideo;


public class SplashActivity extends BasicActivity {
    private AlphaAnimation buttonClick;
    private Backgoundvideo video;
    private Button register;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // hide actionbar for splash activity
        getSupportActionBar().hide();

        //initialize
        register = findViewById(R.id.register_button);
        login = findViewById(R.id.login_button);
        buttonClick = new AlphaAnimation(1F, 0.8F);
        video = new Backgoundvideo();

        // set listener
        register.setOnClickListener(new ButtonClickListener());
        login.setOnClickListener(new ButtonClickListener());

        //play video
        Field[] fields = R.raw.class.getFields();
        Random rand = new Random();
        System.out.println(fields.length);
        int index =  rand.nextInt(fields.length);
        int resourceID = 0;
        try {
            resourceID = fields[index].getInt(fields[index]);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        video.playBackGroundVideo(this,R.id.videoView, resourceID);

    }


    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            view.startAnimation(buttonClick);
            video.pauseVideo();
            switch (view.getId()){
                case R.id.register_button:
                    Intent registerIntent = new Intent(view.getContext(), RegisterActivity.class);
                    startActivity(registerIntent);
                    break;
                case R.id.login_button:
                    Intent loginIntent = new Intent(view.getContext(), LoginActivity.class);
                    startActivity(loginIntent);
                    break;
                default:
                    break;
            }
        }
    }
}
