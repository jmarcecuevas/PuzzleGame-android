package com.example.marce.luckypuzzle.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.ui.activities.HomeActivity;
import com.example.marce.luckypuzzle.ui.recyclerViews.adapters.CommonRecyclerViewAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.itemDecoration.SquareGridSpacingItemDecoration;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.GridLayoutAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.SimpleItemTouchHelperCallback;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.ViewHolder;
import com.example.marce.luckypuzzle.utils.DensityUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by marce on 09/04/17.
 */
public class GameFragment extends Fragment implements GridLayoutAdapter.OnDragStartListener{
    private int mSpanCount;
    private ArrayList<Bitmap> mBitmapBricks;
    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;


    public static final int DIRECTION_DOWN = 100;
    public static final int DIRECTION_UP = -100;
    public static final int DIRECTION_RIGHT = 101;
    public static final int DIRECTION_LEFT = -101;

    public static Fragment newInstance(ArrayList<Bitmap> bitmaps) {
        Bundle args = new Bundle();
        args.putSerializable("bricks", bitmaps);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_game,container,false);
        mRecyclerView= (RecyclerView) v.findViewById(R.id.recyclerView);
        mBitmapBricks = (ArrayList<Bitmap>) getArguments().getSerializable("bricks");
        mSpanCount = 3;
        //generateRandomStatus();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GridLayoutAdapter adapter= new GridLayoutAdapter(mBitmapBricks,this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mSpanCount) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.addItemDecoration(new SquareGridSpacingItemDecoration(getActivity(),R.dimen.brick_divider_width,mSpanCount));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void generateRandomStatus() {
        int count = (int) (40 + Math.random() * 20);
        int lastD = -1;
        for (int i = 0; i < count; i++) {
            int d;
            do {
                d = (int) (Math.random() * 4);
            } while (lastD == d);
            lastD = d;

            int direction = 0;
            switch (d) {
                case 0:
                    direction = DIRECTION_UP;
                    break;
                case 1:
                    direction = DIRECTION_DOWN;
                    break;
                case 2:
                    direction = DIRECTION_LEFT;
                    break;
                case 3:
                    direction = DIRECTION_RIGHT;
                    break;
            }
            //moveBlankBrick(direction, false);
        }
    }



//    private boolean moveBlankBrick(int direction, boolean shouldNotifyView) {
//        Point p = mCurrBlankPos;
//        int newX = p.x;
//        int newY = p.y;
//        if (direction == DIRECTION_UP || direction == DIRECTION_DOWN) {
//            newY += direction == DIRECTION_UP ? -1 : 1;
//        } else if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
//            newX += direction == DIRECTION_LEFT ? -1 : 1;
//        } else {
//            return false;
//        }
//        if (newX >= mSpanCount || newX < 0 || newY >= mSpanCount || newY < 0) {
//            return false;
//        }
//
//        int tInt = mCurrStatus[p.y][p.x];
//        mCurrStatus[p.y][p.x] = mCurrStatus[newY][newX];
//        mCurrStatus[newY][newX] = tInt;
//
//        if (shouldNotifyView) {
//            int posA = p.y * mSpanCount + p.x;
//            int posB = newY * mSpanCount + newX;
//            if (posA > posB) {
//                int t = posA;
//                posA = posB;
//                posB = t;
//            }
//            mRecyclerView.getAdapter().notifyItemMoved(posB, posA);
//            mRecyclerView.getAdapter().notifyItemMoved(posA + 1, posB);
//        }
//
//        p.x = newX;
//        p.y = newY;
//        return true;
//    }


    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
        Log.e("SDASD","FSAFDSF");
        mItemTouchHelper.startDrag(viewHolder);
    }
}
