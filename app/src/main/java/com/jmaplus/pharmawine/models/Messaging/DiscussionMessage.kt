package com.jmaplus.pharmawine.models.Messaging


data class DiscussionMessage(
        val fireMessage: FireMessage = FireMessage(),
        val fromMe: Boolean = false
)