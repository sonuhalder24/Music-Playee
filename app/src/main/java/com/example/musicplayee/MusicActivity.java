package com.example.musicplayee;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.musicplayee.view.JcPlayerView;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.musicplayee.PlaylistActivity.jcPlayerView;

public class MusicActivity extends AppCompatActivity {
    ImageView imageMusic,imagePlayPause;
    TextView textCurrentTime,textTotalTime,songName;
    SeekBar seekBar;
    static MediaPlayer mediaPlayer;
    static Handler handler=new Handler();
    String music;
    static Runnable updater;
    JcPlayerView jcPlayerView2;

    LottieAnimationView lottieAnimationView;
    
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        imageMusic=findViewById(R.id.imageMusic);
        imagePlayPause=findViewById(R.id.playPause);
        textCurrentTime=findViewById(R.id.textCurrentTime);
        songName=findViewById(R.id.name);
        textTotalTime=findViewById(R.id.textTotalDuration);
        seekBar=findViewById(R.id.playerSeekBar);

        lottieAnimationView=findViewById(R.id.lottie);
        music=getIntent().getStringExtra("music_link");
        jcPlayerView2=jcPlayerView;

        if(jcPlayerView2!=null){
            if(jcPlayerView2.isPlaying()){
                jcPlayerView2.pause();
                jcPlayerView2.kill();
            }
        }

        if(mediaPlayer==null){
            mediaPlayer=new MediaPlayer();

            //Background thread, here calculate seekBar position ,time and update it
             updater = new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                    long currentDuration=mediaPlayer.getCurrentPosition();
                    textCurrentTime.setText(milliSecondsToTimer(currentDuration));
                }
            };
        }
        else{
            if(mediaPlayer.isPlaying()) {
                handler.removeCallbacks(updater);
            }
                mediaPlayer.release();
                mediaPlayer=new MediaPlayer();
        }

        seekBar.setMax(100);


        Glide.with(this).load(getIntent().getStringExtra("image_link"))
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(imageMusic);
        songName.setText(getIntent().getStringExtra("music_name"));

        //song play and pause activity
        imagePlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareMediaPlayer();

                if(mediaPlayer.isPlaying()){
                    handler.removeCallbacks(updater);
                    mediaPlayer.pause();
                    imagePlayPause.setImageResource(R.drawable.play);

                }else{
                    mediaPlayer.start();
                    imagePlayPause.setImageResource(R.drawable.pause);
                    updateSeekBar();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                }
            }
        });



        //seekBar touch event
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar seekBar=(SeekBar)v;
                int playPosition=(mediaPlayer.getDuration()/100)*seekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                textCurrentTime.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });

        //starting time like progress in seekBar
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                seekBar.setSecondaryProgress(percent);
            }
        });

        //After complete the music
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                seekBar.setProgress(0);
                imagePlayPause.setImageResource(R.drawable.play);
                textCurrentTime.setText(R.string.zero);
                textTotalTime.setText(R.string.zero);
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });
    }


    //prepare Media for playing the song
    private void prepareMediaPlayer(){
        try{
            mediaPlayer.setDataSource(music);
            mediaPlayer.prepare();
            textTotalTime.setText(milliSecondsToTimer(mediaPlayer.getDuration()));
        }catch (Exception e){

        }
    }



    //Update seekBar with respect to song and every 1s duration time update function
    private void updateSeekBar(){
        if(mediaPlayer.isPlaying()){
            seekBar.setProgress((int) (((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            handler.postDelayed(updater,1000);
        }
    }

    //Time calculate with respect to hours,minutes,seconds function
    private String milliSecondsToTimer(long milliSeconds){
        String timerString="";
        String secondsString;

        int hours=(int)(milliSeconds/(1000*60*60));
        int minutes=(int)(milliSeconds%(1000*60*60))/(1000*60);
        int seconds=(int)((milliSeconds % (1000*60*60))%(1000*60)/1000);
        if (hours>0){
            timerString=hours+":";
        }
        if(seconds<10){
            secondsString="0"+seconds;
        }
        else{
            secondsString=""+seconds;
        }
        timerString=timerString+minutes+":"+secondsString;
        return timerString;
    }
}