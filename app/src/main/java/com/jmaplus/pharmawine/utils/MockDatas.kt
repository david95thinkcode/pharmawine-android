package com.jmaplus.pharmawine.utils

import com.jmaplus.pharmawine.models.Client

class MockDatas {

    companion object {
        private const val TAG = "MockDatas"

        fun getOneFakeClient(): Client {
            var client = Client()

            client.id = 2.toString()
            client.firstName = "Baba"
            client.lastName = "Pauline"
            client.sex = "F"
            client.speciality = "Dermatologue"
            client.status = "CFG"
            client.isKnown = true
            client.phoneNumber = "+22966843445"
            client.maritalStatus = "Marié"
            client.type = Constants.CLIENT_PHARMACY_TYPE_KEY

            return client
        }

        fun getFakeClients(): MutableList<Client> {
            var clientsList: MutableList<Client> = ArrayList()

            var c1 = Client()
            c1.id = 1.toString()
            c1.firstName = "Bounou"
            c1.lastName = "Honorat"
            c1.sex = "M"
            c1.speciality = "Médecin généraliste"
            c1.status = "PCM"
            c1.phoneNumber = "+22966843445"
            c1.type = Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY

            var c2 = Client()
            c2.id = 2.toString()
            c2.firstName = "Baba"
            c2.lastName = "Pauline"
            c2.sex = "F"
            c2.speciality = "Dermatologue"
            c2.status = "CFG"
            c2.isKnown = true
            c2.phoneNumber = "+22966843445"
            c2.type = Constants.CLIENT_PHARMACY_TYPE_KEY

            var c3 = Client()
            c3.id = 3.toString()
            c3.firstName = "Afon"
            c3.lastName = "Gaston"
            c3.sex = "M"
            c3.speciality = "Ophtamologue"
            c3.status = "CFM"
            c3.isKnown = true
            c3.phoneNumber = "+22966843445"
            c3.type = Constants.CLIENT_PHARMACY_TYPE_KEY

            var c4 = Client()
            c4.id = 4.toString()
            c4.firstName = "Bignon"
            c4.lastName = "Patricia"
            c4.sex = "F"
            c4.speciality = "Pédiatre"
            c4.status = "PCM"
            c4.phoneNumber = "+22966843445"
            c3.type = Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY


            clientsList.add(c1)
            clientsList.add(c2)
            clientsList.add(c3)
            clientsList.add(c4)

            return clientsList
        }
    }

}