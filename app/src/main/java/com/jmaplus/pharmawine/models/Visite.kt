package com.jmaplus.pharmawine.models

class Visite {
    var startTime: String = ""
    var endTime: String = ""
    var isForKnownProspect: Boolean = true // permet de faire la difference entre un prospect connu et inconnu
    var client: Client = Client() // contiendra des donnees uniquement si la propriete isForKnownProspect est a true

    override fun toString(): String {
        return "isKnownProspect : $isForKnownProspect, startTime : $startTime, endTime : $endTime, client : $client";
    }
}