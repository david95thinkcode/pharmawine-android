package com.jmaplus.pharmawine.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jmaplus.pharmawine.R
import com.jmaplus.pharmawine.models.Messaging.DiscussionMessage
import java.util.*

class DiscussionMessageAdapter(
        val mContext: Context,
        val messageList: MutableList<DiscussionMessage>,
        var fromUserPictureUrl: String
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var from_me = 0
    private var from_user = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent?.context)

        return if (viewType == from_me) {
            val v1 = inflater.inflate(R.layout.item_message_from_me, parent, false)
            MessageFromMeViewHolder(v1)
        } else {
            val v2 = inflater.inflate(R.layout.item_message_from_user, parent, false)
            MessageFromUserViewHolder(v2)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == from_me) {
            val viewHolder = holder as MessageFromMeViewHolder
            configureFromMeViewHolder(viewHolder, position)
        } else {
            val viewHolder = holder as MessageFromUserViewHolder
            configureFromUserViewHolder(viewHolder, position)
        }
    }

    /**
     * Configure content data for MessageFromMeViewHolder
     */
    private fun configureFromMeViewHolder(holderFromMe: MessageFromMeViewHolder, position: Int) {
        holderFromMe?.content.text = messageList[position].fireMessage.content
        holderFromMe?.datetime.text = getReadableDate(messageList[position].fireMessage.createdAt)

        // picture

        Glide.with(mContext)
                .load(R.drawable.ast_contacts)
                .apply { RequestOptions.centerInsideTransform() }
                .into(holderFromMe?.picture)


    }

    /**
     * Return the date as understanble format for humans
     */
    private fun getReadableDate(timestamps: String): String {
        try {
            var t = timestamps.toLong()
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = t

            return if (!DateUtils.isToday(t)) {
                DateUtils.getRelativeTimeSpanString(t, Date().time, DateUtils.MINUTE_IN_MILLIS).toString()
            } else {
                DateFormat.format("hh:mm a", cal).toString()
            }
        } catch (e: Exception) {
            return timestamps
        }

    }

    /**
     * Configure content data for MessageFromUserViewHolder
     */
    private fun configureFromUserViewHolder(holderFromUser: MessageFromUserViewHolder, position: Int) {
        holderFromUser?.content.text = messageList[position].fireMessage.content
        holderFromUser?.date.text = getReadableDate(messageList[position].fireMessage.createdAt)

        if (!fromUserPictureUrl.isEmpty() && fromUserPictureUrl != "") {
            Glide.with(mContext)
                    .load(fromUserPictureUrl)
                    .apply { RequestOptions.centerInsideTransform() }
                    .into(holderFromUser?.picture)
        } else {
            Glide.with(mContext)
                    .load(R.drawable.ast_contacts)
                    .apply { RequestOptions.centerInsideTransform() }
                    .into(holderFromUser?.picture)
        }

    }

    override fun getItemViewType(position: Int): Int {

        return if (messageList[position].fromMe) {
            from_me
        } else {
            from_user
        }
    }

    override fun getItemCount(): Int = messageList.size
}