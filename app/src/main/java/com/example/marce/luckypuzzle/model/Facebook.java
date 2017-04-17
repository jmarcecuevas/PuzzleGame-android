package com.example.marce.luckypuzzle.model;

import android.content.Context;
import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.util.Log;

import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.apiServices.FacebookAPIService;
import com.example.marce.luckypuzzle.io.callback.EmailCallback;
import com.example.marce.luckypuzzle.io.callback.FBCallback;
import com.example.marce.luckypuzzle.io.callback.FacebookSignUpCallback;
import com.example.marce.luckypuzzle.io.callback.SignUpCallback;
import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by marce on 07/04/17.
 */

public class Facebook {
    private static final String TAG=Facebook.class.getName();
    private Context context;
    private EmailAPIService emailAPIService;
    private FacebookAPIService facebookAPIService;

    public Facebook(Context context,EmailAPIService emailAPIService,FacebookAPIService facebookAPIService){
        this.context=context;
        this.emailAPIService=emailAPIService;
        this.facebookAPIService=facebookAPIService;
    }

    public void requestFacebookUserData(LoginResult loginResult, final FBCallback fbCallback) {
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(final JSONObject object, GraphResponse response) {
                try {
                    String email = object.getString("email");
                    checkEmail(email,fbCallback);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void checkEmail(final String email, final FBCallback fbCallback){
        Call<EmailResponse> call= emailAPIService.checkEmail(email);
        call.enqueue(new retrofit2.Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                EmailResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.doesEmailExists()){
                        Log.i(TAG,"EMAIL ALREADY EXISTS");
                        fbCallback.onFBUserAlreadyExists(mResponse.getUserName(),mResponse.getImageURL());
                    }else{
                        Log.i(TAG,"EMAIL DOESN'T EXISTS ON DATABASE(It's able to register)");
                        fbCallback.onNewFacebookUser(email);
                    }
                }
            }

            @Override
            public void onFailure(Call<EmailResponse> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG,"UNKNOWN ERROR WHILE TRYING TO CHECK EMAIL ON DATABASE");
            }
        });
    }

    public void signUp(final String userName, final String email, final String imageURL, final FacebookSignUpCallback fbCallback){
        Call<SignUpResponse> call= facebookAPIService.insertUser(userName,email,imageURL);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                SignUpResponse mResponse=response.body();
                if(mResponse!=null){
                    if(mResponse.isSuccessful()){
                        fbCallback.onSuccessSignUpFB(userName,email,imageURL);
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                fbCallback.onUnknownErrorFB();
            }
        });
    }

}
