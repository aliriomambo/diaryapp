package com.aliriomambo.diaryapp.Utils;

import com.aliriomambo.diaryapp.data.model.Entry;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Blue on 4/16/2018.
 */

public interface ApiService {
    //Banco json
    @GET("/{jsonName}.json")
    Call<List<Entry>> loadEntryList(@Path("jsonName") String jsonName);

}
