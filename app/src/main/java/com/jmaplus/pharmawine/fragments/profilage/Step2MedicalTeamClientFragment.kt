package com.jmaplus.pharmawine.fragments.profilage

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.jmaplus.pharmawine.R

class Step2MedicalTeamClientFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mAddress: EditText
    private lateinit var mReligion: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_step2_medical_team_client, container, false)
        mAddress = rootView.findViewById(R.id.ed_address)
        mReligion = rootView.findViewById(R.id.spinner_religion)

        // Spinner setting up for religion
        ArrayAdapter.createFromResource(requireContext(), R.array.religious_belief_array, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    mReligion.adapter = adapter
                }


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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        listener?.onReligionSelected(parent?.getItemAtPosition(position).toString())
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onReligionSelected(religion: String)

        fun onAddressEntered(address: String)
    }

}
