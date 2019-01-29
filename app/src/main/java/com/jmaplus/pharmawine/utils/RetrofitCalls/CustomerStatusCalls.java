package com.jmaplus.pharmawine.utils.RetrofitCalls;

import com.jmaplus.pharmawine.models.CustomerStatus;
import com.jmaplus.pharmawine.utils.ApiService;
import com.jmaplus.pharmawine.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerStatusCalls {
    public static ApiService mApiService = ApiService.retrofit.create(ApiService.class);

    public static void getCustomerStatusList(String token, final Callbacks callbacks) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference(callbacks);

        // creating call to Api service
        Call<List<CustomerStatus>> call = mApiService.getAllCustomersStatus(Utils.getBarearTokenString(token));

        call.enqueue(new Callback<List<CustomerStatus>>() {
            @Override
            public void onResponse(Call<List<CustomerStatus>> call, Response<List<CustomerStatus>> response) {
                if (callbacksWeakReference.get() != null) {
                    if (response.code() == 200) {
                        callbacks.onCustomerStatussResponse(response.body());
                    } else {
                        callbacks.onCustomerStatussFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CustomerStatus>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onCustomerStatussFailure();
            }
        });
    }

    // 1 - Interface
    public interface Callbacks {

        void onCustomerStatussResponse(@Nullable List<CustomerStatus> customerStatues);

        void onCustomerStatussFailure();
    }
}
