package com.example.musicplayee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

public class EditActivity extends AppCompatActivity {
    RecyclerView recyclerView5;
    ProgressBar progressBar6;
    Button sel_button2;
    EditAdapter.OnEdited onEdited;
    ArrayList<Song> song;
    ArrayList<SelectElement>element1,element2;
    EditAdapter editAdapter;
    SparseBooleanArray sparseBooleanArray1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        recyclerView5=findViewById(R.id.recycler5);
        progressBar6=findViewById(R.id.progressBar6);
        sel_button2=findViewById(R.id.sel_button2);

        song=new ArrayList<>();
        element1=new ArrayList<>();
        element2=new ArrayList<>();

        onEdited=new EditAdapter.OnEdited() {
            @Override
            public void onEdited(SparseBooleanArray sb) {
                for(int i=0;i<song.size();i++){
                    if(sb.get(i)){
                        sparseBooleanArray1=sb;
                    }
                }
            }
        };

        FirebaseDatabase.getInstance().getReference("Song").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    song.add(snapshot1.getValue(Song.class));
                }
                editAdapter.notifyDataSetChanged();
                progressBar6.setVisibility(View.GONE);
                recyclerView5.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Playlist/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1:snapshot.getChildren()){
                            element1.add(snapshot1.getValue(SelectElement.class));
                        }
                        editAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        editAdapter=new EditAdapter(EditActivity.this,element1,song,onEdited);
        recyclerView5.setLayoutManager(new LinearLayoutManager(this));
        recyclerView5.setAdapter(editAdapter);

        sel_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sparseBooleanArray1==null){
                    Toast.makeText(EditActivity.this, "Select at least one song", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean checked=false;
                    for(int i=0;i<song.size();i++){
                        if(sparseBooleanArray1.get(i)){
                            checked=true;
                            break;
                        }
                    }
                    if(checked==false){
                        Toast.makeText(EditActivity.this, "Select at least one song", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        FirebaseDatabase.getInstance()
                                .getReference("Playlist/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(null);
                        for (int i = 0; i < song.size(); i++) {
                            if (sparseBooleanArray1.get(i)) {
                                element2.add(new SelectElement(song.get(i).getSong_name(), song.get(i).getSong_link()));
                            }
                        }
                        for (int i = 0; i < element2.size(); i++) {
                            FirebaseDatabase.getInstance()
                                    .getReference("Playlist/" + FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .push().setValue(element2.get(i));
                        }

                        Intent intent = new Intent(EditActivity.this, SongActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}