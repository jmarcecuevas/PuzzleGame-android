package com.example.marce.luckypuzzle.presenter;

import android.graphics.Bitmap;
import android.view.View;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.HomeInteractor;
import com.example.marce.luckypuzzle.ui.viewModel.HomeView;
import com.example.marce.luckypuzzle.utils.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by marce on 08/04/17.
 */

public class HomePresenterImp extends LuckyPresenter<HomeView,HomeInteractor> implements HomePresenter{
    private SessionManager sessionManager;

    public HomePresenterImp(HomeView mView, HomeInteractor mInteractor,SessionManager sessionManager) {
        super(mView, mInteractor);
        this.sessionManager=sessionManager;
    }

    @Override
    public void checkSession() {
        if(!sessionManager.isLoggedIn()){
            getView().goToSignUpActivity();
        }
    }

    @Override
    public void scaleBitMap(Bitmap bitmap, View container) {
        // Scale the bitmap to a proper size, avoiding waste of memory
        Bitmap mFullBitmap;
        assert container != null;
        int paddingHorizontal = container.getPaddingLeft() + container.getPaddingRight();
        int paddingVertical = container.getPaddingTop() + container.getPaddingBottom();
        mFullBitmap = Bitmap.createScaledBitmap(
                bitmap,
                container.getWidth() - paddingHorizontal,
                container.getHeight() - paddingVertical,
                false);
        getView().getScaledBitMap(mFullBitmap);
    }

    @Override
    public void cutBitMapIntoPieces(Bitmap bitmap,int spanCount,int dividerWidth) {
        Bitmap[] bitmapBricks= new Bitmap[spanCount*spanCount];
        //List bitmapBricks= new ArrayList<Bitmap>();
        int brickWidth = (bitmap.getWidth() - dividerWidth * (spanCount - 1)) / spanCount;
        int brickHeight = (bitmap.getHeight() - dividerWidth * (spanCount - 1)) / spanCount;
        for (int i = 0; i < spanCount; i++) {
            for (int j = 0; j < spanCount; j++) {
                bitmapBricks[i * spanCount + j] = Bitmap.createBitmap(
                        bitmap,
                        j * (brickWidth + dividerWidth),
                        i * (brickHeight + dividerWidth),
                        brickWidth, brickHeight);
            }
        }
        ArrayList<Bitmap> bitmaps= new ArrayList<Bitmap>(Arrays.asList(bitmapBricks));

        //getView().showBitmapIntoSquares(bitmapBricks);
        getView().showBitmapIntoSquares(bitmaps);
    }
}
