package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.dialogs.ProductDetailsFragmentDialog;
import com.jmaplus.pharmawine.fragments.products.ClassesFragment;
import com.jmaplus.pharmawine.fragments.products.LaboratoriesFragment;
import com.jmaplus.pharmawine.fragments.products.ReferencesFragment;
import com.jmaplus.pharmawine.models.ApiProduct;

public class ProductsActivity extends AppCompatActivity implements LaboratoriesFragment.OnFragmentInteractionListener {

    private Toolbar toolbar;
    public final String TAG = "ProductsActivity";

    private ViewPager viewPager;
    private MenuItem itemSearch, itemLabo, itemRef, itemClass;

    private String mCurrentFragment;
    private ApiProduct mSelectedProduct;

    public LaboratoriesFragment mLaboratoriesFragment = new LaboratoriesFragment();
    public ReferencesFragment mReferencesFragment = new ReferencesFragment();
    public ClassesFragment mClassesFragment = new ClassesFragment();

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

        setUI();

        showFragment(laboratoriesFragment);
    }

    private void setUI() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrentFragment = mLaboratoriesFragment.getClass().getSimpleName();
        mFragmentContainerLayout = findViewById(R.id.ly_prods_fragments_container);
        viewPager = findViewById(R.id.viewpager);
        bottomLay = findViewById(R.id.lay_bottom_lay);
        bottomIcon = findViewById(R.id.img_bottom_icon);
        bottomText = findViewById(R.id.tv_bottom_text);
    }


    @Override
    public void onProductNumberUpdated(Integer productsNumber) {

        String text = productsNumber + " produit(s)";

        updateBottomView(R.drawable.pill, text);
    }

    @Override
    public void onProductSelected(ApiProduct product) {
        Log.i(TAG, "onProductSelected: Product received ==> " + product.toString());
        mSelectedProduct = product;
        showProductDetailsDialog();
    }

    private void showProductDetailsDialog() {
        // Show the alert dialog for the product details
        FragmentManager fragmentManager = getSupportFragmentManager();
        ProductDetailsFragmentDialog detailDialog = new ProductDetailsFragmentDialog();

        detailDialog.updateDetails(mSelectedProduct);
        detailDialog.show(fragmentManager, "olo");
    }

    public void updateBottomView(int iconId, String text) {
        bottomIcon.setImageResource(iconId);
        bottomText.setText(text);
    }


    private void showFragment(Fragment fragment) {
        // TODO: Il y a des exception a chaque fois qu'il s'agit d'un autre fragment
        try {
            FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
            mFragmentTransaction.replace(mFragmentContainerLayout.getId(), fragment);
            mFragmentTransaction.commit();
            mFragment = fragment;

            Log.i(TAG, "showFragment: Fragment replaced by ==> " + fragment.getClass().getName());
        } catch (Exception e) {
            Log.e(TAG, "showFragment: Erreur ==> " + e.getMessage());
        }
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
                Toast.makeText(this, "Option indisponible", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, "Option indisponible", Toast.LENGTH_SHORT).show();

//                showFragment(referencesFragment);
                break;
            case R.id.action_class:
                Toast.makeText(this, "Option indisponible", Toast.LENGTH_SHORT).show();

//                showFragment(classesFragment);
                break;
            case android.R.id.home:
                finish();
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
