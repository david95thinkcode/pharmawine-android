package com.jmaplus.pharmawine.models

import android.util.Log
import com.jmaplus.pharmawine.R

class Client {
    var id: String = ""
    var firstname: String = ""
    var lastname: String = ""
    var sex: String = ""
    var avatar: String = ""
    var birthday: String = ""
    var nationality: String = ""
    var address: String = ""
    var email: String = ""
    var preference: String = ""
    var maritalStatus: String = ""
    var religion: String = ""
    var type: String = ""
    var phoneNumber: String = ""
    var phoneNumber2: String = ""
    var speciality: String = ""
    var status: String = ""
    var isKnown: Boolean = false // a true pour les clients connus et a false sinon

    fun getFullName(): String = "$firstname $lastname"

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
        val totalFieldNumber = 13
        var filledFieldNumber = 0

        if (!this.lastname.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "lastname IS NOT NULL OR EMPTY : $lastname")
        }
        if (!this.firstname.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "firstname IS NOT NULL OR EMPTY : $firstname")
        }
        if (!this.sex.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "sex IS NOT NULL OR EMPTY : $sex")
        }
        if (!this.birthday.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "birthday IS NOT NULL OR EMPTY : $birthday")
        }
        if (!this.maritalStatus.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "maritalStatus IS NOT NULL OR EMPTY : $maritalStatus")
        }
        if (!this.phoneNumber.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "phoneNumber IS NOT NULL OR EMPTY : $phoneNumber")
        }
        if (!this.phoneNumber2.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "phoneNumber2 IS NOT NULL OR EMPTY : $phoneNumber2")
        }
        if (!this.address.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "address IS NOT NULL OR EMPTY : $address")
        }
        if (!this.email.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "email IS NOT NULL OR EMPTY : $email")
        }
        if (!this.avatar.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "avatar IS NOT NULL OR EMPTY : $avatar")
        }
        if (!this.nationality.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "nationality IS NOT NULL OR EMPTY : $nationality")
        }
        if (!this.speciality.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "speciality IS NOT NULL OR EMPTY : $speciality")
        }
        if (!this.religion.isNullOrEmpty()) {
            filledFieldNumber++
            Log.i("Client", "religion IS NOT NULL OR EMPTY : $religion")
        }

        var level = ((filledFieldNumber.toDouble() / totalFieldNumber) * 100).toInt()

        return level
    }

    override fun toString(): String {
        return "" +
                "\n id = $id, " +
                "\n filling level = ${getFillingLevel()}" +
                "\n firstname = $firstname, " +
                "\n lastname = $lastname, " +
                "\n sex = $sex, " +
                "\n avatar = $avatar " +
                "\n birthday = $birthday, " +
                "\n email = $email, " +
                "\n type = $type, " +
                "\n religion = $religion" +
                "\n marital status = $maritalStatus" +
                "\n phone number 1 = $phoneNumber" +
                "\n phone number 2 = $phoneNumber2" +
                "\n nationality = $nationality" +
                "\n address = $address" +
                "\n status = $status, " +
                "\n speciality = $speciality"
    }
}