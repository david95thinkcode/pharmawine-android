package com.jmaplus.pharmawine.utils;

import com.jmaplus.pharmawine.models.AuthenticatedUser;
import com.jmaplus.pharmawine.models.Bonus;
import com.jmaplus.pharmawine.models.Gift;
import com.jmaplus.pharmawine.models.Laboratory;
import com.jmaplus.pharmawine.models.MedicalTeam;
import com.jmaplus.pharmawine.models.Pharmacy;
import com.jmaplus.pharmawine.models.Product;
import com.jmaplus.pharmawine.models.ProductCategory;
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

    //    Connected User
    public static AuthenticatedUser getUserInfos() {
        return new AuthenticatedUser();
    }
}
