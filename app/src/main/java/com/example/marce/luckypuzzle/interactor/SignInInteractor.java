package com.example.marce.luckypuzzle.interactor;

import com.example.marce.luckypuzzle.io.apiServices.SignInAPIService;
import com.example.marce.luckypuzzle.io.callback.SignInCallback;
import com.example.marce.luckypuzzle.model.SignInResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marce on 27/03/17.
 */

public class SignInInteractor {
    private SignInAPIService signInAPIService;

    public SignInInteractor(SignInAPIService signInAPIService){
        this.signInAPIService=signInAPIService;
    }

    public void signIn(String email,String password,final SignInCallback callback){
        Call<SignInResponse> call= signInAPIService.login(email,password);
        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                SignInResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.isSuccessful())
                        callback.onSuccessLogin();
                    else
                        callback.onFailedLogin();
                }
            }
            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {

            }
        });

    }
}
