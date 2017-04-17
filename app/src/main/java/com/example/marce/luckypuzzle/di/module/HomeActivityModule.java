package com.example.marce.luckypuzzle.di.module;

import com.example.marce.luckypuzzle.di.scopes.ActivityScope;
import com.example.marce.luckypuzzle.di.scopes.FragmentScope;
import com.example.marce.luckypuzzle.interactor.HomeInteractor;
import com.example.marce.luckypuzzle.interactor.ImageInteractor;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.interactor.UploadInteractor;
import com.example.marce.luckypuzzle.model.Facebook;
import com.example.marce.luckypuzzle.presenter.HomePresenterImp;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.viewModel.HomeView;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;
import com.example.marce.luckypuzzle.utils.SessionManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marce on 08/04/17.
 */

@Module
public class HomeActivityModule {
    private HomeView homeView;

    public HomeActivityModule(HomeView view) {
        homeView = view;
    }

    @Provides
    @ActivityScope
    public HomeView provideSignInView(){
        return homeView;
    }

    @Provides @ActivityScope public HomePresenterImp providePresenter(HomeView homeView, ImageInteractor imageInteractor, SessionManager sessionManager){
        return new HomePresenterImp(homeView,imageInteractor,sessionManager);
    }
}
