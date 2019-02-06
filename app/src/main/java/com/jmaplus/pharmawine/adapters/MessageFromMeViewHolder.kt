package com.jmaplus.pharmawine.adapters


import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jmaplus.pharmawine.R
import de.hdodenhof.circleimageview.CircleImageView

class MessageFromMeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val content = itemView.findViewById<TextView>(R.id.item_content_from_text_view)
    val datetime = itemView.findViewById<TextView>(R.id.message_from_me_item_datetime)
    val picture = itemView.findViewById<CircleImageView>(R.id.message_from_me_item_picture)
}