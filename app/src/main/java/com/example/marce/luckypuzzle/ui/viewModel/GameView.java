package com.example.marce.luckypuzzle.ui.viewModel;

import android.graphics.Bitmap;

import com.example.marce.luckypuzzle.model.Square;

import java.util.ArrayList;

/**
 * Created by marce on 14/04/17.
 */

public interface GameView {
    void showRandomImages(ArrayList<Square> arrayImages);
    void showUserWon();
    void showGameOver();


}
