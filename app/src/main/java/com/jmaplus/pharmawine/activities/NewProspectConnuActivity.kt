package com.jmaplus.pharmawine.activities

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.fragments.clients.CustomersListFragment
import com.jmaplus.pharmawine.models.Customer
import com.jmaplus.pharmawine.utils.Constants

class NewProspectConnuActivity : AppCompatActivity()
        , CustomersListFragment.OnFragmentInteractionListener {

    private var clientType: Int = -1
    private lateinit var mCustomersListFragment: CustomersListFragment
    private lateinit var mFragmentContainer: ConstraintLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_prospect_connu)

        mCustomersListFragment = CustomersListFragment()
        clientType = intent.getIntExtra(TranslucentNewClientActivity.CLIENT_TYPE_EXTRA_KEY, -1)

        initViews()
    }

    private fun initViews() {
        title = "Prospects connus"
        mFragmentContainer = findViewById(R.id.ly_p_connu)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(mFragmentContainer.id, mCustomersListFragment)
        transaction.commit()
    }

    override fun onRequestCustumerType() {
        mCustomersListFragment.fetchCustomers(clientType, true)
    }

    override fun onCustomerClicked(customer: Customer) {
        var i = Intent(this, VisiteInProgressActivity::class.java)

        // Indispensable
        i.putExtra(VisiteInProgressActivity.EXTRA_PROSPECT_TYPE,
                Constants.PROSPECT_KNOWN_MEDICAL_TEAM_TYPE_KEY)

        i.putExtra(Constants.CLIENT_ID_KEY, customer.id)
        i.putExtra(Constants.CLIENT_FIRSTNAME_KEY, customer.firstname)
        i.putExtra(Constants.CLIENT_FULLNAME_KEY, customer.fullName)
        i.putExtra(Constants.CLIENT_LASTNAME_KEY, customer.lastname)

        // Preventing against null exception
        try {

            if (customer.customerStatus != null && customer.customerType != null
                    && customer.speciality != null) {
                i.putExtra(Constants.CLIENT_SPECIALITY_KEY, customer.speciality.name)
                i.putExtra(Constants.CLIENT_CUSTOMER_TYPE_KEY, customer.customerType.name)
                i.putExtra(Constants.CLIENT_CUSTOMER_STATUS_KEY, customer.customerStatus.name)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            startActivity(i)
        }

    }

}
