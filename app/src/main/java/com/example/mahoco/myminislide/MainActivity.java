package com.example.mahoco.myminislide;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher mImageSwitcher;
    int[] mImageResources = {R.drawable.img00,R.drawable.img03
            ,R.drawable.img04,R.drawable.img05
            ,R.drawable.img06,R.drawable.img07
            ,R.drawable.img08,R.drawable.img16
            ,R.drawable.img17,R.drawable.img19
            ,R.drawable.img21,R.drawable.img22,R.drawable.img24};
    int mPosition = 0;
    boolean mIsSlideshow = false;
    MediaPlayer mMediaPlayer;
    public class MainTimerTask extends TimerTask {
        @Override
        public void run(){
            if(mIsSlideshow){
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        movePosition(1);
                    }
                });
            }
        }
    }
    Timer mTimer = new Timer();
    TimerTask mTimerTask = new MainTimerTask();
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageSwitcher = (ImageSwitcher)findViewById(R.id.imageSwitcher);
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory(){
            @Override
            public View makeView(){
                ImageView imageView =
                        new ImageView(getApplicationContext());
                return imageView;
            }
        });
        mImageSwitcher.setImageResource(mImageResources[0]);
        mTimer.schedule(mTimerTask,0,3000);
        mMediaPlayer = MediaPlayer.create(this,R.raw.getdown);
        mMediaPlayer.setLooping(true);
    }
    private void movePosition(int move){
        mPosition = mPosition + move;
        if(mPosition >= mImageResources.length){
            mPosition = 0;
        }else if(mPosition < 0){
            mPosition = mImageResources.length - 1;
        }
        mImageSwitcher.setImageResource(mImageResources[mPosition]);
    }
    public void onSlideshowButtonTapped(View view){
        mIsSlideshow = !mIsSlideshow;
        if(mIsSlideshow){
            mMediaPlayer.start();
        }else{
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        }
    }
}
