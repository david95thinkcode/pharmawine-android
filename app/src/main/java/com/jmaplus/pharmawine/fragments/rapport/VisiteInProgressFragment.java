package com.jmaplus.pharmawine.fragments.rapport;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jmaplus.pharmawine.R;
import com.jmaplus.pharmawine.models.Visite;
import com.jmaplus.pharmawine.utils.Constants;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * This fragment require an argument
 * This argument is it's own final ARGS_PROSPECT_TYPE
 * If the argument is not passed to the fragment, an
 * Runtime  exception will be thrown
 */
public class VisiteInProgressFragment extends Fragment {

    public static final String ARGS_PROSPECT_TYPE = "ARGS_PROSPECT_TYPE";
    public static final String ARGS_CLIENT_ID_KEY = "ARGS_PROSPECT_TYPE";
    public static final String ARGS_CLIENT_FIRSTNAME_KEY = "ARGS_PROSPECT_TYPE";
    public static final String ARGS_CLIENT_LASTNAME_KEY = "ARGS_PROSPECT_TYPE";
    public static final String ARGS_CLIENT_SPECIALITY_KEY = "client_spec";
    public static final String ARGS_CLIENT_STATUS_KEY = "client_stat";
    public static final String ARGS_CLIENT_AVATAR_UTL_KEY = "ARGS_PROSPECT_TYPE";

    private OnFragmentInteractionListener mListener;
    private CircleImageView profileImage;
    private ImageView mImageViewProspectInconnu;
    private TextView tvNomPrenom, tvTypeClient, tvCategoryClient;
    private Button btnVisiteEnd;
    private Visite mVisite = new Visite();

    private String mProspectType = "";


    public VisiteInProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            mProspectType = getArguments().getString(ARGS_PROSPECT_TYPE);

            // Log prospect type info
            Log.w(getClass().getName(), "mProspectType ==> : " + mProspectType);

            switch (mProspectType) {
                case Constants.PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY: {
                    mVisite.getClient().setId(getArguments().getString(Constants.CLIENT_ID_KEY));
                    mVisite.getClient().setFirstName(getArguments().getString(Constants.CLIENT_FIRSTNAME_KEY));
                    mVisite.getClient().setLastName(getArguments().getString(Constants.CLIENT_LASTNAME_KEY));
                    mVisite.getClient().setAvatarUrl(getArguments().getString(Constants.CLIENT_AVATAR_URL_KEY));
                    mVisite.getClient().setStatus(getArguments().getString(Constants.CLIENT_STATUS_KEY));
                    mVisite.getClient().setSpeciality(getArguments().getString(Constants.CLIENT_SPECIALITY_KEY));
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

                }
                break;
            }

        } else {
            // Throw an exception
            throw new RuntimeException(requireContext().toString()
                    + " must get an ARGS_PROSPECT_TYPE argument");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visite_in_progress, container, false);

        // binding views
        mImageViewProspectInconnu = rootView.findViewById(R.id.iv_unknown_prospect);
        profileImage = rootView.findViewById(R.id.img_profil_client);
        tvNomPrenom = rootView.findViewById(R.id.tv_nom_client);
        tvTypeClient = rootView.findViewById(R.id.tv_type_client);
        tvCategoryClient = rootView.findViewById(R.id.tv_category_client);
        btnVisiteEnd = rootView.findViewById(R.id.btn_visit_fini);

        btnVisiteEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationDialogToEditRapport();
            }
        });

        updateViewsContent();

        return rootView;
    }

    private void updateViewsContent() {

        switch (mProspectType) {
            case Constants.PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY: {

                profileImage.setVisibility(View.VISIBLE);
                mImageViewProspectInconnu.setVisibility(View.INVISIBLE);

                tvNomPrenom.setText(mVisite.getClient().getFullName());
                tvTypeClient.setText(mVisite.getClient().getSpeciality());
                tvCategoryClient.setText(mVisite.getClient().getStatus());

                if (!mVisite.getClient().getAvatarUrl().isEmpty()) {
                    Glide.with(this).load(mVisite.getClient().getAvatarUrl()).into(profileImage);
                }
            }
            break;
            case Constants.PROSPECT_KNOWN_CLIENT_PHARMACY_TYPE_KEY: {

                profileImage.setVisibility(View.VISIBLE);
                mImageViewProspectInconnu.setVisibility(View.INVISIBLE);

                mImageViewProspectInconnu.setVisibility(View.INVISIBLE);

            }
            break;
            case Constants.PROSPECT_UNKNOWN_MEDICAL_TEAM_TYPE_KEY: {
                profileImage.setVisibility(View.INVISIBLE);
                mImageViewProspectInconnu.setVisibility(View.VISIBLE);

            }
            break;
            case Constants.PROSPECT_UNKNOWN_CLIENT_PHARMACY_TYPE_KEY: {
                profileImage.setVisibility(View.INVISIBLE);
                mImageViewProspectInconnu.setVisibility(View.VISIBLE);
            }
            break;
            default: {
                Log.e(getClass().getName(), "This prospect is not KNOWN MEDICAL TEAM");
            }
            break;
        }
    }

    private void confirmationDialogToEditRapport() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Visite terminée ?");
        builder.setMessage(R.string.msg_confim_visite_end);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // update mVisite endTime before call the listenner method
                mVisite.setEndTime(Calendar.getInstance().getTime().toString());

                mListener.onVisiteFinished(mVisite);
            }
        });

        builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        builder.show();
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

        void onVisiteFinished(Visite visiteEnCours);
    }
}
