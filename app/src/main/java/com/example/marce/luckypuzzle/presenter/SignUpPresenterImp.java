package com.example.marce.luckypuzzle.presenter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
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
    public void signUp(String userName,String email,String password,String mediaPath) {
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
            getInteractor().uploadImage(userName,email,mediaPath,password,this);
        }
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

    }

    @Override
    public void onUnknownError() {
        getView().hideProgress();
        getView().setUnknownError();
    }
}
