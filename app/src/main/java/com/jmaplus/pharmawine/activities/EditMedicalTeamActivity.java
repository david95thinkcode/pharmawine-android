package com.jmaplus.pharmawine.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.profilage.Step1MedicalTeamClientFragment;
import com.jmaplus.pharmawine.fragments.profilage.Step2MedicalTeamClientFragment;
import com.jmaplus.pharmawine.fragments.profilage.Step3MedicalTeamClientFragment;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.CustomerCalls;
import com.jmaplus.pharmawine.utils.RetrofitCalls.ProfilageCalls;
import com.jmaplus.pharmawine.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditMedicalTeamActivity extends AppCompatActivity implements
        View.OnClickListener,
        Step1MedicalTeamClientFragment.OnFragmentInteractionListener,
        Step2MedicalTeamClientFragment.OnFragmentInteractionListener,
        Step3MedicalTeamClientFragment.OnFragmentInteractionListener,
        ProfilageCalls.Callbacks, CustomerCalls.Callbacks {

    public static final String MEDICAL_ID_KEY = "com.jmaplus.pharmawine.activities.medicalTeamId";
    public static final String TAG = "EditMedicalTeamActivity";
    public static final Integer NUM_PAGES = 3;
    public static final int STEP_1_FRAGMENT_INDEX = 0;
    public static final int STEP_2_FRAGMENT_INDEX = 1;
    public static final int STEP_3_FRAGMENT_INDEX = 2;

    private String medicalTeamId = "";
    private Client client;
    private Client changingInProgressClient;

    private Customer mCustomer;
    private Customer mChangingCustomer;

    private Menu mMenu;
    private Button mSuivantBtn;
    private Button mNextAdaptativeBtn;
    private Button mPrevAdaptativeBtn;
    private CircleImageView mPicture;
    private RoundCornerProgressBar mRoundCornerProgressBar;
    private TextView mProgressLabel;

    private Context mContext;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private String mToken = "";
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medical_team);

        medicalTeamId = getIntent().getStringExtra(MEDICAL_ID_KEY);

        client = new Client();
        changingInProgressClient = new Client();
        mContext = this;

        mToken = AuthUser.getToken(this);
        mCustomer = new Customer();
        mChangingCustomer = new Customer();

        initialiseUI();

        getClientDetails();
    }

    private void initialiseUI() {
        // Setting up the action bar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Binding views
        mPicture = findViewById(R.id.img_profile_picture);
        mRoundCornerProgressBar = findViewById(R.id.progress_client_i_filling);
        mProgressLabel = findViewById(R.id.tv_i_client_progress);
        mSuivantBtn = findViewById(R.id.btn_next_activity_edit_medical);
        mNextAdaptativeBtn = findViewById(R.id.btn_next_2_to_3_activity_edit_medical);
        mPrevAdaptativeBtn = findViewById(R.id.btn_prev_2_to_1_activity_edit_medical2);
        mPager = findViewById(R.id.fragment_container);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Mis a jour en cours....");
        mProgressDialog.setCancelable(false);

        // listenners
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == STEP_1_FRAGMENT_INDEX) {
                    showAdaptativeNavigationButtons(false);
                } else if (i == STEP_3_FRAGMENT_INDEX) {
                    showAdaptativeNavigationButtons(true);
                    mNextAdaptativeBtn.setText(R.string.terminer);
                } else {
                    mNextAdaptativeBtn.setText(R.string.suivant);
                    showAdaptativeNavigationButtons(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mSuivantBtn.setOnClickListener(this);
        mNextAdaptativeBtn.setOnClickListener(this);
        mPrevAdaptativeBtn.setOnClickListener(this);

        showAdaptativeNavigationButtons(false);

        // Setting up the slide adapter
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

    }

    private void showAdaptativeNavigationButtons(Boolean enableButtons) {
        if (enableButtons) {
            mSuivantBtn.setVisibility(View.GONE);
            mNextAdaptativeBtn.setVisibility(View.VISIBLE);
            mPrevAdaptativeBtn.setVisibility(View.VISIBLE);
        } else {
            mSuivantBtn.setVisibility(View.VISIBLE);
            mNextAdaptativeBtn.setVisibility(View.GONE);
            mPrevAdaptativeBtn.setVisibility(View.GONE);
        }
    }

    private void getClientDetails() {
        //client.setId(medicalTeamId);

        CustomerCalls.getDetails(mToken, this, mCustomer.getId());
    }

    // =================== Start retrofit calls callbacks =============================

    @Override
    public void onUpdatedCustomerResponse(@Nullable Customer updatedCustomer) {

        mProgressDialog.show();
        Toast.makeText(mContext, "Profil mis a jour avec success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onUpdatedCustomerFailure() {
        mProgressDialog.cancel();
    }

    @Override
    public void onCustomerDetailsResponse(@Nullable Customer customer) {
        if (customer != null)
            updateViewsWithCustomerDetails(customer);
    }

    @Override
    public void onCustomerDetailsFailure() {

    }

    @Override
    public void onKnownProspectResponse(@Nullable List<Customer> customers) {

    }

    @Override
    public void onKnownProspectFailure() {

    }

    // =================== End retrofit calls callbacks ==============================

    private void updateViewsWithCustomerDetails(Customer customer) {
        mCustomer = customer;
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.steps_title_menu, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mSuivantBtn.getId()) {
            // move to next fragment
            int nextPosition = mPager.getCurrentItem() + 1;

            if (mPager.getCurrentItem() < STEP_3_FRAGMENT_INDEX) {
                goToFragment(nextPosition);
                showAdaptativeNavigationButtons(true);

            } else {
                Utils.presentToast(this, "Avez-vous terminé ?", true);
            }
        }
        // 2 -> 3
        if (v.getId() == mNextAdaptativeBtn.getId() && mPager.getCurrentItem() == STEP_2_FRAGMENT_INDEX) {
            goToFragment(STEP_3_FRAGMENT_INDEX);
            showAdaptativeNavigationButtons(true);
        }
        // 2 -> 1
        else if (v.getId() == mPrevAdaptativeBtn.getId() && mPager.getCurrentItem() == STEP_2_FRAGMENT_INDEX) {
            goToFragment(STEP_1_FRAGMENT_INDEX);
            showAdaptativeNavigationButtons(false);
        }
        // 3 -> finish
        else if (v.getId() == mNextAdaptativeBtn.getId() && mPager.getCurrentItem() == STEP_3_FRAGMENT_INDEX) {
            onEditionFinished();
            mNextAdaptativeBtn.setText("Terminer");
        }
        // 3 -> 2
        else if (v.getId() == mPrevAdaptativeBtn.getId() && mPager.getCurrentItem() == STEP_3_FRAGMENT_INDEX) {
            goToFragment(STEP_2_FRAGMENT_INDEX);
            showAdaptativeNavigationButtons(true);
        }
    }


    private void onEditionFinished() {
        confirmationFinishDialog();
    }

    private void confirmationFinishDialog() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View customDialogBoxView = factory.inflate(R.layout.custom_dialog_box, null);
        final AlertDialog customDialog = new AlertDialog.Builder(this).create();
        customDialog.setView(customDialogBoxView);
        customDialog.setCancelable(false);
        TextView titleDialog = customDialog.findViewById(R.id.dialog_box_title);
        TextView msgDialog = customDialogBoxView.findViewById(R.id.dialog_box_content);
        msgDialog.setText(R.string.etes_vous_sur_d_avoir_termine);
        titleDialog.setVisibility(View.GONE);
        customDialogBoxView.findViewById(R.id.yes_button_custom_dialogbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileOnServer();
                finish();

            }
        });
        customDialogBoxView.findViewById(R.id.no_button_custom_dialogbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });

        customDialog.show();
    }

    private void updateProgressionBar() {

        Integer progression = changingInProgressClient.getFillingLevel();

        Log.i(TAG, "Progression ==> " + progression);

        mRoundCornerProgressBar.setProgress(progression);
        mProgressLabel.setText(String.valueOf(progression).concat(" %"));
    }

    private void updateProfileOnServer() {
        try {
            mProgressDialog.show();
            // Start a async task to update profile to server
            ProfilageCalls.editCustomerProfile(mToken, this, mCustomer.getId(), mChangingCustomer);

            Utils.presentToast(this, "Updating profile on the server", true);
            Log.i(TAG, changingInProgressClient.toString());
        } catch (Exception e) {
            e.printStackTrace();
            mProgressDialog.cancel();
        }

    }

    /**
     * Switch to corresponding fragment index received
     *
     * @param fragmentIndex
     */
    private void goToFragment(Integer fragmentIndex) {
        try {
            mPager.setCurrentItem(fragmentIndex);
        } catch (Exception e) {
            Log.i(getLocalClassName(), "Error switching to fragment " + fragmentIndex);
            Log.e(getLocalClassName(), "Error message : " + e.getMessage());
        }
    }

    @Override
    public void onReligionSelected(@NotNull String religion) {
        changingInProgressClient.setReligion(religion);
        updateProgressionBar();
    }

    @Override
    public void onAddressEntered(@NotNull String address) {
        changingInProgressClient.setAddress(address);
        updateProgressionBar();
    }

    @Override
    public void onAdresseEmailEntered(@NotNull String email) {
        changingInProgressClient.setEmail(email);
        updateProgressionBar();
    }

    @Override
    public void onPhoneNumber2Entered(@NotNull String phoneNumber2) {
        changingInProgressClient.setPhoneNumber2(phoneNumber2);
        updateProgressionBar();
    }

    @Override
    public void onBirthDayPartiallyUpdated(@NotNull String partialBirthday) {
        changingInProgressClient.setBirthday(partialBirthday);
        updateProgressionBar();
    }

    @Override
    public void onBithdayFullyUpdated(@NotNull String birthDay) {
        changingInProgressClient.setBirthday(birthDay);
        updateProgressionBar();
    }

    @Override
    public void onNationalityUpdated(@NotNull String nationality) {
        changingInProgressClient.setNationality(nationality);
        updateProgressionBar();
    }

    @Override
    public void onMartialStatusUpdated(@NotNull String maritalStatus) {
        changingInProgressClient.setMaritalStatus(maritalStatus);
        updateProgressionBar();
    }

    @Override
    public void onRemainingCustomersResponse(@Nullable List<Customer> customers) {

    }

    @Override
    public void onRemainingCustomersFailure() {

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
                    f = new Step1MedicalTeamClientFragment();
                    break;
                case STEP_2_FRAGMENT_INDEX:
                    f = new Step2MedicalTeamClientFragment();
                    break;
                case STEP_3_FRAGMENT_INDEX:
                    f = new Step3MedicalTeamClientFragment();
                    break;
                default:
                    f = new Step1MedicalTeamClientFragment();
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
