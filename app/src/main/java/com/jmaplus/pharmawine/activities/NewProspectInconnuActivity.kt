package com.jmaplus.pharmawine.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.fragments.rapport.VisiteInProgressFragment
import com.jmaplus.pharmawine.models.DailyReportEnd
import com.jmaplus.pharmawine.models.Visite


class NewProspectInconnuActivity : AppCompatActivity(), VisiteInProgressFragment.OnFragmentInteractionListener {

    private var clientType: String = ""
    var fragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_prospect_inconnu)

        clientType = intent.getStringExtra(TranslucentNewClientActivity.CLIENT_TYPE_EXTRA_KEY)

        Log.i(localClassName, "clientType ==> $clientType")

        var arguments = Bundle()
        arguments.putString(VisiteInProgressFragment.ARGS_PROSPECT_TYPE, clientType);

        var fragmentTransaction = fragmentManager.beginTransaction()
        var fragment = VisiteInProgressFragment()

        fragment.arguments = arguments
        fragmentTransaction.add(R.id.pi_fragment_container, fragment)
        fragmentTransaction.commit()
    }

    override fun onVisiteEnded(reportID: Int?, EndTime: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
