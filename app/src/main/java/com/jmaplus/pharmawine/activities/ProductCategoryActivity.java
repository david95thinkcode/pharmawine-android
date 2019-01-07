package com.jmaplus.pharmawine.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.productCategory.LaboratoriesFragment;
import com.jmaplus.pharmawine.fragments.productCategory.ReferencesFragment;
import com.jmaplus.pharmawine.models.AuthenticatedUser;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem itemSearch;

    public static String LABORATORIES = "com.jmaplus.pharmawine.activities.ProductCategoryActivity.laboratories";
    public static String REFERENCES = "com.jmaplus.pharmawine.activities.ProductCategoryActivity.references";


    private String mCurrentFragment;

    public LaboratoriesFragment mLaboratoriesFragment = new LaboratoriesFragment();
    public ReferencesFragment mReferencesFragment = new ReferencesFragment();

    private LinearLayout bottomLay;
    private ImageView bottomIcon;
    private TextView bottomText;

    public static final String PRODUCT_CATEGORY_KEY = "com.jmaplus.pharmawine.activities.ProductCategoryActivity.productCategoryKey";
    public String selectedProductCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);

        selectedProductCategory = getIntent().getStringExtra(PRODUCT_CATEGORY_KEY);

        if(selectedProductCategory.isEmpty()) {
            finish();
        } else {

            AuthenticatedUser user = AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm);

            getSupportActionBar().setTitle(selectedProductCategory);
            getSupportActionBar().setSubtitle(user.getName().concat(" ").concat(user.getLastName()).concat(" - Produits"));
        }

        mCurrentFragment = mLaboratoriesFragment.getClass().getSimpleName();
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        bottomLay = findViewById(R.id.lay_bottom_lay);
        bottomIcon = findViewById(R.id.img_bottom_icon);
        bottomText = findViewById(R.id.tv_bottom_text);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                bottomLay.setVisibility(View.VISIBLE);

                switch (i) {
                    case 0:
                        mCurrentFragment = mLaboratoriesFragment.getClass().getSimpleName();
                        break;
                    case 1:
                        mCurrentFragment = mReferencesFragment.getClass().getSimpleName();

//                        Hide bottom layout for items counts
                        bottomLay.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

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

                        if(mCurrentFragment.equals(mLaboratoriesFragment.getClass().getSimpleName())) {
                            mLaboratoriesFragment.search(s);
                        } else if (mCurrentFragment.equals(mReferencesFragment.getClass().getSimpleName())) {
                            mReferencesFragment.search(s);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        if(mCurrentFragment.equals(mLaboratoriesFragment.getClass().getSimpleName())) {
                            mLaboratoriesFragment.search(s);
                        } else if (mCurrentFragment.equals(mReferencesFragment.getClass().getSimpleName())) {
                            mReferencesFragment.search(s);
                        }

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

        adapter.addFragment(mLaboratoriesFragment, "Laboratoires");
        adapter.addFragment(mReferencesFragment, "Références");
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

    public void updateBottomView(int iconId, String text) {
        bottomIcon.setImageResource(iconId);
        bottomText.setText(text);
    }
}
