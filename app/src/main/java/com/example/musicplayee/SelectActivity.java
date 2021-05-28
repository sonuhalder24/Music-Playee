package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    ArrayList<SelectElement>elements;
    SparseBooleanArray sparseBooleanArray1;


    Button select_button;
    SelectAdapter.onCheckClicked checkClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        recyclerView4=findViewById(R.id.recycler4);
        progressBar5=findViewById(R.id.progressBar5);
        select_button=findViewById(R.id.sel_button);
        progressBar5.setVisibility(View.VISIBLE);
        recyclerView4.setVisibility(View.GONE);
        songs=new ArrayList<>();
        elements=new ArrayList<>();

        checkClicked=new SelectAdapter.onCheckClicked() {
            @Override
            public void onCheckedItemClicked(SparseBooleanArray sba) {

                for(int i=0;i<songs.size();i++){
                    if(sba.get(i)){
                        sparseBooleanArray1=sba;
                    }
                }

            }
        };

        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sparseBooleanArray1==null){
                    Toast.makeText(SelectActivity.this, "Select at least one song", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < songs.size(); i++) {
                        if (sparseBooleanArray1.get(i)) {
                            elements.add(new SelectElement(songs.get(i).getSong_name(), songs.get(i).getSong_link()));
                        }
                    }
                    for(int i=0;i<elements.size();i++) {
                        FirebaseDatabase.getInstance()
                                .getReference("Playlist/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .push().setValue(elements.get(i));
                    }

                    Intent intent = new Intent(SelectActivity.this, SongActivity.class);
                    startActivity(intent);

                }
            }
        });

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

        selectAdapter=new SelectAdapter(SelectActivity.this,songs,checkClicked);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setAdapter(selectAdapter);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Press the select button", Toast.LENGTH_SHORT).show();
    }
}