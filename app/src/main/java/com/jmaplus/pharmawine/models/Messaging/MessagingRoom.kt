package com.jmaplus.pharmawine.models.Messaging


/**
 * Represents a room on Messaging Fragment
 */
data class MessagingRoom(
        val username: String = "",
        val lastMessage: FireMessage = FireMessage(),
        val userId: String = "", // id of second user
        val roomId: String = "",
        val avatarUrl: String = "",
        val role: Int = 0
)