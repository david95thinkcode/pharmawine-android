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


public class ActiviteMeneActionPharmaFragment extends Fragment {


    private EditText edAMActionPharma;
    private Button btnSaveAMActionPharma, btnBackToRelaPubliq;
    private SharedPreferences spAMActionPharma;
    private SharedPreferences.Editor epAMActionPharma;
    private String amActionPharma;


    public ActiviteMeneActionPharmaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spAMActionPharma = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vAMActionPharma = inflater.inflate(R.layout.fragment_activite_mene_action_pharma, container, false);
        edAMActionPharma = vAMActionPharma.findViewById(R.id.ed_action_pharmacie);
        btnSaveAMActionPharma = vAMActionPharma.findViewById(R.id.btn_go_to_parcours_fidel);
        btnBackToRelaPubliq = vAMActionPharma.findViewById(R.id.btn_back_rela_publiq);

        btnSaveAMActionPharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amActionPharma = edAMActionPharma.getText().toString();
                if (!amActionPharma.isEmpty()) {
                    epAMActionPharma = spAMActionPharma.edit();
                    epAMActionPharma.putString(Constants.REPORT_HEBDO_AM_PHARMACY, amActionPharma);
                    epAMActionPharma.apply();
                    ActiviteMeneParcoursFidelFragment activiteMeneParcoursFidelFragment = new ActiviteMeneParcoursFidelFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack(Constants.REPORT_HEBDO_AM_PHARMACY)
                            .replace(R.id.fragment_container_report_hebdo, activiteMeneParcoursFidelFragment)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Vous devez entrer les actions menées pour les pharmacies", Toast.LENGTH_LONG).show();
                }
            }
        });


        return vAMActionPharma;
    }


}
