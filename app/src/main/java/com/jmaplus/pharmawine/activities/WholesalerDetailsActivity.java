package com.jmaplus.pharmawine.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.wholesaler.InformationsFragment;
import com.jmaplus.pharmawine.fragments.wholesaler.ProductManagedFragment;
import com.jmaplus.pharmawine.fragments.wholesaler.TelemarketerFragment;
import com.jmaplus.pharmawine.models.Wholesaler;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WholesalerDetailsActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static String PRODUCTS_MANAGED = "com.jmaplus.pharmawine.activities.wholesalersdetails.products_managed";
    public static String TELEMARKETER = "com.jmaplus.pharmawine.activities.wholesalersdetails.marketer";
    public static String INFORMATIONS = "com.jmaplus.pharmawine.activities.wholesalersdetails.informations";

    private String mCurrentFragment;

    public ProductManagedFragment mProductManagedFragment = new ProductManagedFragment();
    public TelemarketerFragment mTelemarketer = new TelemarketerFragment();
    public InformationsFragment mInformationsFragment = new InformationsFragment();

    public static String WHOLESALER_ID_KEY = "com.jmaplus.pharmawine.activities.WholesalerDetailsActivity.WHOLESALER_ID_KEY";
    private Integer wholesalerId;
    private Wholesaler wholesaler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesaler_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        TextView title, subTitle;
        title = mCustomView.findViewById(R.id.title_text);
        subTitle = mCustomView.findViewById(R.id.subtitle_text);
        ImageView icon = mCustomView.findViewById(R.id.actionbar_icon);
        CircleImageView imgLogo = mCustomView.findViewById(R.id.actionbar_logo);

        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        wholesalerId = getIntent().getIntExtra(WHOLESALER_ID_KEY, -1);
        wholesaler = Wholesaler.getWholesaler(PharmaWine.mRealm, wholesalerId);

        Glide.with(this).load(wholesaler.getLogo()).into(imgLogo);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (wholesaler != null) {
            Toast.makeText(this, "Grossiste : " + wholesaler.getName(), Toast.LENGTH_LONG).show();
            title.setText(wholesaler.getName());
            subTitle.setText(getResources().getString(R.string.wholesaler));
        } else {
            finish();
        }

        mCurrentFragment = mProductManagedFragment.getClass().getSimpleName();
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(mProductManagedFragment, "Produits gérés");
        adapter.addFragment(mTelemarketer, "Télémarkéteurs");
        adapter.addFragment(mInformationsFragment, "Informations");
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
