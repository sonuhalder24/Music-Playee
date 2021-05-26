package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SongActivity extends AppCompatActivity {
    Button songBut,albumBut;
    ImageView imageHome,imageSearch,imageProfile;
    Fragment fragmentSong,fragmentAlbums;
    FragmentManager fm;
    FragmentTransaction ft1,ft2,ft3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        songBut=findViewById(R.id.songBut);
        albumBut=findViewById(R.id.albumBut);
        imageHome=findViewById(R.id.imageHome);
        imageSearch=findViewById(R.id.imageSearch);
        imageProfile=findViewById(R.id.imageProfile);
        fm=getSupportFragmentManager();
        ft1=fm.beginTransaction();
        fragmentSong=new song_list();
        fragmentAlbums=new album_list();
        ft1.replace(R.id.fragmentSong,fragmentSong);
        ft1.commit();
        imageProfile.setAlpha((float).5);
        imageSearch.setAlpha((float).5);
        imageHome.setAlpha((float)1);

        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageProfile.setAlpha((float)1);
                imageHome.setAlpha((float).5);
                imageProfile.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageProfile.setAlpha((float) .5);
                    }
                },200);
                imageHome.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageHome.setAlpha((float) 1);
                    }
                },200);

                Intent intent=new Intent(SongActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSearch.setAlpha((float)1);
                imageHome.setAlpha((float).5);
                imageSearch.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageSearch.setAlpha((float) .5);
                    }
                },200);
                imageHome.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imageHome.setAlpha((float)1);
                    }
                },200);
             Intent intent=new Intent(SongActivity.this,SearchActivity.class);
             startActivity(intent);

            }
        });
    }

    public void ChangeFragment(View view){


        if(view==findViewById(R.id.textSong)){
            ft2=fm.beginTransaction();
            songBut.setBackgroundColor(Color.BLACK);
            albumBut.setBackgroundColor(Color.WHITE);
            ft2.replace(R.id.fragmentSong,fragmentSong);
            ft2.commit();
        }
        else if(view==findViewById(R.id.textAlbums)){
            ft3=fm.beginTransaction();
            albumBut.setBackgroundColor(Color.BLACK);
            songBut.setBackgroundColor(Color.WHITE);
            ft3.replace(R.id.fragmentSong,fragmentAlbums);
            ft3.commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.log_out){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SongActivity.this,MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
}