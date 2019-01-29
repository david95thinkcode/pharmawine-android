package com.jmaplus.pharmawine.utils;

import com.jmaplus.pharmawine.models.ApiProduct;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthUserResponse;
import com.jmaplus.pharmawine.models.LoginCredentials;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthService {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiRoutes.baseRoute)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @POST("login")
    Call<AuthUserResponse> login(@Body LoginCredentials credentials);

    @GET("user")
    Call<List<AuthUser>> getAuthUserDetails(@Header("Authorization") String authorization);

    @GET("user/products")
    Call<List<ApiProduct>> getAuthUserProducts(@Header("Authorization") String authorization);


}
