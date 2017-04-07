package com.example.marce.luckypuzzle.model;

import android.content.Context;
import android.hardware.camera2.params.Face;
import android.os.Bundle;
import android.util.Log;

import com.example.marce.luckypuzzle.io.apiServices.EmailAPIService;
import com.example.marce.luckypuzzle.io.callback.EmailCallback;
import com.example.marce.luckypuzzle.io.callback.FBCallback;
import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by marce on 07/04/17.
 */

public class Facebook {
    private static final String TAG=Facebook.class.getName();
    private Context context;
    private EmailAPIService emailAPIService;

    public Facebook(Context context,EmailAPIService emailAPIService){
        this.context=context;
        this.emailAPIService=emailAPIService;
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

    public void checkEmail(String email, final FBCallback fbCallback){
        Call<EmailResponse> call= emailAPIService.checkEmail(email);
        call.enqueue(new retrofit2.Callback<EmailResponse>() {
            @Override
            public void onResponse(Call<EmailResponse> call, Response<EmailResponse> response) {
                EmailResponse mResponse= response.body();
                if(mResponse!=null){
                    if(mResponse.doesEmailExists()){
                        Log.i(TAG,"EMAIL ALREADY EXISTS");
                        fbCallback.onFBUserAlreadyExists();
                    }else{
                        Log.i(TAG,"EMAIL DOESN'T EXISTS ON DATABASE(It's able to register)");
                        fbCallback.onNewFacebookUser();
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
