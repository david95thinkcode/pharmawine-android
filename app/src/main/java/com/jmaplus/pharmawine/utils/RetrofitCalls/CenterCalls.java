package com.jmaplus.pharmawine.utils.RetrofitCalls;

import com.jmaplus.pharmawine.models.Center;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CenterCalls {

    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getCentersList(String token, final Callbacks callbacks) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        // creating call to Api service
        Call<List<Center>> call = mApiService.getAllCenters(Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<Center>>() {
            @Override
            public void onResponse(Call<List<Center>> call, Response<List<Center>> response) {
                if (callbacksWeakReference.get() != null) {
                    if (response.code() == 200) {
                        callbacks.onCentersResponse(response.body());
                    } else {
                        callbacks.onCentersFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Center>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onCentersFailure();
            }
        });
    }

    // 1 - Interface
    public interface Callbacks {

        void onCentersResponse(@Nullable List<Center> centers);

        void onCentersFailure();
    }

}
