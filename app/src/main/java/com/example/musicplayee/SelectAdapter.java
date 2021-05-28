package com.example.musicplayee;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.SelectHolder>{
    ArrayList<Song> songs;
    SparseBooleanArray sparseBooleanArray=new SparseBooleanArray();
    Context c;
    onCheckClicked onCC;
    interface onCheckClicked{
        void onCheckedItemClicked(SparseBooleanArray sba);
    }
    public SelectAdapter(Context context, ArrayList<Song> song,onCheckClicked onCheckClicked){
        this.c=context;
        this.songs=song;
        this.onCC=onCheckClicked;
    }

    @NonNull
    @Override
    public SelectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.select_row,parent,false);
        return new SelectHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectHolder holder, int position) {
        holder.nameSelect.setText(songs.get(position).getSong_name());
        holder.aboutSelect.setText(songs.get(position).getSong_about());
        holder.checkBox.setChecked(sparseBooleanArray.get(position));
        Glide.with(c).load(songs.get(position).getSong_image())
                .error(R.drawable.default_image)
                .placeholder(R.drawable.default_image)
                .into(holder.imageSelect);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    sparseBooleanArray.put(position,true);
                }
                else{
                    sparseBooleanArray.put(position,false);
                }
                onCC.onCheckedItemClicked(sparseBooleanArray);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    class SelectHolder extends RecyclerView.ViewHolder{
        CheckBox checkBox;
        ImageView imageSelect;
        TextView nameSelect,aboutSelect;

        public SelectHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox1);
            imageSelect=itemView.findViewById(R.id.imgSelect);
            nameSelect=itemView.findViewById(R.id.titleSelect);
            aboutSelect=itemView.findViewById(R.id.aboutSelect);

        }
    }
}
