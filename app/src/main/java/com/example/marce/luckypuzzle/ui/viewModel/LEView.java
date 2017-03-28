package com.example.marce.luckypuzzle.ui.viewModel;

/**
 * Created by marce on 24/03/17.
 */

public interface LEView {

    void showError(Throwable e);
    void showProgress();
    void hideProgress();
    void showInternetConnectionError();
    void dismissInternetConnectionError();
}
