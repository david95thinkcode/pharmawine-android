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
import android.widget.EditText;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;


public class ActiviteMeneRelationPublicFragment extends Fragment {

    EditText edAMRelaPubliq;
    Button btnSaveAMRelaPublq, btnBackToReunion;
    SharedPreferences spAMRelaPubliq;
    SharedPreferences.Editor epAMRelaPubliq;
    String amRelaPublic;

    public ActiviteMeneRelationPublicFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spAMRelaPubliq = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vAMRelPubliq = inflater.inflate(R.layout.fragment_activite_mene_relation_public, container, false);
        edAMRelaPubliq = vAMRelPubliq.findViewById(R.id.ed_relation_publique);
        btnSaveAMRelaPublq = vAMRelPubliq.findViewById(R.id.btn_go_to_action_pharmacie);
        btnBackToReunion = vAMRelPubliq.findViewById(R.id.btn_back_to_reunion);

        btnSaveAMRelaPublq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amRelaPublic = edAMRelaPubliq.getText().toString();
                if (!amRelaPublic.isEmpty()) {
                    epAMRelaPubliq = spAMRelaPubliq.edit();
                    epAMRelaPubliq.putString(Constants.REPORT_HEBDO_AM_RELATION_PUBLIQ, amRelaPublic);
                    epAMRelaPubliq.apply();
                    ActiviteMeneActionPharmaFragment activiteMeneActionPharmaFragment = new ActiviteMeneActionPharmaFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_report_hebdo, activiteMeneActionPharmaFragment)
                            .addToBackStack(Constants.REPORT_HEBDO_AM_RELATION_PUBLIQ)
                            .commit();
                }
            }
        });
        btnBackToReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .popBackStack(Constants.REPORT_HEBDO_AM_REUNIONS, 0);

            }
        });

        return vAMRelPubliq;
    }


}
