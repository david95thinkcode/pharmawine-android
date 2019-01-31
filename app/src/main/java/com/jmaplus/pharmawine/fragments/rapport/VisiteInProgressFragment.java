package com.jmaplus.pharmawine.fragments.rapport;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.activities.EditMedicalTeamActivity;
import com.jmaplus.pharmawine.models.AuthUser;
import com.jmaplus.pharmawine.models.Customer;
import com.jmaplus.pharmawine.models.DailyReportStart;
import com.jmaplus.pharmawine.models.DailyReportStartResponse;
import com.jmaplus.pharmawine.models.Visite;
import com.jmaplus.pharmawine.utils.Constants;
import com.jmaplus.pharmawine.utils.RetrofitCalls.DailyReportCalls;
import com.jmaplus.pharmawine.utils.Utils;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This fragment require an argument
 * This argument is it's own final ARGS_PROSPECT_TYPE
 * If the argument is not passed to the fragment, an
 * Runtime  exception will be thrown
 */
public class VisiteInProgressFragment extends Fragment
        implements DailyReportCalls.Callbacks {

    public static final String ARGS_PROSPECT_TYPE = "ARGS_PROSPECT_TYPE";
    public static final String ARGS_CLIENT_ID_KEY = "ARGS_CLIENT_ID_KEY";
    public static final String ARGS_CLIENT_FIRSTNAME_KEY = "ARGS_CLIENT_FIRSTNAME_KEY";
    public static final String ARGS_CLIENT_LASTNAME_KEY = "ARGS_CLIENT_LASTNAME_KEY";
    public static final String ARGS_CLIENT_SPECIALITY_KEY = "ARGS_CLIENT_SPECIALITY_KEY";
    public static final String ARGS_CLIENT_CUSTOMER_STATUS_KEY = "ARGS_CLIENT_CUSTOMER_STATUS_KEY";
    public static final String ARGS_CLIENT_CUSTOMER_TYPE_KEY = "ARGS_CLIENT_CUSTOMER_TYPE_KEY";
    public static final String ARGS_CLIENT_AVATAR_URL_KEY = "ARGS_CLIENT_AVATAR_URL_KEY";

    public static final String TAG = "VisInProgressFragment";

    private OnFragmentInteractionListener mListener;
    private CircleImageView profileImage;
    private ImageView mImageViewProspectInconnu;
    private TextView tvNomPrenom, tvCustomerSpeciality, tvCustomerStatus;
    private Button btnVisiteEnd;

    private DailyReportStart mDailyReportStart = new DailyReportStart();
    private Integer reportID = -1;

    private Visite mVisite = new Visite();

    private String mProspectType = "";


    public VisiteInProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visite_in_progress, container, false);

        // binding views
        mImageViewProspectInconnu = rootView.findViewById(R.id.iv_unknown_prospect);
        profileImage = rootView.findViewById(R.id.img_profil_client);
        tvNomPrenom = rootView.findViewById(R.id.tv_nom_client);
        tvCustomerSpeciality = rootView.findViewById(R.id.tv_type_client);
        tvCustomerStatus = rootView.findViewById(R.id.tv_category_client);
        btnVisiteEnd = rootView.findViewById(R.id.btn_visit_fini);

        btnVisiteEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialogToEditRapport();
            }
        });

        //
        Log.i(TAG, "Arguments is null ==> " + (getArguments() == null));
        Log.i(TAG, "Arguments ==> " + getArguments().toString());

        if (getArguments() != null) {

            mProspectType = getArguments().getString(ARGS_PROSPECT_TYPE);
            Log.i(getClass().getName(), "mProspectType ==> : " + mProspectType);

            // Obligatoire
            mDailyReportStart.setCustomerId(getArguments().getInt(ARGS_CLIENT_ID_KEY, -1));

            switch (mProspectType) {
                case Constants.PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY: {
                    mVisite.getClient().setFirstname(getArguments().getString(ARGS_CLIENT_FIRSTNAME_KEY));
                    mVisite.getClient().setLastname(getArguments().getString(ARGS_CLIENT_LASTNAME_KEY));
                    mVisite.getClient().setAvatar(getArguments().getString(ARGS_CLIENT_AVATAR_URL_KEY));
//                    mVisite.getClient().getCustomerStatus().setName(getArguments().getString(ARGS_CLIENT_CUSTOMER_STATUS_KEY));
//                    mVisite.getClient().getSpeciality().setName(getArguments().getString(ARGS_CLIENT_SPECIALITY_KEY));
                }
                break;
                case Constants.PROSPECT_KNOWN_CLIENT_PHARMACY_TYPE_KEY: {

                }
                break;
                case Constants.PROSPECT_UNKNOWN_MEDICAL_TEAM_TYPE_KEY: {

                }
                break;
                case Constants.PROSPECT_UNKNOWN_CLIENT_PHARMACY_TYPE_KEY: {

                }
                break;
                default: {
                    Log.e(VisiteInProgressFragment.class.getName(),
                            "OnCreate: anormal Prospect type ==> " + mProspectType);
                }
                break;
            }

        } else {
            // Throw an exception
            throw new RuntimeException(requireContext().toString()
                    + " must get an ARGS_PROSPECT_TYPE argument");
        }

        // Getting start time string
        mVisite.setStartTime(Calendar.getInstance().getTime().toString());

        updateViewsContent();

        notifyServerAboutReportStarted();

        return rootView;
    }

    private void notifyServerAboutReportStarted() {
        try {

            mDailyReportStart.setUserId(AuthUser.getAuthenticatedUser(requireContext()).getId());
            mDailyReportStart.setStartTime(Utils.getCurrentTime());

            if (mDailyReportStart.getCustomerId() != -1 && mDailyReportStart.getCustomerId() != null
                    && !mDailyReportStart.getStartTime().isEmpty()
                    && !mDailyReportStart.getUserId().toString().isEmpty()) {
                DailyReportCalls.postDailyReportStart(
                        AuthUser.getToken(requireContext()), this, mDailyReportStart);
            } else {
                Toast.makeText(requireContext(), "Donnees incompletes pour signaler le debut de visite", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "notifyServerAboutReportStarted: Objet incomplet => " + mDailyReportStart.toString());
            }

        } catch (Exception e) {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, "notifyServerAboutReportStarted: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onStartDailyReportResponse(@Nullable DailyReportStartResponse response) {
        reportID = response.getId();
        Toast.makeText(requireContext(), "Vos superieurs savent que vous commencez une visite", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStartDailyReportFailure() {
        Log.e(TAG, "onStartDailyReportFailure");
    }

    /**
     * Update views UI with source data
     *
     * @param customer
     */
    public void updateViewsWithSource(Customer customer) {

        if (customer.getCustomerStatus() != null && !customer.getCustomerStatus().getName().isEmpty()) {
            tvCustomerStatus.setText(customer.getCustomerStatus().getName());
        }

        if (customer.getSpeciality() != null && !customer.getSpeciality().getName().isEmpty()) {
            tvCustomerSpeciality.setText(customer.getSpeciality().getName());
        }

        Glide.with(this).load(customer.getDefaultAvatar()).into(profileImage);
    }

    private void updateViewsContent() {

        switch (mProspectType) {
            case Constants.PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY: {
                showCorrespondingViewForProfileImage(false);

                tvNomPrenom.setText(mVisite.getClient().getFullName());
            }
            break;
            case Constants.PROSPECT_KNOWN_CLIENT_PHARMACY_TYPE_KEY: {
                showCorrespondingViewForProfileImage(false);
            }
            break;
            case Constants.PROSPECT_UNKNOWN_MEDICAL_TEAM_TYPE_KEY: {
                showCorrespondingViewForProfileImage(true);
            }
            break;
            case Constants.PROSPECT_UNKNOWN_CLIENT_PHARMACY_TYPE_KEY: {
                showCorrespondingViewForProfileImage(true);
            }
            break;
            default: {
                Log.e(getClass().getName(), "updateViewsContent() : This prospect is suspect ");
            }
            break;
        }
    }

    private void showCorrespondingViewForProfileImage(Boolean estUnProspectInconnu) {
        if (estUnProspectInconnu) {
            profileImage.setVisibility(View.GONE);
            mImageViewProspectInconnu.setVisibility(View.VISIBLE);

            // Textview
            tvNomPrenom.setText(getResources().getString(R.string.prospect_inconnu));
            tvCustomerStatus.setVisibility(View.GONE);
            tvCustomerSpeciality.setVisibility(View.GONE);
        } else {
            // Pour les prospects connus
            profileImage.setVisibility(View.VISIBLE);
            mImageViewProspectInconnu.setVisibility(View.GONE);
        }
    }

    private void confirmationDialogToEditRapport() {
        LayoutInflater factory = LayoutInflater.from(requireContext());
        final View customDialogBoxView = factory.inflate(R.layout.custom_dialog_box, null);
        final AlertDialog customDialog = new AlertDialog.Builder(requireContext()).create();
        customDialog.setView(customDialogBoxView);
        TextView titleDialog = customDialogBoxView.findViewById(R.id.dialog_box_title);
        TextView msgDialog = customDialogBoxView.findViewById(R.id.dialog_box_content);
        msgDialog.setText(R.string.msg_confim_visite_end);
        titleDialog.setText(R.string.visite_terminee);
        customDialogBoxView.findViewById(R.id.yes_button_custom_dialogbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onVisiteEnded(reportID, Utils.getCurrentTime());
                customDialog.cancel();
            }
        });
        customDialogBoxView.findViewById(R.id.no_button_custom_dialogbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.cancel();
            }
        });

        customDialog.show();
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

        void onVisiteEnded(Integer reportID, String EndTime);
    }
}
