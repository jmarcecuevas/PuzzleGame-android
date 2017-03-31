package com.example.marce.luckypuzzle.di.module;

import android.content.Context;

import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;
import com.example.marce.luckypuzzle.ui.viewModel.SignInView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marce on 28/03/17.
 */

@Module
public class SignInModule {
    //private SignInView signInView;
    //private SignupView registerView;

    public SignInModule(/*SignInView view*/) {
        //signInView = view;
    }

//    public LogRegModule(RegisterView view){
//        registerView= view;
//    }

    /*@Provides
    public SignInView provideSigInView() {
        return signInView;
    }*/

    /*@Provides
    public SignInPresenterImp providePresenter(SignInView signInView, SignInInteractor signInInteractor){
        return new SignInPresenterImp(signInView,signInInteractor);
    }*/

//    @Provides
//    public RegisterView provideRegisterView(){ return registerView; }
//
//    @Provides
//    public ConnectivityChangeReceiver provideConnectivityChangeReceiver(Context context){
//        return new ConnectivityChangeReceiver(context);
//    }

//    @Provides
//    public LoginPresenterImp providePresenter(Context context,LoginView view, LoginInteractor interactor,ConnectivityChangeReceiver connectivityChangeReceiver) {
//        return new LoginPresenterImp(context,view, interactor,connectivityChangeReceiver);
//    }

//    @Provides
//    public RegisterPresenterImp provideRegisterPresenter(Context context, RegisterView view, RegisterInteractor interactor,ConnectivityChangeReceiver connectivityChangeReceiver){
//        return new RegisterPresenterImp(context,view,interactor,connectivityChangeReceiver);
//    }
}
