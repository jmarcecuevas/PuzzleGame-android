package com.example.marce.luckypuzzle.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marce.luckypuzzle.R;
import com.example.marce.luckypuzzle.common.LuckyFragment;
import com.example.marce.luckypuzzle.interactor.SignInInteractor;
import com.example.marce.luckypuzzle.io.apiServices.SignInAPIService;
import com.example.marce.luckypuzzle.model.SignInResponse;
import com.example.marce.luckypuzzle.presenter.SignInPresenterImp;
import com.example.marce.luckypuzzle.ui.activities.LaunchActivity;
import com.example.marce.luckypuzzle.ui.viewModel.SignInView;

import retrofit2.Call;
import retrofit2.http.Field;

/**
 * Created by marce on 26/03/17.
 */

public class SignInFragment extends LuckyFragment<SignInPresenterImp> implements SignInView,View.OnClickListener{
    private TextView registerNow,email,password;
    private Button signIn;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private LaunchActivity activity;
    private SignInInteractor signInInteractor;

    @Override
    protected int layout() {
        return R.layout.lucky_code_sign_in;
    }

    @Override
    protected void setUi(View v) {
        signIn= (Button) v.findViewById(R.id.sign_in);
        registerNow= (TextView) v.findViewById(R.id.register_now);
        email= (TextView)v.findViewById(R.id.email);
        password=(TextView)v.findViewById(R.id.password);
    }

    @Override
    protected void init() {
        signInInteractor= new SignInInteractor(new SignInAPIService() {
            @Override
            public Call<SignInResponse> login(@Field("email") String email, @Field("password") String password) {
                return null;
            }
        });
        progressDialog= new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.pleaseWait));
    }

    @Override
    protected void populate() {

    }

    @Override
    protected void setListeners() {
        registerNow.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    protected SignInPresenterImp createPresenter() {
        return new SignInPresenterImp(this,signInInteractor);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(activity,String.valueOf(v.getId()),Toast.LENGTH_SHORT).show();
        switch (v.getId()){
            case R.id.register_now:
                goToRegister();
                break;
            case R.id.sign_in:
                mPresenter.validateCredentials(email.getText().toString(),password.getText().toString());
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (LaunchActivity) context;
    }

    @Override
    public void setUserNameError() {

    }

    @Override
    public void setPasswordError() {

    }

    @Override
    public void setInvalidCredentialsError() {
        alertDialog.setMessage(getString(R.string.failedLogin));
        alertDialog.show();
    }

    @Override
    public void setSuccessLogin() {
        /*sessionManager.createLoginSession(userName.getText().toString(),password.getText().toString());
        settingsManager= new SettingsManager(this,userName.getText().toString());
        sharedPreferences=getSharedPreferences(userName.getText().toString(),MODE_PRIVATE);
        startActivity(new Intent(this, MainActivity.class));
        activity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();*/
        Log.e("TODO","OK");
    }

    @Override
    public void goToRegister() {
        activity.replaceFragmentWithBackStackAnimation(SignUpFragment.class,false);
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void cancelProgress() {
        if(progressDialog!=null && progressDialog.isShowing())
            progressDialog.cancel();
    }
}
