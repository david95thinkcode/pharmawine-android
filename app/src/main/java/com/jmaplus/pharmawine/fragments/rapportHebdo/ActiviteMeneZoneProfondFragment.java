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


public class ActiviteMeneZoneProfondFragment extends Fragment {

    SharedPreferences spAMZoneProfond;
    SharedPreferences.Editor epAMZoneProfond;
    private EditText edAMZoneProfond;
    private Button btnSaveZoneProfond;
    private String amZoneProfond;


    public ActiviteMeneZoneProfondFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spAMZoneProfond = PreferenceManager.getDefaultSharedPreferences(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vAMZoneProfond = inflater.inflate(R.layout.fragment_activite_mene_zone_profond, container, false);
        edAMZoneProfond = vAMZoneProfond.findViewById(R.id.ed_zone_profonde);
        btnSaveZoneProfond = vAMZoneProfond.findViewById(R.id.btn_valide_action_mene);


        btnSaveZoneProfond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amZoneProfond = edAMZoneProfond.getText().toString();
                if (!amZoneProfond.isEmpty()) {
                    epAMZoneProfond = spAMZoneProfond.edit();
                    epAMZoneProfond.putString(Constants.REPORT_HEBDO_AM_ZONE_PROFOND, amZoneProfond);
                    epAMZoneProfond.apply();
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Vous devez entrer les zones profondes", Toast.LENGTH_LONG).show();
                }

            }
        });


        return vAMZoneProfond;
    }

}
