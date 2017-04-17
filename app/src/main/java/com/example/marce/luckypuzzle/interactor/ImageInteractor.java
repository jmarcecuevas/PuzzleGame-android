package com.example.marce.luckypuzzle.interactor;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.example.marce.luckypuzzle.io.callback.ImageCallback;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by marce on 16/04/17.
 */

public class ImageInteractor {

    public void scaleBitmap(Bitmap bitmap, View container, ImageCallback callback){
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
        callback.onScaledBitmap(mFullBitmap);
    }

    public void cutBitmapIntoPieces(Bitmap bitmap,int spanCount,int dividerWidth,ImageCallback callback){
        Bitmap[] bitmapBricks= new Bitmap[spanCount*spanCount];
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
        ArrayList<Bitmap> bitmaps= new ArrayList<>(Arrays.asList(bitmapBricks));
        callback.onBitmapPieces(bitmaps);
    }

}
