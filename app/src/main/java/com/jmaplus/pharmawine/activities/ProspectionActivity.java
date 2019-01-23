package com.jmaplus.pharmawine.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.RemainingCustomersAdapter;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.ItemClickSupport;
import com.jmaplus.pharmawine.utils.RetrofitCalls.DelegueCalls;
import com.jmaplus.pharmawine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ProspectionActivity extends AppCompatActivity implements View.OnClickListener, DelegueCalls.Callbacks {
    private static String TAG = "ProspectionActivity";
    private static String VISITE_MESSAGE = "Êtes-vous sur le point de commencer une visite chez le medecin ";

    private Context mContext;
    private ProgressBar mProgressBar;
    private LinearLayout mNewClient;
    private RecyclerView mRecyclerView;
    private RemainingCustomersAdapter mAdapter;
    private List<Customer> mCustomerList;
    private AuthUser mAuthUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospection);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindViewsAndInitilise();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchRemainingClients();
            }
        }, 1000);

        configureOnClickRecyclerView();
    }

    private void bindViewsAndInitilise() {
        mContext = this;
        mProgressBar = findViewById(R.id.progressBar_remaining_customers);
        mNewClient = findViewById(R.id.lay_new_client);
        mRecyclerView = findViewById(R.id.rv_remaining_customers_prospection);
        mNewClient.setOnClickListener(this);

        mProgressBar.setVisibility(View.GONE);

        // Initializations
        mCustomerList = new ArrayList();
        mAuthUser = AuthUser.getAuthenticatedUser(this);
        mAdapter = new RemainingCustomersAdapter(this, mCustomerList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showNewClientView() {
        Intent i = new Intent(this, TranslucentNewClientActivity.class);
        startActivity(i);
    }

    private void fetchRemainingClients() {
        // todo : use api datas

        String currentDate = Utils.getCurrentDate();
        String token = AuthUser.getToken(mContext);

        try {
            mProgressBar.setVisibility(View.VISIBLE);
            DelegueCalls.getPlanning(token, this,
                    mAuthUser.getId().toString(), currentDate, currentDate);
        } catch (Exception e) {
            Log.e(TAG, "fetchRemainingClients: " + e.getMessage());
            mProgressBar.setVisibility(View.VISIBLE);
        }

    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport
                .addTo(mRecyclerView, R.layout.client_row_without_progression)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ShowConfirmationToProgressPage(mCustomerList.get(position));
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

    private void ShowConfirmationToProgressPage(final Customer customer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation de visite");
        builder.setMessage(VISITE_MESSAGE + customer.getFullName() + " ? ");
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(mContext, VisiteInProgressActivity.class);

                // TODO: Important thing to consider after implentation of Pharmacy model
                // IF customer is instance of pharmacy class, so use the following line instead
                // .putExtra(VisiteInProgressFragment.ARGS_PROSPECT_TYPE, Constants.PROSPECT_KNOWN_PHARMACY_TYPE_KEY);

                i.putExtra(VisiteInProgressActivity.EXTRA_PROSPECT_TYPE,
                        Constants.PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY);

                i.putExtra(Constants.CLIENT_ID_KEY, customer.getId());
                i.putExtra(Constants.CLIENT_FIRSTNAME_KEY, customer.getFirstname());
                i.putExtra(Constants.CLIENT_LASTNAME_KEY, customer.getLastname());
                i.putExtra(Constants.CLIENT_SPECIALITY_KEY, customer.getSpeciality().getName());
                i.putExtra(Constants.CLIENT_STATUS_KEY, customer.getCustomerStatus().getName());
                i.putExtra(Constants.CLIENT_AVATAR_URL_KEY, customer.getAvatar());

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
    public void onPlanningResponse(@Nullable List<Customer> customers) {
        mProgressBar.setVisibility(View.GONE);

        if (customers.isEmpty())
            Toast.makeText(mContext, "Aucun client trouvé", Toast.LENGTH_SHORT).show();
        else {
            for (Customer c : customers) {
                mCustomerList.add(c);
                mAdapter.notifyItemChanged(mCustomerList.size() - 1);
            }
        }
    }

    @Override
    public void onPlanningFailure() {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(mContext, "Fetch to get planning", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mNewClient.getId()) {
            showNewClientView();
        }
    }
}
