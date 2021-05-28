package com.example.musicplayee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class album_list extends Fragment {
    int add_btnClick=0;
    ImageView imageAdd;
    CardView cardView;
    RecyclerView recyclerView,recyclerView2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    ArrayList<DifferentPlaylist> diffAlbum;
    ArrayList<ArtistPlaylist>artistAlbum;
    ProgressBar progressBar,progressBar2;

    DifferentPlaylistAdapter differentPlaylistAdapter;
    ArtistPlaylistAdapter artistPlaylistAdapter;
    DifferentPlaylistAdapter.DifferentPlaylistItemClicked differentPlaylistItemClicked;
    ArtistPlaylistAdapter.ArtistPlaylistItemClicked artistPlaylistItemClicked;


    private View listItemView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listItemView=inflater.inflate(R.layout.fragment_album_list, container, false);

        recyclerView=listItemView.findViewById(R.id.diffRecyclerView);
        recyclerView2=listItemView.findViewById(R.id.artistRecyclerView);
        imageAdd=listItemView.findViewById(R.id.add_btn);
        cardView=listItemView.findViewById(R.id.cardV);
        progressBar=listItemView.findViewById(R.id.progressBar1);
        progressBar2=listItemView.findViewById(R.id.progressBar2);
        diffAlbum=new ArrayList<>();
        artistAlbum=new ArrayList<>();
        differentPlaylistItemClicked=new DifferentPlaylistAdapter.DifferentPlaylistItemClicked() {
            @Override
            public void onDifferentPlaylistItemClicked(int index) {
                String name=diffAlbum.get(index).getDiff_caption();
                String image=diffAlbum.get(index).getDiff_image();

                Intent intent=new Intent(getContext(),PlaylistActivity.class);
                intent.putExtra("PlaylistName",name);
                intent.putExtra("PlayImage",image);
                startActivity(intent);
            }
        };

        artistPlaylistItemClicked=new ArtistPlaylistAdapter.ArtistPlaylistItemClicked() {
            @Override
            public void onArtistPlaylistItemClicked(int index) {
                String name=artistAlbum.get(index).getArtist_name();
                String image=artistAlbum.get(index).getArtist_image();

                Intent intent=new Intent(getContext(),PlaylistActivity.class);
                intent.putExtra("PlaylistName",name);
                intent.putExtra("PlayImage",image);
                startActivity(intent);
            }
        };


        recyclerView.setVisibility(View.GONE);
        recyclerView2.setVisibility(View.GONE);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(add_btnClick==0){
//                    Toast.makeText(getContext(), "Add your playlist", Toast.LENGTH_SHORT).show();
//                }
//                else if(add_btnClick==1){
                    Intent intent=new Intent(getContext(),PlaylistActivity.class);
                    intent.putExtra("PlaylistName", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    intent.putExtra("PlayImage",
                            "https://us.123rf.com/450wm/yusufdemirci/yusufdemirci1803/yusufdemirci180300346/97820259-vector-illustration-of-kids-making-art-performance.jpg?ver=6");
                    startActivity(intent);
//                }
            }
        });
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), SelectActivity.class);
                startActivity(intent);
                if(add_btnClick==0) {
                    add_btnClick = 1;
                    imageAdd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageAdd.setImageResource(R.drawable.edit);
                        }
                    },1000);
                }
            }
        });

        FirebaseDatabase.getInstance().getReference("Different_Playlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    diffAlbum.add(snapshot1.getValue(DifferentPlaylist.class));
                }
                differentPlaylistAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Artists_Playlist").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot2:snapshot.getChildren()){
                    artistAlbum.add(snapshot2.getValue(ArtistPlaylist.class));
                }
                artistPlaylistAdapter.notifyDataSetChanged();
                progressBar2.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        differentPlaylistAdapter=new DifferentPlaylistAdapter(getContext(),diffAlbum,differentPlaylistItemClicked);
        artistPlaylistAdapter=new ArtistPlaylistAdapter(getContext(),artistAlbum,artistPlaylistItemClicked);

        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager2=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(differentPlaylistAdapter);

        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(artistPlaylistAdapter);


        return listItemView;
    }
}