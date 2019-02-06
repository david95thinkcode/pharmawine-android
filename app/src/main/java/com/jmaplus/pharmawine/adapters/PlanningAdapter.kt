package com.jmaplus.pharmawine.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Customer
import de.hdodenhof.circleimageview.CircleImageView

class PlanningAdapter(val context: Context, var customersList: MutableList<Customer>) :
        RecyclerView.Adapter<PlanningAdapter.PlanningViewHolder>() {

    class PlanningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val picture = itemView.findViewById<CircleImageView>(R.id.img_client)
        val fullname = itemView.findViewById<TextView>(R.id.tv_client_fullname)
        val speciality = itemView.findViewById<TextView>(R.id.tv_client_speciality)
        val status = itemView.findViewById<TextView>(R.id.tv_client_status)
        val watchedImageView = itemView.findViewById<ImageView>(R.id.iv_watched)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PlanningViewHolder {
        val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.planning_item_row, parent, false)

        return PlanningViewHolder(inflater)
    }

    override fun getItemCount(): Int = customersList.size

    override fun onBindViewHolder(holder: PlanningViewHolder, position: Int) {
        holder.fullname.text = customersList[position].fullName

        // Speciality
        if (customersList[position].speciality == null)
            holder.speciality.text = customersList[position].specialityId.toString()
        else
            holder.speciality.text = customersList[position].speciality.name

        // Customer status
        if (customersList[position].customerStatus == null)
            holder.status.text = customersList[position].customerStatusId.toString()
        else
            holder.status.text = customersList[position].customerStatus.name

        Glide.with(context).load(customersList[position].defaultAvatar).into(holder.picture)
    }

    fun getClient(position: Int): Customer = customersList[position]

    companion object {
        const val TAG = "PlanningAdapter"
    }
}