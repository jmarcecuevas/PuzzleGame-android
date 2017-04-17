package com.example.marce.luckypuzzle.io.callback;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by marce on 16/04/17.
 */

public interface ImageCallback {
    void onScaledBitmap(Bitmap bitmap);
    void onBitmapPieces(ArrayList<Bitmap> bitmaps);
}
