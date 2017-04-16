package com.example.marce.luckypuzzle.presenter;

import android.graphics.Bitmap;

import com.example.marce.luckypuzzle.model.Square;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by marce on 14/04/17.
 */

public interface GamePresenter {
    void generateRandomStatus(ArrayList<Square> squares);
    void isSorted(ArrayList<Square> squares);

}
