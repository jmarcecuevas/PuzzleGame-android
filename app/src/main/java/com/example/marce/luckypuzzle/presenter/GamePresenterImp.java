package com.example.marce.luckypuzzle.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.GameInteractor;
import com.example.marce.luckypuzzle.model.Square;
import com.example.marce.luckypuzzle.ui.viewModel.GameView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by marce on 14/04/17.
 */

public class GamePresenterImp extends LuckyPresenter<GameView,GameInteractor> implements GamePresenter {


    public GamePresenterImp(GameView mView, GameInteractor mInteractor) {
        super(mView, mInteractor);
    }

    @Override
    public void generateRandomStatus(ArrayList<Square> array) {
        suffleList(array);
        getView().showRandomImages(array);
    }

    @Override
    public void isSorted(ArrayList<Square> squares) {
        for(int i=0;i<squares.size()-1;i++){
            if(squares.get(i).getPosition()!=squares.get(i+1).getPosition()-1){
                return;
            }
        }
        getView().showUserWon();
    }

    private void suffleList(ArrayList<Square> list) {
        final long seed = System.nanoTime();
        Collections.shuffle(list, new Random(seed));
    }
}
