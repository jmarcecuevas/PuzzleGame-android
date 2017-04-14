package com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marce.luckypuzzle.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by marce on 13/04/17.
 */

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private final ArrayList<Bitmap> mData;
    private final OnDragStartListener mDragStartListener;

    public GridLayoutAdapter(ArrayList<Bitmap> mData,OnDragStartListener dragStartListener) {
        this.mData=mData;
        mDragStartListener = dragStartListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brick_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        holder.imageView.setImageBitmap(mData.get(position));
        if(position==mData.size()-1)
            holder.imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onDragStarted(holder);
                    }
                    return false;
                }
            });
    }

    @Override
    public void onItemMoveHorizontally(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

    @Override
    public void onItemMoveVerticallyUp(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        notifyItemMoved(toPosition+1,fromPosition);
    }

    @Override
    public void onItemMoveVerticallyDown(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        notifyItemMoved(toPosition-1,fromPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public final ImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.RED);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }
}