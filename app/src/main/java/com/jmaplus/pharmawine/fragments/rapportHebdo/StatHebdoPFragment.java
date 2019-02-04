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


public class StatHebdoPFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private TextView tvTotalPrevuPim, tvTotalPrevuPig, tvTotalRealPim, tvTotalRealPig, tvRoverPPim, tvRoverPPig;
    private RecyclerView rvStatHebdoP;
    private SwipeRefreshLayout srStatHebdoP;

    public StatHebdoPFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View vP = inflater.inflate(R.layout.fragment_stat_hebdo_p, container, false);
        tvTotalPrevuPig = vP.findViewById(R.id.tv_tp_p_pig);
        tvTotalPrevuPim = vP.findViewById(R.id.tv_tp_p_pim);
        tvTotalRealPig = vP.findViewById(R.id.tv_real_p_pig);
        tvTotalRealPim = vP.findViewById(R.id.tv_real_p_pim);
        tvRoverPPig = vP.findViewById(R.id.tv_r_over_p_p_pig);
        tvRoverPPim = vP.findViewById(R.id.tv_r_over_p_p_pim);

        rvStatHebdoP = vP.findViewById(R.id.rv_stat_hebdo_p);
        srStatHebdoP = vP.findViewById(R.id.swipe_stat_hebdo_p);

        return vP;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
