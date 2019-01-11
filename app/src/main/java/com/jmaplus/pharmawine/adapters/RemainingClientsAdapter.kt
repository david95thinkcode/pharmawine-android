package com.jmaplus.pharmawine.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Client
import de.hdodenhof.circleimageview.CircleImageView

class RemainingClientsAdapter(val context: Context, var remainingClientsList: MutableList<Client>) :
        RecyclerView.Adapter<RemainingClientsAdapter.RemainingClientsViewHolder>() {

    class RemainingClientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val picture = itemView.findViewById<CircleImageView>(R.id.img_client)
        val fullname = itemView.findViewById<TextView>(R.id.tv_client_fullname)
        val speciality = itemView.findViewById<TextView>(R.id.tv_client_speciality)
        val status = itemView.findViewById<TextView>(R.id.tv_client_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RemainingClientsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.client_row_without_progression, parent, false)

        return RemainingClientsViewHolder(inflater)
    }

    override fun getItemCount(): Int = remainingClientsList.size

    override fun onBindViewHolder(holder: RemainingClientsViewHolder, position: Int) {
        holder?.fullname.text = remainingClientsList[position].getFullName()
        holder?.speciality.text = remainingClientsList[position].speciality
        holder?.status.text = remainingClientsList[position].status

        // profile picture setter
        if (!remainingClientsList[position].avatarUrl.isNullOrEmpty()) {
            Glide.with(context).load(remainingClientsList[position].avatarUrl).into(holder?.picture)
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

    fun getClient(position: Int): Client = remainingClientsList[position]

    companion object {
        const val TAG = "RemainingClientAdapter"
        const val useMockedData = true
    }
}