package com.jmaplus.pharmawine.utils.RetrofitCalls;

import com.jmaplus.pharmawine.models.Area;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaCalls {
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getAreasList(String token, final Callbacks callbacks) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        // creating call to Api service
        Call<List<Area>> call = mApiService.getAllAreas(Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<Area>>() {
            @Override
            public void onResponse(Call<List<Area>> call, Response<List<Area>> response) {
                if (callbacksWeakReference.get() != null) {
                    if (response.code() == 200) {
                        callbacks.onAreasResponse(response.body());
                    } else {
                        callbacks.onAreasFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Area>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onAreasFailure();
            }
        });
    }

    // 1 - Interface
    public interface Callbacks {

        void onAreasResponse(@Nullable List<Area> areas);

        void onAreasFailure();
    }
}
