package com.jmaplus.pharmawine.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.fragments.rapport.VisiteInProgressFragment;
import com.jmaplus.pharmawine.models.Visite;
import com.jmaplus.pharmawine.utils.Constants;


public class VisiteInProgressActivity extends AppCompatActivity
        implements VisiteInProgressFragment.OnFragmentInteractionListener {

    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visite_in_progress);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Creating an instance of the fragment and its bundle
        VisiteInProgressFragment fragment = new VisiteInProgressFragment();
        Bundle args = new Bundle();

        // Populating arguments bundle
        args.putString(VisiteInProgressFragment.ARGS_PROSPECT_TYPE, getIntent().getStringExtra(VisiteInProgressFragment.ARGS_PROSPECT_TYPE));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_ID_KEY, getIntent().getStringExtra(Constants.CLIENT_ID_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_FIRSTNAME_KEY, getIntent().getStringExtra(Constants.CLIENT_FIRSTNAME_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_LASTNAME_KEY, getIntent().getStringExtra(Constants.CLIENT_LASTNAME_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_AVATAR_UTL_KEY, getIntent().getStringExtra(Constants.CLIENT_AVATAR_URL_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_STATUS_KEY, getIntent().getStringExtra(Constants.CLIENT_STATUS_KEY));
        args.putString(VisiteInProgressFragment.ARGS_CLIENT_SPECIALITY_KEY, getIntent().getStringExtra(Constants.CLIENT_SPECIALITY_KEY));

        // Passing arguments to the fragment
        fragment.setArguments(args);

        // Adding the fragment to the activity
        fragmentTransaction.add(R.id.fragment_container_visite_in_progress, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onVisiteFinished(Visite v) {


        // TODO: Go to edit rapport activity and pass the client ID to it
        // TODO : #Waiting for Waliss work

        Log.e(getLocalClassName(), v.toString());

    }
}
