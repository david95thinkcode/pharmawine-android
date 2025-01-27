package com.jmaplus.pharmawine.fragments.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeSupervisorFragment extends Fragment {

    private TextView tvDate, tvNetworkLabel, tvProgress;
    private RoundCornerProgressBar dailyProgressBar;
    private Context mContext;
    private FloatingActionButton fabNetwork, fabProspection;

    private AuthenticatedUser authenticatedUser;
    private AuthUser currentUser;

    private RelativeLayout layBottomFabs;
    private LinearLayout clientSeen, clientRemaining;


    public HomeSupervisorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mContext = getActivity();

        View view = inflater.inflate(R.layout.fragment_home_supervisor, container, false);


        tvDate = view.findViewById(R.id.tv_home_date);
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

        currentUser = AuthUser.getAuthenticatedUser(requireContext());
        tvNetworkLabel.setText("Aucun réseau");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        tvDate.setText(dateFormat.format(new Date()));



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

}






