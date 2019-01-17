package com.jmaplus.pharmawine.utils;

import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.models.Bonus;
import com.jmaplus.pharmawine.models.Center;
import com.jmaplus.pharmawine.models.Client;
import com.jmaplus.pharmawine.models.Gift;
import com.jmaplus.pharmawine.models.User;
import com.jmaplus.pharmawine.models.Wholesaler;

import java.util.ArrayList;

import static com.jmaplus.pharmawine.PharmaWine.mFaker;

public class FakeData {

    //    Wholesalers
    public static ArrayList<Wholesaler> getWholesalers() {

        ArrayList<Wholesaler> wholesalers = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            wholesalers.add(new Wholesaler(i, mFaker.company.name(), mFaker.company.logo()));
        return wholesalers;
    }

    //    Gifts
    public static ArrayList<Gift> getGifts() {
        ArrayList<Gift> gifts = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            gifts.add(new Gift(i, mFaker.name.title(), mFaker.number.between(20000, 10000000), mFaker.number.between(1, 10), mFaker.bool.bool() ? Gift.MEDICAL_TEAM : Gift.PHARMACY, mFaker.date.backward().toString()));
        return gifts;
    }

    //    Bonuses
    public static ArrayList<Bonus> getBonuses() {
        ArrayList<Bonus> bonuses = new ArrayList<>();
        for (int i = 1; i <= 10; i++)
            bonuses.add(new Bonus(i, mFaker.name.title(), mFaker.number.between(20000, 10000000), mFaker.number.between(1, 10), mFaker.date.backward().toString()));
        return bonuses;
    }

    //    Users
    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            users.add(new User(i, mFaker.name.lastName(), mFaker.name.firstName(), mFaker.avatar.image(),
                    (new String[]{User.MEDICAL_DELEGATE, User.MEDICAL_SUPERVISOR, User.PHARMACEUTICAL_DELEGATE, User.PHARMACEUTICAL_SUPERVISOR, User.ADMINISTRATOR})[mFaker.number.between(0, 4)],
                    mFaker.number.between(20, 100), mFaker.date.backward().toString()));

        }
        return users;
    }

    public static ArrayList<Client> getMedicalTeamClients() {

        ArrayList<Client> customers = new ArrayList<>();

        for (Integer i = 1; i <= 10; i++) {
            Client client = new Client();

            client.setId(i.toString());
            client.setFirstName(mFaker.name.firstName());
            client.setLastName(mFaker.name.lastName());
            client.setSex("m");
            client.setSpeciality(mFaker.lorem.characters(8) + "" + mFaker.lorem.characters(12));
            client.setStatus(mFaker.lorem.characters(3).toUpperCase());
            client.setKnown(mFaker.bool.bool());
            client.setEmail(mFaker.name.name().concat("@").concat(mFaker.lorem.characters(5)).concat(mFaker.lorem.characters(2)));
            client.setBirthday(mFaker.date.birthday().toString());
            client.setPhoneNumber(mFaker.phoneNumber.phoneNumber());
            client.setPhoneNumber2(mFaker.phoneNumber.phoneNumber());
            client.setMaritalStatus("Marié");
            client.setNationality("Béninoise");
            client.setType(Constants.CLIENT_MEDICAL_TEAM_TYPE_KEY);

            customers.add(client);
        }

        return customers;
    }

    public static ArrayList<Center> getCenters() {
        ArrayList<Center> centers = new ArrayList<>();

        for (Integer i = 1; i <= 5; i++) {
            Center c = new Center();

            c.setId(i.toString());
            c.setName(mFaker.company.name());
            c.setZone(mFaker.address.city());
            centers.add(c);
        }

        return centers;

    }

    //    Connected User
    public static AuthenticatedUser getUserInfos() {
        return new AuthenticatedUser();
    }


    //
}
