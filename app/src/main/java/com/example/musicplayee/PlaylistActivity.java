package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.musicplayee.model.JcAudio;
import com.example.musicplayee.view.JcPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.musicplayee.MusicActivity.handler;
import static com.example.musicplayee.MusicActivity.mediaPlayer;
import static com.example.musicplayee.MusicActivity.updater;

public class PlaylistActivity extends AppCompatActivity {
    String playlistName;
    ArrayList<PlaylistLinks> playlistLinks;
    ImageView imagePlay;

    ArrayList<JcAudio> jcAudios;
    static JcPlayerView jcPlayerView;
    MediaPlayer mediaPlayer2;
    Handler handler2;
    Runnable runnable2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        jcPlayerView = (JcPlayerView) findViewById(R.id.jcPlayer);
        imagePlay=findViewById(R.id.imagePlay);

        mediaPlayer2=mediaPlayer;
        handler2=handler;
        runnable2=updater;
        if(mediaPlayer2!=null && handler2!=null && runnable2!=null){
            if(mediaPlayer.isPlaying()) {
                handler.removeCallbacks(updater);
            }
            mediaPlayer.release();
            mediaPlayer=new MediaPlayer();
        }

        if(jcPlayerView.isPlaying()){
            jcPlayerView.pause();
            jcPlayerView.kill();
        }

        playlistName=getIntent().getStringExtra("PlaylistName");

        Glide.with(this).load(getIntent().getStringExtra("PlayImage"))
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(imagePlay);

        playlistLinks=new ArrayList<>();

        jcAudios = new ArrayList<>();
        
        FirebaseDatabase.getInstance().getReference("Playlist/"+playlistName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    playlistLinks.add(snapshot1.getValue(PlaylistLinks.class));
                }
                for(int i=0;i<playlistLinks.size();i++){
                    jcAudios.add(JcAudio.createFromURL(playlistLinks.get(i).getSong_name(),playlistLinks.get(i).getSong_link()));
                }

                jcPlayerView.initPlaylist(jcAudios, null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

}