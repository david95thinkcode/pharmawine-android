package com.jmaplus.pharmawine.utils.RetrofitCalls;

import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthUserResponse;
import com.jmaplus.pharmawine.models.LoginCredentials;
import com.jmaplus.pharmawine.utils.AuthService;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthCalls {

    public static void startLoginRequest(final Callbacks callbacks, String email, String password) {

        // creating a weak reference to callback to avoid memory leak
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        // CREATIN RETROFIT INSTANCE AND REALTED ENDPOINTS
        AuthService authService = AuthService.retrofit.create(AuthService.class);

        // Creating call to the api
        Call<AuthUserResponse> call = authService.login(new LoginCredentials(email, password));

        // start the call
        call.enqueue(new Callback<AuthUserResponse>() {
            @Override
            public void onResponse(Call<AuthUserResponse> call, Response<AuthUserResponse> response) {
                if (callbacksWeakReference.get() != null)
                    if (response.code() == 200) {
                        callbacks.onLoginResponse(response.body());
                    } else if (response.code() == 400) {
                        callbacks.onLoginWrongCredentialsResponse();
                    } else {
                        callbacks.onLoginFailure();
                    }

            }

            @Override
            public void onFailure(Call<AuthUserResponse> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onLoginFailure();
            }
        });
    }

    public static void getDetails(final Callbacks callbacks, String token) {

        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<Callbacks>(callbacks);

        AuthService authService = AuthService.retrofit.create(AuthService.class);

        Call<List<AuthUser>> call = authService.getAuthUserDetails("Bearer " + token);

        call.enqueue(new Callback<List<AuthUser>>() {
            @Override
            public void onResponse(Call<List<AuthUser>> call, Response<List<AuthUser>> response) {
                if (callbacksWeakReference.get() != null) {
                    callbacks.onFetchingDetailsResponse(response.body().get(0));
                }
            }

            @Override
            public void onFailure(Call<List<AuthUser>> call, Throwable t) {
                if (callbacksWeakReference.get() != null)
                    callbacks.onFetchingDetailsFailure();
            }
        });
    }

    // CALLBACKS
    public interface Callbacks {
        void onLoginResponse(@Nullable AuthUserResponse response);

        void onLoginWrongCredentialsResponse();

        void onLoginFailure();

        void onFetchingDetailsResponse(@Nullable AuthUser response);

        void onFetchingDetailsFailure();
    }
}
