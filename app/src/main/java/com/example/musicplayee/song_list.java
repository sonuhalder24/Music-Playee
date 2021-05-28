package com.example.musicplayee;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class song_list extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Song> song;
    SongAdapter songAdapter;
    ProgressBar progressBar;
    SongAdapter.ItemClicked itemClicked;


    private View listItemView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listItemView=inflater.inflate(R.layout.fragment_song_list, container, false);
        recyclerView=listItemView.findViewById(R.id.recyclerView);
        progressBar=listItemView.findViewById(R.id.progressBar);

        song= new ArrayList<>();
        itemClicked=new SongAdapter.ItemClicked() {
            @Override
            public void onItemClicked(int index) {
                Intent intent=new Intent(getActivity(),MusicActivity.class);
                intent.putExtra("music_link",song.get(index).getSong_link());
                intent.putExtra("image_link",song.get(index).getSong_image());
                intent.putExtra("music_name",song.get(index).getSong_name());
                startActivity(intent);
            }
        };

        FirebaseDatabase.getInstance().getReference("Song").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    song.add(snapshot1.getValue(Song.class));
                }
                songAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        songAdapter=new SongAdapter(getContext(),song,itemClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(songAdapter);


        return listItemView;
    }
}