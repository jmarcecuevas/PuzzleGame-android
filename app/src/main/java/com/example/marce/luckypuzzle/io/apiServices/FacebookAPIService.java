package com.example.marce.luckypuzzle.io.apiServices;

import com.example.marce.luckypuzzle.model.SignUpResponse;
import com.example.marce.luckypuzzle.utils.URLUtils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by marce on 07/04/17.
 */

public interface FacebookAPIService {
    @FormUrlEncoded
    @POST(URLUtils.FACEBOOK_SIGNUP_URL)
    Call<SignUpResponse> insertUser(@Field("userName") String userName,
                                   @Field("email") String email,
                                   @Field("image_url")String image_url);
}
