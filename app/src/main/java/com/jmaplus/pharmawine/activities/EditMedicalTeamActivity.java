package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.profilage.Step1MedicalTeamClientFragment;
import com.jmaplus.pharmawine.fragments.profilage.Step2MedicalTeamClientFragment;
import com.jmaplus.pharmawine.fragments.profilage.Step3MedicalTeamClientFragment;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.utils.Utils;

import org.jetbrains.annotations.NotNull;

public class EditMedicalTeamActivity extends AppCompatActivity implements
        View.OnClickListener,
        Step1MedicalTeamClientFragment.OnFragmentInteractionListener,
        Step2MedicalTeamClientFragment.OnFragmentInteractionListener,
        Step3MedicalTeamClientFragment.OnFragmentInteractionListener {

    public static final String MEDICAL_ID_KEY = "com.jmaplus.pharmawine.activities.medicalTeamId";
    public static final Integer NUM_PAGES = 3;
    public static final int STEP_1_FRAGMENT_INDEX = 0;
    public static final int STEP_2_FRAGMENT_INDEX = 1;
    public static final int STEP_3_FRAGMENT_INDEX = 2;

    private String medicalTeamId = "";
    private Client client;
    private Client changingInProgressClient;

    private Menu mMenu;
    private Button mSuivantBtn;
    private Button mProfilBtn;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medical_team);

        medicalTeamId = getIntent().getStringExtra(MEDICAL_ID_KEY);
        client = new Client();
        changingInProgressClient = new Client();

        // Setting up the action bar
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Binding views
        mSuivantBtn = findViewById(R.id.btn_suivant_activity_edit_medical);
        mProfilBtn = findViewById(R.id.btn_profil_activity_edit_medical);
        mPager = findViewById(R.id.fragment_container);

        // Setting up the slide adapter
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        getClientDetails();
    }

    private void getClientDetails() {
        client.setId(medicalTeamId);

        // TODO: fetch details
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
            int nextPosition = mPager.getCurrentItem();
            Utils.presentToast(this, "Going to step " + (nextPosition + 1), false);
            mPager.setCurrentItem(nextPosition, true);
        } else if (v.getId() == mProfilBtn.getId()) {
            // RAS
        }
    }

    @Override
    public void onReligionSelected(@NotNull String religion) {
        changingInProgressClient.setReligion(religion);
    }

    @Override
    public void onAddressEntered(@NotNull String address) {
        changingInProgressClient.setAddress(address);
    }

    @Override
    public void onAdresseEmailEntered(@NotNull String email) {
        changingInProgressClient.setEmail(email);
    }

    @Override
    public void onPhoneNumber2Entered(@NotNull String phoneNumber2) {
        changingInProgressClient.setPhoneNumber2(phoneNumber2);
    }

    @Override
    public void onBithdayFullyEntered(@NotNull String birthDay) {
        changingInProgressClient.setBirthday(birthDay);
    }

    @Override
    public void onNationalityChoosed(@NotNull String nationality) {
        changingInProgressClient.setNationality(nationality);
    }

    @Override
    public void onMartialStatusChoosed(@NotNull String maritalStatus) {
        changingInProgressClient.setMaritalStatus(maritalStatus);
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
