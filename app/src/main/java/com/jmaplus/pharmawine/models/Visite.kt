package com.jmaplus.pharmawine.models

class Visite {
    var startTime: String = ""
    var endTime: String = ""
    var zone: String = ""

    /**
     * objectifs de la visite
     */
    var purposeOfVisit: String = ""

    /**
     * Promesses tenues
     */
    var promesesHeld: String = ""

    /**
     * prescriptions constatees
     */
    var prescribedRequirements: String = ""

    /**
     *  permet de faire la difference entre un prospect connu et inconnu
     */
    var isForKnownProspect: Boolean = true

    /**
     * contiendra des donnees uniquement si la propriete isForKnownProspect est a true
     */
    var client: Client = Client()

    /**
     * Return true if all required data about visit report
     * is filled
     */
//    fun isCompleted(): Boolean = (!zone.isNullOrEmpty()
//            && !purposeOfVisit.isNullOrEmpty() && !promesesHeld.isNullOrEmpty()
//            && !prescribedRequirements.isNullOrEmpty() && !startTime.isNullOrEmpty())


    fun isCompleted(): Boolean = true

    override fun toString(): String {
        return "zone: $zone, purposeOfVisit: $purposeOfVisit, promesesHeld: $promesesHeld, " +
                "prescribedRequirements: $prescribedRequirements" +
                "isKnownProspect : $isForKnownProspect, startTime : $startTime, endTime : $endTime, client : $client";
    }
}