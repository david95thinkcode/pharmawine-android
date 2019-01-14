package com.jmaplus.pharmawine.activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.clients.MedicalTeamDetailsFragment;
import com.jmaplus.pharmawine.fragments.clients.PharmacyDetailsFragment;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.utils.Constants;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

public class ClientDetailsActivity extends AppCompatActivity implements
        MedicalTeamDetailsFragment.OnFragmentInteractionListener,
        PharmacyDetailsFragment.OnFragmentInteractionListener {

    public static final String CLIENT_ID_KEY = "com.jmaplus.pharmawine.activities.ClientDetailsActivity.clientId";
    public static final String CLIENT_TYPE_KEY = "com.jmaplus.pharmawine.activities.ClientDetailsActivity.clientType";
    public static final String TAG = "ClientDetailsActivity";

    private String clientId;
    private String clientType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        setUI();

        clientId = getIntent().getStringExtra(CLIENT_ID_KEY);
        clientType = getIntent().getStringExtra(CLIENT_TYPE_KEY);

        if (clientId.isEmpty() || clientType.isEmpty()) { // If values passed by intent are not correct
            finish();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (clientType.equals(Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY)) {
                // Medical team client
                MedicalTeamDetailsFragment fragment = new MedicalTeamDetailsFragment();
                Log.i(TAG, "Client is medical");
                fragmentTransaction.add(R.id.client_detail_fragment_container, fragment);
                fragmentTransaction.commit();
            }

            if (clientType.equals(Constants.CLIENT_PHARMACY_TYPE_KEY)) {
                // Pharmacy client
                PharmacyDetailsFragment fragment = new PharmacyDetailsFragment();
                fragmentTransaction.add(R.id.client_detail_fragment_container, fragment);
                fragmentTransaction.commit();
            }
        }
    }

    public void setUI() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setSubtitle("Client");
    }

    @Override
    public void onPhoneNumberCallInteraction(@NotNull String phoneNumber) {
        makeCall(phoneNumber);
    }

    @Override
    public void onClientDetailsReceived(@NotNull Client clientDetails) {
        // update UI Views With Client Details
        getSupportActionBar().setTitle(clientDetails.getFullName());

        if (!clientDetails.getAvatarUrl().isEmpty()) {
            //TODO: find a way to set icon with user avatar url
            getSupportActionBar().setIcon(clientDetails.getDefaultAvatarUrl());
        } else {
            getSupportActionBar().setIcon(clientDetails.getDefaultAvatarUrl());
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
                Log.e(TAG, "Type : " + clientType);

                if (clientType.equals(Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY)) {
                    Log.e(TAG, "Opening edit page for client");

                    Intent i = new Intent(this, EditMedicalTeamActivity.class);
                    i.putExtra(EditMedicalTeamActivity.MEDICAL_ID_KEY, clientId);
                    startActivity(i);

                } else if (clientType == Constants.CLIENT_PHARMACY_TYPE_KEY) {
                    startActivity(new Intent(this, EditPharmacyActivity.class)
                            .putExtra(EditPharmacyActivity.PHARMACY_ID_KEY, clientId));
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeCall(final String phoneNumber) {

        // TODO
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        try {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("phoneNumber:" + ((!phoneNumber.startsWith("+")) ? String.valueOf("+").concat(phoneNumber) : phoneNumber))));
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
