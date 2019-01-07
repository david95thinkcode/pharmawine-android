package com.jmaplus.pharmawine.activities;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.MedicalTeam;
import com.jmaplus.pharmawine.models.Pharmacy;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientDetailsActivity extends AppCompatActivity {

    public static final String CLIENT_MEDICAL_TEAM = "medical_team";
    public static final String CLIENT_PHARMACY = "pharmacy";

    public static final String CLIENT_ID_KEY = "com.jmaplus.pharmawine.activities.ClientDetailsActivity.clientId";
    public static final String CLIENT_TYPE_KEY = "com.jmaplus.pharmawine.activities.ClientDetailsActivity.clientType";
    private int clientId;
    private String clientType;

    private LinearLayout layMedicalInfos, layPharmacyInfos;
//    VIEWS SHARED

    private CircleImageView imgProfile;
    private TextView tvClientName, tvClientCategory, tvClientType, tvProfileProgress;
    private RoundCornerProgressBar profileProgress;
    private Button btnCall1, btnCall2;

    //    VIEWS FOR MEDICAL TEAM
    private TextView tvSexe, tvBirthday, tvNationality, tvMaritalStatus, tvBelieves, tvEmail, tvAddress;

    //    VIEWS FOR PHARMACY
    private TextView tvRepresentative, tvFounder, tvCreatedOn, tvNbEmployees, tvAnnexe, tvPharmaAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setSubtitle("Client");

        imgProfile = findViewById(R.id.img_profile_picture);
        tvClientName = findViewById(R.id.tv_i_client_name);
        tvClientCategory = findViewById(R.id.tv_i_client_category);
        tvClientType = findViewById(R.id.tv_i_client_type);
        tvProfileProgress = findViewById(R.id.tv_i_client_progress);
        profileProgress = findViewById(R.id.progress_client_i_filling);
        btnCall1 = findViewById(R.id.btn_call_1);
        btnCall2 = findViewById(R.id.btn_call_2);

        layMedicalInfos = findViewById(R.id.lay_medical_infos);
        layPharmacyInfos = findViewById(R.id.lay_pharmacy_infos);

        clientId = getIntent().getIntExtra(CLIENT_ID_KEY, -1);
        clientType = getIntent().getStringExtra(CLIENT_TYPE_KEY);

        if (clientId == -1 || clientType.isEmpty()) { // If values passed by intent are not correct
            finish();
        } else {

            if (clientType.equals(CLIENT_MEDICAL_TEAM)) {

//                Changes views configuration
                layMedicalInfos.setVisibility(View.VISIBLE);
                layPharmacyInfos.setVisibility(View.GONE);

                initViewsForMedicalTeam();
            }

            if (clientType.equals(CLIENT_PHARMACY)) {

//                Changes views configuration
                layMedicalInfos.setVisibility(View.GONE);
                layPharmacyInfos.setVisibility(View.VISIBLE);
                btnCall2.setVisibility(View.GONE);


                initViewsForPharmacy();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_edit:

                switch (clientType) {
                    case CLIENT_MEDICAL_TEAM :
                        startActivity(new Intent(this, EditMedicalTeamActivity.class).putExtra(EditMedicalTeamActivity.MEDICAL_ID_KEY, clientId));
                        break;
                    case CLIENT_PHARMACY:
                        startActivity(new Intent(this, EditPharmacyActivity.class).putExtra(EditPharmacyActivity.PHARMACY_ID_KEY, clientId));
                        break;
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViewsForMedicalTeam() {

        final MedicalTeam medicalTeam = MedicalTeam.getById(PharmaWine.mRealm, clientId);
        medicalTeam.load();

        if (medicalTeam.isValid()) {

            getSupportActionBar().setTitle(medicalTeam.getName().concat(" ").concat(medicalTeam.getLastName()));

            if (!medicalTeam.getProfilePicture().isEmpty()) {
                Glide.with(this).load(medicalTeam.getProfilePicture()).into(imgProfile);
            }

            tvClientName.setText(medicalTeam.getName().concat(" ").concat(medicalTeam.getLastName()));
            tvClientCategory.setText(medicalTeam.getCategory());
            profileProgress.setProgress(medicalTeam.getFillingLevel());
            tvProfileProgress.setText(String.valueOf(medicalTeam.getFillingLevel()) + " %");

            int fillingLevel = medicalTeam.getFillingLevel();
            if (fillingLevel < 35) {
                profileProgress.setProgressColor(getResources().getColor(R.color.red));
            } else if (fillingLevel < 69) {
                profileProgress.setProgressColor(getResources().getColor(R.color.orange));
            } else {
                profileProgress.setProgressColor(getResources().getColor(R.color.green));
            }

            tvSexe = findViewById(R.id.tv_i_client_sex);
            tvBirthday = findViewById(R.id.tv_i_client_birthday);
            tvNationality = findViewById(R.id.tv_i_client_nationality);
            tvMaritalStatus = findViewById(R.id.tv_i_client_marital_status);
            tvBelieves = findViewById(R.id.tv_i_client_believes);
            tvEmail = findViewById(R.id.tv_i_client_email);
            tvAddress = findViewById(R.id.tv_i_client_address);

            tvSexe.setText((medicalTeam.getSexe().equals("M")) ? "Homme" : (medicalTeam.getSexe().equals("F")) ? "Femme" : "-");
            tvBirthday.setText(medicalTeam.getBirthday().split("-")[1].concat("/").concat(medicalTeam.getBirthday().split("-")[2]).concat("/").concat(medicalTeam.getBirthday().split("-")[0]));
            tvNationality.setText(medicalTeam.getNationality());
            tvMaritalStatus.setText(medicalTeam.getMaritalStatus());
            tvEmail.setText(medicalTeam.getEmail());
            tvAddress.setText(medicalTeam.getAddress());

            if (!medicalTeam.getPhoneNumber().trim().isEmpty()) {
                btnCall1.setText(medicalTeam.getPhoneNumber());
                btnCall1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeCall(medicalTeam.getPhoneNumber().trim());
                    }
                });
            } else {
                btnCall1.setText(R.string.call);
                btnCall1.setEnabled(false);
                btnCall1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));

                if (!medicalTeam.getPhoneNumber2().trim().isEmpty()) {
                    btnCall1.setVisibility(View.GONE);
                }
            }

            if (!medicalTeam.getPhoneNumber2().trim().isEmpty()) {
                btnCall2.setText(medicalTeam.getPhoneNumber2());
                btnCall2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeCall(medicalTeam.getPhoneNumber2().trim());
                    }
                });
            } else {
                btnCall2.setVisibility(View.GONE);
            }

        } else {
            Toast.makeText(this, "Oops ! Un petit problème s'est passé. Veuillez réessayer", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initViewsForPharmacy() {

        final Pharmacy pharmacy = Pharmacy.getById(PharmaWine.mRealm, clientId);
        pharmacy.load();

        if (pharmacy.isValid()) {

            getSupportActionBar().setTitle(pharmacy.getName());

            Glide.with(this).load(R.drawable.pharma_icon).into(imgProfile);

            tvClientName.setText(pharmacy.getName());
            tvClientCategory.setVisibility(View.GONE);
            profileProgress.setProgress(pharmacy.getFillingLevel());
            tvProfileProgress.setText(String.valueOf(pharmacy.getFillingLevel()).concat(" %"));

            int fillingLevel = pharmacy.getFillingLevel();
            if (fillingLevel < 35) {
                profileProgress.setProgressColor(getResources().getColor(R.color.red));
            } else if (fillingLevel < 69) {
                profileProgress.setProgressColor(getResources().getColor(R.color.orange));
            } else {
                profileProgress.setProgressColor(getResources().getColor(R.color.green));
            }


            tvRepresentative = findViewById(R.id.tv_pharma_representative);
            tvFounder = findViewById(R.id.tv_pharma_founder);
            tvCreatedOn = findViewById(R.id.tv_pharma_created_on);
            tvNbEmployees = findViewById(R.id.tv_pharma_employees);
            tvAnnexe = findViewById(R.id.tv_pharma_annexe);
            tvPharmaAddress = findViewById(R.id.tv_pharma_address);

            tvRepresentative.setText(pharmacy.getRepresentative());
            tvFounder.setText(pharmacy.getFounder());
            tvCreatedOn.setText(pharmacy.getCreatedOn());
            tvNbEmployees.setText(String.valueOf(pharmacy.getEmployees()));
            tvAnnexe.setText(String.valueOf(pharmacy.getAnnexe()));
            tvPharmaAddress.setText(pharmacy.getAddress());

            if (!pharmacy.getContact().isEmpty()) {
                btnCall1.setText(pharmacy.getContact());
                btnCall1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeCall(pharmacy.getContact());
                    }
                });
            } else {
                btnCall1.setText(R.string.call);
                btnCall1.setEnabled(false);
                btnCall1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
            }

        } else {
            Toast.makeText(this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void makeCall(final String phoneNumber) {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        try {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ((!phoneNumber.startsWith("+")) ? String.valueOf("+").concat(phoneNumber) : phoneNumber))));
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ClientDetailsActivity.this, "Permission requise pour continuer !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        Toast.makeText(ClientDetailsActivity.this, "Accédez à PharmaWine dans Paramètres pour accorder la permission requise", Toast.LENGTH_LONG).show();
                    }
                }).check();
    }
}
