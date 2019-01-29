package com.jmaplus.pharmawine.utils.RetrofitCalls;

import com.jmaplus.pharmawine.models.Speciality;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecialityCalls {

    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getSpecialitysList(String token, final Callbacks callbacks) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        // creating call to Api service
        Call<List<Speciality>> call = mApiService.getAllSpecialities(Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<Speciality>>() {
            @Override
            public void onResponse(Call<List<Speciality>> call, Response<List<Speciality>> response) {
                if (callbacksWeakReference.get() != null) {
                    if (response.code() == 200) {
                        callbacks.onSpecialitysResponse(response.body());
                    } else {
                        callbacks.onSpecialitysFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Speciality>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onSpecialitysFailure();
            }
        });
    }

    // 1 - Interface
    public interface Callbacks {

        void onSpecialitysResponse(@Nullable List<Speciality> specialities);

        void onSpecialitysFailure();
    }
}
