package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView3;
    ProgressBar progressBar3;
    SearchView searchView;
    SearchAdapter searchAdapter;
    SearchAdapter.ItemClicked itemClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recyclerView3=findViewById(R.id.recycler);
        progressBar3=findViewById(R.id.progressBar3);
        searchView=findViewById(R.id.search_item);
        progressBar3.setVisibility(View.VISIBLE);
        recyclerView3.setVisibility(View.GONE);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        itemClicked=new SearchAdapter.ItemClicked() {
            @Override
            public void onItemClicked(String songs_name, String songs_url, String songs_image_url) {
                Intent intent=new Intent(SearchActivity.this,MusicActivity.class);
                intent.putExtra("music_link",songs_url);
                intent.putExtra("image_link",songs_image_url);
                intent.putExtra("music_name",songs_name);
                startActivity(intent);
                finish();
            }
        };

        show();
        progressBar3.setVisibility(View.GONE);
        recyclerView3.setVisibility(View.VISIBLE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                FirebaseRecyclerOptions<Song> options=new FirebaseRecyclerOptions.Builder<Song>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Song").orderByChild("song_name")
                                .startAt(query).endAt(query +"\uf8ff"),Song.class)
                        .build();
                searchAdapter=new SearchAdapter(options,itemClicked);
                searchAdapter.startListening();

                recyclerView3.setAdapter(searchAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FirebaseRecyclerOptions<Song> options=new FirebaseRecyclerOptions.Builder<Song>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Song").orderByChild("song_name")
                                .startAt(newText).endAt(newText+"\uf8ff"),Song.class)
                        .build();
                searchAdapter=new SearchAdapter(options,itemClicked);
                searchAdapter.startListening();

                recyclerView3.setAdapter(searchAdapter);
                return false;
            }
        });
    }
    void show(){
        FirebaseRecyclerOptions<Song> options=new FirebaseRecyclerOptions.Builder<Song>()
                .setQuery(FirebaseDatabase.getInstance().getReference("Song"),Song.class)
                .build();
        searchAdapter=new SearchAdapter(options,itemClicked);
        recyclerView3.setAdapter(searchAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        searchAdapter.stopListening();
    }
}