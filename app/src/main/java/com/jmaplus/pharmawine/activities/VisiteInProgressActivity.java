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
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape5Fragment;
import com.jmaplus.pharmawine.fragments.rapport.VisiteInProgressFragment;
import com.jmaplus.pharmawine.models.Visite;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.Utils;

public class VisiteInProgressActivity extends AppCompatActivity
        implements VisiteInProgressFragment.OnFragmentInteractionListener,
        ReportEtape2Fragment.OnFragmentInteractionListener,
        ReportEtape3Fragment.OnFragmentInteractionListener,
        ReportEtape4Fragment.OnFragmentInteractionListener,
        ReportEtape5Fragment.OnFragmentInteractionListener {

    public static final Integer NUM_PAGES = 5;
    public static final int STEP_1_FRAGMENT_INDEX = 0;
    public static final int STEP_2_FRAGMENT_INDEX = 1;
    public static final int STEP_3_FRAGMENT_INDEX = 2;
    public static final int STEP_4_FRAGMENT_INDEX = 3;
    public static final int STEP_5_FRAGMENT_INDEX = 4;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private VisiteInProgressFragment firstFragment;
    private PagerAdapter mPagerAdapter;
    private Context mContext;
    private ViewPager mViewPager;
    private View mRootContainer;
    private Visite mVisite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite_in_progress);

        setUI();

        mContext = this;

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Creating an instance of the fragment and its bundle
        firstFragment = new VisiteInProgressFragment();
        Bundle args = new Bundle();

        // Populating arguments bundle
        args.putString(VisiteInProgressFragment.ARGS_PROSPECT_TYPE,
                getIntent().getStringExtra(VisiteInProgressFragment.ARGS_PROSPECT_TYPE));
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

        // Adding the fragment to the activity
        fragmentTransaction.add(mRootContainer.getId(), firstFragment);
        fragmentTransaction.commit();
    }

    private void setUI() {
        /**
         * The view pager should be invisible
         * until the onVisiteFinished() method is executed
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

        // Show the view pager
        mViewPager.setVisibility(View.VISIBLE);

        // Setting up the slide adapter
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onVisiteFinished(Visite v) {


        // TODO: Go to edit rapport activity and pass the client ID to it

        mVisite = v;

        Log.e(getLocalClassName(), v.toString());

        showViewPager();
    }

    @Override
    public void onStep2Finished(String zone) {
        mVisite.setZone(zone);
    }

    @Override
    public void onStep3Finished(String purposeOfTheVisit) {
        mVisite.setPurposeOfVisit(purposeOfTheVisit);
    }

    @Override
    public void onStep4Finished(String promesesHeld) {
        mVisite.setPromesesHeld(promesesHeld);
    }

    @Override
    public void onStep5Finished(String prescribedRequirements) {
        mVisite.setPrescribedRequirements(prescribedRequirements);

        if (mVisite.isCompleted()) {

            // TODO: Send rapport to the server using AsyncTask

            confirmationDialogToEditProfile();
        } else {
            Utils.presentToast(this,
                    getResources().getString(R.string.certaines_informations_sont_maquantes),
                    true);
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
                i.putExtra(EditMedicalTeamActivity.MEDICAL_ID_KEY, mVisite.getClient().getId());
                startActivity(i);
            }
        });

        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
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
                case STEP_5_FRAGMENT_INDEX:
                    f = new ReportEtape5Fragment();
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
