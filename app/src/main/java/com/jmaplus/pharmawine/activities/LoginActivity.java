package com.jmaplus.pharmawine.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthUserResponse;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.services.ApiClient;
import com.jmaplus.pharmawine.services.ApiInterface;
import com.jmaplus.pharmawine.services.responses.LoginResponse;
import com.jmaplus.pharmawine.utils.PrefManager;
import com.jmaplus.pharmawine.utils.RetrofitCalls.AuthCalls;

import java.util.regex.Pattern;

import javax.annotation.Nullable;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements AuthCalls.Callbacks {

    private EditText etId, etPassword;
    private TextView btnConnexion;

    private PrefManager prefManager;
    private final int MIN_PASSWORD_LENGTH = 6;
    private static final String TAG = "LoginActivity";
    private ProgressDialog dialog;
    private String userToken = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefManager = new PrefManager(this);

        InitalizeUI();

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) loginWithNewApi();
            }
        });
    }

    private void InitalizeUI() {

        // progress dialog
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(getResources().getString(R.string.loading));

        etId = findViewById(R.id.et_login_id);
        etPassword = findViewById(R.id.et_login_password);
        btnConnexion = findViewById(R.id.btn_login_connexion);

//        TODO removes this
//        etId.setText("alcoy@gmail.com");
        etId.setText("rita09@example.com");
        etPassword.setText("secret");

//        Avoid courrier font for passwords input
        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
    }

    private void loginWithNewApi() {
        startLoadingDialog();
        try {
            AuthCalls.startLoginRequest(this,
                    etId.getText().toString().trim(),
                    etPassword.getText().toString().trim()
            );
        } catch (Exception e) {
            Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            stopLoadingDialog();
        }

    }

    private void getAuthenticatedUserInfos(String token) {
        try {
//            LoginCredentials credentials = new LoginCredentials(etId.getText().toString().trim(), etPassword.getText().toString().trim());
            AuthCalls.getDetails(this, token);
        } catch (Exception e) {
            Toast.makeText(this, "Error pour recuperation des details du user", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            stopLoadingDialog();
        }
    }

    @Override
    public void onLoginResponse(@Nullable AuthUserResponse response) {
//        Toast.makeText(this, "Authentication step 1 passed", Toast.LENGTH_SHORT).show();
        userToken = response.getToken();
        getAuthenticatedUserInfos(response.getToken());
    }

    @Override
    public void onLoginWrongCredentialsResponse() {
        stopLoadingDialog();
        Toast.makeText(this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginFailure() {
        stopLoadingDialog();
        Toast.makeText(LoginActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchingDetailsResponse(@Nullable AuthUser response) {
//        Toast.makeText(this, "Authentication step 2 passed", Toast.LENGTH_SHORT).show();

        Boolean isStored = saveUserData(response);

        if (isStored) {
            Toast.makeText(this, "Authentication step 3 passed", Toast.LENGTH_SHORT).show();
            // open the app
            stopLoadingDialog();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            stopLoadingDialog();
            Toast.makeText(this, "Retry please", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFetchingDetailsFailure() {
        stopLoadingDialog();
    }

    private boolean saveUserData(AuthUser user) {
        return user.storeInSharedPreferences(this, userToken);
    }

    private boolean validateForm() {

        if (etId.getText().toString().trim().isEmpty()) {
            etId.setError(getResources().getString(R.string.field_required));
            etId.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().trim().isEmpty()) {
            etPassword.setError(getResources().getString(R.string.field_required));
            etPassword.requestFocus();
            return false;
        }
        if (!isValidEmail(etId.getText().toString())) {
            etId.setError(getResources().getString(R.string.email_not_valid));
            etId.requestFocus();
            return false;
        }
        if (etPassword.getText().toString().trim().length() < MIN_PASSWORD_LENGTH) {
            etPassword.setError(getResources().getString(R.string.not_long_enough).replace("%", String.valueOf(MIN_PASSWORD_LENGTH)));
            etPassword.requestFocus();
            return false;
        }
        etId.setText(etId.getText().toString().trim());
        etPassword.setText(etPassword.getText().toString().trim());
        return true;
    }

    private void login(String email, String password) {

        if (!PharmaWine.getInstance().isOnline()) {
            Toast.makeText(this, getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(getResources().getString(R.string.loading));
        dialog.show();

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Call<LoginResponse> call = apiService.login(body);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                if (response.isSuccessful()) {

                    if (response.body() != null) {

//                        Sans doute inutile mais bon
                        if (response.body().getStatusCode() == 200) {


                            prefManager.setToken(response.body().getToken());

                            AuthenticatedUser authenticatedUser = response.body().getAuthenticatedUser();
                            AuthenticatedUser.saveUser(PharmaWine.mRealm, authenticatedUser);

//                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.logged_in), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

//                            TODO Get the connected user informations

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    } else {

//                        Server returned response error
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } else {

                    // Not found 401
                    if (response.code() == 401) {

                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle(getResources().getString(R.string.app_name))
                                .setMessage(getResources().getString(R.string.unauthorised_id))
                                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        etPassword.setText("");
                                        etId.requestFocus();
                                    }
                                })
                        .show();

                    } else {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void stopLoadingDialog() {
        dialog.dismiss();
    }

    private void startLoadingDialog() {
        dialog.show();
    }

    private boolean isValidEmail(String target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    );
}
