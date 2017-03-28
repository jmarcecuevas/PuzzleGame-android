package com.example.marce.luckypuzzle.common;

/**
 * Created by marce on 24/03/17.
 */

public abstract class LuckyInteractor<T extends LuckyPresenter>{

    T mPresenter;

    public LuckyInteractor(T mPresenter){
        this.mPresenter=mPresenter;
    }
}
