package com.jmaplus.pharmawine.models

class Visite {
    var startTime: String = ""
    var endTime: String = ""
    var zone: String = ""

    /**
     * Centre
     */
    var center: String = ""

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
    var client: Customer = Customer()

    /**
     * Return true if all required data about visit report
     * is filled
     */
    fun isCompleted(): Boolean = (!zone.isNullOrEmpty()
            && !purposeOfVisit.isNullOrEmpty() && !promesesHeld.isNullOrEmpty()
            && !prescribedRequirements.isNullOrEmpty() && !startTime.isNullOrEmpty())


    override fun toString(): String {
        return "\n centre: $center," +
                "\n zone: $zone," +
                "\n purposeOfVisit: $purposeOfVisit," +
                "\n promesesHeld: $promesesHeld," +
                "\n prescribedRequirements: $prescribedRequirements," +
                "\n isKnownProspect : $isForKnownProspect," +
                "\n startTime : $startTime," +
                "\n endTime : $endTime," +
                "\n client : $client"
    }
}