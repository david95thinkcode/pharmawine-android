package com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu


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
 *
 */
class Step1UnknownMedicalProspect : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mFirstname: EditText
    private lateinit var mLastname: EditText
    private lateinit var mSex: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_step1_unknown_medical_prospect, container, false)

        mFirstname = rootView.findViewById(R.id.ed_client_nom)
        mLastname = rootView.findViewById(R.id.ed_client_prenom)
        mSex = rootView.findViewById(R.id.spinner_sex)

        initUI()

        return rootView
    }

    private fun initUI() {
        // populate the sex spinner
        ArrayAdapter.createFromResource(requireContext(), R.array.sex_array, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mSex.adapter = adapter
                }

        setListenners()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    private fun setListenners() {
        mSex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0)
                    listener?.onSexUpdated("M")
                else
                    listener?.onSexUpdated("F")
            }
        }

        val firsnameListenner = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onFirstNameUpdated(s.toString())
            }
        }
        val lastnameListenner = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onLastnameUpdated(s.toString())
            }
        }
        mFirstname.addTextChangedListener(firsnameListenner)
        mLastname.addTextChangedListener(lastnameListenner)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onFirstNameUpdated(firstname: String)

        fun onLastnameUpdated(lastname: String)

        fun onSexUpdated(sex: String)
    }
}
