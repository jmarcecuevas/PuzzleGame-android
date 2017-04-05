package com.example.marce.luckypuzzle.interactor;

import android.util.Log;

import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.apiServices.SignUpAPIService;
import com.example.marce.luckypuzzle.io.callback.EmailCallback;
import com.example.marce.luckypuzzle.io.callback.SignUpCallback;
import com.example.marce.luckypuzzle.model.EmailResponse;
import com.example.marce.luckypuzzle.model.SignUpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by marce on 29/03/17.
 */

public class SignUpInteractor {

    private static String TAG= SignUpInteractor.class.getSimpleName();
    private SignUpAPIService signUpAPIService;
    private EmailAPIService emailApiService;

    public SignUpInteractor(SignUpAPIService signUpAPIService,EmailAPIService emailApiService){
        this.signUpAPIService=signUpAPIService;
        this.emailApiService=emailApiService;
    }

    public void signUp(String userName, String email, String password, final SignUpCallback registerCallback){
       /* Call<SignUpResponse> call= signUpAPIService.inserUser(userName,email,password);
        call.enqueue(new retrofit2.Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.isSuccessful()){
                        Log.i(TAG,"SUCCESSFUL REGISTER");
                        registerCallback.onSuccessSignUp();
                    }
                    else if(mResponse.doesUserExist()){
                        Log.i(TAG,"USERNAME ALREADY EXISTS");
                        registerCallback.onUserAlreadyExists();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.e(TAG,"UNKNOWN ERROR");
                registerCallback.onUnknownError();
            }
        });*/
    }

    /** It Checks whether email exists on database**/
    public void checkEmail(final GoogleSignInAccount acc, final EmailCallback emailCallback){
        Call<EmailResponse> call= emailApiService.checkEmail(acc.getEmail());
        call.enqueue(new retrofit2.Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                EmailResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.doesEmailExists()){
                        Log.i(TAG,"EMAIL ALREADY EXISTS");
                        emailCallback.onEmailAlreadyExists(acc,mResponse.getUserName());
                    }else{
                        Log.i(TAG,"EMAIL DOESN'T EXISTS ON DATABASE(It's able to register)");
                        emailCallback.onEmailOK(acc);
                    }
                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                Log.e(TAG,"UNKNOWN ERROR WHILE TRYING TO CHECK EMAIL ON DATABASE");
            }
        });
    }





}
