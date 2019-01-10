package com.jmaplus.pharmawine.fragments.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.NetworksActivity;
import com.jmaplus.pharmawine.activities.ProspectionActivity;
import com.jmaplus.pharmawine.activities.RemainingClientsActivity;
import com.jmaplus.pharmawine.activities.SeenCustomers;
import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.robertlevonyan.views.expandable.Expandable;
import com.robertlevonyan.views.expandable.ExpandingListener;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView tvDate, tvNetworkLabel, tvProgress;
    private CardView cvDate;
    private RoundCornerProgressBar dailyProgressBar;
    private Context mContext;
    private FloatingActionButton fabNetwork, fabProspection;
    private Button btnRemainingClients;
    private Button btnSeenCustomers;

    private AuthenticatedUser authenticatedUser;

    private RelativeLayout layBottomFabs;
    private Expandable expClientsRemaining, expClientsSeen;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvDate = view.findViewById(R.id.tv_home_date);
        cvDate = view.findViewById(R.id.cv_home_date);
        dailyProgressBar = view.findViewById(R.id.daily_progressbar);
        tvProgress = view.findViewById(R.id.tv_progress);
        layBottomFabs = view.findViewById(R.id.lay_bottom_fabs);
        expClientsRemaining = view.findViewById(R.id.expandable_clients_remaining);
        expClientsSeen = view.findViewById(R.id.expandable_clients_seen);

        tvNetworkLabel = view.findViewById(R.id.tv_network_label);
        fabNetwork = view.findViewById(R.id.fab_network);
        fabProspection = view.findViewById(R.id.fab_see_more);
        btnRemainingClients = view.findViewById(R.id.btn_remaining_clients);
        btnSeenCustomers = view.findViewById(R.id.btn_seen_customers);

        initViews();
        return view;
    }

    private void initViews() {

        authenticatedUser = AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm);


        try {
            tvNetworkLabel.setText((authenticatedUser.getNetworkName() == null) ? "Aucun réseau" : "Réseau " + authenticatedUser.getNetworkName());
        } catch (NullPointerException e) {
            tvNetworkLabel.setText("Aucun réseau");
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        tvDate.setText(dateFormat.format(new Date()));

        // todo: replace the btnremainingClients with the remaining clients card view
        btnRemainingClients.setOnClickListener(this);
        btnSeenCustomers.setOnClickListener(this);

        cvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CalendarDatePickerDialogFragment datePickerDialog = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int month, int dayOfMonth) {
                                String[] frenchMonths = new DateFormatSymbols(Locale.FRENCH).getMonths();
                                tvDate.setText(
                                        String.valueOf(dayOfMonth).concat(" ").
                                                concat(frenchMonths[month]).concat(" ").
                                                concat(String.valueOf(year)));
                            }
                        });
                datePickerDialog.show(getActivity().getSupportFragmentManager(), "DatePicker");

            }
        });


        setDailyProgression(45);

        ExpandingListener expandingListener = new ExpandingListener() {
            @Override
            public void onExpanded() {
                layBottomFabs.setVisibility(View.GONE);
            }

            @Override
            public void onCollapsed() {
                if (expClientsRemaining.isExpanded() || expClientsSeen.isExpanded()) {
                    layBottomFabs.setVisibility(View.GONE);
                } else {
                    layBottomFabs.setVisibility(View.VISIBLE);
                }
            }
        };
        expClientsRemaining.setExpandingListener(expandingListener);
        expClientsSeen.setExpandingListener(expandingListener);

        fabNetwork.setOnClickListener(this);
        fabProspection.setOnClickListener(this);
    }

    private void setDailyProgression(int value) {
        dailyProgressBar.setProgress(value);
        tvProgress.setText(String.valueOf(value).concat("%"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_see_more:
                startActivity(new Intent(mContext, ProspectionActivity.class));
                break;
            case R.id.fab_network:
                startActivity(new Intent(mContext, NetworksActivity.class));
                break;

            // Todo: the case should be replaced with the card view id
            case R.id.btn_remaining_clients:
                Log.i("HomeFragment", "Remaining clients button clicked");
                startActivity(new Intent(mContext, RemainingClientsActivity.class));
                break;
            case R.id.btn_seen_customers:
                startActivity(new Intent(mContext, SeenCustomers.class));
                break;
            default:
                // Nothing to do
                break;
        }
    }
}
