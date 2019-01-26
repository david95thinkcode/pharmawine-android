package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.ApiProduct;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthUserResponse;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.RetrofitCalls.AuthCalls;

import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

public class InfosActivity extends AppCompatActivity implements AuthCalls.Callbacks {

    private CircleImageView imgProfil;
    private ProgressBar mProgressBarInfo;
    private TextView tvWork, tvNetwork;
    private EditText etFirstName, etLastName, etSex, etDayOfBirth, etMonthOfBirth, etYearOfBirth, etMaritalStatus, etAddress, etPhoneNumber1, etPhoneNumber2, etEmail;

    private AuthUser mAuthUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        fetchUsersDataOnline();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initViews() {

        // Binding views
        imgProfil = findViewById(R.id.img_profil);
        tvWork = findViewById(R.id.tv_work);
        tvNetwork = findViewById(R.id.tv_network_label);
        mProgressBarInfo = findViewById(R.id.progressBar_Infos);
        mProgressBarInfo.setVisibility(GONE);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etSex = findViewById(R.id.et_sex);
        etDayOfBirth = findViewById(R.id.et_day_birthday);
        etMonthOfBirth = findViewById(R.id.et_month_birthday);
        etYearOfBirth = findViewById(R.id.et_year_birthday);
        etMaritalStatus = findViewById(R.id.et_marital_status);
        etAddress = findViewById(R.id.et_address);
        etPhoneNumber1 = findViewById(R.id.et_phone_number1);
        etPhoneNumber2 = findViewById(R.id.et_phone_number2);
        etEmail = findViewById(R.id.et_email);

        //        TODO get the updated informations from database online

        switch (AuthUser.getRoleFromSharedPreferences(this)) {
            case Constants.ROLE_ADMIN_KEY:
                tvWork.setText("Administrateur");
                break;
            case Constants.ROLE_DELEGUE_KEY:
                tvWork.setText("Délégué médical");
                break;
            case Constants.ROLE_SUPERVISEUR_KEY:
                tvWork.setText("Superviseur médical");
                break;
            default:
                Log.i(getLocalClassName(), "initViews: Bizzare thing");
                break;
        }
    }

    private void fetchUsersDataOnline() {
        /**
         * fetch data from the server first
         * If failed, show datas from shared preferences
         * If response is successful, update the views, and update the shared preferences datas too
         */

        AuthCalls.getDetails(this, AuthUser.getToken(this));

        mProgressBarInfo.setVisibility(View.VISIBLE);

    }

    private void fetchUserDataFromSharedPreferences() {
        mProgressBarInfo.setVisibility(GONE);
        Toast.makeText(this, "Infos locales", Toast.LENGTH_SHORT).show();

        mAuthUser = AuthUser.getAuthenticatedUser(this);
        updateUIWithUserDatas();
    }

    private void updateUIWithUserDatas() {

        mProgressBarInfo.setVisibility(GONE);

        try {
            tvNetwork.setText("Réseau - " + mAuthUser.getNetwork().getName());
        } catch (NullPointerException e) {
            e.printStackTrace();
            tvNetwork.setText("Réseau - Non défini");

        }

        etFirstName.setText(mAuthUser.getFirstname());
        etLastName.setText(mAuthUser.getLastname());
        etSex.setText((mAuthUser.getSex().equals("M")) ? "Homme" : (mAuthUser.getSex().equals("F")) ? "Femme" : "Non défini");
        etDayOfBirth.setText(mAuthUser.getBirthday().split("-")[2]);
        etMonthOfBirth.setText(mAuthUser.getBirthday().split("-")[1]);
        etYearOfBirth.setText(mAuthUser.getBirthday().split("-")[0]);
        etMaritalStatus.setText(mAuthUser.getMaritalStatus());
        etPhoneNumber1.setText(mAuthUser.getTelephone1());
        etPhoneNumber2.setText(mAuthUser.getTelephone2());

        if (etMaritalStatus.getText().toString().trim().isEmpty())
            etMaritalStatus.setText("Non défini");

        if (etAddress.getText().toString().trim().isEmpty())
            etAddress.setText("Non défini");

        etEmail.setText(mAuthUser.getEmail());

        if ((mAuthUser.getAvatar() != null)
                && (!mAuthUser.getAvatar().isEmpty())) {
            Glide.with(this).load(mAuthUser.getAvatar()).into(imgProfil);
        }
    }

    // ==============

    @Override
    public void onLoginResponse(@Nullable AuthUserResponse response) {

    }

    @Override
    public void onLoginWrongCredentialsResponse() {

    }

    @Override
    public void onLoginFailure() {

    }

    @Override
    public void onFetchingDetailsResponse(@Nullable AuthUser response) {
        if (response != null) {
            mAuthUser = response;
            mAuthUser.storeInSharedPreferences(this, AuthUser.getToken(this));
            updateUIWithUserDatas();
        } else fetchUserDataFromSharedPreferences();
    }

    @Override
    public void onFetchingDetailsFailure() {
        fetchUserDataFromSharedPreferences();
    }

    @Override
    public void onAuthProductsResponse(@Nullable List<ApiProduct> products) {

    }

    @Override
    public void onAuthProductFailure() {

    }
}
