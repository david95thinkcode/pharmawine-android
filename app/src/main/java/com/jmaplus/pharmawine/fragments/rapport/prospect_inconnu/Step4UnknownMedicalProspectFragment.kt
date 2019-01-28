package com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Area
import com.jmaplus.pharmawine.models.CustomerStatus
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

        val statusAdapter = ArrayAdapter<CustomerStatus>(requireContext(), android.R.layout.simple_spinner_item, mCustomerStatusList)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mCategorieSpinner.adapter = adapter
                }

        mZoneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item: CustomerStatus = statusAdapter.getItem(position)
                listener?.onCustomerStatusUpdated(Integer(item.id))
            }
        }
    }

    fun populateSpecialyFromSource(specialities: List<Speciality>) {
        mSpecialitiesList.clear()
        mSpecialitiesList.addAll(specialities)

        val specialitiesAdapter = ArrayAdapter<Speciality>(requireContext(), android.R.layout.simple_spinner_item, mSpecialitiesList)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mSpecialitySpinner.adapter = adapter
                }

        mSpecialitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item: Speciality = specialitiesAdapter.getItem(position)
                listener?.onSpecialityUpdated(item)
            }
        }
    }

    fun populateZoneFromSource(areas: List<Area>) {
        mAreasList.clear()
        mAreasList.addAll(areas)

        val areaAdapter = ArrayAdapter<Area>(requireContext(), android.R.layout.simple_spinner_item, mAreasList)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    mZoneSpinner.adapter = adapter
                }

        mZoneSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var item: Area = areaAdapter.getItem(position)
                listener?.onAreaUpdated(item)
            }
        }
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
