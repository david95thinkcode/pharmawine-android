package com.jmaplus.pharmawine.fragments.clients;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jmaplus.pharmawine.PharmaWine;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.MainActivity;
import com.jmaplus.pharmawine.adapters.ClientAdapter;
import com.jmaplus.pharmawine.models.MedicalTeam;
import com.jmaplus.pharmawine.utils.PrefManager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalTeamFragment extends Fragment {

    private RecyclerView recyclerView;
    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private int mRecyclerViewPosition = 0;
    public PharmaciesFragment mPharmaciesFragment = new PharmaciesFragment();

    private ArrayList<MedicalTeam> medicalTeamList;
    public FavoritesFragment mFavoritesFragment = new FavoritesFragment();
    private ClientAdapter clientAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PrefManager prefManager;
    private OnFragmentInteractionListener mListener;
    private MainActivity mContext;

    public MedicalTeamFragment() {
        // Required empty public constructor
        medicalTeamList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clients_medical_team, container, false);

        setHasOptionsMenu(true);

        recyclerView = view.findViewById(R.id.rv_medical_team);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_medical_team);
        mSwipeRefreshLayout.setRefreshing(false);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.clients_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search: {
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
            }
            break;
//            case R.id.action_pharma:
//                mListener.showFragment(mPharmaciesFragment);
//                break;
//            case R.id.action_favori:
//                mListener.showFragment(mFavoritesFragment);
//                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = (MainActivity) getActivity();
        prefManager = new PrefManager(mContext);

//        Set the adapter
        clientAdapter = new ClientAdapter(medicalTeamList, null, mContext, ClientAdapter.MEDICAL_TEAM);

//        Set the adapter to recycler
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(clientAdapter);

//        Get the medical team's list
        getMedicalTeamList();

//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getMedicalTeamList();
//            }
//        });
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

        void showFragment(Fragment f);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_POSITION, recyclerView.getVerticalScrollbarPosition());
        super.onSaveInstanceState(outState);
    }

    private void getMedicalTeamList() {

        medicalTeamList.clear();
        medicalTeamList.addAll(MedicalTeam.getAll(PharmaWine.mRealm));
        clientAdapter.notifyDataSetChanged();

//        Get the fresh client's list if not loaded & connected

//        if (!mContext.isMedicalTeamsLoaded) {
//
//            if (PharmaWine.getInstance().isOnline()) {
//
//                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                Call<MedicalClientsResponse> call = apiService.getDelegateMedicalTeamCustomers(AuthenticatedUser.getAuthenticatedUser(PharmaWine.mRealm).getId(), ApiClient.TOKEN_TYPE + prefManager.getToken());
//
//                mSwipeRefreshLayout.setRefreshing(true);
//                call.enqueue(new Callback<MedicalClientsResponse>() {
//                    @Override
//                    public void onResponse(Call<MedicalClientsResponse> call, Response<MedicalClientsResponse> response) {
//
//                        if (!response.isSuccessful() || response.body() == null) {
//                            Toast.makeText(mContext, "Response not successful", Toast.LENGTH_SHORT).show();
////                            Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            if (response.body().getStatusCode() == 200 && response.body().getMedicalTeams() != null) {
//
//                                MedicalTeam.saveAll(PharmaWine.mRealm, response.body().getMedicalTeams(), null);
//                                medicalTeamList.clear();
//                                medicalTeamList.addAll(response.body().getMedicalTeams());
//                                mContext.isMedicalTeamsLoaded = true;
//
//                            } else {
////                                Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
//                                Snackbar.make(mSwipeRefreshLayout, getResources().getString(R.string.no_data_returned), Snackbar.LENGTH_LONG).show();
//                            }
//                        }
//                        //Notify changes
//                        notifyChanges();
//                    }
//
//                    @Override
//                    public void onFailure(Call<MedicalClientsResponse> call, Throwable t) {
//                        Toast.makeText(mContext, "Echec de la requete", Toast.LENGTH_SHORT).show();
////                        Toast.makeText(mContext, getResources().getString(R.string.smthg_wrong_request), Toast.LENGTH_SHORT).show();
//                        notifyChanges();
//                    }
//                });
//
//            } else {
//                Toast.makeText(mContext, mContext.getResources().getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
//                notifyChanges();
//            }
//        }

    }

    private void notifyChanges() {
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
        clientAdapter.notifyDataSetChanged();
    }


}
