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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.RemainingCustomersAdapter;
import com.jmaplus.pharmawine.database.model.ClientsList;
import com.jmaplus.pharmawine.database.utils.DatabaseHelper;
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
    private LinearLayout mNewClient, mRetryLayout;
    private Button mRetryBtn;
    private RecyclerView mRecyclerView;
    private RemainingCustomersAdapter mAdapter;
    private List<Customer> mCustomerList;
    private AuthUser mAuthUser;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospection);

        db = new DatabaseHelper(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindViewsAndInitilise();

        Log.i(getClass().getName(), "Fetech remote client");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchRemainingClients();
            }
        }, 800);
        Log.i(getClass().getName(), "Call Save to DB");
        saveCustomerToDb();

        configureOnClickRecyclerView();
    }

    private void saveCustomerToDb() {
        for (Customer c : mCustomerList) {
            String client = c.toString();
            ClientsList clientsList = new ClientsList(client, Utils.getCurrentDate());
            long cID = db.createClient(clientsList);
            Log.e("Client added to db", Long.toString(cID));
        }

    }

    private void bindViewsAndInitilise() {
        mContext = this;
        mRetryBtn = findViewById(R.id.retry_btn);
        mProgressBar = findViewById(R.id.progressBar_remaining_customers);
        mNewClient = findViewById(R.id.lay_new_client);
        mRetryLayout = findViewById(R.id.retry_layout);
        mRecyclerView = findViewById(R.id.rv_remaining_customers_prospection);
        mNewClient.setOnClickListener(this);
        mRetryBtn.setOnClickListener(this);

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
        updateUIViewForFetching();

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

    private void updateUIViewForFetching() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRetryLayout.setVisibility(View.GONE);
    }

    private void updateUIViewForFetchingSuccess() {
        mProgressBar.setVisibility(View.GONE);
        mRetryLayout.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void updateUIViewForFetchingError() {
        mProgressBar.setVisibility(View.GONE);
        mRetryLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
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
        LayoutInflater factory = LayoutInflater.from(this);
        final View customDialogView = factory.inflate(R.layout.custom_dialog_box, null);
        final AlertDialog customDialog = new AlertDialog.Builder(this).create();
        customDialog.setView(customDialogView);
        TextView msgDialog = customDialogView.findViewById(R.id.dialog_box_content);
        TextView titleDialog = customDialogView.findViewById(R.id.dialog_box_title);
        customDialog.findViewById(R.id.dialog_box_content);
        msgDialog.setText(R.string.msg_confirmation_visite);
        titleDialog.setVisibility(View.GONE);

        customDialogView.findViewById(R.id.yes_button_custom_dialogbox).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, VisiteInProgressActivity.class);

                // TODO: Important thing to consider after implentation of Pharmacy model
                // IF customer is instance of pharmacy class, so use the following line instead
                // .putExtra(VisiteInProgressFragment.ARGS_PROSPECT_TYPE, Constants.PROSPECT_KNOWN_PHARMACY_TYPE_KEY);

                i.putExtra(VisiteInProgressActivity.EXTRA_PROSPECT_TYPE,
                        Constants.PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY);

                i.putExtra(Constants.CLIENT_ID_KEY, customer.getId());
                i.putExtra(Constants.CLIENT_FIRSTNAME_KEY, customer.getFirstname());
                i.putExtra(Constants.CLIENT_FULLNAME_KEY, customer.getFullName());
                i.putExtra(Constants.CLIENT_LASTNAME_KEY, customer.getLastname());
                i.putExtra(Constants.CLIENT_AVATAR_URL_KEY, customer.getAvatar());

                // Preventing against null exception
                try {
                    if (customer.getCustomerStatus() != null && customer.getCustomerType() != null
                            && customer.getSpeciality() != null) {
                        i.putExtra(Constants.CLIENT_SPECIALITY_KEY, customer.getSpeciality().getName());
                        i.putExtra(Constants.CLIENT_CUSTOMER_TYPE_KEY, customer.getCustomerType().getName());
                        i.putExtra(Constants.CLIENT_CUSTOMER_STATUS_KEY, customer.getCustomerStatus().getName());
                    }
                } catch (NullPointerException e) {

                } catch (Exception e) {

                }

                startActivity(i);
            }
        });
        customDialogView.findViewById(R.id.no_button_custom_dialogbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    @Override
    public void onPlanningResponse(@Nullable List<Customer> customers) {
        updateUIViewForFetchingSuccess();

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
        updateUIViewForFetchingError();
        Toast.makeText(mContext, "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mNewClient.getId()) {
            showNewClientView();
        } else if (v.getId() == mRetryBtn.getId()) {
            fetchRemainingClients();
        }
    }
}
