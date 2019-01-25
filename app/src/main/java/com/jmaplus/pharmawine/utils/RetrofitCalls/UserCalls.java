package com.jmaplus.pharmawine.utils.RetrofitCalls;

import android.support.annotation.Nullable;

import com.jmaplus.pharmawine.models.SimpleUser;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCalls {

    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getDetails(Integer userID, final Callbacks callbacks, String token) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);
        Call<SimpleUser> call = mApiService.getUserDetails(userID, Utils.getBarearTokenString(token));

        call.enqueue(new Callback<SimpleUser>() {
            @Override
            public void onResponse(Call<SimpleUser> call, Response<SimpleUser> response) {
                if (callbacksWeakReference.get() != null && response.code() == 200) {
                    callbacks.onUserDetailsResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<SimpleUser> call, Throwable t) {
                if (callbacksWeakReference.get() != null) {
                    callbacks.onUserDetailsFailure();
                }
            }
        });
    }

    // CALLBACKS
    public interface Callbacks {
        void onUserDetailsResponse(@Nullable SimpleUser response);

        void onUserDetailsFailure();
    }
}
