package com.example.marce.luckypuzzle.presenter;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by marce on 08/04/17.
 */

public interface HomePresenter {
    void checkSession();
    void scaleBitMap(Bitmap bitmap, View container);
    void cutBitMapIntoPieces(Bitmap bitmap,int spanCount,int dividerWidth);


}
