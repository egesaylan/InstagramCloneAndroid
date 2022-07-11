package com.egesaylan.instagramandroidjava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.egesaylan.instagramandroidjava.databinding.RecyclerRowBinding;
import com.egesaylan.instagramandroidjava.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.PostHolder> {

    private ArrayList<Post> arrayListPost;

    public Adapter(ArrayList<Post> arrayListPost) {
        this.arrayListPost = arrayListPost;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recyclerRowBinding.UserMailTextRecyclerView.setText(arrayListPost.get(position).mail);
        holder.recyclerRowBinding.commentTextRecyclerView.setText(arrayListPost.get(position).comment);
        Picasso.get().load(arrayListPost.get(position).downloadUrl).into(holder.recyclerRowBinding.ImageViewRecyclerView);
    }

    @Override
    public int getItemCount() {
        return arrayListPost.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding recyclerRowBinding;

        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
