package com.example.marce.luckypuzzle.presenter;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
import com.example.marce.luckypuzzle.ui.viewModel.SignUpView;

/**
 * Created by marce on 29/03/17.
 */

public class SignUpPresenterImp extends LuckyPresenter<SignUpView,SignUpInteractor> implements SignUpPresenter{


    public SignUpPresenterImp(SignUpView mView, SignUpInteractor mInteractor) {
        super(mView, mInteractor);
    }

    @Override
    public void validateCredentials(String nickName, String email, String password) {

    }
}
