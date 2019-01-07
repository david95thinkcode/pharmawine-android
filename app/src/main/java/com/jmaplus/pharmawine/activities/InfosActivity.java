package com.jmaplus.pharmawine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;

public class InfosActivity extends AppCompatActivity {

    private CircleImageView imgProfil;
    private TextView tvWork, tvNetwork;
    private EditText etFirstName, etLastName, etSex, etDayOfBirth, etMonthOfBirth, etYearOfBirth, etMaritalStatus, etAddress, etPhoneIndic1, etPhoneNumber1, etPhoneIndic2, etPhoneNumber2, etEmail;


    private AuthenticatedUser authenticatedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authenticatedUser = AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm);

        imgProfil = findViewById(R.id.img_profil);
        tvWork = findViewById(R.id.tv_work);
        tvNetwork = findViewById(R.id.tv_network_label);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etSex = findViewById(R.id.et_sex);
        etDayOfBirth = findViewById(R.id.et_day_birthday);
        etMonthOfBirth = findViewById(R.id.et_month_birthday);
        etYearOfBirth = findViewById(R.id.et_year_birthday);
        etMaritalStatus = findViewById(R.id.et_marital_status);
        etAddress = findViewById(R.id.et_address);
        etPhoneIndic1 = findViewById(R.id.et_indicatif_tel1);
        etPhoneNumber1 = findViewById(R.id.et_phone_number1);
        etPhoneIndic2 = findViewById(R.id.et_indicatif_tel2);
        etPhoneNumber2 = findViewById(R.id.et_phone_number2);
        etEmail = findViewById(R.id.et_email);

        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    private void initViews() {
        //        TODO get the updated informations from database online

        switch (authenticatedUser.getRole()) {
            case User.ADMINISTRATOR :
                tvWork.setText("Administrateur");
                break;
            case User.MEDICAL_DELEGATE:
                tvWork.setText("Délégué médical");
                break;
            case User.MEDICAL_SUPERVISOR :
                tvWork.setText("Superviseur médical");
                break;
            case User.PHARMACEUTICAL_DELEGATE:
                tvWork.setText("Délégué pharmaceutique");
                break;
            case User.PHARMACEUTICAL_SUPERVISOR :
                tvWork.setText("Superviseur pharmaceutique");
                break;
        }

        try {
            tvNetwork.setText("Réseau - " + authenticatedUser.getNetworkName());
        } catch (NullPointerException e) {
            e.printStackTrace();
            tvNetwork.setText("Réseau - Non défini");

        }

        etFirstName.setText(authenticatedUser.getLastName());
        etLastName.setText(authenticatedUser.getName());
        etSex.setText((authenticatedUser.getSexe().equals("M")) ? "Homme" : (authenticatedUser.getSexe().equals("F")) ? "Femme" : "Non défini");
        etDayOfBirth.setText(authenticatedUser.getBirthday().split("-")[2]);
        etMonthOfBirth.setText(authenticatedUser.getBirthday().split("-")[1]);
        etYearOfBirth.setText(authenticatedUser.getBirthday().split("-")[0]);
        etMaritalStatus.setText(authenticatedUser.getMaritalStatus());

        if (etMaritalStatus.getText().toString().trim().isEmpty())
            etMaritalStatus.setText("Non défini");

        etAddress.setText(authenticatedUser.getActualCountryName());
        if (etAddress.getText().toString().trim().isEmpty())
            etAddress.setText("Non défini");

        if (authenticatedUser.getPhoneNumber().split(" ",2).length == 2) {
            etPhoneIndic1.setText(authenticatedUser.getPhoneNumber().split(" ", 2)[0]);
            etPhoneNumber1.setText(authenticatedUser.getPhoneNumber().split(" ", 2)[1]);

        } else {
            etPhoneIndic1.setVisibility(GONE);
            etPhoneNumber1.setText(authenticatedUser.getPhoneNumber());
            etPhoneNumber1.setHint("Téléphone 1");
        }

        if (authenticatedUser.getPhoneNumber2().split(" ", 2).length > 2) {
            etPhoneIndic2.setText(authenticatedUser.getPhoneNumber2().split(" ", 2)[0]);
            etPhoneNumber2.setText(authenticatedUser.getPhoneNumber2().split(" ", 2)[1]);
        } else {
            etPhoneIndic2.setVisibility(GONE);
            etPhoneNumber2.setText(authenticatedUser.getPhoneNumber2());
            etPhoneNumber2.setHint("Téléphone 2");
        }

        if (authenticatedUser.getPhoneNumber2() == null || authenticatedUser.getPhoneNumber2().trim().isEmpty()) {
            etPhoneIndic2.setVisibility(GONE);
            etPhoneNumber2.setVisibility(GONE);
        }

        etEmail.setText(authenticatedUser.getEmail());

        if (authenticatedUser.getProfilePicture() != null) {
            if (!authenticatedUser.getProfilePicture().isEmpty() && authenticatedUser.getProfilePicture().startsWith("https://")) {
                Glide.with(this).load(authenticatedUser.getProfilePicture()).into(imgProfil);
            }
        }
    }
}
