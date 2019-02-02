package com.jmaplus.pharmawine.fragments.clients

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.RemainingCustomersAdapter
import com.jmaplus.pharmawine.models.AuthUser
import com.jmaplus.pharmawine.models.Customer
import com.jmaplus.pharmawine.utils.*
import com.jmaplus.pharmawine.utils.RetrofitCalls.customers.SeenCustomerCalls


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CustomersListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class CustomersListFragment : Fragment(),
        CustomerCalls.Callbacks, SeenCustomerCalls.Callbacks {

    companion object {
        const val TAG = "CustomersListFragment"
        const val TESTMODE = true
    }

    private var listener: OnFragmentInteractionListener? = null
    private var mCustomerType: Int = -1
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mEmptyCustomersLayout: LinearLayout

    private lateinit var mCustomersList: MutableList<Customer>
    private lateinit var mSafeCustomersList: MutableList<Customer>
    private lateinit var mAdapter: RemainingCustomersAdapter
    private lateinit var mContext: Context
    private lateinit var mAuthUser: AuthUser
    private lateinit var mToken: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_customers_list, container, false)

        mContext = requireContext()
        mToken = AuthUser.getToken(mContext)
        mAuthUser = AuthUser.getAuthenticatedUser(mContext)
        mRecyclerView = rootView.findViewById(R.id.rv_customers)
        mProgressBar = rootView.findViewById(R.id.pb_customers)
        mEmptyCustomersLayout = rootView.findViewById(R.id.layout_cutomers_empty_list_layout)

        // Initializations
        mEmptyCustomersLayout.visibility = View.GONE
        mCustomersList = ArrayList()
        mSafeCustomersList = ArrayList()
        mAdapter = RemainingCustomersAdapter(mContext, mCustomersList)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayout.VERTICAL, false)
        mRecyclerView.adapter = mAdapter

        configureOnClickRecyclerView()

        // We request the parent to get customer type
        listener?.onRequestCustumerType()

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

    private fun getCustomers(isProspectConnu: Boolean) {
        mProgressBar.visibility = View.VISIBLE
        if (isProspectConnu) {
            Log.i(TAG, "Recuperation prospect connus")
            CustomerCalls.getAllKnownProspects(AuthUser.getToken(mContext), this)
        } else {
            // Others cases
            Log.i(TAG, "Recuperation prospect inconnus")
        }
    }

    /**
     * This method can be called by fragment parent
     */
    fun fetchSeenCustomers() {
        mProgressBar.visibility = View.VISIBLE

        if (!TESTMODE) {
            SeenCustomerCalls.getSeenCustomers(mToken, this)
        } else {
            updateUIWithResponse(FakeData.getCustomers());
        }
    }

    override fun onSeenCustomersResponse(customers: MutableList<Customer>?) {
        updateUIWithResponse(customers!!)
    }

    override fun onSeenCustomersFailure() {
        showFailure()
    }

    private fun configureOnClickRecyclerView() {
        ItemClickSupport
                .addTo(mRecyclerView, R.layout.client_row_without_progression)
                .setOnItemClickListener { theRecyclerView, position, v ->
                    var customer = mAdapter.getClient(position)

                    listener?.onCustomerClicked(customer)
                }
    }

    // ============ METHODS WHICH CAN BE CALLED BY PARENT

    /**
     * Should be called by the parent activity
     */
    fun fetchCustomers(customerType: Int, isProspectConnu: Boolean) {

        if (customerType != null
                && (customerType == Constants.TYPE_MEDICAL_KEY || customerType == Constants.TYPE_PHARMACEUTICAL_KEY)) {
            mProgressBar.visibility = View.VISIBLE

            mCustomerType = customerType // important

            getCustomers(isProspectConnu) // let's get customers

        } else {
            // Le parent n'a envoyeé aucun customer type id ou un type mon valide
            Utils.presentToast(mContext, "Impossible de recuperer ce type de client", true)
        }

    }

    /**
     * This method should be called from parent
     * to filter the list by customer type id
     */
    fun filteredCustomerListByCustomerType(customerTypeID: Int) {
        if (mCustomersList.isNotEmpty()) {
            val newList = mSafeCustomersList.filter {
                it.customerTypeId == customerTypeID
            }
            refreshRecyclerViewWithNewList(newList)
        } else {
            Utils.presentToast(mContext, "Rien a filtrer", true)
        }
    }

    fun showAllWithoutFiltering() {
        refreshRecyclerViewWithNewList(mSafeCustomersList)
    }


    fun filterCustomersByKnownCriteria(isKnownCustomer: Boolean) {

        if (mSafeCustomersList.isNotEmpty()) {

            val filteredList = mSafeCustomersList.filter {
                it.isKnown == isKnownCustomer
            }

            refreshRecyclerViewWithNewList(filteredList)

        } else {
            Utils.presentToast(mContext, "Rien a filtrer", true)
        }
    }

    private fun refreshRecyclerViewWithNewList(newCustomerList: List<Customer>) {

        mProgressBar.visibility = View.GONE

        mCustomersList.clear()
        mCustomersList.addAll(newCustomerList)
        mAdapter.notifyDataSetChanged()

        if (newCustomerList.isEmpty()) {
            Utils.presentToast(mContext, "Liste vide", true)
        }
    }

    /**
     * Should be called from parent activity
     */
    fun fetchRemainingCustomers() {
        mProgressBar.visibility = View.VISIBLE

        if (!Constants.ENV_TESTMODE) {
            CustomerCalls.getRemaining(mToken, this)
        } else {
            updateUIWithResponse(FakeData.getCustomers());
        }
    }

    override fun onCustomerDetailsResponse(customer: Customer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCustomerDetailsFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onKnownProspectResponse(customers: MutableList<Customer>?) {
        if (!customers.isNullOrEmpty()) {

            mCustomersList.clear()
            mSafeCustomersList.clear()

            for (c: Customer in customers) {
                if (c.customerTypeId == mCustomerType) {
                    mCustomersList.add(c)
                    mAdapter.notifyItemInserted(customers.size - 1)
                }
            }

            mSafeCustomersList.addAll(mCustomersList) // important
        } else {
            mCustomersList.clear()
            mSafeCustomersList.clear()
        }

        mProgressBar.visibility = View.GONE
    }

    override fun onRemainingCustomersResponse(customers: MutableList<Customer>?) {
        updateUIWithResponse(customers!!)
    }


    override fun onRemainingCustomersFailure() {
        showFailure()
    }

    override fun onKnownProspectFailure() {
        showFailure()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Method used to populate the list
     * and update the UI
     */
    private fun updateUIWithResponse(customers: List<Customer>) {

        if (customers.isNotEmpty()) {
            mCustomersList.clear()
            mEmptyCustomersLayout.visibility = View.GONE

            // Populating the recycler view
            for (c: Customer in customers) {
                mCustomersList.add(c)
                mAdapter.notifyItemInserted(customers.size - 1)
            }

            mSafeCustomersList.addAll(mCustomersList) // important
        } else showEmptyResults()

        mProgressBar.visibility = View.GONE
    }

    private fun showEmptyResults() {
        mProgressBar.visibility = View.GONE
        mEmptyCustomersLayout.visibility = View.VISIBLE
    }

    private fun showFailure() {
        Utils.presentToast(mContext, "Une erreur s'est produite", true)
        mProgressBar.visibility = View.GONE
    }

    interface OnFragmentInteractionListener {

        fun onRequestCustumerType()

        fun onCustomerClicked(Customer: Customer)

    }

}
