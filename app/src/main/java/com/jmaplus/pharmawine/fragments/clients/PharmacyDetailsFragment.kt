package com.jmaplus.pharmawine.fragments.clients

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Client
import de.hdodenhof.circleimageview.CircleImageView

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PharmacyDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class PharmacyDetailsFragment : Fragment(), View.OnClickListener {

    private var listener: OnFragmentInteractionListener? = null
    private var pharmacy: Client = Client()

    private lateinit var tvRepresentative: TextView
    private lateinit var tvFounder: TextView
    private lateinit var tvCreatedOn: TextView
    private lateinit var tvNbEmployees: TextView
    private lateinit var tvAnnexe: TextView
    private lateinit var tvPharmaAddress: TextView

    private lateinit var imgProfile: CircleImageView
    private lateinit var tvClientName: TextView
    private lateinit var tvClientCategory: TextView
    private lateinit var tvClientType: TextView
    private lateinit var tvProfileProgress: TextView
    private lateinit var profileProgress: RoundCornerProgressBar
    private lateinit var btnCall1: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_pharmacy_details, container, false)
        imgProfile = rootView.findViewById(R.id.img_profile_picture)
        tvClientName = rootView.findViewById(R.id.tv_i_client_name)
        tvClientCategory = rootView.findViewById(R.id.tv_i_client_category)
        tvClientType = rootView.findViewById(R.id.tv_i_client_type)
        tvProfileProgress = rootView.findViewById(R.id.tv_i_client_progress)
        profileProgress = rootView.findViewById(R.id.progress_client_i_filling)
        tvRepresentative = rootView.findViewById(R.id.tv_pharma_representative)
        tvFounder = rootView.findViewById(R.id.tv_pharma_founder)
        tvCreatedOn = rootView.findViewById(R.id.tv_pharma_created_on)
        tvNbEmployees = rootView.findViewById(R.id.tv_pharma_employees)
        tvAnnexe = rootView.findViewById(R.id.tv_pharma_annexe)
        tvPharmaAddress = rootView.findViewById(R.id.tv_pharma_address)
        btnCall1 = rootView.findViewById(R.id.btn_call_1)

        getPharmacyDetails()

        return rootView
    }

    private fun getPharmacyDetails() {
        // TODO: call the api to get pharmacy details

        // When the details is successfully fetched
        listener?.onClientDetailsReceived(pharmacy)
        updateViewsWithDatas()
    }

    private fun updateViewsWithDatas() {

        // TODO: Review  details about pharmacy model

        /*
        tvRepresentative.setText(pharmacy.getRepresentative())
        tvFounder.setText(pharmacy.getFounder())
        tvCreatedOn.setText(pharmacy.getCreatedOn())
        tvNbEmployees.setText(String.valueOf(pharmacy.getEmployees()))
        tvAnnexe.setText(String.valueOf(pharmacy.getAnnexe()))
        tvPharmaAddress.setText(pharmacy.getAddress())

        Glide.with(this).load(R.drawable.pharma_icon).into(imgProfile)

        tvClientName.setText(pharmacy.getName())
        tvClientCategory.setVisibility(View.GONE)
        profileProgress.setProgress(pharmacy.getFillingLevel())
        tvProfileProgress.setText(String.valueOf(pharmacy.getFillingLevel()).concat(" %"))

        var fillingLevel = pharmacy.getFillingLevel()

        if (fillingLevel < 35) {
            profileProgress.setProgressColor(getResources().getColor(R.color.red))
        } else if (fillingLevel < 69) {
            profileProgress.setProgressColor(getResources().getColor(R.color.orange))
        } else {
            profileProgress.setProgressColor(getResources().getColor(R.color.green))
        }


        tvRepresentative = findViewById(R.id.tv_pharma_representative)
        tvFounder = findViewById(R.id.tv_pharma_founder)
        tvCreatedOn = findViewById(R.id.tv_pharma_created_on)
        tvNbEmployees = findViewById(R.id.tv_pharma_employees)
        tvAnnexe = findViewById(R.id.tv_pharma_annexe)
        tvPharmaAddress = findViewById(R.id.tv_pharma_address)

        tvRepresentative.setText(pharmacy.getRepresentative())
        tvFounder.setText(pharmacy.getFounder())
        tvCreatedOn.setText(pharmacy.getCreatedOn())
        tvNbEmployees.setText(String.valueOf(pharmacy.getEmployees()))
        tvAnnexe.setText(String.valueOf(pharmacy.getAnnexe()))
        tvPharmaAddress.setText(pharmacy.getAddress())

        if (!pharmacy.getContact().isEmpty()) {
            btnCall1.setText(pharmacy.getContact())
            btnCall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeCall(pharmacy.getContact())
                }
            })
        } else {
            btnCall1.setText(R.string.call)
            btnCall1.setEnabled(false)
            btnCall1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)))
        }

        */

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            btnCall1.id -> {
                listener?.onPhoneNumberCallInteraction(pharmacy.phoneNumber.trim { it <= ' ' })
            }
            else -> { // Nothing to do}
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onPhoneNumberCallInteraction(phoneNumber: String)
        fun onClientDetailsReceived(clientDetails: Client)
    }

    companion object {
        private const val TAG = "PharmacyDetailsFragment"
    }
}
