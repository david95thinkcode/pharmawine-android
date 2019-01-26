package com.jmaplus.pharmawine.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.RemainingCustomersAdapter
import com.jmaplus.pharmawine.models.AuthUser
import com.jmaplus.pharmawine.models.Customer
import com.jmaplus.pharmawine.utils.Constants
import com.jmaplus.pharmawine.utils.CustomerCalls
import com.jmaplus.pharmawine.utils.ItemClickSupport
import com.jmaplus.pharmawine.utils.Utils
import com.jmaplus.pharmawine.utils.Utils.presentToast

class NewProspectConnuActivity : AppCompatActivity(), CustomerCalls.Callbacks {

    private var clientType: Int = -1
    private lateinit var clientsList: MutableList<Customer>
    private lateinit var mProgress: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RemainingCustomersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_prospect_connu)

        initViews()

        clientType = intent.getIntExtra(TranslucentNewClientActivity.CLIENT_TYPE_EXTRA_KEY, -1)

        if (clientType == Constants.TYPE_MEDICAL_KEY || clientType == Constants.TYPE_PHARMACEUTICAL_KEY) {
            fetchCustomers()
        } else {
            presentToast(this, "Impossible de recuperer ce type de prospect", true);
        }
    }

    private fun fetchCustomers() {
        CustomerCalls.getAllKnownProspects(AuthUser.getToken(this), this)
    }

    private fun initViews() {
        mRecyclerView = findViewById(R.id.rv_unknown_prospects)
        mProgress = findViewById(R.id.progressBar_unknown)

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

                    var i = Intent(this, VisiteInProgressActivity::class.java)
                    i.putExtra(Constants.CLIENT_ID_KEY, customer.id)
                    i.putExtra(Constants.CLIENT_FIRSTNAME_KEY, customer.firstname)
                    i.putExtra(Constants.CLIENT_FULLNAME_KEY, customer.fullName)
                    i.putExtra(Constants.CLIENT_LASTNAME_KEY, customer.lastname)
                    startActivity(i)
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

            for (c: Customer in customers) {

                if (c.customerTypeId == Constants.TYPE_MEDICAL_KEY) {
                    clientsList.add(c)
                    mAdapter.notifyItemInserted(clientsList.size - 1)
                } else if (c.customerTypeId == Constants.TYPE_PHARMACEUTICAL_KEY) {
                    clientsList.add(c)
                    mAdapter.notifyItemInserted(clientsList.size - 1)
                }
            }

            mProgress.visibility = View.GONE

        } else {
            Toast.makeText(this, "Aucune prospect trouvé", Toast.LENGTH_LONG).show()
        }
    }

    override fun onKnownProspectFailure() {
        Utils.presentToast(this, "Une erreur s'est produite", true)
        mProgress.visibility = View.GONE
    }
}
