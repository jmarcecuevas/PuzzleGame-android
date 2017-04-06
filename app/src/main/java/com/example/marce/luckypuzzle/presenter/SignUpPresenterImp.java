package com.example.marce.luckypuzzle.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.UploadInteractor;
import com.example.marce.luckypuzzle.io.callback.SignUpCallback;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpView;

/**
 * Created by marce on 29/03/17.
 */

public class SignUpPresenterImp extends LuckyPresenter<SignUpView,UploadInteractor> implements SignUpPresenter,SignUpCallback{


    public SignUpPresenterImp(SignUpView mView, UploadInteractor uploadInteractor) {
        super(mView, uploadInteractor);
    }


    @Override
    public void validateUserName(String userName) {
        if(userName.length()<6){
            Log.e("VALIDO","NO");
            getView().setValidUserName(false);
            getView().setInvalidUserNameError();
        }else{
            getView().setValidUserName(true);
            Log.e("VALIDO","SI");
        }
    }

    @Override
    public void validatePassword(String password) {
        if(password.length()<6){
            getView().setValidPassword(false);
            getView().setInvalidPasswordError();
        }else{
            getView().setValidPassword(true);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void signUp(String userName,String email,String password) {
        if(TextUtils.isEmpty(email)){
            getView().setEmptyEmailError();
        }else if(isValidEmail(email)){
            getView().setValidEmail(true);
        }else if(!isValidEmail(email)){
            getView().setInvalidEmailError();
            getView().setValidEmail(false);
        }
        if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(userName))
                getView().setEmptyUserNameError();
            if (TextUtils.isEmpty(password)) {
                getView().setEmptyPasswordError();
            }
        }else if(getView().isUserNameValid() && getView().isPasswordValid() && getView().isEmailValid()){
            getView().showProgress();
            getInteractor().signUp(userName,email,password,this);
        }
    }

    @Override
    public void uploadPhoto(String mediaPath) {
        getView().showImageProgress();
        getInteractor().uploadImage(mediaPath,this);
    }

    @Override
    public void onSuccessSignUp() {
        getView().hideProgress();
        getView().setSuccessSignUp();
    }

    @Override
    public void onUserAlreadyExists() {
        getView().hideProgress();
        getView().setUserAlreadyExistsError();
    }

    @Override
    public void onImageError() {
        getView().hideProgress();
        Log.e("ERROR","RRR");
    }

    @Override
    public void onEmptyImageError() {
        getView().hideProgress();
        getView().setEmptyImageError();
    }

    @Override
    public void onImageSuccess() {
        getView().hideImageProgress();
        getView().updatePhoto();
        Log.e("TODO","OK");
    }

    @Override
    public void onUnknownError() {
        getView().hideProgress();
        getView().setUnknownError();
    }
}
