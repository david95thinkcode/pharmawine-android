package com.jmaplus.pharmawine.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.MedicalCenter

class MedicalTeamCenterSelectionAdapter(var centersList: MutableList<MedicalCenter>) :
        RecyclerView.Adapter<MedicalTeamCenterSelectionAdapter.MedicalTeamCenterSelectionViewHolder>() {

    class MedicalTeamCenterSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val center = itemView.findViewById<TextView>(R.id.tv_center_label)
        val zone = itemView.findViewById<TextView>(R.id.tv_zone_label)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MedicalTeamCenterSelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.mt_center_row, parent, false)

        return MedicalTeamCenterSelectionViewHolder(inflater)
    }

    override fun getItemCount(): Int = centersList.size

    override fun onBindViewHolder(holder: MedicalTeamCenterSelectionViewHolder, position: Int) {
        holder?.center.text = centersList[position].name
        holder?.zone.text = centersList[position].zone
    }

    fun getCenter(position: Int): MedicalCenter = centersList[position]


    companion object {
        const val TAG = "MedicalTeamCenterSelectionAdapter"
    }
}