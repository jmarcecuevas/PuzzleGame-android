package com.example.marce.luckypuzzle.presenter;

import android.graphics.Bitmap;
import android.view.View;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.io.callback.ImageCallback;
import com.example.marce.luckypuzzle.ui.viewModel.HomeView;
import com.example.marce.luckypuzzle.interactor.ImageInteractor;
import com.example.marce.luckypuzzle.utils.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by marce on 08/04/17.
 */

public class HomePresenterImp extends LuckyPresenter<HomeView,ImageInteractor> implements HomePresenter,ImageCallback{
    private SessionManager sessionManager;

    public HomePresenterImp(HomeView mView, ImageInteractor mInteractor,SessionManager sessionManager) {
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
        getInteractor().scaleBitmap(bitmap,container,this);
    }

    @Override
    public void cutBitMapIntoPieces(Bitmap bitmap,int spanCount,int dividerWidth) {
        getInteractor().cutBitmapIntoPieces(bitmap,spanCount,dividerWidth,this);
    }

    @Override
    public void onScaledBitmap(Bitmap bitmap) {
        getView().getScaledBitMap(bitmap);
    }

    @Override
    public void onBitmapPieces(ArrayList<Bitmap> bitmaps) {
        getView().showBitmapIntoSquares(bitmaps);
    }
}
