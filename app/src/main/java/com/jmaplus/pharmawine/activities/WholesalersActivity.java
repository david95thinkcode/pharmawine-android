package com.jmaplus.pharmawine.activities;

import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.WholesalerAdapter;
import com.jmaplus.pharmawine.models.Wholesaler;
import com.jmaplus.pharmawine.utils.FakeData;

import java.util.ArrayList;

public class WholesalersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private ArrayList<Wholesaler> wholesalerList, searchedWholesalerList;
    private WholesalerAdapter wholesalerAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wholesalers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        wholesalerList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_wholesalers);
        mSwipeRefreshLayout = findViewById(R.id.swipe_wholesalers);

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
                        searchWholesaler(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        searchWholesaler(s);
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
        wholesalerAdapter = new WholesalerAdapter(wholesalerList, this);

//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(wholesalerAdapter);

//        Get the wholesalers's list
        getWholesalerList();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWholesalerList();
            }
        });
    }

    private void getWholesalerList() {
        wholesalerList.clear();
        wholesalerList.addAll(Wholesaler.getAll(PharmaWine.mRealm));

//        Get the fresh wholesaler's list if connected
        if (PharmaWine.getInstance().isOnline()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);

//                Get wholesalers and save them
                    ArrayList<Wholesaler> wholesalers = FakeData.getWholesalers();
                    wholesalerList.clear();
                    wholesalerList.addAll(wholesalers);
                    Wholesaler.saveAll(PharmaWine.mRealm, wholesalers, null);
                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
        }

//        Notify changes
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        wholesalerAdapter.notifyDataSetChanged();
    }

    private void searchWholesaler(String query) {
        ArrayList<Wholesaler> models = wholesalerList;
        ArrayList<Wholesaler> filteredModelList = new ArrayList<>();

        if (!query.isEmpty()) {
            query = query.toLowerCase();
            for (Wholesaler model : models) {
                final String text = model.getName().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        } else {
            filteredModelList = models;
        }
        wholesalerAdapter = new WholesalerAdapter(filteredModelList, this);
        recyclerView.setAdapter(wholesalerAdapter);
    }
}
