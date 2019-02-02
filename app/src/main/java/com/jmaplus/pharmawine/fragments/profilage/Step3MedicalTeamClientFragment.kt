package com.jmaplus.pharmawine.fragments.profilage

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.jmaplus.pharmawine.R

class Step3MedicalTeamClientFragment : Fragment() {

    companion object {
        val TAG = "Step3MedicalFragment"
    }

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mEmail: EditText
    private lateinit var mPhoneNumber2: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_step3_medical_team_client, container, false)
        mEmail = rootView.findViewById(R.id.ed_client_email)
        mPhoneNumber2 = rootView.findViewById(R.id.ed_phone_number_2_full)

        setUpListenners()

        listener?.onRequestExistingEmail()
        listener?.onRequestExistingPhoneNumber2()

        return rootView
    }

    private fun setUpListenners() {

        mEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onEmailEntered(s.toString())
            }
        })

        mPhoneNumber2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onPhoneNumber2Entered(s.toString())
            }
        })
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

    // =========== PUBLIC METHODS ==========

    fun setExistingEmail(existingEmail: String) {
        mEmail.setText(existingEmail)
    }

    fun setExistingPhoneNumber2(existingPhone2: String) {
        mPhoneNumber2.setText(existingPhone2)
        mPhoneNumber2.isEnabled = false
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {

        fun onEmailEntered(email: String)

        fun onPhoneNumber2Entered(phoneNumber2: String)

        fun onRequestExistingEmail()

        fun onRequestExistingPhoneNumber2()
    }

}
