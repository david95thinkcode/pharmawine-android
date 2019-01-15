package com.jmaplus.pharmawine.fragments.profilage

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

import com.jmaplus.pharmawine.R

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Step1MedicalTeamClientFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class Step1MedicalTeamClientFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mDay: EditText
    private lateinit var mYear: EditText
    private lateinit var mMonthSpinner: Spinner
    private lateinit var mNationalitySpinner: Spinner
    private lateinit var mMaritalStatusSpinner: Spinner

    private var mMonth: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_step1_medical_team_client, container, false)

        mDay = rootView.findViewById(R.id.tv_day)
        mYear = rootView.findViewById(R.id.tv_year)
        mMonthSpinner = rootView.findViewById(R.id.spinner_month)
        mNationalitySpinner = rootView.findViewById(R.id.spinner_nationality)
        mMaritalStatusSpinner = rootView.findViewById(R.id.spinner_marital_status)

        // Spinner setting up for month
        ArrayAdapter.createFromResource(requireContext(), R.array.months_array_fr, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mMonthSpinner.adapter = adapter
                }

        ArrayAdapter.createFromResource(requireContext(), R.array.nationality_fake_array_fr, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mNationalitySpinner.adapter = adapter
                }

        // marital status spinner
        ArrayAdapter.createFromResource(requireContext(), R.array.marital_status_array_fr, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mMaritalStatusSpinner.adapter = adapter
                }

        setViewEvents()

        return rootView
    }

    private fun setViewEvents() {
        val birthdayInputListenner = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // call the listenner method with full birthday string
                listener?.onBithdayFullyUpdated(getFullBirthday())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

        mDay.addTextChangedListener(birthdayInputListenner)
        mYear.addTextChangedListener(birthdayInputListenner)
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (view?.id) {
            mMonthSpinner.id -> {
                mMonth = parent?.getItemAtPosition(position).toString()
                listener?.onBirthDayPartiallyUpdated(getFullBirthday())
            }
            mNationalitySpinner.id -> listener?.onNationalityUpdated(parent?.getItemAtPosition(position).toString())
            mMaritalStatusSpinner.id -> listener?.onMartialStatusUpdated(parent?.getItemAtPosition(position).toString())
            else -> {
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    /**
     * Return full birthday string
     */
    private fun getFullBirthday(): String {

        var b = ""
        if (!mDay.text.isEmpty() && !mMonth.isNullOrEmpty() && !mYear.text.isNullOrEmpty()) {
            b = "${mDay.text}/$mMonth/${mYear.text}"
            listener?.onBithdayFullyUpdated(b)
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
        fun onBirthDayPartiallyUpdated(partialBirthday: String)

        fun onBithdayFullyUpdated(birthDay: String)

        fun onMartialStatusUpdated(maritalStatus: String)

        fun onNationalityUpdated(nationality: String)

    }

}
