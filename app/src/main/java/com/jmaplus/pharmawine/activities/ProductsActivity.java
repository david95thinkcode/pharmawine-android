package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.products.ClassesFragment;
import com.jmaplus.pharmawine.fragments.products.LaboratoriesFragment;
import com.jmaplus.pharmawine.fragments.products.ReferencesFragment;

public class ProductsActivity extends AppCompatActivity implements LaboratoriesFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    public final String TAG = "ProductsActivity";

    private ViewPager viewPager;
    private MenuItem itemSearch, itemLabo, itemRef, itemClass;

    private String mCurrentFragment;

    public LaboratoriesFragment mLaboratoriesFragment = new LaboratoriesFragment();
    public ReferencesFragment mReferencesFragment = new ReferencesFragment();
    public ClassesFragment mClassesFragment = new ClassesFragment();

    public boolean isProductsLoaded = false;
    public boolean isProdClassesLoaded = false;

    private LinearLayout bottomLay;
    private LinearLayout mFragmentContainerLayout;
    private ImageView bottomIcon;
    private TextView bottomText;

    private Fragment mFragment = null;
    private LaboratoriesFragment laboratoriesFragment = new LaboratoriesFragment();
    private ReferencesFragment referencesFragment = new ReferencesFragment();
    private ClassesFragment classesFragment = new ClassesFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrentFragment = mLaboratoriesFragment.getClass().getSimpleName();
        mFragmentContainerLayout = findViewById(R.id.ly_prods_fragments_container);
        viewPager = findViewById(R.id.viewpager);
        bottomLay = findViewById(R.id.lay_bottom_lay);
        bottomIcon = findViewById(R.id.img_bottom_icon);
        bottomText = findViewById(R.id.tv_bottom_text);

        showFragment(laboratoriesFragment);
    }

    @Override
    public void onProductNumberUpdated(Integer productsNumber) {

        String text = productsNumber + " produit(s)";

        updateBottomView(R.drawable.pill, text);
    }

    public void updateBottomView(int iconId, String text) {
        bottomIcon.setImageResource(iconId);
        bottomText.setText(text);
    }


    private void showFragment(Fragment fragment) {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(mFragmentContainerLayout.getId(), fragment);
        mFragmentTransaction.commit();
        mFragment = fragment;

        Log.i(TAG, "showFragment: Fragment replaced by ==> " + fragment.getClass().getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.products_menu, menu);
        itemSearch = menu.getItem(0);
        itemLabo = menu.getItem(1);
        itemRef = menu.getItem(2);
        itemClass = menu.getItem(3);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_labo:
                showFragment(laboratoriesFragment);
                break;
            case R.id.action_search:
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {

                        if (mCurrentFragment.equals(mLaboratoriesFragment.getClass().getSimpleName())) {
                            mLaboratoriesFragment.search(s);
                        } else if (mCurrentFragment.equals(mReferencesFragment.getClass().getSimpleName())) {
                            mReferencesFragment.search(s);
                        } else if (mCurrentFragment.equals(mClassesFragment.getClass().getSimpleName())) {
                            mClassesFragment.search(s);
                        }

                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        if(mCurrentFragment.equals(mLaboratoriesFragment.getClass().getSimpleName())) {
                            mLaboratoriesFragment.search(s);
                        } else if (mCurrentFragment.equals(mReferencesFragment.getClass().getSimpleName())) {
                            mReferencesFragment.search(s);
                        } else if (mCurrentFragment.equals(mClassesFragment.getClass().getSimpleName())) {
                            mClassesFragment.search(s);
                        }

                        return false;
                    }
                });
                break;
            case R.id.action_reference:
                showFragment(referencesFragment);
                break;
            case R.id.action_class:
                showFragment(classesFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//        adapter.addFragment(mLaboratoriesFragment, "Laboratoires");
//        adapter.addFragment(mReferencesFragment, "Références");
//        adapter.addFragment(mClassesFragment, "Classes");
//        viewPager.setAdapter(adapter);
//    }

//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        public ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        public void addFragment(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        public void removeFragment(int index) {
//            mFragmentList.remove(index);
//            mFragmentTitleList.remove(index);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//
//    }



}
