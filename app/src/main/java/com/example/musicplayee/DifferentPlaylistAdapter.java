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

public class DifferentPlaylistAdapter extends RecyclerView.Adapter<DifferentPlaylistAdapter.DiffHolder> {
    ArrayList<DifferentPlaylist> differentPlaylists;
    Context context;
    DifferentPlaylistItemClicked diff_activity;
    public interface DifferentPlaylistItemClicked{
        void onDifferentPlaylistItemClicked(int index);
    }

    public DifferentPlaylistAdapter(Context c,ArrayList<DifferentPlaylist> differentPlaylists,DifferentPlaylistItemClicked diff_activity){

        this.context=c;
        this.differentPlaylists=differentPlaylists;
        this.diff_activity=diff_activity;
    }

    @NonNull
    @Override
    public DiffHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.diff_item,parent,false);
        return new DiffHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DiffHolder holder, int position) {
        holder.diffText.setText( differentPlaylists.get(position).getDiff_caption());
        Glide.with(context).load(differentPlaylists.get(position).getDiff_image())
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(holder.diffImage);
    }

    @Override
    public int getItemCount() {
        return differentPlaylists.size();
    }

     class DiffHolder extends RecyclerView.ViewHolder{

        ImageView diffImage;
        TextView diffText;
        public DiffHolder(@NonNull View itemView) {
            super(itemView);
            diffImage=itemView.findViewById(R.id.diffImg);
            diffText=itemView.findViewById(R.id.diffText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    diff_activity.onDifferentPlaylistItemClicked(getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
