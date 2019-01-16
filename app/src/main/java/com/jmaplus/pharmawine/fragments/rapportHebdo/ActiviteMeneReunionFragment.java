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
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;


public class ActiviteMeneReunionFragment extends Fragment {

    SharedPreferences spAMReunion;
    SharedPreferences.Editor epAMReunion;
    private EditText edAMreunion;
    private Button btnsaveAMReunion, btnBackToGarde;
    private String amReunion;



    public ActiviteMeneReunionFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spAMReunion = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vAMReunion = inflater.inflate(R.layout.fragment_activite_mene_reunion, container, false);
        edAMreunion = vAMReunion.findViewById(R.id.ed_reunions);
        btnsaveAMReunion = vAMReunion.findViewById(R.id.btn_go_to_relation_public);
        btnBackToGarde = vAMReunion.findViewById(R.id.btn_back_to_garde);

        btnsaveAMReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amReunion = edAMreunion.getText().toString();
                if (!amReunion.isEmpty()) {
                    epAMReunion = spAMReunion.edit();
                    epAMReunion.putString(Constants.REPORT_HEBDO_AM_REUNIONS, amReunion);
                    epAMReunion.apply();
                    ActiviteMeneRelationPublicFragment activiteMeneRelationPublicFragment = new ActiviteMeneRelationPublicFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_report_hebdo, activiteMeneRelationPublicFragment)
                            .addToBackStack(Constants.REPORT_HEBDO_AM_REUNIONS)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Vous devez entrer les reunions faites", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBackToGarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .popBackStack(Constants.REPORT_HEBDO_AM_GARDE, 0);
            }
        });


        return vAMReunion;
    }
}
