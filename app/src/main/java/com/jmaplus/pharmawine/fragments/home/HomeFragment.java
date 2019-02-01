package com.jmaplus.pharmawine.fragments.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.NetworksActivity;
import com.jmaplus.pharmawine.activities.ProspectionActivity;
import com.jmaplus.pharmawine.activities.RemainingClientsActivity;
import com.jmaplus.pharmawine.activities.SeenCustomersActivity;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.CustomerCalls;
import com.jmaplus.pharmawine.utils.RetrofitCalls.customers.SeenCustomerCalls;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements
        CustomerCalls.Callbacks,
        SeenCustomerCalls.Callbacks {

    private String TAG = "HomeFragment";
    private TextView tvDate, tvNetworkLabel, tvProgress;

    //private LinearLayout cvDate;
    private TextView tvRemainingCount;
    private TextView tvSeenCount;
    private TextView tvSeenUnknown;
    private TextView tvSeenKnown;
    
    
    private LinearLayout cvDate;
    private RoundCornerProgressBar dailyProgressBar;
    private Context mContext;
    private FloatingActionButton fabNetwork, fabProspection;

    private AuthenticatedUser authenticatedUser;
    private AuthUser currentUser;
    private String mToken;

    private RelativeLayout layBottomFabs;
    private LinearLayout clientSeen, clientRemaining;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mToken = AuthUser.getToken(mContext);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvDate = view.findViewById(R.id.tv_home_date);
        //cvDate = view.findViewById(R.id.cv_home_date);
        dailyProgressBar = view.findViewById(R.id.daily_progressbar);
        tvProgress = view.findViewById(R.id.tv_progress);
        layBottomFabs = view.findViewById(R.id.lay_bottom_fabs);
        clientRemaining = view.findViewById(R.id.clients_remaining);
        clientSeen = view.findViewById(R.id.clients_seen);
        tvRemainingCount = view.findViewById(R.id.textView_num_visit_restant);
        tvSeenCount = view.findViewById(R.id.tv_SeenCustomersTotal);
        tvSeenKnown = view.findViewById(R.id.tv_known_customer);
        tvSeenUnknown = view.findViewById(R.id.tv_unknown_customer);
        
        tvNetworkLabel = view.findViewById(R.id.tv_network_label);
        fabNetwork = view.findViewById(R.id.fab_network);
        fabProspection = view.findViewById(R.id.fab_see_more);

        initViews();

        return view;
    }

    @Override
    public void onResume() {

        try {
            // Refresh all datas

            // here we get the remaining customers
            CustomerCalls.getRemaining(mToken, this);

            // here we get the known customers
            SeenCustomerCalls.getSeenCustomers(mToken, this);
        } catch (Exception e) {
            Log.e(TAG, "onResume: " + e.getMessage());
            e.printStackTrace();
        }

        super.onResume();
    }

    private void initViews() {

        tvRemainingCount.setText(String.valueOf(0));

        currentUser = AuthUser.getAuthenticatedUser(requireContext());
        try {
            tvNetworkLabel.setText(String.format("Réseau %s", currentUser.getNetwork().getName()));
        } catch (Exception e) {
            tvNetworkLabel.setText(getString(R.string.aucun_reseau));
            Log.e(TAG, "initViews: " + e.getMessage());
            e.printStackTrace();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        tvDate.setText(dateFormat.format(new Date()));

//        cvDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                CalendarDatePickerDialogFragment datePickerDialog = new CalendarDatePickerDialogFragment()
//                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int month, int dayOfMonth) {
//                                String[] frenchMonths = new DateFormatSymbols(Locale.FRENCH).getMonths();
//                                tvDate.setText(
//                                        String.valueOf(dayOfMonth).concat(" ").
//                                                concat(frenchMonths[month]).concat(" ").
//                                                concat(String.valueOf(year)));
//                            }
//                        });
//                datePickerDialog.show(getActivity().getSupportFragmentManager(), "DatePicker");
//
//            }
//        });


        setDailyProgression(0);

        clientRemaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RemainingClientsActivity.class));
            }
        });

        clientSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SeenCustomersActivity.class));
            }
        });

        fabNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, NetworksActivity.class));
            }
        });

        fabProspection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ProspectionActivity.class));
                // startActivity(new Intent(mContext, RapportHebdoActivity.class));
            }
        });
    }

    private void setDailyProgression(int value) {
        dailyProgressBar.setProgress(value);
        tvProgress.setText(String.valueOf(value).concat("%"));
    }

    @Override
    public void onCustomerDetailsResponse(@Nullable Customer customer) {

    }

    @Override
    public void onCustomerDetailsFailure() {

    }

    @Override
    public void onKnownProspectResponse(@Nullable List<Customer> customers) {

    }

    @Override
    public void onKnownProspectFailure() {

    }

    @Override
    public void onRemainingCustomersResponse(@Nullable List<Customer> customers) {

        tvRemainingCount.setText(String.valueOf(customers.size()));
//        Toast.makeText(mContext, "Nombre de client restant mis a jour", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemainingCustomersFailure() {

    }

    @Override
    public void onSeenCustomersResponse(@Nullable List<Customer> customers) {
        Integer UnknownCount = 0;
        Integer KnownCount = 0;
        Integer TotalKnown = 0;

        if (!customers.isEmpty()) {
            for (Customer c : customers) {
                if (c.isKnown())
                    KnownCount++;
                else
                    UnknownCount++;
            }
            TotalKnown = UnknownCount + KnownCount;

            // Update the view
            tvSeenCount.setText(TotalKnown.toString());
            tvSeenUnknown.setText("Inconnus : " + UnknownCount);
            tvSeenKnown.setText("Connus : " + KnownCount);
        }
    }

    @Override
    public void onSeenCustomersFailure() {
        Log.e(TAG, "onSeenCustomersFailure: Failed to get seen customer from the API");
    }
}
