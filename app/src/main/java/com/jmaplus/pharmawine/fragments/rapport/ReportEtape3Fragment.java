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

public class ReportEtape3Fragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Button nextBtn;
    private Button prevBtn;
    private EditText objectifEditText;
    private String mObjectif = new String();

    public ReportEtape3Fragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_report_etape3,
                container, false);

        objectifEditText = rootView.findViewById(R.id.ed_objectif_visit);
        prevBtn = rootView.findViewById(R.id.btn_precedent_etape_3_to_2);
        nextBtn = rootView.findViewById(R.id.btn_suivant_etape_3_to_4);

        setUpEvents();

        return rootView;
    }

    private void setUpEvents() {
        nextBtn.setOnClickListener(this);
        prevBtn.setOnClickListener(this);


        objectifEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mObjectif = s.toString();
                mListener.onPurposeUpdated(mObjectif);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == prevBtn.getId()) {
            mListener.onReturnToStep2();
        } else if (v.getId() == nextBtn.getId()) {
            mObjectif = objectifEditText.getText().toString();
            mListener.onStep3Finished(mObjectif);
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
        void onStep3Finished(String purposeOfTheVisit);

        void onPurposeUpdated(String updatedPurposeOfTheVisit);

        void onReturnToStep2();
    }
}
