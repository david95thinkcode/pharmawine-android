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


public class ObjectionFragment extends Fragment {

    private SharedPreferences spObjection;
    private Button btnSaveObjection;
    private EditText edObjection;
    private String objections;


    public ObjectionFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spObjection = PreferenceManager.getDefaultSharedPreferences(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vObjection = inflater.inflate(R.layout.fragment_objection, container, false);
        edObjection = vObjection.findViewById(R.id.ed_objection);
        btnSaveObjection = vObjection.findViewById(R.id.btn_save_objection);

        btnSaveObjection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objections = edObjection.getText().toString();
                if (!objections.isEmpty()) {
                    spObjection.edit().putString(Constants.REPORT_HEBDO_OBJECTION, objections).apply();
                    Log.e(getClass().getName(), "Objection ReportH" + objections);
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), "Vous devez entrer vos objections, ecrire neant si aucune", Toast.LENGTH_LONG).show();
                }

            }
        });


        return vObjection;
    }
}
