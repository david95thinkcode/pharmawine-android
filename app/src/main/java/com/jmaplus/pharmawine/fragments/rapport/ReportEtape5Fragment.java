package com.jmaplus.pharmawine.fragments.rapport;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jmaplus.pharmawine.R;

public class ReportEtape5Fragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Button prevBtn;
    private Button nextBtn;
    private EditText prescriptionEditText;
    private String mPrescription = new String();

    public ReportEtape5Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_etape5,
                container, false);

        nextBtn = rootView.findViewById(R.id.btn_terminer_report_etape_5);
        prescriptionEditText = rootView.findViewById(R.id.ed_prescription_constate);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrescription = prescriptionEditText.getText().toString();
                mListener.onStep5Finished(mPrescription);
            }
        });

        prescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPrescription = s.toString();
                mListener.onPrescriptionUpdated(mPrescription);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
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

        void onPrescriptionUpdated(String updatedPrescription);

        void onStep5Finished(String prescribedRequirements);
    }
}
