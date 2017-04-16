package com.example.marce.luckypuzzle.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    @BindView(R.id.wonImage)ImageView wonImage;
    @BindView(R.id.recyclerView)RecyclerView mRecyclerView;
    @Inject GamePresenterImp mPresenter;

    public static Fragment newInstance(ArrayList<Square> squares,int photoID) {
        Bundle args = new Bundle();
        args.putSerializable("squares", squares);
        args.putInt("photoID",photoID);
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
        photo= BitmapFactory.decodeResource(getResources(),getArguments().getInt("photoID"));
        getArguments().remove("squares");
        mSpanCount = 2;
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

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    @Override
    public void showRandomImages(ArrayList<Square> arrayImages) {

    }

    @Override
    public void showUserWon() {
        wonImage.setImageBitmap(photo);
    }

    @Override
    public void showGameOver() {

    }

    @Override
    public void onItemMoved() {
        mPresenter.isSorted(mBitmapBricks);
    }

    @Override
    public void onDragStarted(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
