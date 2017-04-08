package com.example.marce.luckypuzzle.di.module;

import android.content.Context;

import com.example.marce.luckypuzzle.interactor.HomeInteractor;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
import com.example.marce.luckypuzzle.interactor.UploadInteractor;
import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.apiServices.FacebookAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignInAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignUpAPIService;
import com.example.marce.luckypuzzle.io.apiServices.UploadAPIService;
import com.example.marce.luckypuzzle.model.Facebook;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marce on 28/03/17.
 */

@Module
public class InteractorModule {

    @Provides
    public SignInInteractor provideSigInInteractor(SignInAPIService signInAPIService){
        return new SignInInteractor(signInAPIService);
    }

    @Provides SignUpInteractor provideSignUpInteractor(SignUpAPIService signUpAPIService, EmailAPIService emailAPIService){
        return new SignUpInteractor(signUpAPIService,emailAPIService);
    }

    @Provides
    UploadInteractor provideUploadInteractor(SignUpAPIService signUpAPIService,UploadAPIService uploadAPIService){
        return new UploadInteractor(signUpAPIService,uploadAPIService);
    }

    @Provides
    Facebook provideFacebook(Context context, EmailAPIService emailAPIService, FacebookAPIService facebookAPIService){
        return new Facebook(context,emailAPIService,facebookAPIService);
    }

    @Provides
    HomeInteractor provideHomeInteractor(){
        return new HomeInteractor();
    }

}