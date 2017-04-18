package com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.ImageView;

import com.example.marce.luckypuzzle.utils.MathUtils;

/**
 * Created by marce on 13/04/17.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;
    private boolean dragAllowed=true;
    private int spanCount;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter,int spanCount) {
        mAdapter = adapter;
        this.spanCount=spanCount;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }

        int x= source.getAdapterPosition()-target.getAdapterPosition();
        double sourceRow= ((float)source.getAdapterPosition()+1)/spanCount;
        double targetRow=((float)target.getAdapterPosition()+1)/spanCount;
        int realSourceRow= MathUtils.roundUP(sourceRow);
        int realTargetRow=MathUtils.roundUP(targetRow);

        if(dragAllowed) {
            if ((x == 1 || x == -1) && (realSourceRow==realTargetRow)) {
                mAdapter.onItemMoveHorizontally(source.getAdapterPosition(), target.getAdapterPosition());
                dragAllowed=false;
            } else if (x == spanCount) {
                dragAllowed=false;
                mAdapter.onItemMoveVerticallyUp(source.getAdapterPosition(), target.getAdapterPosition());
            } else if (x == -spanCount) {
                dragAllowed=false;
                mAdapter.onItemMoveVerticallyDown(source.getAdapterPosition(), target.getAdapterPosition());
            }
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        dragAllowed=true;
    }


}