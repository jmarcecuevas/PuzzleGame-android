package com.example.marce.luckypuzzle.presenter;

import android.content.pm.PackageInstaller;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.HomeInteractor;
import com.example.marce.luckypuzzle.ui.viewModel.HomeView;
import com.example.marce.luckypuzzle.utils.SessionManager;

/**
 * Created by marce on 08/04/17.
 */

public class HomePresenterImp extends LuckyPresenter<HomeView,HomeInteractor> implements HomePresenter{
    private SessionManager sessionManager;

    public HomePresenterImp(HomeView mView, HomeInteractor mInteractor,SessionManager sessionManager) {
        super(mView, mInteractor);
        this.sessionManager=sessionManager;
    }

    @Override
    public void checkSession() {
        if(!sessionManager.isLoggedIn()){
            getView().goToSignUpActivity();
        }
    }
}
