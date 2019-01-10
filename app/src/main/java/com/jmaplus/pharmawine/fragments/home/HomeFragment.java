package com.jmaplus.pharmawine.fragments.home;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.datepicker.DatePickerBuilder;
import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.NetworksActivity;
import com.jmaplus.pharmawine.activities.ProspectionActivity;
import com.jmaplus.pharmawine.activities.RemainingClientsActivity;
import com.jmaplus.pharmawine.activities.SeenCustomers;
import com.jmaplus.pharmawine.activities.VisiteInProgressActivity;
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
public class HomeFragment extends Fragment {

    private TextView tvDate, tvNetworkLabel, tvProgress;
    private CardView cvDate;
    private RoundCornerProgressBar dailyProgressBar;
    private Context mContext;
    private FloatingActionButton fabNetwork, fabProspection;

    private AuthenticatedUser authenticatedUser;

    private RelativeLayout layBottomFabs;
    private LinearLayout clientSeen, clientRemaining;

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
        clientRemaining = view.findViewById(R.id.clients_remaining);
        clientSeen = view.findViewById(R.id.clients_seen);

        tvNetworkLabel = view.findViewById(R.id.tv_network_label);
        fabNetwork = view.findViewById(R.id.fab_network);
        fabProspection = view.findViewById(R.id.fab_see_more);

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

        clientRemaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RemainingClientsActivity.class));
            }
        });

        clientSeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, SeenCustomers.class));
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
                startActivity(new Intent(mContext, VisiteInProgressActivity.class));
            }
        });
    }

    private void setDailyProgression(int value) {
        dailyProgressBar.setProgress(value);
        tvProgress.setText(String.valueOf(value).concat("%"));
    }

}
