package com.jmaplus.pharmawine.utils.RetrofitCalls;

import android.support.annotation.Nullable;

import com.jmaplus.pharmawine.models.DailyReportStart;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyReportCalls {

    // Retrofit instance
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void postDailyReportStart(String token, final Callbacks callbacks, DailyReportStart reportStart) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        Call<DailyReportStartResponse> call = mApiService.startDaylyReport(reportStart, Utils.getBarearTokenString(token));

        call.enqueue(new Callback<DailyReportStartResponse>() {
            @Override
            public void onResponse(Call<DailyReportStartResponse> call, Response<DailyReportStartResponse> response) {
                if (callbacksWeakReference.get() != null)
                    if (response.code() == 200)
                        callbacks.onStartDailyReportResponse(response.body());
                    else
                        callbacks.onStartDailyReportFailure();

            }

            @Override
            public void onFailure(Call<DailyReportStartResponse> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onStartDailyReportFailure();
            }
        });
    }

    public interface Callbacks {
        void onStartDailyReportResponse(@Nullable DailyReportStartResponse response);

        void onStartDailyReportFailure();

    }

}
