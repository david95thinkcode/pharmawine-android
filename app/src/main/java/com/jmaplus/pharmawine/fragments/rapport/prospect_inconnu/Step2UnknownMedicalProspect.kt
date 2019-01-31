package com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu


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

/**
 * A simple [Fragment] subclass.
 *
 */
class Step2UnknownMedicalProspect : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mAddresse: EditText
    private lateinit var mPhoneNumberFlag: EditText
    private lateinit var mPhoneNumber: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_step2_unknown_prospect, container, false)

        mAddresse = rootView.findViewById(R.id.ed_address)
        mPhoneNumberFlag = rootView.findViewById(R.id.ed_country_flag)
        mPhoneNumber = rootView.findViewById(R.id.ed_phone_number)

        initUI()

        return rootView
    }

    private fun initUI() {
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

        val addressListenner = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onAddressUpdated(s.toString())
            }
        }
        val phoneNumberListenner = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                var fullNumber = getPhoneNumberWithFlag()

                if (!fullNumber.isNullOrEmpty()) {
                    listener?.onPhoneNumberUpdated(fullNumber)
                }

            }
        }

        mAddresse.addTextChangedListener(addressListenner)
        mPhoneNumber.addTextChangedListener(phoneNumberListenner)

        // todo: gerer les country flag avec une librairies de phone numbers
    }

    private fun getPhoneNumberWithFlag(): String {
        // check if flag is not null before building full phone number and call listener

        return if (!mPhoneNumberFlag.text.isNullOrEmpty() && !mPhoneNumber.text.isNullOrEmpty()) {
            "${mPhoneNumberFlag.text}${mPhoneNumber.text}"
        } else {
            ""
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onAddressUpdated(address: String)

        fun onPhoneNumberUpdated(fullPhoneNumber: String)
    }


}
