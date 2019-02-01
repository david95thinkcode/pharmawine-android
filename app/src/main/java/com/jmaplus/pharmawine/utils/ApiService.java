package com.jmaplus.pharmawine.utils;

import com.jmaplus.pharmawine.models.Area;
import com.jmaplus.pharmawine.models.Center;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.models.CustomerStatus;
import com.jmaplus.pharmawine.models.DailyReportEnd;
import com.jmaplus.pharmawine.models.DailyReportEndResponse;
import com.jmaplus.pharmawine.models.DailyReportStart;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;
import com.jmaplus.pharmawine.models.Network;
import com.jmaplus.pharmawine.models.SimpleUser;
import com.jmaplus.pharmawine.models.Speciality;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    Retrofit retrofit = new Retrofit.Builder()
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

    @Headers("X-HTTP-Method-Override: PUT")
    @POST("report/daily/{daily_report_id}")
    Call<DailyReportEndResponse> endDailyReport(
            @Path("daily_report_id") Integer daily_report_id,
            @Body DailyReportEnd endObject,
            @Header("Authorization") String authorization);

    // ======================================= CUSTOMERS =======================================

    @GET("customer/{id}")
    Call<List<Customer>> getCustomerDetails(
            @Path("id") Integer id,
            @Header("Authorization") String authorization);


    @GET("customers/prospect/connu")
    Call<List<Customer>> getKnownProspects(@Header("Authorization") String authorization);

    @GET("remaining")
    Call<List<Customer>> getRemainingCustomers(@Header("Authorization") String authorization);

    @GET("customers/viewed")
    Call<List<Customer>> getSeenCustomers(@Header("Authorization") String authorization);

    // ======================================= NETWORKS =======================================

    @GET("network")
    Call<List<Network>> getAllNetworks(@Header("Authorization") String authorization);

    @GET("user/network")
    Call<List<Network>> getNetworkDetails(
            @Header("Authorization") String authorization);

    @GET("network/{network_id}/users")
    Call<List<SimpleUser>> getNetworkUsers(
            @Path("network_id") Integer networkID,
            @Header("Authorization") String authorization);

    // ======================================= PROFILAGE =======================================
    @PUT("customer/{customer_id}")
    Call<Customer> editCustomer(
            @Path("customer_id") Integer customer_id,
            @Body Customer customer,
            @Header("Authorization") String authorization);

    // ======================================= CENTERS =======================================
    @GET("center")
    Call<List<Center>> getAllCenters(@Header("Authorization") String authorization);

    // ======================================= SPECIALITY =======================================
    @GET("speciality")
    Call<List<Speciality>> getAllSpecialities(@Header("Authorization") String authorization);

    // ======================================= SPECIALITY =======================================
    @GET("area")
    Call<List<Area>> getAllAreas(@Header("Authorization") String authorization);

    // ======================================= CUSTOMER STATUS =======================================
    @GET("customers/status")
    Call<List<CustomerStatus>> getAllCustomersStatus(@Header("Authorization") String authorization);

}
