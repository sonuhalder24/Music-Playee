package com.example.musicplayee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArtistPlaylistAdapter extends RecyclerView.Adapter<ArtistPlaylistAdapter.ArtistHolder> {
    ArrayList<ArtistPlaylist> artistPlaylists;
    Context context;
    ArtistPlaylistItemClicked artist__activity;

    public interface ArtistPlaylistItemClicked{
        void onArtistPlaylistItemClicked(int index);
    }

    public ArtistPlaylistAdapter(Context c,ArrayList<ArtistPlaylist> artistPlaylists,ArtistPlaylistItemClicked artist__activity){
        this.artistPlaylists=artistPlaylists;
        this.context=c;
        this.artist__activity=artist__activity;
    }

    @NonNull
    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.diff_item,parent,false);
        return new ArtistHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
        holder.artistText.setText( artistPlaylists.get(position).getArtist_name());
        Glide.with(context).load(artistPlaylists.get(position).getArtist_image())
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(holder.artistImage);
    }

    @Override
    public int getItemCount() {
        return artistPlaylists.size();
    }

    class ArtistHolder extends RecyclerView.ViewHolder{
        ImageView artistImage;
        TextView artistText;
        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            artistImage=itemView.findViewById(R.id.diffImg);
            artistText=itemView.findViewById(R.id.diffText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    artist__activity.onArtistPlaylistItemClicked(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
