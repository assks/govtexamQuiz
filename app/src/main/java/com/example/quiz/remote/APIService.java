package com.example.quiz.remote;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lenovo on 3/31/2018.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("login.php?apicall=login")
    Call<String> userlogincall (@Field("username") String username, @Field("password") String userpass);





}
