package com.jmaplus.pharmawine.fragments.rapportHebdo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;


public class StatistiqueHebdoFragment extends Fragment {

    private SharedPreferences spSeeStatHebdo;
    private boolean seeStat;
    private Button btnSeeStatHebdo, btnFDC, btnPDD, btnP, btnTotal;
    private ImageView ivSeeStat;



    public StatistiqueHebdoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spSeeStatHebdo = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vStatHebdo = inflater.inflate(R.layout.fragment_statistique_hebdo, container, false);

        btnSeeStatHebdo = vStatHebdo.findViewById(R.id.btn_vu_stat);
        ivSeeStat = vStatHebdo.findViewById(R.id.iv_see_stat_icon);
        btnFDC = vStatHebdo.findViewById(R.id.btn_stat_fdc);
        btnP = vStatHebdo.findViewById(R.id.btn_stat_p);
        btnPDD = vStatHebdo.findViewById(R.id.btn_stat_pdd);
        btnTotal = vStatHebdo.findViewById(R.id.btn_stat_total);


        setupView();


        return vStatHebdo;
    }

    private void setupView() {

        ivSeeStat.setImageDrawable(getResources().getDrawable(R.drawable.valideicon));
        btnSeeStatHebdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spSeeStatHebdo.edit().putBoolean(Constants.REPORT_HEBDO_SEE_STATISTIQUE, true).apply();

            }
        });

        btnFDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatHebdoFDCFragment statHebdoFDCFragment = new StatHebdoFDCFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_table_of_stat, statHebdoFDCFragment)
                        .commit();
            }
        });

        btnPDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatHebdoPDDFragment statHebdoPDDFragment = new StatHebdoPDDFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_table_of_stat, statHebdoPDDFragment)
                        .commit();
            }
        });

        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatHebdoPFragment statHebdoPFragment = new StatHebdoPFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_table_of_stat, statHebdoPFragment)
                        .commit();
            }
        });

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatHebdoTotalFragment statHebdoTotalFragment = new StatHebdoTotalFragment();
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_table_of_stat, statHebdoTotalFragment)
                        .commit();
            }
        });

    }


}
