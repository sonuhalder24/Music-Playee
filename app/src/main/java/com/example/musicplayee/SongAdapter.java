package com.example.musicplayee;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    ArrayList<Song> songs;
    Context context;
    ItemClicked activity;
    public interface ItemClicked{
        void onItemClicked(int index);
    }
    public SongAdapter(Context c, ArrayList<Song> songs,ItemClicked itemClicked) {
        this.context=c;
        this.songs=songs;
        this.activity=itemClicked;
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new SongHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {

        holder.titleSong.setText(songs.get(position).getSong_name());
        holder.aboutSong.setText(songs.get(position).getSong_about());
        Glide.with(context).load(songs.get(position).getSong_image())
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(holder.imageSong);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SongHolder extends RecyclerView.ViewHolder{
        ImageView imageSong;
        TextView titleSong;
        TextView aboutSong;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            imageSong=itemView.findViewById(R.id.imgSong);
            titleSong=itemView.findViewById(R.id.titleSong);
            aboutSong=itemView.findViewById(R.id.aboutSong);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(getAbsoluteAdapterPosition());
                    titleSong.setTextColor(context.getResources().getColor(R.color.colorSelect));
                    titleSong.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            titleSong.setTextColor(Color.BLACK);
                        }
                    },100);
                }
            });
        }
    }
}
