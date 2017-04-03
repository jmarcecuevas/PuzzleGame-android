package com.example.marce.luckypuzzle.io.apiServices;

import com.example.marce.luckypuzzle.model.SignInResponse;
import com.example.marce.luckypuzzle.utils.URLUtils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by marce on 27/03/17.
 */

public interface SignInAPIService {
    @FormUrlEncoded
    @POST(URLUtils.SIGNIN_URL)
    Call<SignInResponse> login(@Field("userName")String email,
                               @Field("password")String password);
}
