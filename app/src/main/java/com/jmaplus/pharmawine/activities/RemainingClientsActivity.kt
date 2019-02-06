package com.jmaplus.pharmawine.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.RemainingCustomersAdapter
import com.jmaplus.pharmawine.fragments.clients.CustomersListFragment
import com.jmaplus.pharmawine.models.Customer

class RemainingClientsActivity : AppCompatActivity(), CustomersListFragment.OnFragmentInteractionListener {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var clientsList: MutableList<Customer>
    private lateinit var mAdapter: RemainingCustomersAdapter
    private lateinit var mCustomersListFragment: CustomersListFragment
    private lateinit var mFragmentContainer: ConstraintLayout

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

        mCustomersListFragment = CustomersListFragment()

        mFragmentContainer = findViewById(R.id.fragment_cont)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(mFragmentContainer.id, mCustomersListFragment)
        transaction.commit()
    }

    override fun onRequestCustumerType() {
    }

    override fun onCustomerClicked(customer: Customer) {
        // Open the details activity
        try {
            // Converting customer objet to json string
            var gson = Gson()
            var jsonCustomer = gson.toJson(customer)

            var i = Intent(this, ClientDetailsActivity::class.java)
            i.putExtra(ClientDetailsActivity.CLIENT_TYPE_EXTRA, customer.customerTypeId)
            i.putExtra(ClientDetailsActivity.CLIENT_ID_EXTRA, customer.id)
            i.putExtra(ClientDetailsActivity.CLIENT_SEX_EXTRA, customer.sex)
            i.putExtra(ClientDetailsActivity.CLIENT_NAME_EXTRA, customer.fullName)
            i.putExtra(ClientDetailsActivity.CUSTOMER_OBJECT_EXTRA, jsonCustomer)
            startActivity(i)
        } catch (e: Exception) {
            Log.w(TAG, "onCustomerClicked:" + e.message)
            e.printStackTrace()
        }

    }

    private fun searchInList() {

    }

    private fun fetchClients() {
        // Ce delai est mis pour prevenir l'initialisation de la progressBar
        Handler().postDelayed({
            mCustomersListFragment.fetchRemainingCustomers()
        }, 2000)
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
    }
}
