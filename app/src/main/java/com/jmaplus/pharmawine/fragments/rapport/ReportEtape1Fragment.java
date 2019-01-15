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
import com.jmaplus.pharmawine.utils.Utils;


public class ReportEtape1Fragment extends Fragment {

    private static final String TAG = "ReportEtape1Fragment";
    private OnFragmentInteractionListener mListener;
    private Spinner centreSpinner;
    private Button nextBtn;
    private Context mParentContext;

    private String mCentre = "";

    public ReportEtape1Fragment() {
        // this constructor should be empty
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_report_etape1, container, false);

        mParentContext = requireContext();

        // binding views
        centreSpinner = rootView.findViewById(R.id.sp_choix_centre_pour_rapport);
        nextBtn = rootView.findViewById(R.id.btn_suivant_etape_1);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mParentContext,
                R.array.centres_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        centreSpinner.setAdapter(adapter);

        setUpEvents();

        return rootView;
    }

    public void setUpEvents() {

        // events
        centreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCentre = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCentre.isEmpty() || mCentre != null) {
                    mListener.onStep1Finished(mCentre);
                } else {
                    Utils.presentToast(requireContext(),
                            "Vous devez selectionner le centre",
                            true);
                }

            }
        });
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
        void onStep1Finished(String center);
    }
}
