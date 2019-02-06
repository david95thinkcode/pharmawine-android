package com.jmaplus.pharmawine.fragments.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.ApiProduct

class ProductDetailsFragmentDialog : DialogFragment() {

    private lateinit var mProductName: TextView
    private lateinit var mProductCategory: TextView
    private lateinit var mProductLaboratory: TextView
    private lateinit var mPGHT: TextView
    private lateinit var mPC: TextView
    private lateinit var mPP: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.product_info_dialog, container, false)

        mProductName = rootView.findViewById(R.id.tv_product_name)
        mProductCategory = rootView.findViewById(R.id.tv_product_category)
        mProductLaboratory = rootView.findViewById(R.id.tv_product_laboratory)
        mPGHT = rootView.findViewById(R.id.tv_global_price_ht)
        mPC = rootView.findViewById(R.id.tv_pc_price)
        mPP = rootView.findViewById(R.id.tv_pp_price)


        return rootView
    }

    fun updateDetails(mProduct: ApiProduct) {
        try {

            // todo: make this work
            Log.i(javaClass.name, mProduct.toString())
//            mProductName.text = mProduct.name

            mProductLaboratory.text = mProduct.laboratory.name
            mPGHT.text = mProduct.prixGht
            mPC.text = mProduct.prixCession
            mPP.text = mProduct.prixPublic
        } catch (e: Exception) {
            Log.e(javaClass.name, e.message)
            Log.e(javaClass.name, e.cause.toString())
            e.printStackTrace()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.

        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

}