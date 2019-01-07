package com.jmaplus.pharmawine.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.GiftAdapter;
import com.jmaplus.pharmawine.models.Gift;
import com.jmaplus.pharmawine.models.MedicalTeam;
import com.jmaplus.pharmawine.models.Pharmacy;
import com.jmaplus.pharmawine.utils.FakeData;

import java.util.ArrayList;

import io.realm.Realm;

public class GiftListActivity extends AppCompatActivity {

    private String GIFT_TYPE;

    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private ArrayList<Gift> giftList;
    private GiftAdapter giftAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.close));

        if (getIntent().getBooleanExtra(Gift.MEDICAL_TEAM, false)) {
            getSupportActionBar().setSubtitle(getResources().getString(R.string.medical_teams));
            GIFT_TYPE = Gift.MEDICAL_TEAM;
        } else if (getIntent().getBooleanExtra(Gift.HOSPITAL, false)) {
            getSupportActionBar().setSubtitle(getResources().getString(R.string.hospitals));
            GIFT_TYPE = Gift.HOSPITAL;
        } else if (getIntent().getBooleanExtra(Gift.PHARMACY, false)) {
            getSupportActionBar().setSubtitle(getResources().getString(R.string.pharmacies));
            GIFT_TYPE = Gift.PHARMACY;
        } else finish();


        giftList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_gifts);
        mSwipeRefreshLayout = findViewById(R.id.swipe_gifts);

        initList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

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
                        searchGift(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        searchGift(s);
                        return false;
                    }
                });
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initList() {

//        Init the adapter
        giftAdapter = new GiftAdapter(giftList, this, this.GIFT_TYPE);

//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(giftAdapter);

//        Get the gifts's list
        getGiftList();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGiftList();
            }
        });
    }

    private void getGiftList() {
        giftList.clear();
        giftList.addAll(Gift.getAll(PharmaWine.mRealm));

//        Get the fresh gifts's list if connected
        if (PharmaWine.getInstance().isOnline()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);


//                Get gifts and save them
                    final ArrayList<Gift> gifts = FakeData.getGifts();
                    Gift.saveAll(PharmaWine.mRealm, gifts);
                    giftList.clear();
                    giftList.addAll(gifts);

                    switch (GIFT_TYPE) {
                        case Gift.MEDICAL_TEAM:

                            ArrayList<MedicalTeam> medicalTeams = new ArrayList<>();
                            MedicalTeam.saveAll(PharmaWine.mRealm, medicalTeams, null);


                            break;
                        case Gift.PHARMACY:
//                            Pharmacy.saveAll(PharmaWine.mRealm, FakeData.getPharmacies());
                            break;
                    }
                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
        }

        // Notify changes
        if (mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(false);
        giftAdapter.notifyDataSetChanged();
    }

    private void searchGift(String query) {
        ArrayList<Gift> models = giftList;
        ArrayList<Gift> filteredModelList = new ArrayList<>();

        if (!query.isEmpty()) {
            query = query.toLowerCase();
            for (Gift model : models) {
                final String text = model.getLabel().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        } else {
            filteredModelList = models;
        }
        giftAdapter = new GiftAdapter(filteredModelList, this, this.GIFT_TYPE);
        recyclerView.setAdapter(giftAdapter);
    }
}
