package com.jmaplus.pharmawine.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape1Fragment;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape2Fragment;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape3Fragment;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape4Fragment;
import com.jmaplus.pharmawine.fragments.rapport.VisiteInProgressFragment;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Center;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.models.DailyReportEnd;
import com.jmaplus.pharmawine.models.DailyReportEndResponse;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.CustomerCalls;
import com.jmaplus.pharmawine.utils.RetrofitCalls.DailyReportCalls;
import com.jmaplus.pharmawine.utils.RetrofitCalls.DailyReportEndCall;
import com.jmaplus.pharmawine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class VisiteInProgressActivity extends AppCompatActivity
        implements VisiteInProgressFragment.OnFragmentInteractionListener,
        ReportEtape1Fragment.OnFragmentInteractionListener,
        ReportEtape2Fragment.OnFragmentInteractionListener,
        ReportEtape3Fragment.OnFragmentInteractionListener,
        ReportEtape4Fragment.OnFragmentInteractionListener,
        CustomerCalls.Callbacks, DailyReportCalls.Callbacks,
        DailyReportEndCall.Callbacks {

    public static final Integer NUM_PAGES = 4;
    public static final int STEP_1_FRAGMENT_INDEX = 0;
    public static final int STEP_2_FRAGMENT_INDEX = 1;
    public static final int STEP_3_FRAGMENT_INDEX = 2;
    public static final int STEP_4_FRAGMENT_INDEX = 3;
    public static final String EXTRA_PROSPECT_TYPE = "prospectType";
    private static final String TAG = "VisiteActivity";
    private ProgressDialog dialog;


    private FragmentManager fragmentManager = getSupportFragmentManager();
    private VisiteInProgressFragment firstFragment;
    private PagerAdapter mPagerAdapter;
    private Context mContext;
    private ViewPager mViewPager;
    private View mRootContainer;
    private String prospectType = "";
    private LinearLayout headerReport;

    private Integer customerID = -1;
    private String customerName = "";

    private Integer currentReportID = -1;
    private DailyReportEnd mDailyReportEnd = new DailyReportEnd();
    private Customer mCustomer = new Customer();
    private List<Center> mCentersList = new ArrayList();
    private ReportEtape1Fragment mReportEtape1Fragment = new ReportEtape1Fragment();
    private ReportEtape2Fragment mReportEtape2Fragment = new ReportEtape2Fragment();
    private ReportEtape3Fragment mReportEtape3Fragment = new ReportEtape3Fragment();
    private ReportEtape4Fragment mReportEtape4Fragment = new ReportEtape4Fragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite_in_progress);

        setUI();

        mContext = this;
        prospectType = getIntent().getStringExtra(VisiteInProgressActivity.EXTRA_PROSPECT_TYPE);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Creating an instance of the fragment and its bundle
        firstFragment = new VisiteInProgressFragment();

        // Important
        customerID = getIntent().getIntExtra(Constants.CLIENT_ID_KEY, -1);
        customerName = getIntent().getStringExtra(Constants.CLIENT_FULLNAME_KEY);

        // Populating arguments bundle
        Bundle args = new Bundle();

        args.putString(VisiteInProgressFragment.ARGS_PROSPECT_TYPE, prospectType);
        args.putInt(VisiteInProgressFragment.ARGS_CLIENT_ID_KEY, customerID);
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_FIRSTNAME_KEY,
                getIntent().getStringExtra(Constants.CLIENT_FIRSTNAME_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_LASTNAME_KEY,
                getIntent().getStringExtra(Constants.CLIENT_LASTNAME_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_AVATAR_URL_KEY,
                getIntent().getStringExtra(Constants.CLIENT_AVATAR_URL_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_CUSTOMER_STATUS_KEY,
                getIntent().getStringExtra(Constants.CLIENT_CUSTOMER_STATUS_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_SPECIALITY_KEY,
                getIntent().getStringExtra(Constants.CLIENT_SPECIALITY_KEY));

        // Passing arguments to the fragment
        firstFragment.setArguments(args);

        Log.i(TAG, "First fragment arguments ==> " + args);

        // Adding the fragment to the activity
        fragmentTransaction.add(mRootContainer.getId(), firstFragment);
        fragmentTransaction.commit();

        // Fetching customer details again
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchCustomerDetails();
            }
        }, 1000);

    }

    private void setUI() {
        /**
         * The view pager should be invisible
         * until the onVisiteFinished() method is not executed
         * When it will be executed, we make the view pager visible
         */
        mRootContainer = findViewById(R.id.fragment_container_visite_in_progress);
        mViewPager = findViewById(R.id.view_pager_rapport_fragments_container);
        mViewPager.setVisibility(View.GONE);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Envoie du rapport en cours....");

    }

    private void fetchCustomerDetails() {
        CustomerCalls.getDetails(AuthUser.getToken(this), this, customerID);
    }

    @Override
    public void onCustomerDetailsResponse(@Nullable Customer customer) {

//        Toast.makeText(this, "Details fetched", Toast.LENGTH_LONG).show();
        Log.i(TAG, "onCustomerDetailsResponse: Customer details fetched");

        mCustomer = customer;
        firstFragment.updateViewsWithSource(customer);

        if (mCustomer.getCenters().size() > 0) {
            for (Center c : mCustomer.getCenters()) {
                mCentersList.add(c);
            }
        }

    }

    @Override
    public void onCustomerDetailsFailure() {
        Toast.makeText(this, "Failed to get customer centers", Toast.LENGTH_LONG).show();

    }


    @Override
    public void onEndDailyReportResponse(@Nullable DailyReportEndResponse response) {

        Toast.makeText(mContext, "Rapport envoyé", Toast.LENGTH_SHORT).show();

        confirmationDialogToEditProfile();

        dialog.cancel();
    }

    @Override
    public void onEndDailyReportFailure() {
        dialog.cancel();
        Toast.makeText(mContext, "Echec d'envoie du rapport. Reessayez !", Toast.LENGTH_SHORT).show();
    }

    private void showViewPager() {
        // Remove the visiteInProgressFragment

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(firstFragment);
        fragmentTransaction.commit();

        mViewPager.setVisibility(View.VISIBLE);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onVisiteEnded(Integer reportID, String EndTime) {
        /**
         * Lorsque l'utlisateur finit la visite
         * depuis VisiteInProgressFragment
         */
        currentReportID = reportID;
        mDailyReportEnd.setEndTime(EndTime);

        setTitle(customerName);

        showViewPager();

        Log.i(TAG, "onVisiteEnded: mDailyEnd ==> " + mDailyReportEnd);
    }

    @Override
    public void onRequestGetCenters() {
        if (mCentersList.isEmpty()) {
            CustomerCalls.getDetails(AuthUser.getToken(this), this, customerID);
        } else {
            mReportEtape1Fragment.populateCenters(mCentersList);
        }
    }

    @Override
    public void onPromeseUpdated(String updatedPromes) {
        mDailyReportEnd.setPromise(updatedPromes);
    }

    @Override
    public void onPurposeUpdated(String updatedPurposeOfTheVisit) {
        mDailyReportEnd.setGoal(updatedPurposeOfTheVisit);
    }

    @Override
    public void onPrescriptionUpdated(String updatedPrescription) {
        mDailyReportEnd.setPrescription(updatedPrescription);
    }

    @Override
    public void onStep1Finished(Center center) {
        mDailyReportEnd.setCenterId(center.getId());
        goToFragment(STEP_2_FRAGMENT_INDEX);
    }

    @Override
    public void onStep2Finished(String purposeOfTheVisit) {
        mDailyReportEnd.setGoal(purposeOfTheVisit);
        goToFragment(STEP_3_FRAGMENT_INDEX);
    }

    @Override
    public void onStep3Finished(String promesesHeld) {
        mDailyReportEnd.setPromise(promesesHeld);
        goToFragment(STEP_4_FRAGMENT_INDEX);
    }

    @Override
    public void onStep4Finished(String prescribedRequirements) {
        /**
         * Se produit juste avant d'envoyer le
         * Rapport de fin de visite au server
         */

        mDailyReportEnd.setPrescription(prescribedRequirements);
        mDailyReportEnd.setCustomerId(customerID);

        Log.i(TAG, "onStep4Finished: ==> " + mDailyReportEnd);

        if (mDailyReportEnd.isCompleted()) {

            sendReportTOTheServer();

        } else {
            Utils.presentToast(this,
                    getResources().getString(R.string.certaines_informations_sont_maquantes),
                    true);
        }
    }

    private void sendReportTOTheServer() {
        dialog.show();


        DailyReportEndCall.postDailyReportEnd(
                AuthUser.getToken(this),
                this, mDailyReportEnd, currentReportID);
//        try {
//
//        } catch (Exception e) {
//            Log.e(TAG, "sendReportTOTheServer: " + e.getMessage());
//            e.printStackTrace();
//            dialog.cancel();
//        }
    }


    @Override
    public void onReturnToStep1() {
        goToFragment(STEP_1_FRAGMENT_INDEX);
    }

    @Override
    public void onReturnToStep2() {
        goToFragment(STEP_2_FRAGMENT_INDEX);
    }

    /**
     * Switch to corresponding fragment index received
     *
     * @param fragmentIndex
     */
    private void goToFragment(Integer fragmentIndex) {
        try {
            mViewPager.setCurrentItem(fragmentIndex);
        } catch (Exception e) {
            Log.i(getLocalClassName(), "Error switching to fragment " + fragmentIndex);
            Log.e(getLocalClassName(), "Error message : " + e.getMessage());
        }
    }

    private void confirmationDialogToEditProfile() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.rapport_termine);
        builder.setMessage(R.string.voulez_vous_terminer_le_profilage_de_ce_client);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(mContext, EditMedicalTeamActivity.class);
                i.putExtra(EditMedicalTeamActivity.MEDICAL_ID_KEY, mCustomer.getId().toString());
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(i);
                finish();
            }
        });

        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        builder.show();
    }

    @Override
    public void onStartDailyReportResponse(@Nullable DailyReportStartResponse response) {

    }

    @Override
    public void onStartDailyReportFailure() {

    }

    @Override
    public void onKnownProspectResponse(@Nullable List<Customer> customers) {

    }

    @Override
    public void onKnownProspectFailure() {

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f;
            switch (position) {
                case STEP_1_FRAGMENT_INDEX:
                    f = mReportEtape1Fragment;
                    break;
                case STEP_2_FRAGMENT_INDEX:
                    f = mReportEtape2Fragment;
                    break;
                case STEP_3_FRAGMENT_INDEX:
                    f = mReportEtape3Fragment;
                    break;
                case STEP_4_FRAGMENT_INDEX:
                    f = mReportEtape4Fragment;
                    break;
                default:
                    f = mReportEtape1Fragment;
                    break;
            }
            // TODO: Update title

            return f;
        }


        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    public void headerVisibility(boolean hv) {
        if (hv) {
            headerReport.setVisibility(View.GONE);
        } else {
            headerReport.setVisibility(View.VISIBLE);
        }

    }
}
