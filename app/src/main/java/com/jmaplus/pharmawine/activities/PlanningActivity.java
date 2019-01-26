package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.planning.ActivitiesAreaFragment;
import com.jmaplus.pharmawine.fragments.planning.SalesGoalsFragment;
import com.jmaplus.pharmawine.fragments.planning.VisitFragment;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.RetrofitCalls.DelegueCalls;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class PlanningActivity extends AppCompatActivity
        implements View.OnClickListener,
        DelegueCalls.Callbacks,
        VisitFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem itemSearch;
    private LinearLayout cvSelectDate;
    private TextView tvDateLabel;

    public static String VISIT = "com.jmaplus.pharmawine.activities.visites";
    public static String SALES_GOALS = "com.jmaplus.pharmawine.activities.sales_goals";
    public static String ACTIVITY_AREA = "com.jmaplus.pharmawine.activities.activity_area";
    public List<Customer> mCustomerList = new ArrayList();
    private String TAG = "PlanningActivity";
    private String mCurrentFragment;
    private int userRole;
    private AuthUser mAuthUser;
    private String mDate;
    private ViewPagerAdapter mPagerAdapter;
    public VisitFragment mVisitFragment = new VisitFragment();
    public SalesGoalsFragment mSalesGoalsFragment = new SalesGoalsFragment();
    public ActivitiesAreaFragment mActAreaFragment = new ActivitiesAreaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        mAuthUser = AuthUser.getAuthenticatedUser(this);
        mCurrentFragment = mVisitFragment.getClass().getSimpleName();

        initViews();
    }

    private void initViews() {

        // Binding views
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        tvDateLabel = findViewById(R.id.tv_planning_date);
        cvSelectDate = findViewById(R.id.cv_planning_visit_date);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        // Set listenners
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment datePickerDialog = new CalendarDatePickerDialogFragment()

                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int month, int dayOfMonth) {

                                int realMonthIndex = month + 1;
                                String date;
                                if (realMonthIndex < 10)
                                    date = year + "-0" + realMonthIndex + "-" + dayOfMonth;
                                else
                                    date = year + "-" + realMonthIndex + "-" + dayOfMonth;

                                String[] frenchMonths = new DateFormatSymbols(Locale.FRENCH).getMonths();

                                tvDateLabel.setText(frenchMonths[month].toUpperCase().concat(" ").
                                        concat(String.valueOf(year)));

                                Toast.makeText(PlanningActivity.this, "Date : " + date, Toast.LENGTH_SHORT).show();

                                // Very important
                                // We should inform visitefragment that date have changed
                                mVisitFragment.setDateString(date);
                            }
                        });
                datePickerDialog.show(getSupportFragmentManager(), "DatePicker");
            }
        });
    }

    // ================ CALLBACKS =============

    @Override
    public void onRequestUserRoleID() {
        int userRole = AuthUser.getRoleFromSharedPreferences(this);

        if (userRole != -1 && userRole > 0)
            mVisitFragment.setUserRoleIDFromSource(userRole);
        else
            Log.i(TAG, "onRequestUserRoleID: Le role de ce user n'est pas clair");
    }

    @Override
    public void onRequestPlanning(String startDate, String endDate) {
        DelegueCalls.getPlanning(AuthUser.getToken(this), this,
                mAuthUser.getId().toString(), startDate, endDate);
    }

    @Override
    public void onPlanningResponse(@Nullable List<Customer> customers) {
        mCustomerList = customers;
        mVisitFragment.populateCustomerListFromSource(mCustomerList);
    }

    @Override
    public void onPlanningFailure() {
        mVisitFragment.populateCustomerListFromSource(mCustomerList);
        Toast.makeText(this, R.string.erreur_de_recuperation_du_planning, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        itemSearch = menu.getItem(0);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_search:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        mPagerAdapter.addFragment(mVisitFragment, "Visites");
        mPagerAdapter.addFragment(mSalesGoalsFragment, "Obj. de ventes");
        mPagerAdapter.addFragment(mActAreaFragment, "Zones d'act.");
        viewPager.setAdapter(mPagerAdapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void removeFragment(int index) {
            mFragmentList.remove(index);
            mFragmentTitleList.remove(index);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
