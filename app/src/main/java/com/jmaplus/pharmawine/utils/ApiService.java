package com.jmaplus.pharmawine.utils;

import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.models.DailyReportEnd;
import com.jmaplus.pharmawine.models.DailyReportEndResponse;
import com.jmaplus.pharmawine.models.DailyReportStart;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;

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

    @GET("planning/{delegate_id}/clients/{StartDate}/{EndDate}")
    Call<List<Customer>> getDelegatePlanning(
            @Path("delegate_id") String delegateId,
            @Path("StartDate") String startDate,
            @Path("EndDate") String endDate,
            @Header("Authorization") String authorization);

    @POST("report/daily")
    Call<DailyReportStartResponse> startDaylyReport(
            @Body DailyReportStart startObject,
            @Header("Authorization") String authorization);

    @PUT("/report/daily/{daily_report_id}")
    Call<DailyReportEndResponse> endDailyReport(
            @Path("daily_report_id") Integer daily_report_id,
            @Body DailyReportEnd endObject,
            @Header("Authorization") String authorization);

    @GET("customer/{id}")
    Call<List<Customer>> getCustomerDetails(
            @Path("id") Integer id,
            @Header("Authorization") String authorization);
}
