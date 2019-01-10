package com.jmaplus.pharmawine.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.adapters.RemainingClientsAdapter
import com.jmaplus.pharmawine.models.Client
import com.jmaplus.pharmawine.utils.MockDatas

class RemainingClientsActivity : AppCompatActivity() {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var clientsList: MutableList<Client>
    private lateinit var mAdapter: RemainingClientsAdapter

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
        mAdapter = RemainingClientsAdapter(this, clientsList)
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        mRecyclerView.adapter = mAdapter
    }


    private fun searchInList() {

    }

    private fun fetchClients() {

        if (!useMockedData) {
            // TODO: Fetching data from the api

        }
        else {
            // Fetching datas from fake data
            for (c in MockDatas.getFakeClients()) {
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
