package com.jmaplus.pharmawine.utils

import com.jmaplus.pharmawine.models.Client

class MockDatas {

    companion object {
        private const val TAG = "MockDatas"

        fun getOneFakeClient(): Client {
            var client = Client()

            return client
        }

        fun getFakeClients(): MutableList<Client> {
            var clientsList: MutableList<Client> = ArrayList()

            var c1 = Client()
            c1.firstName = "Bounou"
            c1.lastName = "Honorat"
            c1.sex = "M"
            c1.speciality = "Médecin généraliste"
            c1.status = "PCM"

            var c2 = Client()
            c2.firstName = "Baba"
            c2.lastName = "Pauline"
            c2.sex = "F"
            c2.speciality = "Dermatologue"
            c2.status = "CFG"

            var c3 = Client()
            c3.firstName = "Afon"
            c3.lastName = "Gaston"
            c3.sex = "M"
            c3.speciality = "Ophtamologue"
            c3.status = "CFM"

            var c4 = Client()
            c4.firstName = "Bignon"
            c4.lastName = "Patricia"
            c4.sex = "F"
            c4.speciality = "Pédiatre"
            c4.status = "PCM"

            clientsList.add(c1)
            clientsList.add(c2)
            clientsList.add(c3)
            clientsList.add(c4)

            return clientsList
        }
    }

}