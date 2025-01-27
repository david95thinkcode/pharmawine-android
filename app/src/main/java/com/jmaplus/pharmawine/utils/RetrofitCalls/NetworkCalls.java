package com.jmaplus.pharmawine.utils.RetrofitCalls;

import android.util.Log;

import com.jmaplus.pharmawine.models.Network;
import com.jmaplus.pharmawine.models.SimpleUser;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkCalls {
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getNetworkMembers(final Callbacks callbacks, String token, Integer networkID) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        Call<List<SimpleUser>> call = mApiService.getNetworkUsers(networkID, Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<SimpleUser>>() {
            @Override
            public void onResponse(Call<List<SimpleUser>> call, Response<List<SimpleUser>> response) {
                if ((callbacksWeakReference.get() != null) && (response.code() == 200))
                    callbacks.onNetworkMembersResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<SimpleUser>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onNetworkMembersFailure();
            }
        });
    }

    public static void getDetails(final Callbacks callbacks, String token) {
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        Call<List<Network>> call = mApiService.getNetworkDetails(Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<Network>>() {
            @Override
            public void onResponse(Call<List<Network>> call, Response<List<Network>> response) {
                if ((callbacksWeakReference.get() != null) && (response.code() == 200))
                    callbacks.onNetworkDetailsResponse(response.body().get(0));
            }

            @Override
            public void onFailure(Call<List<Network>> call, Throwable t) {
                Log.e(getClass().getName(), "onFailure: " + t.getMessage());
                if (callbacksWeakReference.get() != null)
                    callbacks.onNetworkDetailsFailure();
            }
        });

    }

    public interface Callbacks {
        void onNetworkMembersResponse(@Nullable List<SimpleUser> members);

        void onNetworkMembersFailure();

        void onNetworkDetailsResponse(@Nullable Network network);

        void onNetworkDetailsFailure();
    }
}
