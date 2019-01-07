package com.jmaplus.pharmawine.models

class Client {
    var id: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var sex: String = ""
    var avatarUrl: String = ""
    var birthday: String = ""
    var address: String = ""
    var email: String = ""
    var preference: String = ""
    var type: String = ""
    var speciality: String = ""
    var status: String = ""

    fun getFullName(): String = "$firstName $lastName"

//    fun getDetailsFromServer() {
//
//    }
//
//    fun getDetailsFromLocalStorage() {
//
//    }

    override fun toString(): String {
        return "[id = $id, firstname = $firstName, lastname = $lastName, sex = $sex, avatarUrl = $avatarUrl " +
                "birthday = $birthday, email = $email, type = $type, status = $status, speciality = $speciality]"
    }
}