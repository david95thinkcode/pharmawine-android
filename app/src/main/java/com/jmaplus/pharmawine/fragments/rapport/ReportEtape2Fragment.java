package com.jmaplus.pharmawine.fragments.rapport;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.jmaplus.pharmawine.R;

public class ReportEtape2Fragment extends Fragment implements
        View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Spinner zoneSpinner;
    private Button nextBtn;
    private Button prevBtn;
    private String mZone = new String();
    private Context mParentContext;

    public ReportEtape2Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_etape2,
                container, false);

        mParentContext = requireContext();

        zoneSpinner = rootView.findViewById(R.id.choix_zone_pour_rapport);
        nextBtn = rootView.findViewById(R.id.btn_suivant_etape_2_to_3);
        prevBtn = rootView.findViewById(R.id.btn_precedent_etape_2_to_1);

        setUpEvents();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mParentContext,
                R.array.zones_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        zoneSpinner.setAdapter(adapter);

        return rootView;
    }

    public void setUpEvents() {
        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);

        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mZone = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == prevBtn.getId()) {
            mListener.onReturnToStep1();
        } else if (v.getId() == nextBtn.getId()) {
            mListener.onStep2Finished(mZone);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onStep2Finished(String zone);

        void onReturnToStep1();
    }
}
