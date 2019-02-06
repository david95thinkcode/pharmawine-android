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


public class ObjectifNexWkPharmaFragment extends Fragment {

    private SharedPreferences spObjPharma;
    private Button btnGoBackPrescipteur, btnValiderpharma;
    private EditText edPharma;
    private String objectifPharmacie;



    public ObjectifNexWkPharmaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spObjPharma = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewPharma = inflater.inflate(R.layout.fragment_objectif_nex_wk_pharma, container, false);
        edPharma = viewPharma.findViewById(R.id.ed_objectif_next_week_pharma);
        btnGoBackPrescipteur = viewPharma.findViewById(R.id.btn_back_to_prescipteur);
        btnValiderpharma = viewPharma.findViewById(R.id.btn_valide_objNextWeek);


        btnGoBackPrescipteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Back to ObjectifNexWkPrescripteurFragment
                getActivity().onBackPressed();
            }
        });


        btnValiderpharma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                objectifPharmacie = edPharma.getText().toString();
                if (!objectifPharmacie.isEmpty()) {
                    spObjPharma.edit().putString(Constants.REPORT_HEBDO_OBJECTIF_NEXT_WEEK_PHARMACY, objectifPharmacie).apply();
                    Log.e(getClass().getName(), objectifPharmacie);
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "Vous devez entrer les objectifs pharmacies", Toast.LENGTH_LONG).show();

                }
            }
        });

        return viewPharma;
    }
}
