package com.example.marce.luckypuzzle.di.module;

import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.interactor.GameInteractor;
import com.example.marce.luckypuzzle.presenter.GamePresenterImp;
import com.example.marce.luckypuzzle.ui.viewModel.GameView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marce on 14/04/17.
 */

@Module
public class GameModule {
    private GameView gameView;

    public GameModule(GameView gameView){
        this.gameView=gameView;
    }

    @Provides @FragmentScope public GameView provideGameView(){
        return gameView;
    }

    @Provides @FragmentScope public GamePresenterImp provideGamePresenterImp(GameView gameView,GameInteractor gameInteractor){
        return new GamePresenterImp(gameView,gameInteractor);
    }
}
