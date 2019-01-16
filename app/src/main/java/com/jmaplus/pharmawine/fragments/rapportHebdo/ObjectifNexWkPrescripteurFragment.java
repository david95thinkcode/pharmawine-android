package com.jmaplus.pharmawine.fragments.rapportHebdo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;


public class ObjectifNexWkPrescripteurFragment extends Fragment {

    SharedPreferences spPrecipteurs;
    SharedPreferences.Editor epPrescripteur;
    private EditText edPrecripteur;
    private Button btnSavePrescipteurAndGotoParma;
    private String prescripteurs;
    public ObjectifNexWkPrescripteurFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spPrecipteurs = PreferenceManager.getDefaultSharedPreferences(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewPrescripteur = inflater.inflate(R.layout.fragment_objectif_nex_wk_prescripteur, container, false);
        edPrecripteur = viewPrescripteur.findViewById(R.id.ed_prescripteurs);
        btnSavePrescipteurAndGotoParma = viewPrescripteur.findViewById(R.id.btn_save_prescripteur);


        btnSavePrescipteurAndGotoParma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                prescripteurs = edPrecripteur.getText().toString();
                Log.e(getClass().getName(), prescripteurs);
                if (prescripteurs.isEmpty()) {
                    Toast.makeText(getActivity(), "Vous devez entrer les prescripteurs", Toast.LENGTH_LONG).show();
                } else {
                    epPrescripteur = spPrecipteurs.edit();
                    epPrescripteur.putString(Constants.REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PRESCRIPTEUR, prescripteurs);
                    epPrescripteur.apply();
                    Log.e(getClass().getName(), prescripteurs);
                    ObjectifNexWkPharmaFragment objectifNexWkPharmaFragment = new ObjectifNexWkPharmaFragment();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_report_hebdo, objectifNexWkPharmaFragment)
                            .addToBackStack(Constants.REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PRESCRIPTEUR)
                            .commit();


                }
            }
        });

        return viewPrescripteur;
    }


    /*
     *TODO: Return a string ObjectifNextWeekPrescipteur to Fragment ObjectifNexWkPharmaFragment
     */

}
