package com.jmaplus.pharmawine.fragments.clients

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
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
 * [MedicalTeamDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class MedicalTeamDetailsFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val TAG = "MedicalTeamDetailsFrag"
    }

    private var listener: OnFragmentInteractionListener? = null
    private var mCustomer: Customer = Customer()

    private lateinit var mContext: Context
    private lateinit var tvSexe: TextView
    private lateinit var tvBirthday: TextView
    private lateinit var tvNationality: TextView
    private lateinit var tvMaritalStatus: TextView
    private lateinit var tvBelieves: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvAddress: TextView
    private lateinit var imgProfile: CircleImageView
    private lateinit var tvClientName: TextView
    private lateinit var tvClientCategory: TextView
    private lateinit var tvClientType: TextView
    private lateinit var tvProfileProgress: TextView
    private lateinit var profileProgress: RoundCornerProgressBar
    private lateinit var btnCall1: Button
    private lateinit var btnCall2: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_medical_team_details, container, false)

        mContext = requireContext()

        var gson = Gson()
        var customerStringFronArguments = arguments?.getString(ClientDetailsActivity.CUSTOMER_OBJECT_EXTRA)
        mCustomer = gson.fromJson(customerStringFronArguments, Customer::class.java)

        // Binding UI views

        imgProfile = rootView.findViewById(R.id.img_profile_picture)
        tvClientName = rootView.findViewById(R.id.tv_i_client_name)
        tvClientCategory = rootView.findViewById(R.id.tv_i_client_category)
        tvClientType = rootView.findViewById(R.id.tv_i_client_type)
        tvProfileProgress = rootView.findViewById(R.id.tv_i_client_progress)
        profileProgress = rootView.findViewById(R.id.progress_client_i_filling)
        btnCall1 = rootView.findViewById(R.id.btn_call_1)
        btnCall2 = rootView.findViewById(R.id.btn_call_2)
        tvSexe = rootView.findViewById(R.id.tv_i_client_sex)
        tvBirthday = rootView.findViewById(R.id.tv_i_client_birthday)
        tvNationality = rootView.findViewById(R.id.tv_i_client_nationality)
        tvMaritalStatus = rootView.findViewById(R.id.tv_i_client_marital_status)
        tvBelieves = rootView.findViewById(R.id.tv_i_client_believes)
        tvEmail = rootView.findViewById(R.id.tv_i_client_email)
        tvAddress = rootView.findViewById(R.id.tv_i_client_address)
        tvProfileProgress = rootView.findViewById(R.id.tv_i_client_progress)
        profileProgress = rootView.findViewById(R.id.progress_client_i_filling)

        /**
         * If customer received from parent is null
         * A request is sent to parent activity to send us a customer object
         */
        if (mCustomer == null) listener?.onRequestCustomerObjectForUIInitialization()
        else updateUI()

        return rootView
    }

    /**
     * This method should be called from parent
     */
    fun updateCustomersWith(customer: Customer) {
        updateUI()
    }

    private fun updateUI() {

        // Client avatar
        if (!mCustomer.sex.isNullOrEmpty() && mCustomer.sex.toUpperCase() == "F") {
            Glide.with(mContext).load(R.drawable.bg_doctor_woman).into(imgProfile)
        } else {
            Glide.with(mContext).load(R.drawable.bg_doctor_man).into(imgProfile)
        }

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

        // Customer status
        if (mCustomer.customerStatus != null && !mCustomer.customerStatus.name.isNullOrEmpty()) {
            tvClientType.text = mCustomer.customerStatus.name
        } else {
            tvClientType.text = resources.getString(R.string.non_defini)
        }

        // Nationality
        if (mCustomer.nationality != null && !mCustomer.nationality.isNullOrEmpty()) {
            tvNationality.text = mCustomer.nationality
        } else {
            tvNationality.text = resources.getString(R.string.non_defini)
        }

        // Religion
        if (mCustomer.religion != null && !mCustomer.religion.isNullOrEmpty()) {
            tvBelieves.text = mCustomer.religion
        } else {
            tvBelieves.text = resources.getString(R.string.non_defini)
        }

        // Marital status
        if (mCustomer.maritalStatus != null && !mCustomer.maritalStatus.isNullOrEmpty()) {
            tvMaritalStatus.text = mCustomer.maritalStatus
        } else {
            tvMaritalStatus.text = resources.getString(R.string.non_defini)
        }

        tvClientName.text = mCustomer.fullName
        tvSexe.text = if (mCustomer.sex.toUpperCase() == "M") "Homme" else if (mCustomer.sex == "F") "Femme" else "-"
        tvEmail.text = mCustomer.email
        tvAddress.text = mCustomer.address
        tvBirthday.text = mCustomer.redabledate

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
        if (!mCustomer.phoneNumber2.isNullOrEmpty()) {
            if (!mCustomer.phoneNumber2.trim { it <= ' ' }.isEmpty()) {
                btnCall2.text = mCustomer.phoneNumber2
                btnCall2.setOnClickListener(this)
            } else {
                btnCall2.visibility = View.GONE
            }
        } else {
            btnCall2.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            btnCall1.id -> {
                Log.i(TAG, "Sending ==> ${mCustomer.tel}")
                if (mCustomer.tel.isNotEmpty()) listener?.onPhoneNumberCallInteraction(mCustomer.tel)
            }
            btnCall2.id -> {
                if (mCustomer.phoneNumber2.isNotEmpty()) listener?.onPhoneNumberCallInteraction(mCustomer.phoneNumber2)
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {

        fun onPhoneNumberCallInteraction(phoneNumber: String)

        fun onClientDetailsReceived(clientDetails: Client)

        fun onRequestCustomerObjectForUIInitialization()
    }

}
