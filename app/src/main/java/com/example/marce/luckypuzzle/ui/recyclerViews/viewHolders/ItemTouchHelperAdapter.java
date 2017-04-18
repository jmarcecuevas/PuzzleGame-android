package com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders;

/**
 * Created by marce on 13/04/17.
 */

public interface ItemTouchHelperAdapter {

    void onItemMoveHorizontally(int fromPosition, int toPosition);

    void onItemMoveVerticallyUp(int fromPosition,int toPosition);

    void onItemMoveVerticallyDown(int fromPosition,int toPosition);

    void onItemDismiss(int position);
}