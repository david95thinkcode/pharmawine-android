package com.jmaplus.pharmawine.fragments.clients

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.activities.ClientDetailsActivity
import com.jmaplus.pharmawine.models.Client
import com.jmaplus.pharmawine.models.Customer
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
    private var mCustomer: Customer = Customer()
    private lateinit var mContext: Context


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

        mContext = requireContext()

        var gson = Gson()
        var customerStringFronArguments = arguments?.getString(ClientDetailsActivity.CUSTOMER_OBJECT_EXTRA)
        mCustomer = gson.fromJson(customerStringFronArguments, Customer::class.java)

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

//        getPharmacyDetails()
        if (mCustomer == null) listener?.onRequestCustomerPharmacyObjectForUIInitialization()
        else updateUI()

        return rootView
    }

    fun updateCustomersWith(customer: Customer) {
        updateUI()
    }

    private fun updateUI() {

        // Client avatar
        if (!mCustomer.sex.isNullOrEmpty() && mCustomer.sex.toUpperCase() == "F") {
            Glide.with(mContext).load(R.drawable.bg_avatar_pharmacy).into(imgProfile)
        }

        // Representant name
        tvRepresentative.text = mCustomer.fullName

        // TODO: Founder name
        tvFounder.text = resources.getString(R.string.non_defini)
        // tvRepresentative.text = mCustomer.fullName

        tvPharmaAddress.text = mCustomer.address
        tvCreatedOn.text = mCustomer.redabledate

        // Progress bar
        profileProgress.progress = mCustomer.getFillingLevel().toFloat()
        tvProfileProgress.text = mCustomer.getFillingLevel().toString() + " %"

        val fillingLevel = mCustomer.fillingLevel

        if (fillingLevel < 35) {
            profileProgress.progressColor = resources.getColor(R.color.red)
        } else if (fillingLevel < 69) {
            profileProgress.progressColor = resources.getColor(R.color.orange)
        } else {
            profileProgress.progressColor = resources.getColor(R.color.green)
        }

        // Speciality
        if (mCustomer.speciality != null && !mCustomer.speciality.name.isNullOrEmpty()) {
            tvClientCategory.text = mCustomer.speciality.name
        } else {
            tvClientCategory.text = resources.getString(R.string.non_defini)
        }

        // Nb Employes
        if (mCustomer.nbEmploye != null && !mCustomer.nbEmploye.isNullOrEmpty()) {
            tvNbEmployees.text = mCustomer.nbEmploye
        } else {
            tvNbEmployees.text = resources.getString(R.string.non_defini)
        }

        // Customer status
        if (mCustomer.customerStatus != null && !mCustomer.customerStatus.name.isNullOrEmpty()) {
            tvClientType.text = mCustomer.customerStatus.name
        } else {
            tvClientType.text = resources.getString(R.string.non_defini)
        }

        // customer name
        if (mCustomer.name != null && !mCustomer.name.isNullOrEmpty()) {
            tvClientName.text = mCustomer.name
        } else {
            tvClientName.text = resources.getString(R.string.non_defini)
        }

        // PhoneNumber view management
        if (!mCustomer.phoneNumber2.isNullOrEmpty()) {

            if (!mCustomer.tel.trim { it <= ' ' }.isEmpty()) {
                btnCall1.text = mCustomer.tel
                btnCall1.setOnClickListener(this)
            } else {
                // Empty phone number
                btnCall1.setText(R.string.call)
                btnCall1.isEnabled = false
                btnCall1.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey))

                if (!mCustomer.phoneNumber2.trim { it <= ' ' }.isEmpty()) {
                    btnCall1.visibility = View.GONE
                }
            }
        } else {
            btnCall1.visibility = View.GONE
        }

        // PhoneNumber2 view management
//        if (!mCustomer.phoneNumber2.isNullOrEmpty()) {
//            if (!mCustomer.phoneNumber2.trim { it <= ' ' }.isEmpty()) {
//                btnCall2.text = mCustomer.phoneNumber2
//                btnCall2.setOnClickListener(this)
//            } else {
//                btnCall2.visibility = View.GONE
//            }
//        } else {
//            btnCall2.visibility = View.GONE
//        }
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
        fun onRequestCustomerPharmacyObjectForUIInitialization()
        fun onPhoneNumberCallInteraction(phoneNumber: String)
        fun onClientDetailsReceived(clientDetails: Client)
    }

    companion object {
        private const val TAG = "PharmacyDetailsFragment"
    }
}
