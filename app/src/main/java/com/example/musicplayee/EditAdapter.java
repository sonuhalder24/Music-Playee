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

public class EditAdapter extends RecyclerView.Adapter<EditAdapter.EditHolder>{
    Context c;
    ArrayList<SelectElement> elements;
    ArrayList<Song> songs;
    SparseBooleanArray sparseBooleanArray=new SparseBooleanArray();
    ArrayList<String> selected_name=new ArrayList<>();
    ArrayList<String> exists_name=new ArrayList<>();
    OnEdited onEdited;

    public interface OnEdited{
        void onEdited(SparseBooleanArray sb);
    }

    public EditAdapter(Context c, ArrayList<SelectElement> elements, ArrayList<Song> songs,OnEdited onEdited) {
        this.c = c;
        this.elements = elements;
        this.songs = songs;
        this.onEdited=onEdited;
    }

    @NonNull
    @Override
    public EditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.select_row,parent,false);
        return new EditHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EditHolder holder, int position) {
        selected_name.clear();
        exists_name.clear();

        for(int i=0;i<songs.size();i++){
            exists_name.add(songs.get(i).getSong_name());
        }
        for(int i=0;i<elements.size();i++){
            selected_name.add(elements.get(i).getSong_name());
        }

        holder.nameSelect.setText(songs.get(position).getSong_name());
        holder.aboutSelect.setText(songs.get(position).getSong_about());
        if(selected_name.contains(exists_name.get(position))) {
            sparseBooleanArray.put(position,true);
        }
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
                    elements.add(new SelectElement(songs.get(position).getSong_name(),songs.get(position).getSong_link()));
                }
                else{
                    sparseBooleanArray.put(position,false);
                    int pause=-1;
                    for(int i=0;i<elements.size();i++){
                        if(songs.get(position).getSong_name().equals(elements.get(i).getSong_name())){
                            pause=i;
                        }
                    }
                    elements.remove(pause);
                }
                onEdited.onEdited(sparseBooleanArray);

            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class EditHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        ImageView imageSelect;
        TextView nameSelect,aboutSelect;
        public EditHolder(@NonNull View itemView) {
            super(itemView);
            checkBox=itemView.findViewById(R.id.checkbox1);
            imageSelect=itemView.findViewById(R.id.imgSelect);
            nameSelect=itemView.findViewById(R.id.titleSelect);
            aboutSelect=itemView.findViewById(R.id.aboutSelect);
        }
    }

}
