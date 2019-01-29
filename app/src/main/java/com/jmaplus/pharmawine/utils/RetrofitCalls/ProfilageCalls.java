package com.jmaplus.pharmawine.utils.RetrofitCalls;

import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilageCalls {
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void editCustomerProfile(String token, final Callbacks callbacks, Integer customerID, Customer body) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        // creating call to Api service
        Call<Customer> call = mApiService.editCustomer(customerID, body, Utils.getBarearTokenString(token));

        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (callbacksWeakReference.get() != null) {
                    if (response.code() == 200) {
                        callbacks.onUpdatedCustomerResponse(response.body());
                    } else {
                        callbacks.onUpdatedCustomerFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onUpdatedCustomerFailure();
            }
        });
    }

    // 1 - Interface
    public interface Callbacks {

        void onUpdatedCustomerResponse(@Nullable Customer updatedCustomer);

        void onUpdatedCustomerFailure();
    }
}
