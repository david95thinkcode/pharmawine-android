package com.jmaplus.pharmawine.fragments.profilage

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.jmaplus.pharmawine.R

class Step3MedicalTeamClient : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mDay: EditText
    private lateinit var mMonth: EditText
    private lateinit var mYear: EditText


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_step3_medical_team_client, container, false)

        mDay = rootView.findViewById(R.id.tv_day)
        mMonth = rootView.findViewById(R.id.tv_month)
        mYear = rootView.findViewById(R.id.tv_year)

        return rootView
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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
     * Return full birthday string
     */
    private fun getFullBirthday(): String {

        var b: String = "";
        if (!mDay.text.isEmpty() && !mMonth.text.isNullOrEmpty() && !mYear.text.isNullOrEmpty()) {
            b = "${mDay.text}/${mMonth.text}/${mYear.text}"
            listener?.onBithdayFullyEntered(b)
        }

        return b
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)

        fun onBithdayFullyEntered(birthDay: String)

        fun onNationalityChoosed(nationality: String)
    }

}
