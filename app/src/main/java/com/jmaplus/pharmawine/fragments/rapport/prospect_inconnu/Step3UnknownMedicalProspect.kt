package com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.CenterSelectionAdapter
import com.jmaplus.pharmawine.models.Center
import com.jmaplus.pharmawine.utils.ItemClickSupport

/**
 * A simple [Fragment] subclass.
 *
 */
class Step3UnknownMedicalProspect : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mCentersRecyclerView: RecyclerView
    private lateinit var mCentersList: MutableList<Center>
    private lateinit var mContext: Context
    private lateinit var mCenter: Center

    private var mAdapter: CenterSelectionAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_step3_unknown_medical_prospect, container, false)

        mContext = requireContext()
        mCentersRecyclerView = rootView.findViewById(R.id.rv_centers)

        initUI()

        return rootView
    }

    private fun initUI() {

        mCenter = Center()
        mCentersList = ArrayList()

        fetchCenters()

        configureOnClickRecyclerView()

        // Configure recycler view
        mAdapter = CenterSelectionAdapter(mCentersList)
        mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mCentersRecyclerView.layoutManager = mLayoutManager
        mCentersRecyclerView.adapter = mAdapter
    }

    private fun configureOnClickRecyclerView() {
        ItemClickSupport
                .addTo(mCentersRecyclerView, R.layout.client_row_without_progression)
                .setOnItemClickListener { recyclerView, position, v ->
                    mCenter = mCentersList[position]

                    listener?.onCenterSelected(mCentersList[position])
                }
    }

    private fun fetchCenters() {
        listener?.onRequestCentersList()
    }

    fun populateCentersFromSource(centers: List<Center>) {
        mCentersList.clear()
        mAdapter?.notifyDataSetChanged()

        for (i in centers) {
            mCentersList.add(i)
            mAdapter?.notifyItemInserted(mCentersList.size - 1)
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
        fun onRequestCentersList()

        fun onCenterSelected(selectedCenter: Center)
    }
}
