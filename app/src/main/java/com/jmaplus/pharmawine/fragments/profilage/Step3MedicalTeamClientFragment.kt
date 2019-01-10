package com.jmaplus.pharmawine.fragments.profilage

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.jmaplus.pharmawine.R

class Step3MedicalTeamClientFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mEmail: EditText
    private lateinit var mPhoneNumber2: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_step3_medical_team_client, container, false)



        return rootView
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

        fun onAdresseEmailEntered(email: String)

        fun onPhoneNumber2Entered(phoneNumber2: String)
    }

}
