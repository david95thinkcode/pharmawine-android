package com.jmaplus.pharmawine.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.fragments.clients.CustomersListFragment
import com.jmaplus.pharmawine.models.Customer

class SeenCustomersActivity : AppCompatActivity(),
        CustomersListFragment.OnFragmentInteractionListener {

    private lateinit var mFragmentContainerLayout: ConstraintLayout
    private lateinit var mCustomersListFragment: CustomersListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seen_customers)

        mCustomersListFragment = CustomersListFragment()

        setUI()
        fetchCustomers()
    }

    private fun setUI() {

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        mFragmentContainerLayout = findViewById(R.id.layout_seen_Fragment_container)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(mFragmentContainerLayout.id, mCustomersListFragment)
        transaction.commit()
    }

    private fun fetchCustomers() {
        // Ce delai est mis pour prevenir l'initialisation de la progressBar
        // dans le fragment

        Handler().postDelayed({
            mCustomersListFragment.fetchSeenCustomers()
        }, 2000)
    }

    override fun onRequestCustumerType() {
        // ...
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
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_option_connus -> {
//                filterClients(true)
                title = resources.getString(R.string.clients_connus)
                return true
            }
            R.id.menu_option_inconnus -> {
//                filterClients(false)
                title = resources.getString(R.string.clients_inconnus)
                return true
            }
            R.id.menu_option_tous -> {
                title = resources.getString(R.string.clients_vus)
//                populateRecyclerView()
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
    }
}
