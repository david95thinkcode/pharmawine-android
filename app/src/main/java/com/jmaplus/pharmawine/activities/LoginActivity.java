package com.jmaplus.pharmawine.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.services.ApiClient;
import com.jmaplus.pharmawine.services.ApiInterface;
import com.jmaplus.pharmawine.services.responses.LoginResponse;
import com.jmaplus.pharmawine.utils.ApiRoutes;
import com.jmaplus.pharmawine.utils.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etId, etPassword;
    private TextView btnConnexion;

    private PrefManager prefManager;
    private final int MIN_PASSWORD_LENGTH = 6;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefManager = new PrefManager(this);

        etId = findViewById(R.id.et_login_id);
        etPassword = findViewById(R.id.et_login_password);
        btnConnexion = findViewById(R.id.btn_login_connexion);

//        TODO removes this
        etId.setText("alcoy@gmail.com");
        etPassword.setText("secret");

//        Avoid courrier font for passwords input
        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());

//        btnConnexion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (validateForm()) {
////                    Login with email and password
//                    login(etId.getText().toString().trim(), etPassword.getText().toString().trim());
//                }
//            }
//        });

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithNewApi(etId.getText().toString().trim(), etPassword.getText().toString().trim());
            }
        });
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

    private void loginWithNewApi(String email, String password) {

        try {
            RequestQueue queue = Volley.newRequestQueue(this);

            JSONObject requestBody = new JSONObject();

            requestBody.put("email", email);
            requestBody.put("password", password);

            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage(getResources().getString(R.string.loading));
            dialog.show();

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST, ApiRoutes.login, requestBody,
                    new com.android.volley.Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Request Succeed
                            // ...
                            dialog.dismiss();
                            try {
                                getAuthenticatedUserInfos(response.getString("token"));
                                Log.i(getLocalClassName(), "Response : " + response);
                                Toast.makeText(LoginActivity.this, "User data getted", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Log.e(TAG, e.getMessage());
                                e.printStackTrace();
                            }

                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                    Log.e(getLocalClassName(), "Request failed : " + error.getMessage());
                    Log.e(TAG, error.toString());
                    dialog.dismiss();

                }

            });

            queue.add(request);
        } catch (JSONException jsonError) {
            Toast.makeText(this, "Probleme interne", Toast.LENGTH_SHORT).show();
            Log.e(TAG, jsonError.getMessage());
            jsonError.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(this, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

    }

    private void getAuthenticatedUserInfos(String token) {

    }

    private void storeAuthenticatedUserInfosInSharedPreferences() {

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

    private boolean isValidEmail(String target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    );
}
