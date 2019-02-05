package com.jmaplus.pharmawine.fragments.rapportHebdo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmaplus.pharmawine.R;


public class StatHebdoPDDFragment extends Fragment {


    private TextView tvTotalPrevuCIG, tvTotalPrevuCIM, tvTotalPrevuPCG, tvTotalPrevuPCM;
    private TextView tvTotalRealCIG, tvTotalRealCIM, tvTotalRealPCG, tvTotalRealPCM;
    private TextView tvTotalRoverPCIG, tvTotalRoverPCIM, tvTotalRoverPPCG, tvTotalRoverPPCM;
    private RecyclerView rvStatHebdoPDD;
    private SwipeRefreshLayout srStatHebdoPDD;


    public StatHebdoPDDFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vPdd = inflater.inflate(R.layout.fragment_stat_hebdo_pdd, container, false);
        tvTotalPrevuCIG = vPdd.findViewById(R.id.tv_pdd_tp_cig);
        tvTotalPrevuCIM = vPdd.findViewById(R.id.tv_pdd_tp_cim);
        tvTotalPrevuPCG = vPdd.findViewById(R.id.tv_pdd_tp_pcg);
        tvTotalPrevuPCM = vPdd.findViewById(R.id.tv_pdd_tp_pcm);

        tvTotalRealCIG = vPdd.findViewById(R.id.tv_pdd_real_cig);
        tvTotalRealCIM = vPdd.findViewById(R.id.tv_pdd_real_cim);
        tvTotalRealPCG = vPdd.findViewById(R.id.tv_pdd_real_pcg);
        tvTotalRealPCM = vPdd.findViewById(R.id.tv_pdd_real_pcm);

        tvTotalRoverPCIG = vPdd.findViewById(R.id.tv_pdd_r_over_p_cig);
        tvTotalRoverPCIM = vPdd.findViewById(R.id.tv_pdd_r_over_p_cim);
        tvTotalRoverPPCG = vPdd.findViewById(R.id.tv_pdd_r_over_p_pcg);
        tvTotalRoverPPCM = vPdd.findViewById(R.id.tv_pdd_r_over_p_pcm);

        rvStatHebdoPDD = vPdd.findViewById(R.id.rv_stat_hebdo_pdd);
        srStatHebdoPDD = vPdd.findViewById(R.id.swipe_stat_hebdo_pdd);


        return vPdd;
    }

}
