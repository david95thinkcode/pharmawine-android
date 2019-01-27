package com.jmaplus.pharmawine.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.NetworksActivity;
import com.jmaplus.pharmawine.activities.ProspectionActivity;
import com.jmaplus.pharmawine.activities.RemainingClientsActivity;
import com.jmaplus.pharmawine.activities.SeenCustomers;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.AuthenticatedUser;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class HomeAdminFragment extends Fragment {

    private TextView tvDate, tvNetworkLabel, tvProgress;
    private LinearLayout cvDate;
    private RoundCornerProgressBar dailyProgressBar;
    private Context mContext;
    private FloatingActionButton fabNetwork;

    private AuthenticatedUser authenticatedUser;
    private AuthUser currentUser;

    private ConstraintLayout layBottomFabs;
    private LinearLayout clientSeen, clientRemaining;


    public HomeAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_admin, container, false);


        tvDate = view.findViewById(R.id.tv_home_date);
        cvDate = view.findViewById(R.id.cv_home_date);
        dailyProgressBar = view.findViewById(R.id.daily_progressbar);
        tvProgress = view.findViewById(R.id.tv_progress);
        layBottomFabs = view.findViewById(R.id.lay_bottom_fabs);
        clientRemaining = view.findViewById(R.id.clients_remaining);
        clientSeen = view.findViewById(R.id.clients_seen);

        tvNetworkLabel = view.findViewById(R.id.tv_network_label);
        fabNetwork = view.findViewById(R.id.fab_network);

        initViews();


        return view;
    }


    private void initViews() {

        currentUser = AuthUser.getAuthenticatedUser(requireContext());
        tvNetworkLabel.setText("Aucun réseau");

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

    }


    private void setDailyProgression(int value) {
        dailyProgressBar.setProgress(value);
        tvProgress.setText(String.valueOf(value).concat("%"));
    }


}












