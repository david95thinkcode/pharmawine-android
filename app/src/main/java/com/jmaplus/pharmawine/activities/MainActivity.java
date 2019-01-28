package com.jmaplus.pharmawine.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.firebase.database.FirebaseDatabase;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.clients.MedicalTeamFragment;
import com.jmaplus.pharmawine.fragments.home.HomeAdminFragment;
import com.jmaplus.pharmawine.fragments.home.HomeFragment;
import com.jmaplus.pharmawine.fragments.home.HomeSupervisorFragment;
import com.jmaplus.pharmawine.fragments.home.MoreFragment;
import com.jmaplus.pharmawine.fragments.home.NotificationsFragment;
import com.jmaplus.pharmawine.fragments.home.ReportsFragment;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.utils.Constants;

public class MainActivity extends AppCompatActivity implements MedicalTeamFragment.OnFragmentInteractionListener {
    public static final String TAG = "MainActivity";

    private AHBottomNavigation bottomNavigation;
    private FrameLayout mFrameContainer;

    public Integer mUserRole = -1;
    private HomeSupervisorFragment mHomeSupervisorFragment = new HomeSupervisorFragment();
    private HomeAdminFragment mHomeAdminFragment = new HomeAdminFragment();
    private ReportsFragment mReportsFragment = new ReportsFragment();
    private NotificationsFragment mNotificationsFragment = new NotificationsFragment();
    private MoreFragment mMoreFragment = new MoreFragment();
    private Fragment mHomeFragment = new Fragment();

    public boolean isMedicalTeamsLoaded = false;
    private Fragment mFragment = null;
    private MedicalTeamFragment mMedicalTeamFragment = new MedicalTeamFragment();

    private final int IND_NAV_HOME = 0;
    private final int IND_NAV_REPORTS = 1;
    private final int IND_NAV_NOTIFS = 2;
    private final int IND_NAV_CLIENT = 3;
    private final int IND_NAV_MORE = 4;

    private AuthUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // This allow firebase offline capabilities
            // Should stay here
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (com.google.firebase.database.DatabaseException e) {
            /**
             * Exception catched when Firebase set persistent is already called
             * This one happens when we exit the app using backPress and returned to it
             */

            Log.w(TAG, "${e.message}");
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

//
//        authenticatedUser = AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm);

        currentUser = AuthUser.getAuthenticatedUser(this);
        mUserRole = AuthUser.getRoleFromSharedPreferences(this);

        initViews();

        // Initialise view based oon user role
        switch (mUserRole) {
            case Constants.ROLE_ADMIN_KEY: {
                mHomeFragment = new HomeAdminFragment();
            }
            break;
            case Constants.ROLE_SUPERVISEUR_KEY: {
                mHomeFragment = new HomeSupervisorFragment();
            }
            break;
            case Constants.ROLE_DELEGUE_KEY: {
                mHomeFragment = new HomeFragment();
            }
            break;
            default: {
                mHomeFragment = new HomeFragment();
                Toast.makeText(this, "Attention ! Votre compte est suspect", Toast.LENGTH_SHORT).show();
            }
            break;
        }

        showFragment(mHomeFragment);

        Log.i(getLocalClassName(), "onCreate: authUser ==> " + currentUser);

        Toast.makeText(this, "Content de vous revoir " + currentUser.getLastname() + " !", Toast.LENGTH_LONG).show();

//        try {
//            DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setFirstDayOfWeek(Calendar.MONDAY);
//            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//
//            String[] days = new String[7];
//            for (int i = 0; i < 7; i++)
//            {
//                days[i] = format.format(calendar.getTime());
//                calendar.add(Calendar.DAY_OF_MONTH, 1);
//            }
//
//            Log.i(TAG, "onCreate: days ==> " + days);
//
//        } catch(Exception e) {
//            Log.e(TAG, "onCreate: " + e.getMessage() );
//            e.printStackTrace();
//            Toast.makeText(this, "Probleme recuperation jours de la semaine", Toast.LENGTH_SHORT).show();
//        }

        Log.i(TAG, "onCreate: token ==> " + AuthUser.getToken(this));

    }

    private void initViews() {
        bottomNavigation = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();

        mFrameContainer = findViewById(R.id.frame_container);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setLogo(R.drawable.logo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_messenger:
                startActivity(new Intent(MainActivity.this, MessagingActivity.class));
                break;
            case R.id.menu_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.menu_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }
        return true;
    }

    private void setupBottomNavigation() {
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.empty_string, R.drawable.home, R.color.nav_item_state_list));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.empty_string, R.drawable.file_document_box_multiple_outline, R.color.nav_item_state_list));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.empty_string, R.drawable.bell_ring, R.color.nav_item_state_list));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.empty_string, R.drawable.ic_client, R.color.nav_item_state_list));
        bottomNavigation.addItem(new AHBottomNavigationItem(R.string.empty_string, R.drawable.view_dashboard, R.color.nav_item_state_list));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case IND_NAV_HOME:
                        bottomNavigation.setBehaviorTranslationEnabled(false);
                        getSupportActionBar().setTitle(getResources().getString(R.string.nav_home_title));
                        showFragment(mHomeFragment);
                        break;
                    case IND_NAV_REPORTS:
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        getSupportActionBar().setTitle(getResources().getString(R.string.nav_reports_title));
                        showFragment(mReportsFragment);
                        break;
                    case IND_NAV_NOTIFS:
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        getSupportActionBar().setTitle(getResources().getString(R.string.menu_option_notifications));
                        showFragment(mNotificationsFragment);
                        break;
                    case IND_NAV_CLIENT:
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        //startActivity(new Intent(MainActivity.this, ClientsActivity.class));
                        getSupportActionBar().setTitle("Corps Méd.");
                        showFragment(mMedicalTeamFragment);
                        break;
                    case IND_NAV_MORE:
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        getSupportActionBar().setTitle(getResources().getString(R.string.nav_more_title));
                        showFragment(mMoreFragment);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void showFragment(Fragment fragment) {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, fragment);
        mFragmentTransaction.commit();
        mFragment = fragment;
    }

    public void clicksHandler(View view) {
        mMoreFragment.clicksHandler(view);
    }
}
