package com.example.marce.luckypuzzle.di.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;

import com.example.marce.luckypuzzle.io.apiServices.APIAdapter;
import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignInAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignUpAPIService;
import com.example.marce.luckypuzzle.utils.SessionManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by marce on 24/03/17.
 */

@Module
public class LuckyGameModule {

    LuckyGameApp app;

    public LuckyGameModule (LuckyGameApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides @Singleton public Context provideContext() {
        return app;
    }

    @Provides @Singleton SessionManager provideSessionManager(Context context){
        return new SessionManager(context);
    }

    @Provides @Singleton SharedPreferences provideSharedPreferences(){
        return app.getSharedPreferences("MyPref",app.MODE_PRIVATE);
    }

    @Provides @Singleton public GoogleApiClient provideGoogleApíClient(Context context, GoogleSignInOptions gso){
        return new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Provides @Singleton public GoogleSignInOptions provideGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    @Provides @Singleton public Retrofit provideRetrofitInstance() {
        return APIAdapter.getInstance();
    }

    @Provides @Singleton public SignInAPIService provideSignInAPIService(Retrofit retrofit){
        return retrofit.create(SignInAPIService.class);
    }

    @Provides @Singleton public SignUpAPIService provideSignUpAPIService(Retrofit retrofit){
        return retrofit.create(SignUpAPIService.class);
    }

    @Provides @Singleton public EmailAPIService provideNicknameApiService(Retrofit retrofit){
        return retrofit.create(EmailAPIService.class);
    }

    /*@Provides @Singleton public RegisterAPIService provideRegisterAPIService(Retrofit retrofit){
        return retrofit.create(RegisterAPIService.class);
    }

    @Provides @Singleton public MainAPIService provideMainAPIService(Retrofit retrofit){
        return retrofit.create(MainAPIService.class);
    }

    @Provides @Singleton public EmailApiService provideNicknameApiService(Retrofit retrofit){
        return retrofit.create(EmailApiService.class);
    }

    @Provides @Singleton public GoogleSignInOptions provideGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
    }

    @Provides @Singleton public GoogleApiClient providesGoogleApiClient(Context context, GoogleSignInOptions gso){
        return new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Provides @Named("sign_in_api") public GoogleApiClient provideGoogleApíClient(Context context, GoogleSignInOptions gso){
        return new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Provides @Singleton public Vibrator provideVibrator(Context context){
        return (Vibrator)context.getSystemService(context.VIBRATOR_SERVICE);
    }*/
}
