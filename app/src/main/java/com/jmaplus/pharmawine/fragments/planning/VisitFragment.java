package com.jmaplus.pharmawine.fragments.planning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 *
 * Le contenu de ce fragment depends du role de l'utilisateur qui y accède
 * Le role de l'utilisateur est recu en argument de ce fragment
 * Si aucun argument n'est passé au fragment une RunTimeException est declenchée
 *
 * Quand l'utilisateur est :
 * - Delegue : Liste de son planning
 * - Superviseur: Liste de ces delegué. Le clic sur l'un affiche son planning de la semaine
 * - Admin : Todo
 */
public class VisitFragment extends Fragment {

    public static final String TAG = "VisitFragment";
    public static final String USER_ROLE_KEY = "USER_ROLE_KEY";

    private int userRole = -1;
    private RecyclerView mSupervisorRv;
    private RecyclerView mDelegueRv;

    public VisitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visit, container, false);

        if (getArguments() != null) {
            userRole = getArguments().getInt(USER_ROLE_KEY);
            initialiseViews();
            initialiseContent();
            initalizeListenners();
        } else {
            throw new RuntimeException(requireContext().toString()
                    + " must get at least USER_ROLE_KEY argument");
        }

        return rootView;
    }

    private void initalizeListenners() {
        configureOnClickRecyclerView();

    }

    private void initialiseViews() {
        // Initialise la recycler view pour le delegue
        // Initialise la recycler view pour le superviseur

    }

    private void initialiseContent() {
        switch (userRole) {
            case (Constants.ROLE_DELEGUE_KEY): {
                fetchDeleguePlanning();
            }
            break;
            case (Constants.ROLE_SUPERVISEUR_KEY): {
                fetchPlanningForSupervisor();
            }
            break;
            case (Constants.ROLE_ADMIN_KEY): {
                fetchPlanningForAdmin();
            }
            break;
            default: {
                Log.i(TAG, "onCreateView: Unknown user role ID ==> " + userRole);
            }
            break;

        }
    }

    private void configureOnClickRecyclerView() {

        switch (userRole) {
            case (Constants.ROLE_DELEGUE_KEY): {
                // TODO:

            }
            break;
            case (Constants.ROLE_SUPERVISEUR_KEY): {
                // TODO:

            }
            break;
            case (Constants.ROLE_ADMIN_KEY): {
                // Nothing yet
            }
            break;
            default: {
                Log.i(TAG, "configureOnClickRecyclerView: Unknown user role ID ==> " + userRole);
            }
            break;
        }

//        ItemClickSupport
//                .addTo(mRecyclerView, R.layout.client_row_without_progression)
//                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                    @Override
//                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                        ShowConfirmationToProgressPage(clientsList.get(position));
//                    }
//                });
    }

    private void fetchDeleguePlanning() {
        // TODO: Complete when the api will be ready
        Log.i(TAG, "fetchDeleguePlanning: Fetching datas...");

    }

    private void fetchPlanningForSupervisor() {
        // TODO: Complete when the api will be ready
        Log.i(TAG, "fetchPlanningForSupervisor: Fetching datas...");

    }

    private void fetchPlanningForAdmin() {
        // TODO: Complete when the api will be ready
        Log.i(TAG, "fetchPlanningForAdmin: Fetching datas...");

    }

}
