package com.example.marce.luckypuzzle.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.ImageView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.di.component.DaggerGameComponent;
import com.example.marce.luckypuzzle.di.module.GameModule;
import com.example.marce.luckypuzzle.model.Square;
import com.example.marce.luckypuzzle.presenter.GamePresenterImp;
import com.example.marce.luckypuzzle.ui.activities.HomeActivity;
import com.example.marce.luckypuzzle.ui.recyclerViews.itemDecoration.SquareGridSpacingItemDecoration;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.GridLayoutAdapter;
import com.example.marce.luckypuzzle.ui.recyclerViews.viewHolders.SimpleItemTouchHelperCallback;
import com.example.marce.luckypuzzle.ui.viewModel.GameView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by marce on 09/04/17.
 */
public class GameFragment extends LuckyFragment implements GameView,GridLayoutAdapter.OnDragStartListener,GridLayoutAdapter.AdapterListener{

    private int mSpanCount;
    private ArrayList<Square> mBitmapBricks;
    private ItemTouchHelper mItemTouchHelper;
    private GridLayoutAdapter adapter;
    private Bitmap photo;
    private GameCallback listener;
    @BindView(R.id.wonImage)ImageView wonImage;
    @BindView(R.id.recyclerView)RecyclerView mRecyclerView;
    @Inject GamePresenterImp mPresenter;

    public static Fragment newInstance(ArrayList<Square> squares,int spanCount) {
        Bundle args = new Bundle();
        args.putSerializable("squares", squares);
        args.putInt("spanCount",spanCount);
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUpComponent() {
        DaggerGameComponent.builder()
                .homeComponent(((HomeActivity)getActivity()).getComponent())
                .gameModule(new GameModule(this))
                .build().inject(this);
    }

    @Override
    protected int layout() {
        return R.layout.fragment_game;
    }

    @Override
    protected void init() {
        mBitmapBricks = (ArrayList<Square>) getArguments().getSerializable("squares");
        mSpanCount= getArguments().getInt("spanCount");
        Log.e("span",String.valueOf(mSpanCount));
        getArguments().remove("squares");
        mPresenter.generateRandomStatus(mBitmapBricks);
        adapter= new GridLayoutAdapter(mBitmapBricks,this,this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), mSpanCount) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerView.addItemDecoration(new SquareGridSpacingItemDecoration(getActivity(),R.dimen.brick_divider_width,mSpanCount));

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter,mSpanCount);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void showRandomImages(ArrayList<Square> arrayImages) {

    }

    @Override
    public void showUserWon() {
        listener.onUserWon();
    }

    @Override
    public void showGameOver() {

    }

    @Override
    public void onItemMoved() {
        mPresenter.isSorted(mBitmapBricks);
        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(75);
        listener.onItemMoved();
    }

    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GameCallback) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    public interface GameCallback{
        void onUserWon();
        void onItemMoved();
    }
}
