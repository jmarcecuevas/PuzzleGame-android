package com.example.marce.luckypuzzle.presenter;

import com.example.marce.luckypuzzle.common.LuckyPresenter;
import com.example.marce.luckypuzzle.interactor.UserInteractor;
import com.example.marce.luckypuzzle.ui.viewModel.UserView;

/**
 * Created by marce on 24/03/17.
 */

public class UserPresenter extends LuckyPresenter<UserView> {

    public UserPresenter(UserView mView) {
        super(mView);
    }

    public void loadUser(long userID){
        getView().showProgress();

        //Make API CALL to get user data
    }
}
