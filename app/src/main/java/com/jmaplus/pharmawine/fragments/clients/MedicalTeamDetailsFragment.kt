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
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Client
import com.jmaplus.pharmawine.utils.Constants
import de.hdodenhof.circleimageview.CircleImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    private var client: Client = Client()

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

        getMedicalTeamClientDetails()

        return rootView
    }

    private fun getMedicalTeamClientDetails() {
        // todo: Fetch client datas from api and use it instead of fake data
        val currentClient = getFakeClient()


        // TODO: Call this method only when data was successfully fetched from server
        updateViewsForMedicalTeamClient(currentClient)

        listener?.onClientDetailsReceived(currentClient)
    }

    private fun updateViewsForMedicalTeamClient(client: Client) {

        profileProgress.progress = client.getFillingLevel().toFloat()
        tvProfileProgress.text = client.getFillingLevel().toString() + " %"

        val fillingLevel = client.getFillingLevel()
        if (fillingLevel < 35) {
            profileProgress.progressColor = resources.getColor(R.color.red)
        } else if (fillingLevel < 69) {
            profileProgress.progressColor = resources.getColor(R.color.orange)
        } else {
            profileProgress.progressColor = resources.getColor(R.color.green)
        }

        tvClientName.text = client.getFullName()
        tvClientCategory.text = client.speciality
        tvSexe.text = if (client.sex == "M") "Homme" else if (client.sex == "F") "Femme" else "-"
        tvNationality.text = client.nationality
        tvMaritalStatus.text = client.maritalStatus
        tvEmail.text = client.email
        tvAddress.text = client.address
        tvBirthday.text = client.getRedabledate()

        // PhoneNumber view management
        if (!client.phoneNumber.trim { it <= ' ' }.isEmpty()) {
            btnCall1.text = client.phoneNumber
            btnCall1.setOnClickListener(this)
        } else {
            // Empty phone number
            btnCall1.setText(R.string.call)
            btnCall1.isEnabled = false
            btnCall1.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey))

            if (!client.phoneNumber2.trim { it <= ' ' }.isEmpty()) {
                btnCall1.visibility = View.GONE
            }
        }

        // PhoneNumber2 view management
        if (!client.phoneNumber2.trim { it <= ' ' }.isEmpty()) {
            btnCall2.text = client.phoneNumber2
            btnCall2.setOnClickListener(this)
        } else {
            btnCall2.visibility = View.GONE
        }
    }

    private fun getFakeClient(): Client {
        val client = Client()

        client.id = "2"
        client.firstname = "Ablonon"
        client.lastname = "Veronique"
        client.sex = "F"
        client.speciality = "Medecin generaliste"
        client.status = "CFG"
        client.isKnown = true
        client.email = "davidhihea@gmail.com"
        client.birthday = "08-09-2000"
        client.phoneNumber = "+22966843445"
        client.phoneNumber2 = "22995754591"
        client.maritalStatus = "Marié"
        client.nationality = "Béninoise"
        client.type = Constants.CLIENT_PHARMACY_TYPE_KEY

        return client
    }

    override fun onClick(v: View?) {


        val DEFAULT_PHONE_NUMBER = "+22966843445"

        when (v?.id) {
            btnCall1.id -> {
                Log.i(TAG, "Sending ==> ${client.phoneNumber}")
                // todo: make sur the phone number is note empty and send the real phone number
                listener?.onPhoneNumberCallInteraction(DEFAULT_PHONE_NUMBER)
            }
            btnCall2.id -> {
                // todo: make sur the phone number is note empty
                listener?.onPhoneNumberCallInteraction(DEFAULT_PHONE_NUMBER)
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
    }

}
