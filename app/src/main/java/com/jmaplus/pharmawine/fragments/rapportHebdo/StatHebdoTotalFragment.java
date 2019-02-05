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


public class StatHebdoTotalFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private TextView tvTotalPrevu, tvTotalReal, tvTotalRoverP;
    private RecyclerView rvStatHebdoTotal;
    private SwipeRefreshLayout srStatHebdoTotal;

    public StatHebdoTotalFragment() {
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

        View vTotal = inflater.inflate(R.layout.fragment_stat_hebdo_total, container, false);

        tvTotalPrevu = vTotal.findViewById(R.id.tv_tp_total);
        tvTotalReal = vTotal.findViewById(R.id.tv_real_total);
        tvTotalRoverP = vTotal.findViewById(R.id.tv_r_over_p_total);


        rvStatHebdoTotal = vTotal.findViewById(R.id.rv_stat_hebdo_total);
        srStatHebdoTotal = vTotal.findViewById(R.id.swipe_stat_hebdo_total);

        return vTotal;
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
