package com.example.marce.luckypuzzle.ui.fragments;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.di.app.LuckyGameComponent;
import com.example.marce.luckypuzzle.di.component.ActivityComponent;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;
import com.example.marce.luckypuzzle.ui.viewModel.UserView;

/**
 * Created by marce on 24/03/17.
 */

public class SignUpOptionsFragment extends LuckyFragment<SignInPresenterImp> implements UserView,View.OnClickListener {

    private static final int RC_SIGN_IN = 007;
    private LaunchActivity activity;
    private Button luckyCode,googleButton;


    @Override
    public void setUpComponent() {

    }

    @Override
    protected int layout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void setUi(View v) {
        luckyCode= (Button) v.findViewById(R.id.luckyCode);
        googleButton= (Button) v.findViewById(R.id.google);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void populate() {
    }

    @Override
    protected void setListeners() {
        luckyCode.setOnClickListener(this);
        googleButton.setOnClickListener(this);
    }

    @Override
    protected SignInPresenterImp createPresenter() {
        return null;
    }


    @Override
    public void signInWithLuckyCode() {
        activity.replaceFragmentWithBackStackAnimation(SignInFragment.class,false);
    }

    @Override
    public void signInWithGoogle() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void showError(Throwable e) {
        hideProgress();
        showToast(e.getMessage());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {


    }

    @Override
    public void showInternetConnectionError() {
        showToast("INTERNET ERROR");
    }

    @Override
    public void dismissInternetConnectionError() {

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.luckyCode:
                signInWithLuckyCode();
                break;
            case R.id.google:
                signInWithGoogle();
            default:

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=(LaunchActivity)context;
    }
}
