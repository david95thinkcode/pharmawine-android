package com.jmaplus.pharmawine.fragments.planning;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.adapters.PlanningAdapter;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.Utils;
import com.jmaplus.pharmawine.utils.WeekStartEnd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * Le contenu de ce fragment depends du role de l'utilisateur qui y accède
 *
 * - On demande a l'initialisation le role de l'utilisateur actuel
 * - Lorsque l'activite parente reponds, on enregistre le role
 * - Ensuite on demande a l'activite parente de nous envoyer le planning de la semaine
 * - Lorsque l'activite parente nous renvoie des donnees non vides, on mets a jour la recycler view
 *
 * NB: L'utilisateur, depuis l'activite parente peut modifier la date du fragment
 * Des que la date du fragment est modifiée, on lance une requete vers l'activite
 * pour nous retourner le planning
 *
 */
public class VisitFragment extends Fragment {

    public static final String TAG = "VisitFragment";
    public static final String USER_ROLE_KEY = "USER_ROLE_KEY";

    public List<Customer> mCustomerList = new ArrayList();
    public Context mContext;
    /**
     * Date du fragment
     */
    public String mDateString;
    private WeekStartEnd mWeek;
    private OnFragmentInteractionListener mListener;
    private PlanningAdapter mAdapter;
    /**
     * Role de l'utilisateur
     */
    private int mUserRoleID = 0;

    private LinearLayout cvSelectDate;
    private TextView tvMonthLabel;
    private TextView mWeekTextView;
    private ProgressBar mProgressBar;
    private RecyclerView mPlanningRv;
    private RecyclerView mSupervisorRv;
    private RecyclerView mDelegueRv;

    public VisitFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visit, container, false);
        mWeek = new WeekStartEnd(Calendar.getInstance().getTime());

        mContext = requireContext();
        tvMonthLabel = rootView.findViewById(R.id.tv_planning_date);
        cvSelectDate = rootView.findViewById(R.id.cv_planning_visit_date);
        mWeekTextView = rootView.findViewById(R.id.tv_datesInterval);
        mProgressBar = rootView.findViewById(R.id.progressBar);
        mPlanningRv = rootView.findViewById(R.id.rv_planning);
        mProgressBar.setVisibility(View.GONE);

        init();

        return rootView;
    }

    private void init() {
        mAdapter = new PlanningAdapter(mContext, mCustomerList);
        mPlanningRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        mPlanningRv.setAdapter(mAdapter);

        // Let's request parent activity to send us user role ID
        mDateString = Utils.getCurrentDate(); // important
        mListener.onRequestUserRoleID();
    }

    public void setUserRoleIDFromSource(Integer userRoleID) {
        // Here we get user role ID
        mUserRoleID = userRoleID;

        switch (mUserRoleID) {
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
                Log.i(TAG, "onCreateView: Unknown user role ID ==> " + mUserRoleID);
            }
            break;

        }
    }

    /**
     * That method get the selected date from the parent activity.
     * Get end day of the week and finally
     * send a request to the parent activity to fetch Customers
     * in the planning
     *
     * @param date
     */
    public void setDateString(Date date) {
        /**
         * 1- Get all days in the same week as the received date
         * 2- Filter the previous list and get only the dates withing the same month
         * 3- Get the first and the last day of the week as string inf format YYYY-MM-DD
         * 4- Call retrofit and send these two dates to the server
         */

        if (date != null) {

            if (mUserRoleID == Constants.ROLE_DELEGUE_KEY) {

                mDateString = Utils.getFormattedDateForApiRequest(date);

                mWeek = new WeekStartEnd(date);

                fetchDeleguePlanning();
            }
        }
    }

    private void fetchDeleguePlanning() {
        Log.i(TAG, "fetchDeleguePlanning: Fetching datas...");

        mListener.onRequestPlanning(
                Utils.getFormattedDateForApiRequest(mWeek.getStartDate()),
                Utils.getFormattedDateForApiRequest(mWeek.getEndDate()));

        updateUIDuringFetching();
    }

    /**
     * The only method used to populate the customers list
     *
     * @param customers
     */
    public void populateCustomerListFromSource(List<Customer> customers) {
        // Here we get the customers list
        if (!customers.isEmpty()) {
            mCustomerList.clear();
            mAdapter.notifyDataSetChanged();

            for (Customer c : customers) {
                mCustomerList.add(c);
                mAdapter.notifyItemInserted(mCustomerList.size() - 1);
            }

            updateLabel();

        } else {
            Toast.makeText(mContext, "Planning vide", Toast.LENGTH_SHORT).show();
        }
        updateUIWhenDataFetched();
    }

    private void updateLabel() {
        // Update date label on fragment
        String startInterval = Utils.getDayLabelFromDate(mWeek.getStartDate())
                + " " + Utils.getDayOfMonthFromDate(mWeek.getStartDate());
        String endInterval = Utils.getDayLabelFromDate(mWeek.getEndDate())
                + " " + Utils.getDayOfMonthFromDate(mWeek.getEndDate());

        mWeekTextView.setText(startInterval + " - " + endInterval);
    }



    private void fetchPlanningForSupervisor() {
        // TODO: Complete when the api will be ready
        Log.i(TAG, "fetchPlanningForSupervisor: Fetching datas...");

        updateUIDuringFetching();
    }

    private void fetchPlanningForAdmin() {
        // TODO: Complete when the api will be ready
        Log.i(TAG, "fetchPlanningForAdmin: Fetching datas...");

        updateUIDuringFetching();
    }

    private void updateUIDuringFetching() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void updateUIWhenDataFetched() {
        mProgressBar.setVisibility(View.GONE);
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

        void onRequestUserRoleID();

        void onRequestPlanning(String startDate, String EndDate);
    }

}
