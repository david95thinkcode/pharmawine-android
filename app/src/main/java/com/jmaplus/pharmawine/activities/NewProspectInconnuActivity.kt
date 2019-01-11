package com.jmaplus.pharmawine.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.activities.TranslucentNewClientActivity.CLIENT_TYPE_EXTRA_KEY
import com.jmaplus.pharmawine.utils.Constants


class NewProspectInconnuActivity : AppCompatActivity() {

    private var clientType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_prospect_inconnu)

        clientType = intent.getStringExtra(CLIENT_TYPE_EXTRA_KEY)

        if (clientType == Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY) {
            // todo
        } else if (clientType == Constants.CLIENT_PHARMACY_TYPE_KEY) {
            // TODO
        }
    }
}
