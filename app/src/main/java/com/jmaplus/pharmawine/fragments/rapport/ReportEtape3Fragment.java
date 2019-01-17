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
    private Button prevBtn;
    private Button nextBtn;
    private EditText promesseEditText;
    private String mPromesse = new String();

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_report_etape4, container, false);

        prevBtn = rootView.findViewById(R.id.btn_precedent_etape_4_to_3);
        nextBtn = rootView.findViewById(R.id.btn_suivant_etape_4_to_5);
        promesseEditText = rootView.findViewById(R.id.ed_promesse_obtenue);

        prevBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);

        promesseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPromesse = s.toString();
                mListener.onPromeseUpdated(mPromesse);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == prevBtn.getId()) {
            mListener.onReturnToStep2();
        } else if (v.getId() == nextBtn.getId()) {
            mPromesse = promesseEditText.getText().toString();
            mListener.onStep3Finished(mPromesse);
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
        void onStep3Finished(String promesesHeld);

        void onPromeseUpdated(String updatedPromes);

        void onReturnToStep2();
    }
}
