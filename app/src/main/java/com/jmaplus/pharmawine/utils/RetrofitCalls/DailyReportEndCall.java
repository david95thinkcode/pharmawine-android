package com.jmaplus.pharmawine.utils.RetrofitCalls;

import android.support.annotation.Nullable;
import android.util.Log;

import com.jmaplus.pharmawine.models.DailyReportEnd;
import com.jmaplus.pharmawine.models.DailyReportEndResponse;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyReportEndCall {

    // Retrofit instance
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void postDailyReportEnd(String token, final Callbacks callbacks, DailyReportEnd reportEnd, Integer reportID) {

        Log.i("postDailyReportEnd",
                "postDailyReportEnd: " + reportEnd.toString() +
                        "\n Report: " + reportID +
                        "\n token: " + token);

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);

        Call<DailyReportEndResponse> call = mApiService.endDailyReport(reportID, reportEnd, Utils.getBarearTokenString(token));

        call.enqueue(new Callback<DailyReportEndResponse>() {
            @Override
            public void onResponse(Call<DailyReportEndResponse> call, Response<DailyReportEndResponse> response) {

                if (callbacksWeakReference.get() != null)
                    if (response.code() == 200)
                        callbacks.onEndDailyReportResponse(response.body());
                    else
                        callbacks.onEndDailyReportFailure();

            }

            @Override
            public void onFailure(Call<DailyReportEndResponse> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onEndDailyReportFailure();
            }
        });
    }

    public interface Callbacks {
        void onEndDailyReportResponse(@Nullable DailyReportEndResponse response);

        void onEndDailyReportFailure();
    }
}
