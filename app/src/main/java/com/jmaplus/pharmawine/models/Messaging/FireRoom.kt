package com.jmaplus.pharmawine.models.Messaging


//import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

/**
 * Represents a room in firebase database
 */
@IgnoreExtraProperties
data class FireRoom(
    val first: String = "",
    val second: String = "",
    val createdAt: String = Date().time.toString(),
    val lastMessage: FireMessage = FireMessage(),
    val id: String = ""
)
