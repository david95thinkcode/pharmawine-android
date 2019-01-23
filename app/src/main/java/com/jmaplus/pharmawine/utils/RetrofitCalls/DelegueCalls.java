package com.jmaplus.pharmawine.utils.RetrofitCalls;

import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.models.DailyReportEnd;
import com.jmaplus.pharmawine.models.DailyReportEndResponse;
import com.jmaplus.pharmawine.models.DailyReportStart;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DelegueCalls {

    // Retrofit instance
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    // 1 - Interface
    public interface Callbacks {

        void onPlanningResponse(@Nullable List<Customer> customers);
        void onPlanningFailure();
    }

    // Public method to fetch pllanning
    public static void getPlanning(String token, final Callbacks callbacks,
                                   String delegueID, String startDate, String endDate) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // creating call to Api service
        Call<List<Customer>> call = mApiService.getDelegatePlanning(delegueID,
                startDate, endDate, Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onPlanningResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onPlanningFailure();
            }
        });
    }

}
