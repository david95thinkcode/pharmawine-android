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

class Step2MedicalTeamClientFragment : Fragment() {

    companion object {
        val TAG = "Step2MedicalFragment"
    }

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mAddress: EditText
    private lateinit var mReligion: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_step2_medical_team_client, container, false)
        mAddress = rootView.findViewById(R.id.ed_address)
        mReligion = rootView.findViewById(R.id.spinner_religion)

        setListenners()

        fetchReligions()

        // Here we request the parent activty to send us existing values
        listener?.onRequestExistingAddress()
        listener?.onRequestExistingReligion()

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

    private fun fetchReligions() {
        // Spinner setting up for religion
        ArrayAdapter.createFromResource(requireContext(), R.array.religious_belief_array, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    mReligion.adapter = adapter
                }
    }

    private fun setListenners() {
        mReligion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                listener?.onReligionSelected(parent?.getItemAtPosition(position).toString())
            }
        }

        val adressListenner = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onAddressEntered(s.toString())
            }
        }
        mAddress.addTextChangedListener(adressListenner)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    // =========== PUBLIC METHODS ==========
    fun setExistingAddress(existingAddress: String) {
        if (!existingAddress.isNullOrEmpty()) {
            mAddress.setText(existingAddress)
        }

    }

    fun setExistingReligion(religion: String) {
        if (!religion.isNullOrEmpty()) {
            // TODO: Preselect the corresponding religion
        }
    }

    interface OnFragmentInteractionListener {

        fun onReligionSelected(religion: String)

        fun onAddressEntered(address: String)

        fun onRequestExistingAddress()

        fun onRequestExistingReligion()
    }

}
