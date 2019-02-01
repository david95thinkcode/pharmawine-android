package com.jmaplus.pharmawine.utils.RetrofitCalls.customers;

import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeenCustomerCalls {

    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getSeenCustomers(String token, final Callbacks callbacks) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        Call<List<Customer>> call = mApiService.getSeenCustomers(Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (callbacksWeakReference.get() != null) {
                    if (response.code() == 200)
                        callbacks.onSeenCustomersResponse(response.body());
                    else
                        callbacks.onSeenCustomersFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onSeenCustomersFailure();
            }
        });
    }

    public interface Callbacks {
        void onSeenCustomersResponse(@Nullable List<Customer> customers);

        void onSeenCustomersFailure();
    }
}
