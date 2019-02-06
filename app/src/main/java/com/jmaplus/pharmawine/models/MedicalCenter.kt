package com.jmaplus.pharmawine.models

/**
 * Represente le centre d'un medecin
 */
class MedicalCenter {
    var id: String = ""

    var name: String = ""

    var zone: String = ""

    override fun toString(): String {
        return "\n id -> $id" +
                "\n name -> $name" +
                "\n zone -> $zone"
    }
}