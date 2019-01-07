package com.jmaplus.pharmawine.activities;

import android.support.design.widget.Snackbar;
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
import com.jmaplus.pharmawine.adapters.LaboratoryAdapter;
import com.jmaplus.pharmawine.models.Laboratory;
import com.jmaplus.pharmawine.services.ApiClient;
import com.jmaplus.pharmawine.services.ApiInterface;
import com.jmaplus.pharmawine.utils.FakeData;
import com.jmaplus.pharmawine.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaboratoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;

    private ArrayList<Laboratory> laboratoryList, searchedLaboratoryList;
    private LaboratoryAdapter laboratoryAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratories);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        laboratoryList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_laboratories);
        mSwipeRefreshLayout = findViewById(R.id.swipe_laboratories);

        prefManager = new PrefManager(this);

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
                        searchLaboratory(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        searchLaboratory(s);
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
        laboratoryAdapter = new LaboratoryAdapter(laboratoryList, this);

//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(laboratoryAdapter);

//        Get the laboratory's list
        getLaboratoryList();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLaboratoryList();
            }
        });
    }

    private void getLaboratoryList() {
        laboratoryList.clear();
        laboratoryList.addAll(Laboratory.getAll(PharmaWine.mRealm));

//        Get the fresh laboratory's list if connected
        if (PharmaWine.getInstance().isOnline()) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<ArrayList<Laboratory>> call = apiService.getLaboratories(ApiClient.TOKEN_TYPE + prefManager.getToken());

            mSwipeRefreshLayout.setRefreshing(true);
            mSwipeRefreshLayout.setColorSchemeResources(R.color.orange);

            call.enqueue(new Callback<ArrayList<Laboratory>>() {
                @Override
                public void onResponse(Call<ArrayList<Laboratory>> call, Response<ArrayList<Laboratory>> response) {

                    if(!response.isSuccessful() || response.body() == null) {
                        Toast.makeText(LaboratoriesActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                    } else {

                        if(response.code() == 200) {

                            Laboratory.saveAll(PharmaWine.mRealm, response.body(), null);
                            laboratoryList.clear();
                            laboratoryList.addAll(response.body());

                            //        Notify changes
                            mSwipeRefreshLayout.setColorSchemeResources(R.color.green);
                            if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
                            laboratoryAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(LaboratoriesActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                            Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<Laboratory>> call, Throwable t) {
                    Toast.makeText(LaboratoriesActivity.this, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
        }

//        Notify changes
//        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        laboratoryAdapter.notifyDataSetChanged();
    }

    private void searchLaboratory(String query) {
        ArrayList<Laboratory> models = laboratoryList;
        ArrayList<Laboratory> filteredModelList = new ArrayList<>();

        if (!query.isEmpty()) {
            query = query.toLowerCase();
            for (Laboratory model : models) {
                final String text = model.getName().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(model);
                }
            }
        } else {
            filteredModelList = models;
        }
        laboratoryAdapter = new LaboratoryAdapter(filteredModelList, this);
        recyclerView.setAdapter(laboratoryAdapter);
    }
}
