package com.jmaplus.pharmawine.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.RemainingClientsAdapter
import com.jmaplus.pharmawine.models.Client
import com.jmaplus.pharmawine.utils.Constants
import com.jmaplus.pharmawine.utils.ItemClickSupport
import com.jmaplus.pharmawine.utils.MockDatas

class SeenCustomers : AppCompatActivity() {

    private lateinit var mTextView: TextView
    private lateinit var mEmptyClientsLayout: LinearLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgressBar: ProgressBar
    private lateinit var safeCustomersList: MutableList<Client>
    private lateinit var customersList: MutableList<Client>
    private lateinit var mAdapter: RemainingClientsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seen_customers)

        setUI()
        populateRecyclerView()
    }

    private fun setUI() {
        // configure toolbar
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        // Binding elements
        mTextView = findViewById(R.id.tv_not_seen_empty)
        mRecyclerView = findViewById(R.id.rv_seen_customers)
        mProgressBar = findViewById(R.id.pb_seen_customers)
        mEmptyClientsLayout = findViewById(R.id.seen_sutomers_empty_list_layout)
        mProgressBar.visibility = View.GONE
        mEmptyClientsLayout.visibility = View.GONE

        // Initializations
        customersList = ArrayList()
        safeCustomersList = ArrayList()

        mAdapter = RemainingClientsAdapter(this, customersList)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        mRecyclerView.adapter = mAdapter

        configureOnClickRecyclerView()
    }

    /**
     * Fetch seen customers and populate the recycler view with it
     * and save a copy of customerList on safeCostumerList
     */
    private fun populateRecyclerView() {

        if (safeCustomersList.isEmpty()) {
            // todo: replace with api call


            customersList.clear()
            for (customer in MockDatas.getFakeClients()) {
                // populating list for the adapter of recycler view
                customersList.add(customer)
                mAdapter.notifyItemInserted(customersList.size - 1)
            }

            // Saving the original list of customers
            safeCustomersList.addAll(customersList)
        } else { // Safe Customer list is not empty
            updateUIForNotEmptyData()

            if (customersList != safeCustomersList) {
                customersList.clear()

                customersList.addAll(safeCustomersList)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * Filter shown customers on recycler view
     * If seen is true, filter by seen customers
     * else, filter by unseen customers
     */
    private fun filterClients(seen: Boolean) {
        if (customersList.isNotEmpty()) {
            if (seen) {
                val newList = safeCustomersList.filter { it.isKnown }
                refreshRecyclerViewListWith(newList, true)
            } else {
                val newList = safeCustomersList.filter { !it.isKnown }
                refreshRecyclerViewListWith(newList, false)
            }
        }
    }

    private fun refreshRecyclerViewListWith(list: List<Client>, knownCostumers: Boolean) {

        if (list.isNotEmpty()) {
            mRecyclerView.visibility = View.VISIBLE
            mEmptyClientsLayout.visibility = View.GONE
            mProgressBar.visibility = View.GONE

            customersList.clear()
            customersList.addAll(list)
            mAdapter.notifyDataSetChanged()
        } else {
            mRecyclerView.visibility = View.GONE
            mEmptyClientsLayout.visibility = View.VISIBLE
            mProgressBar.visibility = View.GONE
        }
    }

    private fun configureOnClickRecyclerView() {
        ItemClickSupport
                .addTo(mRecyclerView, R.layout.client_row_without_progression)
                .setOnItemClickListener { theRecyclerView, position, v ->
                    var customer = mAdapter.getClient(position)

                    // Open the details activity
                    var i = Intent(this, ClientDetailsActivity::class.java)
                    i.putExtra(ClientDetailsActivity.CLIENT_TYPE_KEY, Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY)
                    i.putExtra(ClientDetailsActivity.CLIENT_ID_KEY, customer.id)
                    startActivity(i)
                }
    }

    private fun updateUIForNotEmptyData() {

        mRecyclerView.visibility = View.VISIBLE
        mEmptyClientsLayout.visibility = View.GONE
        mProgressBar.visibility = View.GONE
    }

    private fun updateUIForEmptyData(message: String) {
        mTextView.text = message
        mRecyclerView.visibility = View.GONE
        mEmptyClientsLayout.visibility = View.VISIBLE
        mProgressBar.visibility = View.GONE
    }

    private fun updateUIForLoading() {
        mRecyclerView.visibility = View.GONE
        mEmptyClientsLayout.visibility = View.GONE
        mProgressBar.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_option_connus -> {
                filterClients(true)
                title = resources.getString(R.string.clients_connus)
                return true
            }
            R.id.menu_option_inconnus -> {
                filterClients(false)
                title = resources.getString(R.string.clients_inconnus)
                return true
            }
            R.id.menu_option_tous -> {
                title = resources.getString(R.string.clients_vus)
                populateRecyclerView()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.seen_customers_menu, menu)
        return true
    }

    companion object {
        const val TAG = "SeenCustomersActivity"
        const val useMockedData = true
    }
}
