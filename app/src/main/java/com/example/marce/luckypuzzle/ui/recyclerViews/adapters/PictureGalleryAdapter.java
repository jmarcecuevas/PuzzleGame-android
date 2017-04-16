package com.example.marce.luckypuzzle.ui.recyclerViews.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.di.module.InteractorModule;
import com.example.marce.luckypuzzle.model.Square;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.ItemTouchHelperAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.ItemTouchHelperViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;

import static com.example.marce.luckypuzzle.R.drawable.splash_background;

/**
 * Created by marce on 15/04/17.
 */

public class PictureGalleryAdapter extends RecyclerView.Adapter<PictureGalleryAdapter.GalleryItemViewHolder> {

    private Context context;
    private ArrayList<Integer> imgIds;
    private ItemListener itemListener;

    public PictureGalleryAdapter(Context context, ArrayList<Integer> imgIds,ItemListener itemListener){
        this.context=context;
        this.imgIds=imgIds;
        this.itemListener=itemListener;
    }

    @Override
    public GalleryItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        GalleryItemViewHolder itemViewHolder = new GalleryItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(GalleryItemViewHolder holder, final int position) {
        Picasso.with(context).load(imgIds.get(position)).resize(240,320).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemListener.onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgIds.size();
    }


    public static class GalleryItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public GalleryItemViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.galleryItem);
        }
    }

    public interface ItemListener{
        void onItemSelected(int position);
    }
}
