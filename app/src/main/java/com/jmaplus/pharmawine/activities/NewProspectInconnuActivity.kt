package com.jmaplus.pharmawine.activities

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape2Fragment
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape3Fragment
import com.jmaplus.pharmawine.fragments.rapport.ReportEtape4Fragment
import com.jmaplus.pharmawine.fragments.rapport.VisiteInProgressFragment
import com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu.Step1UnknownMedicalProspect
import com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu.Step2UnknownMedicalProspect
import com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu.Step3UnknownMedicalProspect
import com.jmaplus.pharmawine.fragments.rapport.prospect_inconnu.Step4UnknownMedicalProspectFragment
import com.jmaplus.pharmawine.models.*
import com.jmaplus.pharmawine.utils.RetrofitCalls.*
import com.jmaplus.pharmawine.utils.RetrofitCalls.customers.CustomerEditionCalls
import com.jmaplus.pharmawine.utils.Utils


class NewProspectInconnuActivity : AppCompatActivity(),
        View.OnClickListener,
        VisiteInProgressFragment.OnFragmentInteractionListener,
        Step1UnknownMedicalProspect.OnFragmentInteractionListener,
        Step2UnknownMedicalProspect.OnFragmentInteractionListener,
        Step3UnknownMedicalProspect.OnFragmentInteractionListener,
        Step4UnknownMedicalProspectFragment.OnFragmentInteractionListener,
        ReportEtape2Fragment.OnFragmentInteractionListener,
        ReportEtape3Fragment.OnFragmentInteractionListener,
        ReportEtape4Fragment.OnFragmentInteractionListener,
        CenterCalls.Callbacks, SpecialityCalls.Callbacks,
        AreaCalls.Callbacks, CustomerStatusCalls.Callbacks,
        DailyReportEndCall.Callbacks, CustomerEditionCalls.Callbacks {

    private var isReportUpdated: Boolean = false
    private var clientType = ""
    private var mReportID: Int = -1
    private var mCustomer = Customer()
    private var mFirstFragmentArgs = Bundle()
    private var mDailyReportEnd = DailyReportEnd()
    private var fragmentManager = supportFragmentManager
    private var isAllowToGoBack: Boolean = false


    private var mToken = ""

    private var mAreasList: MutableList<Area> = ArrayList()
    private var mCenterList: MutableList<Center> = ArrayList()
    private var mSpecialitiesList: MutableList<Speciality> = ArrayList()
    private var mCustomerStatusList: MutableList<CustomerStatus> = ArrayList()

    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerAdapter: PagerAdapter
    private lateinit var mBtnNext: Button
    private lateinit var mProgressDialog: ProgressDialog
    private lateinit var mTopRapportLayout: RelativeLayout

    private lateinit var mFirstFragment: VisiteInProgressFragment
    private lateinit var mStep1Fragment: Step1UnknownMedicalProspect
    private lateinit var mStep2Fragment: Step2UnknownMedicalProspect
    private lateinit var mStep3Fragment: Step3UnknownMedicalProspect
    private lateinit var mStep4Fragment: Step4UnknownMedicalProspectFragment
    private lateinit var mStep5Fragment: ReportEtape2Fragment
    private lateinit var mStep6Fragment: ReportEtape3Fragment
    private lateinit var mStep7Fragment: ReportEtape4Fragment


    companion object {
        val TAG = "NewProspectInconnu"
        val NUM_PAGES = 7
        val STEP_1_FRAGMENT_INDEX = 0
        val STEP_2_FRAGMENT_INDEX = 1
        val STEP_3_FRAGMENT_INDEX = 2
        val STEP_4_FRAGMENT_INDEX = 3
        val STEP_5_FRAGMENT_INDEX = 4
        val STEP_6_FRAGMENT_INDEX = 5
        val STEP_7_FRAGMENT_INDEX = 6
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_prospect_inconnu)

        clientType = intent.getStringExtra(TranslucentNewClientActivity.CLIENT_TYPE_EXTRA_KEY)
        mFirstFragmentArgs.putString(VisiteInProgressFragment.ARGS_PROSPECT_TYPE, clientType)

        Log.i(localClassName, "clientType ==> $clientType")

        initializations()

        showTheFirstFragment()
    }

    private fun showTheFirstFragment() {
        var fragmentTransaction = fragmentManager.beginTransaction()
        mFirstFragment.arguments = mFirstFragmentArgs
        fragmentTransaction.add(R.id.cl_root_layout, mFirstFragment)
        fragmentTransaction.commit()
    }

    private fun initializations() {

        mToken = AuthUser.getToken(this)

        title = "Visite en cours..."

        // Views initializations
        mBtnNext = findViewById(R.id.btn_next_edit_profile)
        mViewPager = findViewById(R.id.view_pager)
        mTopRapportLayout = findViewById(R.id.layout_profil_activity_edit_medical)
        mTopRapportLayout.visibility = View.GONE
        mBtnNext.setOnClickListener(this)

        mProgressDialog = ProgressDialog(this);
        mProgressDialog.setMessage("Mis a jour en cours....")
        mProgressDialog.setCancelable(false)

        // fragments initialization
        mFirstFragment = VisiteInProgressFragment()
        mStep1Fragment = Step1UnknownMedicalProspect()
        mStep2Fragment = Step2UnknownMedicalProspect()
        mStep3Fragment = Step3UnknownMedicalProspect()
        mStep4Fragment = Step4UnknownMedicalProspectFragment()
        mStep5Fragment = ReportEtape2Fragment()
        mStep6Fragment = ReportEtape3Fragment()
        mStep7Fragment = ReportEtape4Fragment()

        // listenners
        mViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                if (i < STEP_7_FRAGMENT_INDEX) {
                    mBtnNext.text = "Suivant"
                } else {
                    mBtnNext.text = "Terminer mon rapport"
                }
            }

            override fun onPageScrollStateChanged(i: Int) {

            }
        })

        // Fetching datas after 10 secondes to make sure a visit is in progress
        Handler().postDelayed({
            fetchCenters()
            fetchAreas()
            fetchCustomerStatues()
            fetchSpeciaities()
        }, 1000)


    }

    private fun showViewPager() {

        setTitle(R.string.nouveau_client)
        mTopRapportLayout.visibility = View.VISIBLE

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.hide(mFirstFragment)
        fragmentTransaction.commit()

        mViewPager.visibility = View.VISIBLE
        mPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerAdapter

    }

    private fun hideFirstFragment() {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.hide(mFirstFragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (!isAllowToGoBack) {
            // User should not go back when he is doing a visit
            Toast.makeText(this, "Vous ne pouvez revenir en arriere tant que la visite n'est pas terminee", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    private fun fetchCenters() {
        CenterCalls.getCentersList(mToken, this)
    }

    private fun fetchAreas() {
        Log.i(TAG, "Fetching areas...")
        AreaCalls.getAreasList(mToken, this)
    }

    private fun fetchSpeciaities() {
        Log.i(TAG, "Fetching specialities...")
        SpecialityCalls.getSpecialitysList(mToken, this)
    }

    private fun fetchCustomerStatues() {
        Log.i(TAG, "Fetching customer statues...")
        CustomerStatusCalls.getCustomerStatusList(mToken, this)
    }

    // First Fragment callbacks ====================

    override fun onVisiteEnded(reportID: Int?, EndTime: String?) {

        mDailyReportEnd.endTime = EndTime
        isAllowToGoBack = true

        mReportID = reportID!!

        Log.i(TAG, "onVisiteEnded: mDailyEnd ==> $mDailyReportEnd")

        hideFirstFragment()

        showViewPager()
    }

    // Step 1 callbacks ====================
    override fun onFirstNameUpdated(firstname: String) {
        mCustomer.firstname = firstname
    }

    override fun onLastnameUpdated(lastname: String) {
        mCustomer.lastname = lastname
    }

    override fun onSexUpdated(sex: String) {
        mCustomer.sex = sex
    }

    // Step 2 callbacks ====================
    override fun onAddressUpdated(address: String) {
        mCustomer.address = address
    }

    override fun onPhoneNumberUpdated(fullPhoneNumber: String) {
        mCustomer.tel = fullPhoneNumber
    }

    // Step 3 callbacks ====================
    override fun onRequestCentersList() {
        if (mCenterList.isEmpty()) {
            fetchCenters()
        } else {
            mStep3Fragment.populateCentersFromSource(mCenterList)
        }
    }

    override fun onCenterSelected(selectedCenter: Center) {
        // todo talk with corentin about hat route
        mViewPager.currentItem++
    }

    // Step 4 callbacks ====================
    override fun onSpecialityUpdated(speciality: Speciality) {
        mCustomer.specialityId = speciality.id
    }

    override fun onCustomerStatusUpdated(customerStatusID: Integer) {
        mCustomer.customerStatusId = customerStatusID.toInt()
    }

    override fun onAreaUpdated(zone: Area) {
    }

    override fun onRequestAreasList() {
        if (mAreasList.isEmpty()) {
            fetchAreas()
        } else {
            mStep4Fragment.populateZoneFromSource(mAreasList)
        }
    }

    override fun onRequestSpecialitiesList() {
        if (mSpecialitiesList.isEmpty()) {
            fetchSpeciaities()
        } else {
            mStep4Fragment.populateSpecialyFromSource(mSpecialitiesList)
        }
    }

    override fun onRequestCustomerStatusList() {
        if (mCustomerStatusList.isEmpty()) {
            fetchCustomerStatues()
        } else {
            mStep4Fragment.populateCategoryFromSource(mCustomerStatusList)
        }
    }

    // Step 5 callbacks ====================
    override fun onStep2Finished(purposeOfTheVisit: String?) {

    }

    override fun onPurposeUpdated(updatedPurposeOfTheVisit: String?) {
        mDailyReportEnd.goal = updatedPurposeOfTheVisit
    }

    override fun onReturnToStep1() {

    }

    // Step 6 callbacks ====================
    override fun onPromeseUpdated(updatedPromes: String?) {
        mDailyReportEnd.promise = updatedPromes
    }

    override fun onStep3Finished(promesesHeld: String?) {

    }

    override fun onReturnToStep2() {

    }

    // Step 7 callbacks ====================
    override fun onPrescriptionUpdated(updatedPrescription: String?) {
        mDailyReportEnd.prescription = updatedPrescription
    }

    override fun onStep4Finished(prescribedRequirements: String?) {

    }


    // ==================== Start Retrofit Callbacks ====================
    override fun onCentersResponse(centers: MutableList<Center>?) {
        mCenterList = centers!!
//        Utils.presentToast(this, "Centers fetched", false)
    }

    override fun onCentersFailure() {
        Utils.presentToast(this, "Echec de recuperation des centres", false)
    }

    override fun onSpecialitysResponse(specialities: MutableList<Speciality>?) {
//        Utils.presentToast(this, "specialities fetched", false)
        mSpecialitiesList = specialities!!
    }

    override fun onSpecialitysFailure() {
        Utils.presentToast(this, "Echec de recuperation des specialites", false)
    }

    override fun onAreasResponse(areas: MutableList<Area>?) {
        mAreasList = areas!!
//        Utils.presentToast(this, "area fetched", false)
    }

    override fun onAreasFailure() {

    }

    override fun onCustomerStatussResponse(customerStatues: MutableList<CustomerStatus>?) {
        mCustomerStatusList = customerStatues!!
//        Utils.presentToast(this, "Customers Statues fetched", false)
    }

    override fun onCustomerStatussFailure() {
    }

    // ==================== End Retrofit Callbacks ====================


    // ==================== Pager adapter class ====================

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {

            val f: Fragment

            when (position) {
                STEP_1_FRAGMENT_INDEX -> f = mStep1Fragment
                STEP_2_FRAGMENT_INDEX -> f = mStep2Fragment
                STEP_3_FRAGMENT_INDEX -> f = mStep3Fragment
                STEP_4_FRAGMENT_INDEX -> f = mStep4Fragment
                STEP_5_FRAGMENT_INDEX -> f = mStep5Fragment
                STEP_6_FRAGMENT_INDEX -> f = mStep6Fragment
                STEP_7_FRAGMENT_INDEX -> f = mStep7Fragment
                else -> f = mStep1Fragment
            }

            return f
        }

        override fun getCount(): Int {
            return NUM_PAGES
        }
    }


    override fun onEndDailyReportResponse(response: DailyReportEndResponse?) {
        // Envoie du rapport reussi
        // Enregistrons maintenant le profil du customer
        mProgressDialog.cancel()
        isReportUpdated = true
        sendProfile()
    }

    override fun onEndDailyReportFailure() {
        mProgressDialog.cancel()
        showConfirmationToRetryReportSend()
    }

    override fun onCustomerEditionResponse(customer: Customer?) {
        mProgressDialog.cancel()
    }

    override fun onCustomerEditionFailure() {
        mProgressDialog.cancel()
        showConfirmationDialogToUpdateProfile()
    }

    private fun sendReportEnd() {
        mProgressDialog.show()
        Log.i(TAG, "$mDailyReportEnd")
        DailyReportEndCall.postDailyReportEnd(mToken, this, mDailyReportEnd, mReportID)
    }

    private fun sendProfile() {
        mProgressDialog.show()
        Log.i(TAG, "$mCustomer")
        CustomerEditionCalls.editCustomerProfile(mToken, this, mCustomer.id, mCustomer)
    }

    private fun showConfirmationToRetryReportSend() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Echec d'enregistrement du rapport")
        builder.setMessage("Voulez-vous reessayer ?")
        builder.setCancelable(false)

        builder.setPositiveButton(R.string.oui) { dialog, which ->
            sendReportEnd()
        }

        builder.setNegativeButton(R.string.non) { dialog, which ->
            {
                //                finish()
            }
        }

        builder.show()
    }

    private fun showConfirmationDialogToUpdateProfile() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Echec d'enregistrement du client")
        builder.setMessage("Voulez-vous reessayer ?")
        builder.setCancelable(false)

        builder.setPositiveButton(R.string.oui) { dialog, which ->
            sendProfile()
        }

        builder.setNegativeButton(R.string.non) { dialog, which ->
            {
                if (isReportUpdated) {
                    finish()
                }

            }
        }

        builder.show()
    }

    private fun showConfirmationDialogToFinish() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("C'est bon ?")
        builder.setMessage("Etes vous sur d'avoir terminé votre rapport ?")
        builder.setCancelable(false)

        builder.setPositiveButton(R.string.oui) { dialog, which ->
            sendReportEnd()
        }

        builder.setNegativeButton(R.string.non) { dialog, which ->
            {
                // code
                // ...
            }
        }

        builder.show()
    }

    override fun onClick(v: View?) {
        if (v?.id == mBtnNext.id) {
            if (mViewPager.currentItem < STEP_7_FRAGMENT_INDEX) {
                // Action ==> Passer au fragment suivant
                mViewPager.currentItem = mViewPager.currentItem + 1
            } else if (mViewPager.currentItem >= STEP_7_FRAGMENT_INDEX) {
                // Action ==> Terminer le rapport

                showConfirmationDialogToFinish()
            }
        }
    }
}
