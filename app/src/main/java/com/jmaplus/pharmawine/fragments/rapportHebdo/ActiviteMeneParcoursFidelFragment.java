package com.jmaplus.pharmawine.fragments.rapportHebdo;

import android.content.SharedPreferences;
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


public class ActiviteMeneParcoursFidelFragment extends Fragment {

    private EditText edAMParFidel;
    private Button btnSaveParcoursFidel, btnBackToActionPharma;
    private SharedPreferences spAMParFidel;
    private String amParFidel;



    public ActiviteMeneParcoursFidelFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spAMParFidel = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vAMParFidel = inflater.inflate(R.layout.fragment_activite_mene_parcours_fidel, container, false);

        edAMParFidel = vAMParFidel.findViewById(R.id.ed_parcours_fidelisation);
        btnSaveParcoursFidel = vAMParFidel.findViewById(R.id.btn_go_to_zone_profonde);
        btnBackToActionPharma = vAMParFidel.findViewById(R.id.btn_back_to_action_pharmacy);

        btnSaveParcoursFidel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amParFidel = edAMParFidel.getText().toString();
                if (!amParFidel.isEmpty()) {
                    spAMParFidel.edit().putString(Constants.REPORT_HEBDO_AM_PARCOURS_FIDEL, amParFidel).apply();
                    ActiviteMeneZoneProfondFragment activiteMeneZoneProfondFragment = new ActiviteMeneZoneProfondFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .addToBackStack(Constants.REPORT_HEBDO_AM_PARCOURS_FIDEL)
                            .replace(R.id.fragment_container_report_hebdo, activiteMeneZoneProfondFragment)
                            .commit();
                } else {
                    Toast.makeText(getActivity(), "Vous devez entrer votre parcours de fidélisation", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnBackToActionPharma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return vAMParFidel;
    }
}
