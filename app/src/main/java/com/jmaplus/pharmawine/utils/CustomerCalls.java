package com.jmaplus.pharmawine.utils;

import com.jmaplus.pharmawine.models.Customer;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerCalls {
    // Retrofit instance
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getDetails(String token, final Callbacks callbacks, Integer customerID) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // creating call to Api service
        Call<List<Customer>> call = mApiService.getCustomerDetails(customerID, Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onCustomerDetailsResponse(response.body().get(0));
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onCustomerDetailsFailure();
            }
        });
    }

    // 1 - Interface
    public interface Callbacks {

        void onCustomerDetailsResponse(@Nullable Customer customer);

        void onCustomerDetailsFailure();
    }


}
