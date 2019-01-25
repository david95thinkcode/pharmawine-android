package com.jmaplus.pharmawine.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Customer
import de.hdodenhof.circleimageview.CircleImageView

class RemainingCustomersAdapter(val context: Context, var remainingClientsList: MutableList<Customer>) :
        RecyclerView.Adapter<RemainingCustomersAdapter.RemainingCustomersViewHolder>() {

    class RemainingCustomersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val picture = itemView.findViewById<CircleImageView>(R.id.img_client)
        val fullname = itemView.findViewById<TextView>(R.id.tv_client_fullname)
        val speciality = itemView.findViewById<TextView>(R.id.tv_client_speciality)
        val status = itemView.findViewById<TextView>(R.id.tv_client_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RemainingCustomersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.client_row_without_progression, parent, false)

        return RemainingCustomersViewHolder(inflater)
    }

    override fun getItemCount(): Int = remainingClientsList.size

    override fun onBindViewHolder(holder: RemainingCustomersViewHolder, position: Int) {
        holder.fullname.text = remainingClientsList[position].fullName

        // Speciality
        if (remainingClientsList[position].speciality == null)
            holder.speciality.text = remainingClientsList[position].specialityId.toString()
        else
            holder.speciality.text = remainingClientsList[position].speciality.name

        // Customer status
        if (remainingClientsList[position].customerStatus == null)
            holder.status.text = remainingClientsList[position].customerStatusId.toString()
        else
            holder.status.text = remainingClientsList[position].customerStatus.name

        // profile picture setter
        if (!remainingClientsList[position].avatar.isNullOrEmpty()) {
            Glide.with(context).load(remainingClientsList[position].avatar).into(holder?.picture)
        } else {
            // Avatar url is empty
            if (remainingClientsList[position].sex == "M" || remainingClientsList[position].sex == "m") {
                Glide.with(context).load(R.drawable.ic_ast_man).into(holder?.picture)
            } else {
                // woman case
                Glide.with(context).load(R.drawable.ic_ast_woman).into(holder?.picture)
            }
        }
    }

    fun getClient(position: Int): Customer = remainingClientsList[position]

    companion object {
        const val TAG = "RemainingClientAdapter"
    }
}