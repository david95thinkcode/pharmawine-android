package com.jmaplus.pharmawine.fragments.rapportHebdo;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmaplus.pharmawine.R;


public class StatistiqueHebdoFragment extends Fragment {


    public StatistiqueHebdoFragment() {
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
        return inflater.inflate(R.layout.fragment_statistique_hebdo, container, false);
    }

    /*
     * TODO: Return a bool to ReportHebdoManagerActivity
     */

}
