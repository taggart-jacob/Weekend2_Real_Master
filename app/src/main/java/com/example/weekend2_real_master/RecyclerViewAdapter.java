package com.example.weekend2_real_master;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//import android.widget.ImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    ArrayList<Celebrity> celebList;

    public RecyclerViewAdapter(ArrayList<Celebrity> celebList){
        this.celebList = celebList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //takes the item and inflates it into memory so that it actually exists
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celeb_item, parent, false);
        //creates item view to display each item in the list
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //position keeps track of which item gets rendered with position acting as a iterator
        final Celebrity itemsCeleb = celebList.get(position);
        //pulls the object at the specific index of the array list and render the values from the movie's obj to views
        holder.tvName.setText(itemsCeleb.getName());
        holder.tvAge.setText(itemsCeleb.getAge());
        holder.tvProfession.setText(itemsCeleb.getProfession());
        //
        //Glide.with(holder.imgImage.getContext()).load("https://cdn.wallpapersafari.com/70/36/6mEV3A.jpg").into(holder.imgImage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), itemsCeleb.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addCeleb(Celebrity celeb){
        celebList.add(celeb);
        //adds a new movie to the recycler view
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return celebList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvAge;
        TextView tvProfession;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvProfession = itemView.findViewById(R.id.tvProfession);
        }
    }

}