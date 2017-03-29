package com.example.marce.luckypuzzle.ui.fragments;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.interactor.SignUpInteractor;
import com.example.marce.luckypuzzle.presenter.SignUpPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;

/**
 * Created by marce on 26/03/17.
 */

public class SignUpFragment extends LuckyFragment<SignUpPresenterImp> implements View.OnClickListener{
    private LaunchActivity activity;
    private TextView loginNow;
    private SignUpInteractor signUpInteractor;

    @Override
    protected int layout() {
        return R.layout.lucky_code_sign_up;
    }

    @Override
    protected void setUi(View v) {
        loginNow= (TextView) v.findViewById(R.id.login_now);
    }

    @Override
    protected void init() {
        signUpInteractor= new SignUpInteractor();
    }

    @Override
    protected void populate() {

    }

    @Override
    protected void setListeners() {
        loginNow.setOnClickListener(this);
    }

    @Override
    protected SignUpPresenterImp createPresenter() {
        //return new SignUpPresenterImp(this,signUpInteractor);
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_now:
               activity.replaceFragmentWithBackStackAnimation(SignInFragment.class,false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(LaunchActivity)context;
    }
}
