package com.jmaplus.pharmawine.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.profilage.Step1MedicalTeamClientFragment;
import com.jmaplus.pharmawine.fragments.profilage.Step2MedicalTeamClientFragment;
import com.jmaplus.pharmawine.fragments.profilage.Step3MedicalTeamClientFragment;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.RetrofitCalls.customers.CustomerDetailsCalls;
import com.jmaplus.pharmawine.utils.RetrofitCalls.customers.CustomerEditionCalls;
import com.jmaplus.pharmawine.utils.Utils;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditMedicalTeamActivity extends AppCompatActivity implements
        View.OnClickListener,
        Step1MedicalTeamClientFragment.OnFragmentInteractionListener,
        Step2MedicalTeamClientFragment.OnFragmentInteractionListener,
        Step3MedicalTeamClientFragment.OnFragmentInteractionListener,
        CustomerEditionCalls.Callbacks, CustomerDetailsCalls.Callbacks {

    public static final String TAG = "EditMedicalTeamActivity";
    public static final String CUSTOMER_JSON_EXTRA = "customer_json";
    public static final String CUSTOMER_ID_EXTRA = "com.mCustomerId";
    public static final Integer NUM_PAGES = 3;
    public static final int STEP_1_FRAGMENT_INDEX = 0;
    public static final int STEP_2_FRAGMENT_INDEX = 1;
    public static final int STEP_3_FRAGMENT_INDEX = 2;

    private Integer mCustomerId = -1;
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

        mContext = this;
        mToken = AuthUser.getToken(this);
        mCustomerId = getIntent().getIntExtra(CUSTOMER_ID_EXTRA, -1);

        // Getting customer full details from intent string extra
        String mCustomerString = getIntent().getStringExtra(CUSTOMER_JSON_EXTRA);
        Gson gson = new Gson();
        mCustomer = gson.fromJson(mCustomerString, Customer.class);

        initialiseUI();

        if (mCustomer == null) {
            // the activity caller have not set customer json extra
            // So we have to fetch the customers details from server
            mCustomer = new Customer();
            mChangingCustomer = new Customer();
            getClientDetails();
        } else {
            Log.i(TAG, "Customer received ==> " + mCustomer.toString());
            updateViewsWithCustomerDetails(mCustomer);
        }
    }

    private void updateAvatarView() {
        if (mCustomer.getCustomerTypeId() == Constants.TYPE_MEDICAL_KEY) {
            // Medical team customer
            if (mCustomer.getSex() != null && mCustomer.getSex().toUpperCase().equals("F")) {
                Glide.with(this).load(R.drawable.bg_doctor_woman).into(mPicture);
            }
        } else {
            // Here is pharmacy case
            Glide.with(this).load(R.drawable.bg_avatar_pharmacy).into(mPicture);
        }
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
        CustomerDetailsCalls.getDetails(mToken, this, mCustomer.getId());
    }

    // =================== Start retrofit calls callbacks =============================

    @Override
    public void onCustomerEditionResponse(@Nullable Customer customer) {
        mProgressDialog.show();
        Toast.makeText(mContext, "Profil mis a jour avec success", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCustomerEditionFailure() {
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

    // =================== End retrofit calls callbacks ==============================

    private void updateViewsWithCustomerDetails(Customer customer) {
        mCustomer = customer;
        mChangingCustomer = mCustomer;

        updateAvatarView();

        // Todo: send data to fragments
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.etes_vous_sur_d_avoir_termine);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateProfileOnServer();
                finish();
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

    private void updateProgressionBar() {

        Integer progression = mChangingCustomer.getFillingLevel();

        Log.i(TAG, "Progression ==> " + progression);

        mRoundCornerProgressBar.setProgress(progression);
        mProgressLabel.setText(String.valueOf(progression).concat(" %"));
    }

    private void updateProfileOnServer() {
        try {
            mProgressDialog.show();
            Log.i(TAG, "Updating profile on the server");
            Log.i(TAG, mChangingCustomer.toString());
            // Start a async task to update profile to server
            CustomerEditionCalls.editCustomerProfile(mToken, this, mCustomer.getId(), mChangingCustomer);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
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
        mChangingCustomer.setReligion(religion);
        updateProgressionBar();
    }

    @Override
    public void onAddressEntered(@NotNull String address) {
        mChangingCustomer.setAddress(address);
        updateProgressionBar();
    }

    @Override
    public void onAdresseEmailEntered(@NotNull String email) {
        mChangingCustomer.setEmail(email);
        updateProgressionBar();
    }

    @Override
    public void onPhoneNumber2Entered(@NotNull String phoneNumber2) {
        mChangingCustomer.setPhoneNumber2(phoneNumber2);
        updateProgressionBar();
    }

    @Override
    public void onBirthDayPartiallyUpdated(@NotNull String partialBirthday) {
        mChangingCustomer.setBirthday(partialBirthday);
        updateProgressionBar();
    }

    @Override
    public void onBithdayFullyUpdated(@NotNull String birthDay) {
        mChangingCustomer.setBirthday(birthDay);
        updateProgressionBar();
    }

    @Override
    public void onNationalityUpdated(@NotNull String nationality) {
        mChangingCustomer.setNationality(nationality);
        updateProgressionBar();
    }

    @Override
    public void onMartialStatusUpdated(@NotNull String maritalStatus) {
        mChangingCustomer.setMaritalStatus(maritalStatus);
        updateProgressionBar();
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
