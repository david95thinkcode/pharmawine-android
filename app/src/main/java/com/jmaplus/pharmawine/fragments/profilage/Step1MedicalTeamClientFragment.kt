package com.jmaplus.pharmawine.fragments.profilage

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.TestCountry
import com.jmaplus.pharmawine.utils.Utils

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Step1MedicalTeamClientFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class Step1MedicalTeamClientFragment : Fragment() {

    companion object {
        val TAG = "Step1MedicalFragment"
    }

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mDay: EditText
    private lateinit var mYear: EditText
    private lateinit var mMonthSpinner: Spinner
    private lateinit var mNationalitySpinner: Spinner
    private lateinit var mMaritalStatusSpinner: Spinner

    private var mMonth: String = ""
    private var mCountriesList: MutableList<TestCountry> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_step1_medical_team_client, container, false)

        mDay = rootView.findViewById(R.id.tv_day)
        mYear = rootView.findViewById(R.id.tv_year)
        mMonthSpinner = rootView.findViewById(R.id.spinner_month)
        mNationalitySpinner = rootView.findViewById(R.id.spinner_nationality)
        mMaritalStatusSpinner = rootView.findViewById(R.id.spinner_marital_status)

        initialiseViews()

        listener?.onRequestExistingBirthday()
        listener?.onRequestExistingMaritalStatus()
        listener?.onRequestExistingNationality()

        return rootView
    }

    private fun initialiseViews() {

        // Start Populating nationality spinner
        for (c in Utils.getCountries()) {
            mCountriesList.add(c)
        }

        val countriesAdapter = ArrayAdapter<TestCountry>(requireContext(), android.R.layout.simple_spinner_item, mCountriesList)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mNationalitySpinner.adapter = adapter
                }
        // End populating nationality spinner

        // Spinner setting up for month
        ArrayAdapter.createFromResource(requireContext(), R.array.months_array_fr, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mMonthSpinner.adapter = adapter
                }

        // marital status spinner
        ArrayAdapter.createFromResource(requireContext(), R.array.marital_status_array_fr, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mMaritalStatusSpinner.adapter = adapter
                }


        // Events listenner ----------------
        mMonthSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mMonth = (position + 1).toString()
                updateBirthday()
            }
        }

        mNationalitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item: TestCountry = countriesAdapter.getItem(position)
                listener?.onNationalityUpdated(item.name)
            }
        }

        mMaritalStatusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                listener?.onMartialStatusUpdated(parent?.getItemAtPosition(position).toString())
            }
        }

        setViewEvents()
    }

    private fun setViewEvents() {
        val birthdayInputListenner = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateBirthday()
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

    /**
     * Update the birthday and call the listenner if necessary
     */
    private fun updateBirthday() {

        var b = ""
        if (!mDay.text.isEmpty() && !mMonth.isNullOrEmpty() && !mYear.text.isNullOrEmpty()) {
            b = "${mYear.text}-$mMonth-${mDay.text}"
            Log.i(TAG, "Birthday updated ==> $b ")
        } else // Log.i(TAG, "Birthday is not complete")

            listener?.onBirthdayFullyUpdated(b)
    }

    // =========== PUBLIC METHODS ==========
    fun setExistingBirthday(day: Int, month: Int, year: Int) {
        mDay.setText(day.toString())
        mYear.setText(year.toString())
        mMonthSpinner.setSelection(month - 1);

        mDay.isEnabled = false
        mYear.isEnabled = false
        mMonthSpinner.isEnabled = false
    }

    fun setExistingMaritalStatus(status: String) {
        if (!status.isNullOrEmpty()) mMaritalStatusSpinner.isEnabled = false
    }

    fun setExistingNationality(nationality: String) {
        if (!nationality.isNullOrEmpty()) mNationalitySpinner.isEnabled = false
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFragmentInteractionListener {

        fun onBirthDayPartiallyUpdated(partialBirthday: String)

        fun onBirthdayFullyUpdated(birthDay: String)

        fun onMartialStatusUpdated(maritalStatus: String)

        fun onNationalityUpdated(nationality: String)

        fun onRequestExistingBirthday()

        fun onRequestExistingNationality()

        fun onRequestExistingMaritalStatus()

    }

}
