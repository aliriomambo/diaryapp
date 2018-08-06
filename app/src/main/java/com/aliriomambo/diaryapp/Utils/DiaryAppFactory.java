package com.aliriomambo.diaryapp.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Blue on 4/16/2018.
 */

public class DiaryAppFactory {
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(FileConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    private static final ApiService API_SERVICE = retrofit.create(ApiService.class);

    public static ApiService getApiService() {
        return API_SERVICE;
    }

}
