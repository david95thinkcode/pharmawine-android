package com.jmaplus.pharmawine.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.RemainingClientsAdapter;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.FakeData;
import com.jmaplus.pharmawine.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class ProspectionActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "ProspectionActivity";
    private static String VISITE_MESSAGE = "Êtes-vous sur le point de commencer une visite chez le medecin ";

    private Context mContext;
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

        configureOnClickRecyclerView();
    }

    private void bindViewsAndInitilise() {
        mContext = this;
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
        Intent i = new Intent(this, TranslucentNewClientActivity.class);
        startActivity(i);
    }

    private void fetchRemainingClients() {
        // todo : use api datas

        // using fake data
        for (Client c : FakeData.getMedicalTeamClients()) {
            clientsList.add(c);
            mAdapter.notifyItemInserted(clientsList.size() - 1);
        }
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport
                .addTo(mRecyclerView, R.layout.client_row_without_progression)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ShowConfirmationToProgressPage(clientsList.get(position));
                    }
                });
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

    private void ShowConfirmationToProgressPage(final Client customer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation de visite");
        builder.setMessage(VISITE_MESSAGE + customer.getFullName() + " ? ");
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(mContext, VisiteInProgressActivity.class);

                i.putExtra(Constants.CLIENT_ID_KEY, customer.getId());
                i.putExtra(Constants.CLIENT_FIRSTNAME_KEY, customer.getFirstName());
                i.putExtra(Constants.CLIENT_LASTNAME_KEY, customer.getLastName());
                i.putExtra(Constants.CLIENT_SPECIALITY_KEY, customer.getSpeciality());
                i.putExtra(Constants.CLIENT_STATUS_KEY, customer.getStatus());
                i.putExtra(Constants.CLIENT_AVATAR_URL_KEY, customer.getAvatarUrl());
                startActivity(i);
            }
        });

        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        builder.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mNewClient.getId()) {
            showNewClientView();
        }
    }
}
