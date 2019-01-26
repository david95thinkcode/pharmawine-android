package com.jmaplus.pharmawine.utils;

import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.models.DailyReportEnd;
import com.jmaplus.pharmawine.models.DailyReportEndResponse;
import com.jmaplus.pharmawine.models.DailyReportStart;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;
import com.jmaplus.pharmawine.models.Network;
import com.jmaplus.pharmawine.models.SimpleUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ApiRoutes.baseRoute)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // ======================================= USERS =======================================

    @GET("users/{user_id}")
    Call<SimpleUser> getUserDetails(
            @Path("user_id") Integer userID,
            @Header("Authorization") String authorization);

    // ======================================= PLANNING =======================================

    @GET("planning/{delegate_id}/clients/{StartDate}/{EndDate}")
    Call<List<Customer>> getDelegatePlanning(
            @Path("delegate_id") String delegateId,
            @Path("StartDate") String startDate,
            @Path("EndDate") String endDate,
            @Header("Authorization") String authorization);

    // ======================================= REPORTS =======================================

    @POST("report/daily")
    Call<DailyReportStartResponse> startDaylyReport(
            @Body DailyReportStart startObject,
            @Header("Authorization") String authorization);

    @PUT("/report/daily/{daily_report_id}")
    Call<DailyReportEndResponse> endDailyReport(
            @Path("daily_report_id") Integer daily_report_id,
            @Body DailyReportEnd endObject,
            @Header("Authorization") String authorization);

    // ======================================= CUSTOMERS =======================================

    @GET("customer/{id}")
    Call<List<Customer>> getCustomerDetails(
            @Path("id") Integer id,
            @Header("Authorization") String authorization);

    // ======================================= NETWORKS =======================================

    @GET("network")
    Call<List<Network>> getAllNetworks(@Header("Authorization") String authorization);

    @GET("network/{network_id}")
    Call<Network> getNetworkDetails(
            @Path("network_id") Integer networkID,
            @Header("Authorization") String authorization);

    @GET("network/{network_id}/users")
    Call<List<SimpleUser>> getNetworkUsers(
            @Path("network_id") Integer networkID,
            @Header("Authorization") String authorization);

    // ======================================= WORKS =======================================


}
