package com.jmaplus.pharmawine.fragments.rapport;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.jmaplus.pharmawine.R;

import java.util.ArrayList;
import java.util.List;


public class ReportEtape1Fragment extends Fragment {

    private Spinner zoneSpinner;
    private Button btnE1toE2Report;

    public ReportEtape1Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fre1View = inflater.inflate(R.layout.fragment_report_etape1, container, false);
        zoneSpinner = fre1View.findViewById(R.id.sp_choix_centre_pour_rapport);

        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Centre1");
        spinnerArray.add("Centre2");
        spinnerArray.add("Centre3");

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(fre1View.getContext(), spinnerArray.size(), android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        zoneSpinner.setAdapter(adapter);


        return fre1View;

    }


}
