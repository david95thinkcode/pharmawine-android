package com.jmaplus.pharmawine.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.jmaplus.pharmawine.R


class MessagingRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val picture = itemView.findViewById<ImageView>(R.id.fragment_messaging_room_item_picture)
    val roomName = itemView.findViewById<TextView>(R.id.fragment_messaging_room_item_title)
    val roomLastMessage = itemView.findViewById<TextView>(R.id.fragment_messaging_room_item_subtitle)
    val roomLastMessageTime = itemView.findViewById<TextView>(R.id.fragment_messaging_room_item_hour)
    val notification = itemView.findViewById<Button>(R.id.new_message_image_btn)
}