package com.example.marce.luckypuzzle.presenter;

import android.hardware.camera2.params.Face;
import android.text.TextUtils;
import android.util.Log;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
import com.example.marce.luckypuzzle.interactor.UploadInteractor;
import com.example.marce.luckypuzzle.io.apiServices.UploadAPIService;
import com.example.marce.luckypuzzle.io.callback.FBCallback;
import com.example.marce.luckypuzzle.io.callback.FacebookSignUpCallback;
import com.example.marce.luckypuzzle.io.callback.SignInCallback;
import com.example.marce.luckypuzzle.io.callback.SignUpCallback;
import com.example.marce.luckypuzzle.model.Facebook;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpOptions;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

/**
 * Created by marce on 27/03/17.
 */

public class SignInPresenterImp extends LuckyPresenter<SignUpOptions,SignInInteractor> implements SignInPresenter,
        SignInCallback,FBCallback,FacebookSignUpCallback {

    private UploadInteractor uploadInteractor;
    private Facebook facebook;

    public SignInPresenterImp(SignUpOptions mView, SignInInteractor mInteractor,UploadInteractor uploadInteractor,Facebook facebook) {
        super(mView,mInteractor);
        this.uploadInteractor=uploadInteractor;
        this.facebook=facebook;
    }

    @Override
    public void validateCredentials(String userName, String password) {
        if(TextUtils.isEmpty(userName)|| TextUtils.isEmpty(password)){
            if(TextUtils.isEmpty(userName))
                getView().setUserNameError();
            if(TextUtils.isEmpty(password))
                getView().setPasswordError();
        }else{
            getView().showProgress();
            getInteractor().signIn(userName,password,this);
        }
    }

    @Override
    public void requestFacebookUserData(LoginResult result) {
        facebook.requestFacebookUserData(result,this);
    }

    @Override
    public void onSuccessLogin(String userName,String imageURL) {
        getView().hideProgress();
        getView().setSuccessLogin(userName,imageURL);
    }

    @Override
    public void onFailedLogin() {
        getView().hideProgress();
        getView().setInvalidCredentialsError();
    }

    @Override
    public void onFBUserAlreadyExists(String userName, String imageURL) {
        getView().setSuccessLogin(userName,imageURL);
    }

    @Override
    public void onNewFacebookUser(String email) {
        String userName="",imageURL="";
        if (Profile.getCurrentProfile() != null) {
            Log.e("HOLA","NEW USER");
            Profile profile = Profile.getCurrentProfile();
            userName = profile.getFirstName() + " " + profile.getLastName();
            imageURL = profile.getProfilePictureUri(200, 200).toString();
        }
        facebook.signUp(userName,email,imageURL,this);
    }


    @Override
    public void onSuccessSignUpFB(String userName, String email, String imageURL) {
        getView().setSuccessLogin(userName,imageURL);
    }

    @Override
    public void onUnknownErrorFB() {

    }
}
