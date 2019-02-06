package com.jmaplus.pharmawine.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Center

class CenterSelectionAdapter(var centersList: MutableList<Center>) :
        RecyclerView.Adapter<CenterSelectionAdapter.CenterSelectionViewHolder>() {

    class CenterSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val center = itemView.findViewById<TextView>(R.id.tv_centre_name)
        val zone = itemView.findViewById<TextView>(R.id.tv_zone_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): CenterSelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.centre_item, parent, false)

        return CenterSelectionViewHolder(inflater)
    }

    override fun getItemCount(): Int = centersList.size

    override fun onBindViewHolder(holder: CenterSelectionViewHolder, position: Int) {
        holder.center.text = centersList[position].name
//        holder?.zone.text = centersList[position].zone
    }

    fun getCenter(position: Int): Center = centersList[position]


    companion object {
        const val TAG = "CenterSelectionAdapter"
    }
}