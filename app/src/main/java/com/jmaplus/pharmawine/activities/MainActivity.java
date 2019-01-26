package com.jmaplus.pharmawine.activities;

import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.home.HomeFragment;
import com.jmaplus.pharmawine.fragments.home.MoreFragment;
import com.jmaplus.pharmawine.fragments.home.NotificationsFragment;
import com.jmaplus.pharmawine.fragments.home.ReportsFragment;
import com.jmaplus.pharmawine.models.AuthenticatedUser;

public class MainActivity extends AppCompatActivity {

    private AHBottomNavigation bottomNavigation;
    private FrameLayout mFrameContainer;
    private HomeFragment mHomeFragment = new HomeFragment();
    private ReportsFragment mReportsFragment = new ReportsFragment();
    private NotificationsFragment mNotificationsFragment = new NotificationsFragment();
    private MoreFragment mMoreFragment = new MoreFragment();
    private Fragment mFragment = null;

    private final int IND_NAV_HOME = 0;
    private final int IND_NAV_REPORTS = 1;
    private final int IND_NAV_NOTIFS = 2;
    private final int IND_NAV_CLIENT = 3;
    private final int IND_NAV_MORE = 4;


    private AuthenticatedUser authenticatedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigation = findViewById(R.id.bottom_navigation);
        setupBottomNavigation();

        mFrameContainer = findViewById(R.id.frame_container);

        showFragment(mHomeFragment);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.logo);
        getSupportActionBar().setIcon(R.drawable.logo);
        getSupportActionBar().setLogo(R.drawable.logo);

//
        authenticatedUser = AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm);

        Toast.makeText(this, "Content de vous revoir " + authenticatedUser.getLastName() + " !", Toast.LENGTH_LONG).show();


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
            case R.id.menu_messenger :
                startActivity(new Intent(MainActivity.this, MessengerActivity.class));
                break;
            case R.id.menu_settings :
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.menu_about :
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
        //bottomNavigation.setLayoutParams(new ActionBar.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, 65));
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case IND_NAV_HOME :
                        bottomNavigation.setBehaviorTranslationEnabled(false);
                        getSupportActionBar().setTitle(getResources().getString(R.string.nav_home_title));
                        showFragment(mHomeFragment);
                        break;
                    case IND_NAV_REPORTS :
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        getSupportActionBar().setTitle(getResources().getString(R.string.nav_reports_title));
                        showFragment(mReportsFragment);
                        break;
                    case IND_NAV_NOTIFS :
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        getSupportActionBar().setTitle(getResources().getString(R.string.menu_option_notifications));
                        showFragment(mNotificationsFragment);
                        break;
                    case IND_NAV_CLIENT:
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        startActivity(new Intent(MainActivity.this, ClientsActivity.class));

                        break;
                    case IND_NAV_MORE :
                        bottomNavigation.setBehaviorTranslationEnabled(true);
                        getSupportActionBar().setTitle(getResources().getString(R.string.nav_more_title));
                        showFragment(mMoreFragment);
                        break;
                }
                return true;
            }
        });
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.frame_container, fragment);
        mFragmentTransaction.commit();
        mFragment = fragment;
    }

    public void clicksHandler(View view) {
        mMoreFragment.clicksHandler(view);
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}
