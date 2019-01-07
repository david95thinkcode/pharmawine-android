package com.jmaplus.pharmawine.fragments.planning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmaplus.pharmawine.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesGoalsFragment extends Fragment {


    public SalesGoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales_goals, container, false);
    }

}
