package com.example.marce.luckypuzzle.ui.viewModel;

/**
 * Created by marce on 29/03/17.
 */

public interface SignUpView {
    void showPictureDialog();
    void loadPictureFromGallery();
    void showProgress();
    void hideProgress();
    void cancelProgress();
    void setEmptyUserNameError();
    void setEmptyPasswordError();
    void setEmptyEmailError();
    void setValidUserName(boolean validUserName);
    void setValidEmail(boolean validEmail);
    void setSuccessSignUp();
    boolean isUserNameValid();
    boolean isEmailValid();
    void setInvalidUserNameError();
    void setInvalidPasswordError();
    void setInvalidEmailError();
    void setUserAlreadyExistsError();
    void backToSignIn();
    void setUnknownError();
    void setValidPassword(boolean validPassword);
    boolean isPasswordValid();

}
