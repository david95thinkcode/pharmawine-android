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


public class ActiviteMeneGardeFragment extends Fragment {

    private SharedPreferences spAMgarde;
    private EditText edAMgarde;
    private Button btnSaveAMgarde;
    private String amGarde;


    public ActiviteMeneGardeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spAMgarde = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vAMGarde = inflater.inflate(R.layout.fragment_activite_mene_garde, container, false);
        edAMgarde = vAMGarde.findViewById(R.id.ed_garde);
        btnSaveAMgarde = vAMGarde.findViewById(R.id.btn_save_garde);


        btnSaveAMgarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amGarde = edAMgarde.getText().toString();
                if (!amGarde.isEmpty()) {
                    spAMgarde.edit().putString(Constants.REPORT_HEBDO_AM_GARDE, amGarde).apply();
                    ActiviteMeneReunionFragment activiteMeneReunionFragment = new ActiviteMeneReunionFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_report_hebdo, activiteMeneReunionFragment)
                            .addToBackStack(Constants.REPORT_HEBDO_AM_GARDE)
                            .commit();

                } else {
                    Toast.makeText(getActivity(), "Vous devez entrer les gardes", Toast.LENGTH_LONG).show();

                }

            }
        });


        return vAMGarde;
    }


}
