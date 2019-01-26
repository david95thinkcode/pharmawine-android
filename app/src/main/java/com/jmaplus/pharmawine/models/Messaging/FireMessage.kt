package com.jmaplus.pharmawine.models.Messaging

import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

@IgnoreExtraProperties
data class FireMessage(
        var from: String = "",
        var to: String = "",
        var content: String = "",
        var type: String = "text",
        var createdAt: String = Date().time.toString(),
        var status: String = "pending",
        var isRead: Boolean = false,
        var isDeletedByFrom: Boolean = false,
        var isDeletedByTo: Boolean = false,
        var id: String = ""
)
