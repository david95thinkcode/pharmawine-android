package com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner

import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Area
import com.jmaplus.pharmawine.models.CustomerStatus
import com.jmaplus.pharmawine.models.CustomerType
import com.jmaplus.pharmawine.models.Speciality

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Step4UnknownMedicalProspectFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class Step4UnknownMedicalProspectFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var mAreasList: MutableList<Area> = ArrayList()
    private var mSpecialitiesList: MutableList<Speciality> = ArrayList()
    private var mCustomerStatusList: MutableList<CustomerStatus> = ArrayList()

    private lateinit var mSpecialitySpinner: Spinner
    private lateinit var mZoneSpinner: Spinner
    private lateinit var mCategorieSpinner: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(
                R.layout.fragment_step4_unknown_medical_prospect, container, false)

        mSpecialitySpinner = rootView.findViewById(R.id.spinner_speciality)
        mZoneSpinner = rootView.findViewById(R.id.spinner_zone)
        mCategorieSpinner = rootView.findViewById(R.id.spinner_category)

        initUI()

        return rootView
    }

    private fun initUI() {
        // todo : set listenners for spinners

        // fetch needed datas
        listener?.onRequestSpecialitiesList()
        listener?.onRequestCustomerStatusList()
        listener?.onRequestAreasList()
    }

    fun populateCategoryFromSource(customerStatus: List<CustomerStatus>) {
        mCustomerStatusList.clear()
        mCustomerStatusList.addAll(customerStatus)

        //todo: update spinner adapter
    }

    fun populateSpecialyFromSource(specialities: List<Speciality>) {
        mSpecialitiesList.clear()
        mSpecialitiesList.addAll(specialities)

        //todo: update spinner adapter
    }

    fun populateZoneFromSource(areas: List<Area>) {
        mAreasList.clear()
        mAreasList.addAll(areas)

        //todo: update spinner adapter
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


    interface OnFragmentInteractionListener {

        fun onSpecialityUpdated(speciality: Speciality)

        fun onAreaUpdated(zone: Area)

        fun onCustomerStatusUpdated(customerStatusID: Integer)

        fun onRequestAreasList()

        fun onRequestSpecialitiesList()

        fun onRequestCustomerStatusList()
    }

}
