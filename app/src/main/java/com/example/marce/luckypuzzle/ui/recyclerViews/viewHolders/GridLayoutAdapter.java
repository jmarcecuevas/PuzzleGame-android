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
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.model.Square;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by marce on 13/04/17.
 */

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private final ArrayList<Square> mData;
    private final OnDragStartListener mDragStartListener;
    private final AdapterListener mAdapterListener;


    public GridLayoutAdapter(ArrayList<Square> mData,OnDragStartListener dragStartListener,AdapterListener adapterListener) {
        this.mData=mData;
        this.mAdapterListener=adapterListener;
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
        holder.imageView.setImageBitmap(mData.get(position).getPicture());
        holder.number.setText(String.valueOf(mData.get(position).getPosition()));
        if(mData.get(position).getPosition()==mData.size()-1) {
            holder.imageView.setAlpha(.3f);
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
    }

    @Override
    public void onItemMoveHorizontally(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        mAdapterListener.onItemMoved();
    }

    @Override
    public void onItemMoveVerticallyUp(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        notifyItemMoved(toPosition+1,fromPosition);
        mAdapterListener.onItemMoved();
    }

    @Override
    public void onItemMoveVerticallyDown(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        notifyItemMoved(toPosition-1,fromPosition);
        mAdapterListener.onItemMoved();
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

        public ImageView imageView;
        public TextView number;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            number=(TextView) itemView.findViewById(R.id.number);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.parseColor("#B3E5FC"));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public interface OnDragStartListener {
        void onDragStarted(RecyclerView.ViewHolder viewHolder);
    }

    public interface AdapterListener{
        void onItemMoved();
    }
}