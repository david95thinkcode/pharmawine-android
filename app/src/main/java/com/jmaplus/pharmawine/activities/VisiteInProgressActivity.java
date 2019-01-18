package com.jmaplus.pharmawine.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape1Fragment;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape2Fragment;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape3Fragment;
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape4Fragment;
import com.jmaplus.pharmawine.fragments.rapport.VisiteInProgressFragment;
import com.jmaplus.pharmawine.models.Visite;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.Utils;

public class VisiteInProgressActivity extends AppCompatActivity
        implements VisiteInProgressFragment.OnFragmentInteractionListener,
        ReportEtape1Fragment.OnFragmentInteractionListener,
        ReportEtape2Fragment.OnFragmentInteractionListener,
        ReportEtape3Fragment.OnFragmentInteractionListener,
        ReportEtape4Fragment.OnFragmentInteractionListener {

    public static final Integer NUM_PAGES = 4;
    public static final int STEP_1_FRAGMENT_INDEX = 0;
    public static final int STEP_2_FRAGMENT_INDEX = 1;
    public static final int STEP_3_FRAGMENT_INDEX = 2;
    public static final int STEP_4_FRAGMENT_INDEX = 3;
    public static final String EXTRA_PROSPECT_TYPE = "prospectType";
    private static final String TAG = "VisiteInProgressActivity";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private VisiteInProgressFragment firstFragment;
    private PagerAdapter mPagerAdapter;
    private Context mContext;
    private ViewPager mViewPager;
    private View mRootContainer;
    private Visite mVisite;
    private String prospectType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite_in_progress);

        setUI();

        mContext = this;
        mVisite = new Visite();
        prospectType = getIntent().getStringExtra(VisiteInProgressActivity.EXTRA_PROSPECT_TYPE);

        Log.i(getLocalClassName(), "Prospect type extra ==> " + prospectType);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Creating an instance of the fragment and its bundle
        firstFragment = new VisiteInProgressFragment();

        // Populating arguments bundle
        Bundle args = new Bundle();

        args.putString(VisiteInProgressFragment.ARGS_PROSPECT_TYPE, prospectType);
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_ID_KEY,
                getIntent().getStringExtra(Constants.CLIENT_ID_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_FIRSTNAME_KEY,
                getIntent().getStringExtra(Constants.CLIENT_FIRSTNAME_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_LASTNAME_KEY,
                getIntent().getStringExtra(Constants.CLIENT_LASTNAME_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_AVATAR_UTL_KEY,
                getIntent().getStringExtra(Constants.CLIENT_AVATAR_URL_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_STATUS_KEY,
                getIntent().getStringExtra(Constants.CLIENT_STATUS_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_SPECIALITY_KEY,
                getIntent().getStringExtra(Constants.CLIENT_SPECIALITY_KEY));

        // Passing arguments to the fragment
        firstFragment.setArguments(args);

        Log.i(TAG, "First fragment arguments ==> " + args);

        // Adding the fragment to the activity
        fragmentTransaction.add(mRootContainer.getId(), firstFragment);
        fragmentTransaction.commit();
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
    public void onVisiteFinished(Visite v) {
        // TODO: Go to edit rapport activity and pass the client ID to it

        mVisite = v;

        setTitle(mVisite.getClient().getFullName());
        Log.i(getLocalClassName(), v.toString());

        showViewPager();
    }

    @Override
    public void onCenterUpdated(String updatedCenter) {
        mVisite.setCenter(updatedCenter);
    }

    @Override
    public void onPromeseUpdated(String updatedPromes) {
        mVisite.setPromesesHeld(updatedPromes);
    }

    @Override
    public void onPurposeUpdated(String updatedPurposeOfTheVisit) {
        mVisite.setPurposeOfVisit(updatedPurposeOfTheVisit);
    }

    @Override
    public void onPrescriptionUpdated(String updatedPrescription) {
        mVisite.setPrescribedRequirements(updatedPrescription);
    }

    @Override
    public void onStep1Finished(String center) {
        mVisite.setCenter(center);
        goToFragment(STEP_2_FRAGMENT_INDEX);
    }

    @Override
    public void onStep2Finished(String purposeOfTheVisit) {
        // TODO: Get zone from center ID from API
        // Here am using an mock data for zone
        mVisite.setZone("Une zone contenue dans les donnees recue de l'API");

        mVisite.setPurposeOfVisit(purposeOfTheVisit);
        goToFragment(STEP_3_FRAGMENT_INDEX);
    }

    @Override
    public void onStep3Finished(String promesesHeld) {
        mVisite.setPromesesHeld(promesesHeld);
        goToFragment(STEP_4_FRAGMENT_INDEX);
    }

    @Override
    public void onStep4Finished(String prescribedRequirements) {
        mVisite.setPrescribedRequirements(prescribedRequirements);

        if (mVisite.isCompleted()) {
            /**
             * Here we have to :
             * - TODO: Send rapport to the server using AsyncTask
             * - Shows confirmation to edit client profile
             */

            Utils.presentToast(this, "Sending report to server...", false);

            logVisiteObject();

            confirmationDialogToEditProfile();
        } else {
            logVisiteObject();

            Utils.presentToast(this,
                    getResources().getString(R.string.certaines_informations_sont_maquantes),
                    true);
        }
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
        logVisiteObject();
        try {
            mViewPager.setCurrentItem(fragmentIndex);
        } catch (Exception e) {
            Log.i(getLocalClassName(), "Error switching to fragment " + fragmentIndex);
            Log.e(getLocalClassName(), "Error message : " + e.getMessage());
        }
    }

    private void logVisiteObject() {
        Log.i(getLocalClassName(), "================");
        Log.i(getLocalClassName(), mVisite.toString());
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
                i.putExtra(EditMedicalTeamActivity.MEDICAL_ID_KEY, mVisite.getClient().getId());
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


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f;
            switch (position) {
                case STEP_1_FRAGMENT_INDEX:
                    f = new ReportEtape1Fragment();
                    break;
                case STEP_2_FRAGMENT_INDEX:
                    f = new ReportEtape2Fragment();
                    break;
                case STEP_3_FRAGMENT_INDEX:
                    f = new ReportEtape3Fragment();
                    break;
                case STEP_4_FRAGMENT_INDEX:
                    f = new ReportEtape4Fragment();
                    break;
                default:
                    f = new ReportEtape1Fragment();
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
}
