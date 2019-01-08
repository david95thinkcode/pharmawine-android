package com.jmaplus.pharmawine.models

import com.jmaplus.pharmawine.R

class Client {
    var id: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var sex: String = ""
    var avatarUrl: String = ""
    var birthday: String = ""
    var nationality: String = ""
    var address: String = ""
    var email: String = ""
    var preference: String = ""
    var maritalStatus: String = ""
    var type: String = ""
    var phoneNumber: String = ""
    var phoneNumber2: String = ""
    var speciality: String = ""
    var status: String = ""
    var isKnown: Boolean = false // a true pour les clients connus et a false sinon

    fun getFullName(): String = "$firstName $lastName"

//    fun getDetailsFromServer() {
//
//    }
//
//    fun getDetailsFromLocalStorage() {
//
//    }

    fun getDefaultAvatarUrl(): Int {
        return if (this.sex == "m" || this.sex == "M") R.drawable.ic_ast_man
        else R.drawable.ic_ast_woman
    }

    fun getRedabledate(): String {
        // TODO: Format to readable date and return it instead of default brithday returned
//        tvBirthday.setText(client.birthday.split("-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[1] + "/" + client.birthday.split("-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[2] + "/" + client.birthday.split("-".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0])
        return birthday
    }

    fun getFillingLevel(): Int {
        val totalFieldNumber = 12
        var filledFieldNumber = 0

        if (this.lastName != null || this.lastName.isEmpty()) {
            filledFieldNumber++
        }
        if (this.firstName != null || this.firstName.isEmpty()) {
            filledFieldNumber++
        }
        if (this.sex != null || this.sex.isEmpty()) {
            filledFieldNumber++
        }
        if (this.birthday != null || this.birthday.isEmpty()) {
            filledFieldNumber++
        }
        if (this.maritalStatus != null || this.maritalStatus.isEmpty()) {
            filledFieldNumber++
        }
        if (this.phoneNumber != null || this.phoneNumber.isEmpty()) {
            filledFieldNumber++
        }
        if (this.phoneNumber2 != null || this.phoneNumber2.isEmpty()) {
            filledFieldNumber++
        }
        if (this.address != null || this.address.isEmpty()) {
            filledFieldNumber++
        }
        if (this.email != null || this.email.isEmpty()) {
            filledFieldNumber++
        }
        if (this.avatarUrl != null || this.avatarUrl.isEmpty()) {
            filledFieldNumber++
        }
        if (this.nationality != null || this.nationality.isEmpty()) {
            filledFieldNumber++
        }
        if (this.speciality != null && !this.speciality.isEmpty()) {
            filledFieldNumber++
        }

        return filledFieldNumber / totalFieldNumber * 100
    }

    override fun toString(): String {
        return "[id = $id, firstname = $firstName, lastname = $lastName, sex = $sex, avatarUrl = $avatarUrl " +
                "birthday = $birthday, email = $email, type = $type, status = $status, speciality = $speciality]"
    }
}