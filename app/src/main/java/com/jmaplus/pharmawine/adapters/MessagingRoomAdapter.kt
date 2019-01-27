package com.jmaplus.pharmawine.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Messaging.MessagingRoom
import java.util.*

class MessagingRoomAdapter(val roomList: MutableList<MessagingRoom>, val mContext: Context,
                           val currentUserID: String
) :
        RecyclerView.Adapter<MessagingRoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagingRoomViewHolder {
        val v = LayoutInflater.from(parent?.context)
                .inflate(R.layout.fragment_messaging_room_item, parent, false)

        return MessagingRoomViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessagingRoomViewHolder, position: Int) {

        // Ici on defini les valeurs qui a mettre dans chaque element du layout d'un room
        holder?.roomLastMessage.text = roomList[position].lastMessage.content
        holder?.roomName.text = roomList[position].username

        if (!roomList[position].lastMessage.isRead && roomList[position].lastMessage.to == currentUserID)
            holder?.notification.visibility = View.VISIBLE
        else
            holder?.notification.visibility = View.GONE

        // Image de profile
        if (roomList[position].avatarUrl.isNullOrEmpty()) {
            Glide.with(mContext)
                    .load(R.drawable.ast_contacts)
                    .apply { RequestOptions.centerInsideTransform() }
                    .into(holder?.picture)
        } else {
            Glide.with(mContext)
                    .load(roomList[position].avatarUrl)
                    .apply { RequestOptions.centerInsideTransform() }
                    .into(holder?.picture)
        }

        // Last message time
        try {
            var t = roomList[position].lastMessage.createdAt.toLong()
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = t

            if (!DateUtils.isToday(t))
                holder?.roomLastMessageTime.text =
                        DateUtils.getRelativeTimeSpanString(t, Date().time, DateUtils.MINUTE_IN_MILLIS).toString()
            else
                holder?.roomLastMessageTime.text = DateFormat.format("hh:mm a", cal).toString()
        } catch (e: Exception) {
            holder?.roomLastMessageTime.text = roomList[position].lastMessage.createdAt
        }

    }

    fun getRoom(position: Int): MessagingRoom = roomList[position]

    override fun getItemCount(): Int = roomList.size


}