package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {
    RecyclerView recyclerView4;
    SelectAdapter selectAdapter;
    ProgressBar progressBar5;
    ArrayList<Song> songs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        recyclerView4=findViewById(R.id.recycler4);
        progressBar5=findViewById(R.id.progressBar5);
        progressBar5.setVisibility(View.VISIBLE);
        recyclerView4.setVisibility(View.GONE);
        songs=new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("Song").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    songs.add(snapshot1.getValue(Song.class));
                }
                selectAdapter.notifyDataSetChanged();
                progressBar5.setVisibility(View.GONE);
                recyclerView4.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selectAdapter=new SelectAdapter(SelectActivity.this,songs);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setAdapter(selectAdapter);

    }
}