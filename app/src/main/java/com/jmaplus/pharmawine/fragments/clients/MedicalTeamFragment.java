package com.jmaplus.pharmawine.fragments.clients;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.RemainingCustomersAdapter;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.CustomerCalls;
import com.jmaplus.pharmawine.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedicalTeamFragment extends Fragment implements
        CustomerCalls.Callbacks {

    private final String TAG = this.getClass().getSimpleName();
    private static final String KEY_LAYOUT_POSITION = "layoutPosition";
    private OnFragmentInteractionListener mListener;


    private List<Customer> mCustomersList;
    private List<Customer> mSafeCustomersList;
    private RemainingCustomersAdapter mAdapter;
    private Context mContext;
    private AuthUser mAuthUser;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    public MedicalTeamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clients_medical_team, container, false);
        setHasOptionsMenu(true);

        mContext = requireContext();
        mAuthUser = AuthUser.getAuthenticatedUser(mContext);
        mRecyclerView = view.findViewById(R.id.rv_customers2);
        mProgressBar = view.findViewById(R.id.pb_customers2);

        // Initializations
        mCustomersList = new ArrayList();
        mSafeCustomersList = new ArrayList();
        mAdapter = new RemainingCustomersAdapter(mContext, mCustomersList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        getCustomers();
        return view;
    }

    private void getCustomers() {
        mProgressBar.setVisibility(View.VISIBLE);
        CustomerCalls.getAllKnownProspects(AuthUser.getToken(mContext), this);

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
            case R.id.action_pharma: {
                Log.i(TAG, "onOptionsItemSelected: Pharmacye clicked");
                filteredCustomerListByCustomerType(Constants.TYPE_PHARMACEUTICAL_KEY);
            }
            break;
            case R.id.action_favori:
                Toast.makeText(mContext, "Option non disponible", Toast.LENGTH_LONG);
//                mListener.showFragment(mFavoritesFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    private void filteredCustomerListByCustomerType(Integer customerTypeID) {
        if (!mCustomersList.isEmpty()) {
            List<Customer> newList = new ArrayList();

            for (Customer c : mCustomersList) {
                if (c.getCustomerTypeId() == customerTypeID)
                    newList.add(c);
            }
            refreshRecyclerViewWithNewList(newList);
        } else {
            Utils.presentToast(mContext, "Rien a filtrer", true);
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

    private void refreshRecyclerViewWithNewList(List<Customer> newCustomerList) {
        Log.i(TAG, "refreshRecyclerViewWithNewList: ==> " + newCustomerList);
        if (!newCustomerList.isEmpty()) {
            mProgressBar.setVisibility(View.GONE);

            mCustomersList.clear();
            mCustomersList.addAll(newCustomerList);
            mAdapter.notifyDataSetChanged();

        } else {
            Utils.presentToast(mContext, "Liste vide", true);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCustomerDetailsResponse(@javax.annotation.Nullable Customer customer) {

    }

    @Override
    public void onCustomerDetailsFailure() {

    }

    @Override
    public void onKnownProspectResponse(@javax.annotation.Nullable List<Customer> customers) {
        if (!customers.isEmpty()) {

            mCustomersList.clear();
            mSafeCustomersList.clear();

            for (Customer c : customers) {
                mCustomersList.add(c);
                mAdapter.notifyItemInserted(customers.size() - 1);
            }
            mSafeCustomersList.addAll(mCustomersList); // important
        } else {
            mCustomersList.clear();
            mSafeCustomersList.clear();
        }

        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onKnownProspectFailure() {
        Utils.presentToast(mContext, "Une erreur s'est produite", true);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRemainingCustomersResponse(@Nullable List<Customer> customers) {

    }

    @Override
    public void onRemainingCustomersFailure() {

    }

    public interface OnFragmentInteractionListener {

        void showFragment(Fragment f);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_LAYOUT_POSITION, mRecyclerView.getVerticalScrollbarPosition());
        super.onSaveInstanceState(outState);
    }

}
