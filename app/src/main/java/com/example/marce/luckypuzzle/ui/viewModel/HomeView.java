package com.example.marce.luckypuzzle.ui.viewModel;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by marce on 08/04/17.
 */

public interface HomeView {
    void goToSignUpActivity();
    void getScaledBitMap(Bitmap bitmap);
    //void showBitmapIntoSquares(Bitmap[] bitmaps);
    void showBitmapIntoSquares(ArrayList<Bitmap> bitmaps);
}
