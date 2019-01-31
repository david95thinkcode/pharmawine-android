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
import com.jmaplus.pharmawine.fragments.clients.CustomersListFragment;
import com.jmaplus.pharmawine.fragments.clients.MedicalTeamDetailsFragment;
import com.jmaplus.pharmawine.fragments.clients.PharmacyDetailsFragment;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.RetrofitCalls.customers.CustomerDetailsCalls;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ClientDetailsActivity extends AppCompatActivity implements
        MedicalTeamDetailsFragment.OnFragmentInteractionListener,
        PharmacyDetailsFragment.OnFragmentInteractionListener,
        CustomersListFragment.OnFragmentInteractionListener,
        CustomerDetailsCalls.Callbacks {

    public static final String TAG = "ClientDetailsActivity";
    public static final String CLIENT_ID_EXTRA = "activities.ClientDetailsActivity.mCustomerId";
    public static final String CLIENT_NAME_EXTRA = "activities.ClientDetailsActivity.clientFullname";
    public static final String CLIENT_TYPE_EXTRA = "activities.ClientDetailsActivity.mCustomerTypeId";
    public static final String CLIENT_SEX_EXTRA = "activities.ClientDetailsActivity.clientSex";
    public static final String CUSTOMER_OBJECT_EXTRA = "activities.ClientDetailsActivity.fullobject";


    private String mToken;
    private Integer mCustomerId;
    private Integer mCustomerTypeId;
    private String mCustomerString;
    private String mCustomerName = "Chargement du nom en cours...";
    private MedicalTeamDetailsFragment mMedicalTeamDetailsFragment;
    private PharmacyDetailsFragment mPharmacyDetailsFragment;
    private Customer mCustomer = new Customer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        mCustomerId = getIntent().getIntExtra(CLIENT_ID_EXTRA, -1);
        mCustomerTypeId = getIntent().getIntExtra(CLIENT_TYPE_EXTRA, -1);
        mCustomerName = getIntent().getStringExtra(CLIENT_NAME_EXTRA);
        mCustomerString = getIntent().getStringExtra(CUSTOMER_OBJECT_EXTRA);

        setUI();

        if ((mCustomerId == -1) || (mCustomerTypeId == -1)) { // If values passed by intent are not correct
            Log.e(TAG, "onCreate: " + TAG + " automatically closes because some argument is missing");
            finish();
        } else {
            // Creating arguments to pass to fragment
            Bundle fragmentsArgs = new Bundle();
            fragmentsArgs.putString(CUSTOMER_OBJECT_EXTRA, mCustomerString);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (mCustomerTypeId == Constants.TYPE_MEDICAL_KEY) {
                mMedicalTeamDetailsFragment = new MedicalTeamDetailsFragment(); // Medical team client
                mMedicalTeamDetailsFragment.setArguments(fragmentsArgs);
                fragmentTransaction.add(R.id.client_detail_fragment_container, mMedicalTeamDetailsFragment);
                fragmentTransaction.commit();
            } else {
                mPharmacyDetailsFragment = new PharmacyDetailsFragment(); // Pharmacy client
                mPharmacyDetailsFragment.setArguments(fragmentsArgs);
                fragmentTransaction.add(R.id.client_detail_fragment_container, mPharmacyDetailsFragment);
                fragmentTransaction.commit();
            }
        }

        mToken = AuthUser.getToken(this); // important
        getCustomerDetailsOnline();
    }

    public void setUI() {
        setTitle(mCustomerName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setSubtitle("Client");
    }

    public void getCustomerDetailsOnline() {
        CustomerDetailsCalls.getDetails(mToken, this, mCustomerId);
    }

    @Override
    public void onCustomerDetailsResponse(@Nullable Customer customer) {
        mCustomer = customer;
        sendNewDataToFragment();
    }

    @Override
    public void onRequestCustomerObjectForUIInitialization() {
        mMedicalTeamDetailsFragment.updateCustomersWith(mCustomer);
    }

    @Override
    public void onCustomerDetailsFailure() {
        Log.e(TAG, "onCustomerDetailsFailure: ");
    }

    public void sendNewDataToFragment() {
        if (mCustomerTypeId == Constants.TYPE_MEDICAL_KEY) {

        } else {

        }
    }

    @Override
    public void onPhoneNumberCallInteraction(@NotNull String phoneNumber) {
        makeCall(phoneNumber);
    }

    @Override
    public void onClientDetailsReceived(@NotNull Client clientDetails) {
        // update UI Views With Client Details
//        getSupportActionBar().setTitle(clientDetails.getFullName());

        if (!clientDetails.getAvatar().isEmpty()) {
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
                Log.e(TAG, "Type : " + mCustomerTypeId);

                if (mCustomerTypeId == Constants.TYPE_MEDICAL_KEY) {
                    Log.e(TAG, "Opening edit page for client");

                    Intent i = new Intent(this, EditMedicalTeamActivity.class);
                    i.putExtra(EditMedicalTeamActivity.MEDICAL_ID_KEY, mCustomerId);
                    startActivity(i);

                } else if (mCustomerTypeId == Constants.TYPE_PHARMACEUTICAL_KEY) {
                    startActivity(new Intent(this, EditPharmacyActivity.class)
                            .putExtra(EditPharmacyActivity.PHARMACY_ID_KEY, mCustomerId));
                }
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestCustumerType() {

    }

    @Override
    public void onCustomerClicked(@NotNull Customer Customer) {

    }

    private void makeCall(final String phoneNumber) {

        final String tel = phoneNumber.trim();

        // TODO
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        try {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + tel));

                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivity(intent);
                            }
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ClientDetailsActivity.this, "Une erreur empeche le lancement de l'appel", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ClientDetailsActivity.this, "Permission requise pour continuer !", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest
                                                                           permission, PermissionToken token) {
                        Toast.makeText(ClientDetailsActivity.this, "Accédez à PharmaWine dans Paramètres pour accorder la permission requise", Toast.LENGTH_LONG).show();
                    }
                }).

                check();
    }
}
