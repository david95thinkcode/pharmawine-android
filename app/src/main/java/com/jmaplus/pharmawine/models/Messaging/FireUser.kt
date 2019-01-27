package com.jmaplus.pharmawine.models.Messaging


/**
 * Represents user model inside Firebase Database
 */
data class FireUser(
        var id: String = "",
        var firstname: String = "",
        var lastName: String = "",
        var avatarUrl: String = "",
        var role: Int = 0
)