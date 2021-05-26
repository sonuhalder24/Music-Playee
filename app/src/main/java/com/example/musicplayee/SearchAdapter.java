package com.example.musicplayee;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SearchAdapter extends FirebaseRecyclerAdapter<Song,SearchAdapter.SearchHolder> {
SearchAdapter.ItemClicked activity;
    public interface ItemClicked{
        void onItemClicked(String songs_name,String songs_url,String songs_image_url);
    }

    public SearchAdapter(@NonNull FirebaseRecyclerOptions<Song> options,ItemClicked itemClicked) {
        super(options);
        this.activity=itemClicked;
    }

    @Override
    protected void onBindViewHolder(@NonNull SearchHolder holder, int position, @NonNull Song model) {
        holder.titleSong.setText(model.getSong_name());
        holder.aboutSong.setText(model.getSong_about());
        Glide.with(holder.imageSong.getContext()).load(model.getSong_image())
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(holder.imageSong);
        holder.single_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClicked(model.getSong_name(),model.getSong_link(),model.getSong_image());
            }
        });
    }

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);
        return new SearchAdapter.SearchHolder(v);

    }

    class SearchHolder extends RecyclerView.ViewHolder{
        LinearLayout single_linear;
        ImageView imageSong;
        TextView titleSong;
        TextView aboutSong;
        public SearchHolder(@NonNull View itemView) {
            super(itemView);

            single_linear=itemView.findViewById(R.id.single_lin);
            imageSong=itemView.findViewById(R.id.imgSong);
            titleSong=itemView.findViewById(R.id.titleSong);
            aboutSong=itemView.findViewById(R.id.aboutSong);

        }
    }
}
