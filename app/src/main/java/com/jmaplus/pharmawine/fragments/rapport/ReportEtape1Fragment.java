package com.jmaplus.pharmawine.fragments.rapport;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.MedicalTeamCenterSelectionAdapter;
import com.jmaplus.pharmawine.models.Center;
import com.jmaplus.pharmawine.models.MedicalCenter;
import com.jmaplus.pharmawine.utils.FakeData;
import com.jmaplus.pharmawine.utils.ItemClickSupport;
import com.jmaplus.pharmawine.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class ReportEtape1Fragment extends Fragment {

    private static final String TAG = "ReportEtape1Fragment";
    private OnFragmentInteractionListener mListener;
    private RecyclerView centersRecyclerView;
    private Button nextBtn;
    private Context mParentContext;
    private MedicalTeamCenterSelectionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Center> mCentersList;

    private Center mCentre;

    public ReportEtape1Fragment() {
        // this constructor should be empty
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_report_etape1, container, false);

        // binding views
        centersRecyclerView = rootView.findViewById(R.id.rv_centers);
        nextBtn = rootView.findViewById(R.id.btn_suivant_etape_1);

        Initialise();

        fetchCenters();

        return rootView;
    }

    private void Initialise() {

        mCentre = new Center();

        setUpEvents();

        configureOnClickRecyclerView();

        mParentContext = requireContext();
        mCentersList = new ArrayList();

        // Setting required thins for recycler view
        mAdapter = new MedicalTeamCenterSelectionAdapter(mCentersList);
        mLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        centersRecyclerView.setLayoutManager(mLayoutManager);
        centersRecyclerView.setAdapter(mAdapter);
    }

    private void configureOnClickRecyclerView() {
        ItemClickSupport
                .addTo(centersRecyclerView, R.layout.client_row_without_progression)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        mCentre = mCentersList.get(position);

                        mListener.onStep1Finished(mCentersList.get(position));
                    }
                });
    }

    private void fetchCenters() {

        // TODO: use api call
        mListener.onRequestGetCenters();


//        // Using fake data below
//        for (MedicalCenter c : FakeData.getCenters()) {
//            mCentersList.add(c);
//            mAdapter.notifyItemInserted(mCentersList.size() - 1);
//        }
    }

    public void populateCenters(List<Center> centers) {
        mCentersList.clear();
        mAdapter.notifyDataSetChanged();

        for (Center i: centers) {
            mCentersList.add(i);
            mAdapter.notifyItemInserted(mCentersList.size() - 1);
        }

        // Update
    }

    public void setUpEvents() {

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCentre != null) {
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

        void onRequestGetCenters();

        void onStep1Finished(Center center);
    }
}
