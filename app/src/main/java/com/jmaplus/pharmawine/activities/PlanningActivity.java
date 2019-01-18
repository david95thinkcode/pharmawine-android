package com.jmaplus.pharmawine.activities;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.planning.ActivitiesAreaFragment;
import com.jmaplus.pharmawine.fragments.planning.SalesGoalsFragment;
import com.jmaplus.pharmawine.fragments.planning.VisitFragment;
import com.jmaplus.pharmawine.utils.Constants;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlanningActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem itemSearch;
    private LinearLayout cvSelectDate;
    private TextView tvDateLabel;


    public static String VISIT = "com.jmaplus.pharmawine.activities.visites";
    public static String SALES_GOALS = "com.jmaplus.pharmawine.activities.sales_goals";
    public static String ACTIVITY_AREA = "com.jmaplus.pharmawine.activities.activity_area";

    private String mCurrentFragment;
    private int userRole;
    private SharedPreferences sharedPref;

    public VisitFragment mVisitFragment = new VisitFragment();
    public SalesGoalsFragment mSalesGoalsFragment = new SalesGoalsFragment();
    public ActivitiesAreaFragment mActAreaFragment = new ActivitiesAreaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        // Get user role from shared preferences
        sharedPref = this.getSharedPreferences(Constants.F_PROFIL,
                Context.MODE_PRIVATE);
        userRole = sharedPref.getInt(Constants.SP_ID_KEY, -1);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrentFragment = mVisitFragment.getClass().getSimpleName();
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tvDateLabel = findViewById(R.id.tv_planning_date);
        cvSelectDate = findViewById(R.id.cv_planning_visit_date);
        cvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment datePickerDialog = new CalendarDatePickerDialogFragment()

                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int month, int dayOfMonth) {
                                String[] frenchMonths = new DateFormatSymbols(Locale.FRENCH).getMonths();
                                tvDateLabel.setText(frenchMonths[month].concat(" ").
                                                concat(String.valueOf(year)));
                            }
                        });
                datePickerDialog.show(getSupportFragmentManager(), "DatePicker");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        itemSearch = menu.getItem(0);
        return true;
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Adding argument to Visite fragment
        Bundle visiteArgs = new Bundle();
        visiteArgs.putInt(VisitFragment.USER_ROLE_KEY, userRole);
        mVisitFragment.setArguments(visiteArgs);

        adapter.addFragment(mVisitFragment, "Visites");
        adapter.addFragment(mSalesGoalsFragment, "Obj. de ventes");
        adapter.addFragment(mActAreaFragment, "Zones d'act.");
        viewPager.setAdapter(adapter);
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
