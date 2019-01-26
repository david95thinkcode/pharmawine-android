package com.jmaplus.pharmawine.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.RemainingCustomersAdapter
import com.jmaplus.pharmawine.models.Customer
import com.jmaplus.pharmawine.utils.Constants
import com.jmaplus.pharmawine.utils.FakeData
import com.jmaplus.pharmawine.utils.ItemClickSupport

class RemainingClientsActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var clientsList: MutableList<Customer>
    private lateinit var mAdapter: RemainingCustomersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remaining_clients)

        setUI()
        fetchClients()
    }

    private fun setUI() {
        // configure toolbar
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        // Binding elements
        mRecyclerView = findViewById(R.id.rv_remaining_customers)

        // Initializations
        clientsList = ArrayList()
        mAdapter = RemainingCustomersAdapter(this, clientsList)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        mRecyclerView.adapter = mAdapter

        configureOnClickRecyclerView()
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


    private fun searchInList() {

    }

    private fun fetchClients() {

        if (!useMockedData) {
            // TODO: Fetching data from the api

        }
        else {
//             Fetching datas from fake data
            for (c in FakeData.getCustomers()) {
                clientsList.add(c)
                mAdapter.notifyItemInserted(clientsList.size - 1)
            }
        }
    }

    // handle click on Toolbar item click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                searchInList()
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
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    companion object {
        const val TAG = "RemainingClientsAct"
        const val useMockedData = true
    }
}
