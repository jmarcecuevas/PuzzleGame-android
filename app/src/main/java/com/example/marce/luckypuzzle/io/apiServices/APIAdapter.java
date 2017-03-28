package com.example.marce.luckypuzzle.io.apiServices;

import com.example.marce.luckypuzzle.utils.URLUtils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marce on 28/03/17.
 */

public class APIAdapter {
    private static Retrofit RETROFIT;

    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder()
//                .setExclusionStrategies(new ExclusionStrategy() {
//                    /**This excludes Marker object used in NearbyPlayer
//                     * class to avoid GSON problems.
//                     */
//                    @Override
//                    public boolean shouldSkipField(FieldAttributes f) {
//                        return f.getDeclaringClass().equals(NearbyPlayer.class)&& f.getName().equals("marker");
//                    }
//
//                    @Override
//                    public boolean shouldSkipClass(Class<?> clazz) {
//                        return false;
//                    }
//                })
                .create();
        //The adapter will be a singleton
        if(RETROFIT == null)
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(URLUtils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        return RETROFIT;
    }


}
