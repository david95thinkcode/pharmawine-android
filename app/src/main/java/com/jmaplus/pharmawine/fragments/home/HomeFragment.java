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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements
        CustomerCalls.Callbacks,
        SeenCustomerCalls.Callbacks {

    private String TAG = "HomeFragment";
    private TextView tvDate, tvNetworkLabel, tvProgress;

    private TextView tvRemainingCount;
    private TextView tvSeenCount;
    private TextView tvSeenUnknown;
    private TextView tvSeenKnown;

    private int deplacement1To2 = 32;
    private int deplacement2To3 = 65;
    private int deplacement3To4 = 105;
    private int deplacement4To5 = 135;
    private int deplacement5To6 = 170;
    private int deplacement6To7 = 202;
    private int deplacement7To8 = 235;
    private int deplacement8To9 = 267;
    private int deplacement9To10 = 305;

    private CircleImageView indicator;
    private TextView tvDecade1;
    private TextView tvDecade2;
    private TextView tvDecade3;
    private TextView tvDecade4;
    private TextView tvDecade5;
    private TextView tvDecade6;
    private TextView tvDecade7;
    private TextView tvDecade8;
    private TextView tvDecade9;
    private TextView tvDecade10;


    //private LinearLayout cvDate;
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
        dailyProgressBar = view.findViewById(R.id.daily_progressbar);
        tvProgress = view.findViewById(R.id.tv_progress);
        layBottomFabs = view.findViewById(R.id.lay_bottom_fabs);
        clientRemaining = view.findViewById(R.id.clients_remaining);
        clientSeen = view.findViewById(R.id.clients_seen);
        tvRemainingCount = view.findViewById(R.id.textView_num_visit_restant);
        tvSeenCount = view.findViewById(R.id.tv_SeenCustomersTotal);
        tvSeenKnown = view.findViewById(R.id.tv_known_customer);
        tvSeenUnknown = view.findViewById(R.id.tv_unknown_customer);

        tvDecade1 = view.findViewById(R.id.date_decate_1);
        tvDecade2 = view.findViewById(R.id.date_decate_2);
        tvDecade3 = view.findViewById(R.id.date_decate_3);
        tvDecade4 = view.findViewById(R.id.date_decate_4);
        tvDecade5 = view.findViewById(R.id.date_decate_5);
        tvDecade6 = view.findViewById(R.id.date_decate_6);
        tvDecade7 = view.findViewById(R.id.date_decate_7);
        tvDecade8 = view.findViewById(R.id.date_decate_8);
        tvDecade9 = view.findViewById(R.id.date_decate_9);
        tvDecade10 = view.findViewById(R.id.date_decate_10);

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

        inflateDecateLayout();
    }

    private void inflateDecateLayout() {
        try {
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int maxDayInpresentMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int posi = day % 10;
            int[] decades = new int[10];
            if (posi == 0) {
                if (day <= maxDayInpresentMonth) {
                    decades[posi] = day;
                }

                for (int i = 0; i < decades.length; i++) {
                    if (day + i + 1 <= maxDayInpresentMonth) {
                        decades[i] = day + i + 1;
                    } else {
                        decades[i] = i + 1;
                    }

                }
            } else if (posi == 1) {
                decades[0] = day - 1;
            } else if (posi > 1) {
                decades[posi - 1] = day;
                for (int i = 0; i <= posi - 1; i++) {
                    decades[i] = day - posi + i + 1;
                }
                int y = 1;
                for (int i = posi; i < decades.length; i++) {

                    if (day + y == maxDayInpresentMonth) {
                        decades[i] = day + y;
                    } else if (day + y < maxDayInpresentMonth) {

                        decades[i] = day + y;

                    } else if (day + y > maxDayInpresentMonth) {
                        decades[i] = y - 1;
                    }
                    y++;
                }
            }
            if (day == maxDayInpresentMonth) {
                posi = 10;
                decades[posi - 1] = day;
                for (int i = 0; i <= posi - 1; i++) {
                    decades[i] = day - posi + i + 1;
                }
            }
            tvDecade1.setText(decades[0]);
            tvDecade2.setText(decades[1]);
            tvDecade3.setText(decades[2]);
            tvDecade4.setText(decades[3]);
            tvDecade5.setText(decades[4]);
            tvDecade6.setText(decades[5]);
            tvDecade7.setText(decades[6]);
            tvDecade8.setText(decades[7]);
            tvDecade9.setText(decades[8]);
            tvDecade10.setText(decades[9]);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(5, 1, 0, 0);
            switch (posi) {
                case 0: {
                    indicator.setLayoutParams(params);
                }
                break;
                case 1: {
                    indicator.setLayoutParams(params);
                }
                break;
                case 2: {
                    params.setMargins(deplacement1To2, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                case 3: {
                    params.setMargins(deplacement2To3, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                case 4: {
                    params.setMargins(deplacement3To4, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                case 5: {
                    params.setMargins(deplacement4To5, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                case 6: {
                    params.setMargins(deplacement5To6, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                case 7: {
                    params.setMargins(deplacement7To8, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                case 8: {
                    params.setMargins(deplacement8To9, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                case 9: {
                    params.setMargins(deplacement9To10, 1, 0, 0);
                    indicator.setLayoutParams(params);
                }
                break;
                default: {
                    indicator.setLayoutParams(params);
                }
                break;

            }
        } catch (Exception e) {
            Log.e(TAG, "An error occured : " + e.getMessage());
            Log.e(TAG, "Because : " + e.getCause());
            e.printStackTrace();
        }
        
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
