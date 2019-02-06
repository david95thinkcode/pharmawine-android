package com.jmaplus.pharmawine.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jmaplus.pharmawine.R
import de.hdodenhof.circleimageview.CircleImageView

class MessageFromUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val content = itemView.findViewById<TextView>(R.id.item_to_text_view)
    val date = itemView.findViewById<TextView>(R.id.item_date_time_text_view)
    val picture = itemView.findViewById<CircleImageView>(R.id.item_from_user_picture)


}