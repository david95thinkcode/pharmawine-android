package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.RemainingClientsAdapter;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.utils.FakeData;

import java.util.ArrayList;
import java.util.List;

public class ProspectionActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mNewClient;
    private RecyclerView mRecyclerView;
    private RemainingClientsAdapter mAdapter;
    private List<Client> clientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospection);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindViewsAndInitilise();

        fetchRemainingClients();
    }

    private void bindViewsAndInitilise() {

        mNewClient = findViewById(R.id.lay_new_client);
        mRecyclerView = findViewById(R.id.rv_remaining_customers_prospection);
        mNewClient.setOnClickListener(this);

        // Initializations
        clientsList = new ArrayList();
        mAdapter = new RemainingClientsAdapter(this, clientsList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showNewClientView() {
        // TODO:

    }

    private void fetchRemainingClients() {
        // todo : use api datas

        // using fake data
        for (Client c : FakeData.getMedicalTeamClients()) {
            clientsList.add(c);
            mAdapter.notifyItemInserted(clientsList.size() - 1);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mNewClient.getId()) {
            showNewClientView();
        }
    }
}
