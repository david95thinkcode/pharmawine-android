package com.jmaplus.pharmawine.activities

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
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


class NewProspectInconnuActivity : AppCompatActivity(),
        VisiteInProgressFragment.OnFragmentInteractionListener,
        Step1UnknownMedicalProspect.OnFragmentInteractionListener,
        Step2UnknownMedicalProspect.OnFragmentInteractionListener,
        Step3UnknownMedicalProspect.OnFragmentInteractionListener,
        Step4UnknownMedicalProspectFragment.OnFragmentInteractionListener,
        ReportEtape2Fragment.OnFragmentInteractionListener,
        ReportEtape3Fragment.OnFragmentInteractionListener,
        ReportEtape4Fragment.OnFragmentInteractionListener {

    private var clientType = ""
    private var mReportID: Int = -1
    private var mCustomer = Customer()
    private var mFirstFragmentArgs = Bundle()
    private var mDailyReportEnd = DailyReportEnd()
    private var fragmentManager = supportFragmentManager

    private var mAreasList: MutableList<Area> = ArrayList()
    private var mCenterList: MutableList<Center> = ArrayList()
    private var mSpecialitiesList: MutableList<Speciality> = ArrayList()
    private var mCustomerStatusList: MutableList<CustomerStatus> = ArrayList()

    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerAdapter: PagerAdapter
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
        // Views initializations
        mViewPager = findViewById(R.id.view_pager)
        mTopRapportLayout = findViewById(R.id.layout_profil_activity_edit_medical)
        mTopRapportLayout.visibility = View.GONE

        // fragments initialization
        mFirstFragment = VisiteInProgressFragment()
        mStep1Fragment = Step1UnknownMedicalProspect()
        mStep2Fragment = Step2UnknownMedicalProspect()
        mStep3Fragment = Step3UnknownMedicalProspect()
        mStep4Fragment = Step4UnknownMedicalProspectFragment()
        mStep5Fragment = ReportEtape2Fragment()
        mStep6Fragment = ReportEtape3Fragment()
        mStep7Fragment = ReportEtape4Fragment()

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


    private fun fetchCenters() {
        Log.i(TAG, "Fetching centers...")
        // todo: implements corresponding retrofit call

    }

    private fun fetchAreas() {
        Log.i(TAG, "Fetching areas...")
        // todo: implements corresponding retrofit call

    }

    private fun fetchSpeciaities() {
        Log.i(TAG, "Fetching specialities...")
        // todo: implements corresponding retrofit call
    }

    private fun fetchCustomerStatues() {
        Log.i(TAG, "Fetching customer statues...")
        // todo: implements corresponding retrofit call

    }

    // First Fragment callbacks ====================

    override fun onVisiteEnded(reportID: Int?, EndTime: String?) {

        mDailyReportEnd.endTime = EndTime

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
        // todo
    }

    // Step 4 callbacks ====================
    override fun onSpecialityUpdated(speciality: Speciality) {
        mCustomer.specialityId = speciality.id
    }

    override fun onCustomerStatusUpdated(customerStatusID: Integer) {
        mCustomer.customerStatusId = customerStatusID.toInt()
    }

    override fun onAreaUpdated(zone: Area) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPurposeUpdated(updatedPurposeOfTheVisit: String?) {
        mDailyReportEnd.goal = updatedPurposeOfTheVisit
    }

    override fun onReturnToStep1() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Step 6 callbacks ====================
    override fun onPromeseUpdated(updatedPromes: String?) {
        mDailyReportEnd.promise = updatedPromes
    }

    override fun onStep3Finished(promesesHeld: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReturnToStep2() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Step 7 callbacks ====================
    override fun onPrescriptionUpdated(updatedPrescription: String?) {
        mDailyReportEnd.prescription = updatedPrescription
    }

    override fun onStep4Finished(prescribedRequirements: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    // ==================== Start Retrofit Callbacks ====================


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
}
