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


public class StatHebdoFDCFragment extends Fragment {

    private TextView tvTotalPrevuCFG, tvTotalPrevuCFM, tvRealCFG, tvRealCFM, tvRealOverPrevuCFG, tvRealOverPrevuCFM;
    private RecyclerView rvStatHebdoFDC;
    private SwipeRefreshLayout srStatHebdoFDC;


    public StatHebdoFDCFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vStatFDC = inflater.inflate(R.layout.fragment_stat_hebdo_fdc, container, false);

        tvTotalPrevuCFG = vStatFDC.findViewById(R.id.tv_fdc_tp_cfg);
        tvTotalPrevuCFM = vStatFDC.findViewById(R.id.tv_fdc_tp_cfm);
        tvRealCFG = vStatFDC.findViewById(R.id.tv_fdc_real_cfg);
        tvRealCFM = vStatFDC.findViewById(R.id.tv_fdc_real_cfm);
        tvRealOverPrevuCFG = vStatFDC.findViewById(R.id.tv_fdc_r_over_p_cfg);
        tvRealOverPrevuCFM = vStatFDC.findViewById(R.id.tv_fdc_r_over_p_cfm);
        rvStatHebdoFDC = vStatFDC.findViewById(R.id.rv_stat_hebdo_fdc);
        srStatHebdoFDC = vStatFDC.findViewById(R.id.swipe_stat_hebdo_fdc);

        return vStatFDC;
    }
}
