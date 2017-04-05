package com.example.marce.luckypuzzle.di.module;

import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
import com.example.marce.luckypuzzle.interactor.UploadInteractor;
import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignInAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignUpAPIService;
import com.example.marce.luckypuzzle.io.apiServices.UploadAPIService;

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

}