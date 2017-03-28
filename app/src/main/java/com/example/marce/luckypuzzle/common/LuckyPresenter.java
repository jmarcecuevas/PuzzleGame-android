package com.example.marce.luckypuzzle.common;

/**
 * Created by marce on 24/03/17.
 */

public class LuckyPresenter<T,R> {

    private T mView;
    private R mInteractor;

    public LuckyPresenter(T mView,R mInteractor){
        this.mView=mView;
        this.mInteractor=mInteractor;
    }

    protected T getView(){
        return mView;
    }

    protected R getInteractor(){
        return mInteractor;
    }

    public void detachView(){
        mView=null;
    }
}
