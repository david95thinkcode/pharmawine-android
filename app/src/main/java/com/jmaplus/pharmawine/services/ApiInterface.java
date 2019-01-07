package com.jmaplus.pharmawine.services;

import com.jmaplus.pharmawine.models.LabAssistance;
import com.jmaplus.pharmawine.models.Laboratory;
import com.jmaplus.pharmawine.models.MedicalTeam;
import com.jmaplus.pharmawine.models.Pharmacy;
import com.jmaplus.pharmawine.models.Product;
import com.jmaplus.pharmawine.models.ProductCategory;
import com.jmaplus.pharmawine.models.User;
import com.jmaplus.pharmawine.services.responses.LoginResponse;
import com.jmaplus.pharmawine.services.responses.MedicalClientsResponse;
import com.jmaplus.pharmawine.services.responses.NetworkMembersResponse;
import com.jmaplus.pharmawine.services.responses.PharmacyClientsResponse;
import com.jmaplus.pharmawine.services.responses.ProductCategoriesResponse;
import com.jmaplus.pharmawine.services.responses.ProductsResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("api/register")
    Call<User> registerDelegate(@Body User user);

    @POST("api/login")
    Call<LoginResponse> login(@Body RequestBody body);

    @GET("api/delegates")
    Call<List<User>> getDelegates();

    @GET("api/delegates/medical")
    Call<List<User>> getMedicalDelegates();

    @GET("api/delegates/pharmacy")
    Call<List<User>> getPharmacyDelegates();

    @GET("api/delegates/supervisor")
    Call<List<User>> getSupervisorDelegates();

    @GET("api/delegate/{id}/products")
    Call<ProductsResponse> getDelegateProducts(@Path("id") int delegateId, @Header("Authorization") String authorization);

    @GET("api/delegate/{id}")
    Call<User> getDelegate(@Path("id") int delegateId);

    @GET("api/category/{id}/products")
    Call<ProductsResponse> getCategoryProducts(@Path("id") int delegateId, @Header("Authorization") String authorization);

    @GET("api/delegate/{id}/products/category")
    Call<ProductCategoriesResponse> getDelegateProductCategories(@Path("id") int delegateId, @Header("Authorization") String authorization);

    @GET("api/delegate/{id}/customers/1")
    Call<MedicalClientsResponse> getDelegateMedicalTeamCustomers(@Path("id") int delegateId, @Header("Authorization") String authorization);

    @GET("api/delegate/{id}/customers/2")
    Call<PharmacyClientsResponse> getDelegatePharmacyCustomers(@Path("id") int delegateId, @Header("Authorization") String authorization);

    @GET("api/network/{id}/members")
    Call<NetworkMembersResponse> getNetworkMembers(@Path("id") int networkId, @Header("Authorization") String authorization);

    @GET("api/delegate/{id}/customers")
    Call<List<User>> getDelegateCustomers(@Path("id") int delegateId);

    @GET("api/laboratories")
    Call<ArrayList<Laboratory>> getLaboratories(@Header("Authorization") String authorization);

    @GET("api/laboratory/{id}")
    Call<Laboratory> getLaboratory(@Path("id") int laboratoryId);

    @FormUrlEncoded
    @POST("api/register")
    Call<Laboratory> registerLaboratory(@Body Laboratory laboratory);

    @GET("api/laboratory/{id}/assistants")
    Call<List<LabAssistance>> getLaboratoryAssistances(@Path("id") int laboratoryId);

    @DELETE("api/laboratory/{id}")
    Call<Laboratory> delLaboratory(@Path("id") int laboratoryId);

    @PUT("api/laboratory/{id}")
    Call<Laboratory> updateLaboratory(@Path("id") int laboratoryId, @HeaderMap Map<String, String> headers);
}
